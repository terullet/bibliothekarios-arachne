package net.terullet.bibliothekarios.arachne.domain;

public class ExhaustedRetryException extends RuntimeException {
	public ExhaustedRetryException(Throwable cause) {
		super(cause);
	}
	public ExhaustedRetryException(String message, Throwable cause) {
		super(message, cause);
	}
}
