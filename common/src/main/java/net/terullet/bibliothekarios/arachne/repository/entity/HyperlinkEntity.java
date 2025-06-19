package net.terullet.bibliothekarios.arachne.repository.entity;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public final class HyperlinkEntity {
	private final long id;
	private final URL link;
	private final String description;

	public HyperlinkEntity(long id, String link, String description) throws MalformedURLException {
		this.id = id;
		this.link = URI.create(link).toURL();
		this.description = description;
	}

	public long getId() {
		return this.id;
	}
	public URL getLink() {
		return this.link;
	}
	public String getDescription() {
		return this.description;
	}
}
