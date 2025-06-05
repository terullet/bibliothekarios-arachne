package net.terullet.bibliothekarios.arachne.web.com.syosetu.ncode;

public class ExhaustedRetryException extends RuntimeException {
	public ExhaustedRetryException(Throwable cause) {
		super(cause);
	}
	public ExhaustedRetryException(String message, Throwable cause) {
		super(message, cause);
	}
}
