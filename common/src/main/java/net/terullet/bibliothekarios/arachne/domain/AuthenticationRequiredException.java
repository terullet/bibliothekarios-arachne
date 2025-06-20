package net.terullet.bibliothekarios.arachne.domain;

public class AuthenticationRequiredException extends RuntimeException {
	public AuthenticationRequiredException(String message) {
		super(message);
	}
}
