package net.terullet.bibliothekarios.arachne.web.com.syosetu.ncode;

import net.terullet.bibliothekarios.arachne.RequestSource;
import net.terullet.bibliothekarios.arachne.RequestStatus;
import net.terullet.bibliothekarios.arachne.web.com.syosetu.NarouEpisode;
import net.terullet.bibliothekarios.arachne.web.com.syosetu.NarouWork;
import net.terullet.util.concurrent.NamedThreadFactory;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.*;

public class NarouNcodeManager {
	private final NarouNcodeFetcher fetcher;
	private final NarouNcodeParser parser;
	private static final ExecutorService executor = Executors.newCachedThreadPool(new NamedThreadFactory("NarouNcodeManager"));

	public void initialize(HttpClient httpClient) {
		NarouNcodeFetcher.initialize(httpClient);
	}

	public NarouNcodeManager() {
		this.fetcher = NarouNcodeFetcher.getInstance();
		this.parser = new NarouNcodeParser();
	}

	public CompletionStage<Boolean> loginAsync(String userid, String password, boolean enablesAutoLogin) {
		return this.fetcher.loginAsync(userid, password, enablesAutoLogin);
	}

	public CompletionStage<List<NarouEpisodeResponseMetadata>> fetchEpisodeListAsync(NarouWork work, RequestSource requestSource) {
		WorkFetchProgress wfp = new WorkFetchProgress(work, requestSource);
		return wfp.targetCompletableFuture;
	}

	public CompletionStage<List<String>> fetchEpisodeContentAsync(NarouEpisode episode, RequestSource requestSource) {
		// TODO: retry.
		return this.fetcher.fetchAsync(episode, requestSource, RequestStatus.ORIGINAL).thenApplyAsync(this.parser::extractToParagraphs, executor);
	}

	private class WorkFetchProgress {
		private final NarouWork work;
		private final RequestSource requestSource;
		final Map<Integer, CompletableFuture<NarouNcodeParser.PageResponse>> pages;
		final CompletableFuture<List<NarouEpisodeResponseMetadata>> targetCompletableFuture;
		final Map<Integer, Throwable> lastExceptions;

		WorkFetchProgress(NarouWork work, RequestSource requestSource) {
			this.work = work;
			this.requestSource = requestSource;
			this.targetCompletableFuture = new CompletableFuture<>();
			this.pages = new TreeMap<>();
			this.lastExceptions = new TreeMap<>();
			this.fetchPageAsync(0, RequestStatus.ORIGINAL);
		}

		private void fetchPageAsync(int pageNumber, RequestStatus requestStatus) {
			// activate fetch.
			CompletableFuture<NarouNcodeParser.PageResponse> completableFuture = fetcher.fetchAsync(this.work, pageNumber, this.requestSource, requestStatus)
					.thenApplyAsync(parser::extractEpisodes, executor);
			this.pages.put(pageNumber, completableFuture);
			// exception handling.
			completableFuture.thenAcceptAsync(this::acceptPage, executor)
					.exceptionallyAsync(ex -> {
						boolean doRetry = false;
						switch (ex) {
							case IOException ioe:
								doRetry = true;
								break;
							case AccessRestrictedException are:
								doRetry = true;
								fetcher.penalty();
								break;
							default:
								break;
						}
						if (doRetry) {
							// retry if (lastException != thisException).
							if (!(this.lastExceptions.containsKey(pageNumber) && this.lastExceptions.get(pageNumber).getClass() == ex.getClass())) {
								this.lastExceptions.put(pageNumber, ex);
								this.fetchPageAsync(pageNumber, RequestStatus.RETRYING);
							} else {
								this.targetCompletableFuture.completeExceptionally(new ExhaustedRetryException(ex));
							}
						} else {
							this.targetCompletableFuture.completeExceptionally(ex);
						}
						return null;
			}, executor);
		}
		private void acceptPage(NarouNcodeParser.PageResponse response) {
			if (response.requestPageNumber() == 0) {
				for (int i = 2; i <= response.lastPageNumber(); i++) {
					this.fetchPageAsync(i, RequestStatus.FOLLOWING);
				}
			} else if (this.pages.values().stream().allMatch(cf -> cf.state() == Future.State.SUCCESS)) {
				// collect pages.
				List<NarouEpisodeResponseMetadata> fullList = new LinkedList<>();
				for (CompletableFuture<NarouNcodeParser.PageResponse> cf : this.pages.values()) {
					fullList.addAll(cf.join().episodes());
				}
				this.targetCompletableFuture.complete(fullList);
			}
		}
	}
}
