package net.terullet.bibliothekarios.arachne.web.com.syosetu.ncode;

import net.terullet.bibliothekarios.arachne.RequestSource;
import net.terullet.bibliothekarios.arachne.RequestStatus;
import net.terullet.bibliothekarios.arachne.web.com.syosetu.NarouEpisodeMetadata;
import net.terullet.bibliothekarios.arachne.web.com.syosetu.NarouR18Work;
import net.terullet.bibliothekarios.arachne.web.com.syosetu.NarouWork;
import net.terullet.bibliothekarios.arachne.web.com.syosetu.NarouWorkType;
import net.terullet.util.concurrent.NamedThreadFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

class NarouNcodeFetcher {
	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());
	private static volatile NarouNcodeFetcher instance;
	public static void initialize(HttpClient httpClient) {
		// ref.) https://mixi-inc.github.io/AndroidTraining/appendix/A.04.advanced-java.html#double-checked-locking
		if (instance == null) {
			synchronized (NarouNcodeFetcher.class) {
				if (instance == null) {
					if (httpClient == null) {
						throw new IllegalArgumentException("httpClient must not be null.");
					}
					instance = new NarouNcodeFetcher(httpClient);
				} else {
					throw new IllegalStateException("Already initialized by another thread.");
				}
			}
		} else {
			throw new IllegalStateException("Already initialized.");
		}
	}
	public static NarouNcodeFetcher getInstance() {
		if (instance == null) {
			throw new IllegalStateException("NarouFetcher is not initialized yet.");
		}
		return instance;
	}
	private NarouNcodeFetcher(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	private final HttpClient httpClient;

	public static final long DELAY_MILLISECONDS = 2500L;
	public static final long DEFAULT_PENALTY_MILLISECONDS = 7500L;
	private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("NarouNcodeFetcher"));
	private ScheduledFuture<?> executorFuture;
	private final Queue<QueueItem> queue = new PriorityQueue<>(32, QueueItem.COMPARATOR);
	private final Object syncObj = new Object();
	private final AtomicLong queueIdGenerator = new AtomicLong(0L);
	private int consecutiveAccessCount = 0;

	public CompletionStage<Boolean> login(String userid, String password, boolean enableAutoLogin) {
		HttpRequest loginRequest = HttpRequest.newBuilder(URI.create("https://syosetu.com/login/login"))
						.header("Content-Type", "application/x-www-form-urlencoded")
						.POST(HttpRequest.BodyPublishers.ofString("narouid=" + userid + "&pass=" + password + "&skip=" + (enableAutoLogin ? '1' : '0')))
						.build();

		CompletionStage<HttpResponse<String>> loginResponse = this.httpClient.sendAsync(loginRequest, HttpResponse.BodyHandlers.ofString());

		return loginResponse.thenApply((lr) -> {
			// TODO: エラーチェック
			// FIXME: ログイン成否判定．（出来ればCookieのSession（ses）を参照したい？）
			if (lr == null) {
				return false;
			}
			if (lr.headers().map().get("location") == null) {
				logger.trace(lr.headers());
				return false;
			}
			logger.info(lr);
			return true;
		});
	}

	private void execute() {
		// get task.
		QueueItem item = null;
		synchronized (this.syncObj) {
			item = queue.poll();
			if (item == null) {
				assert(this.executorFuture != null);
				this.executorFuture.cancel(false);
				this.executorFuture = null;
				this.consecutiveAccessCount = 0;
				return;
			}
		}

		// generate httpRequest.
		HttpRequest  httpRequest;
		switch (item) {
			case WorkQueueItem wqi:
				httpRequest = HttpRequest.newBuilder(URI.create("https://" + (wqi.work() instanceof NarouR18Work ? "novel18" : "ncode") + ".syosetu.com/" + wqi.work().getNcode() + (wqi.pageNumber() == 0 ? "/" : ("/?p=" + wqi.pageNumber())))).build();
				break;
			case EpisodeQueueItem eqi:
				String hostname = "https://" + (eqi.episode().getWork() instanceof NarouR18Work ? "novel18" : "ncode") + "/txtdownload/dlstart/ncode/";
				String ls = switch (System.lineSeparator()) {
					case "\r\n" -> "crlf";
					case "\r" -> "cr";
					default -> "lf";
				};
				if (eqi.episode().getWork().getWorkType() == NarouWorkType.STANDALONE) {
					httpRequest = HttpRequest.newBuilder(URI.create(hostname + eqi.episode().getWork().getId() + "/?hankaku=0&code=utf-8&kaigyo=" + ls)).build();
				} else {
					httpRequest = HttpRequest.newBuilder(URI.create(hostname + eqi.episode().getWork().getId() + "/?no=" + eqi.episode().getEpisodeNumber() + "&hankaku=~&code=utf-8&kaigyo=" + ls)).build();
				}
				break;
		}
		// access.
		try {
			item.complete(this.httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofInputStream()));
		} catch (Throwable ex) {
			item.completeExceptionally(ex);
		}

		// throttling.
		if (++this.consecutiveAccessCount == 10) {
			synchronized (this.syncObj) {
				assert(this.executorFuture != null);
				this.executorFuture.cancel(false);
				this.executorFuture = this.executor.scheduleAtFixedRate(this::execute, DEFAULT_PENALTY_MILLISECONDS, DELAY_MILLISECONDS, TimeUnit.MILLISECONDS);
			}
			this.consecutiveAccessCount = 0;
		}
	}

	private void activate() {
		synchronized (this.syncObj) {
			if (this.executorFuture == null || this.executorFuture.isDone()) {
				this.executorFuture = this.executor.scheduleAtFixedRate(this::execute, 0L, DELAY_MILLISECONDS, TimeUnit.MILLISECONDS);
			}
		}
	}

	CompletionStage<WorkResponse> fetch(NarouWork work, int pageNumber, RequestSource requestSource, RequestStatus requestStatus) {
		CompletableFuture<WorkResponse> completableFuture = new CompletableFuture<>();
		synchronized (this.syncObj) {
			this.queue.offer(new WorkQueueItem(this.queueIdGenerator.incrementAndGet(), work, pageNumber, requestSource, requestStatus, completableFuture));
			this.activate();
		}
		return completableFuture;
	}

	CompletionStage<EpisodeResponse> fetch(NarouEpisodeMetadata episode, RequestSource requestSource, RequestStatus requestStatus) {
		CompletableFuture<EpisodeResponse> completableFuture = new CompletableFuture<>();
		synchronized (this.syncObj) {
			this.queue.offer(new EpisodeQueueItem(this.queueIdGenerator.incrementAndGet(), episode, requestSource, requestStatus, completableFuture));
			this.activate();
		}
		return completableFuture;
	}

	void penalty() {
		synchronized (this.syncObj) {
			if (this.executorFuture != null) {
				this.executorFuture.cancel(false);
				this.executorFuture = this.executor.scheduleAtFixedRate(this::execute, DEFAULT_PENALTY_MILLISECONDS, DELAY_MILLISECONDS, TimeUnit.MILLISECONDS);
			}
		}
	}

	sealed interface Response permits WorkResponse, EpisodeResponse {
		HttpResponse<InputStream> httpResponse();
	}
	record WorkResponse(NarouWork work, int pageNumber, HttpResponse<InputStream> httpResponse) implements Response { }
	record EpisodeResponse(NarouEpisodeMetadata episode, HttpResponse<InputStream> httpResponse) implements Response { }

	private sealed interface QueueItem permits WorkQueueItem, EpisodeQueueItem {
		long id();
		RequestSource requestSource();
		RequestStatus requestStatus();
		boolean complete(HttpResponse<InputStream> httpResponse);
		boolean completeExceptionally(Throwable ex);
		Comparator<QueueItem> COMPARATOR = Comparator
				.comparing(QueueItem::requestSource)
				.thenComparing(QueueItem::requestStatus)
				.thenComparing((a, b) -> {
					if (a.getClass() == b.getClass()) {
						return 0;
					}
					if (a instanceof EpisodeQueueItem) {
						return 1;
					}
					return -1;
				})
				.thenComparing(QueueItem::id);
	}
	private record WorkQueueItem(long id, NarouWork work, int pageNumber, RequestSource requestSource, RequestStatus requestStatus, CompletableFuture<WorkResponse> completableFuture) implements QueueItem {
		public boolean complete(HttpResponse<InputStream> httpResponse) {
			return this.completableFuture.complete(new WorkResponse(this.work, this.pageNumber, httpResponse));
		}
		public boolean completeExceptionally(Throwable ex) {
			return this.completableFuture.completeExceptionally(ex);
		}
	}
	private record EpisodeQueueItem(long id, NarouEpisodeMetadata episode, RequestSource requestSource, RequestStatus requestStatus, CompletableFuture<EpisodeResponse> completableFuture) implements QueueItem {
		public boolean complete(HttpResponse<InputStream> httpResponse) {
			return this.completableFuture.complete(new EpisodeResponse(this.episode, httpResponse));
		}
		public boolean completeExceptionally(Throwable ex) {
			return this.completableFuture.completeExceptionally(ex);
		}
	}
}
