package net.terullet.bibliothekarios.arachne.web.com.syosetu.api;

import net.terullet.bibliothekarios.arachne.RequestSource;
import net.terullet.bibliothekarios.arachne.RequestStatus;

import java.net.http.HttpClient;
import java.util.List;
import java.util.concurrent.CompletionStage;

public class NarouApiManager {
	private final NarouApiFetcher fetcher;
	private final NarouApiParser parser;

	public static void initialize(HttpClient httpClient) {
		NarouApiFetcher.initialize(httpClient);
	}

	public NarouApiManager() {
		this.fetcher = NarouApiFetcher.getInstance();
		this.parser = new NarouApiParser();
	}

	public CompletionStage<List<? extends NarouWorkResponseMetadata>> fetch(NarouApiQuery query, RequestSource requestSource, RequestStatus requestStatus) {
		return this.fetcher.fetch(query, requestSource, requestStatus)
				.thenApplyAsync(this.parser::parse, NarouApiParser.EXECUTOR);
	}
}
