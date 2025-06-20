package net.terullet.bibliothekarios.arachne.domain;

public class AccessRestrictedException extends RuntimeException {
	public AccessRestrictedException(String message) {
		super(message);
	}
}
