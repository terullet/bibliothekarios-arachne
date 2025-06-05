package net.terullet.bibliothekarios.arachne.web.com.syosetu.ncode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletionStage;

public class NarouNcodeFetcher {
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

	public CompletionStage<Boolean> login(String userid, String password, boolean enableAutoLogin) throws IOException, InterruptedException {
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
}
