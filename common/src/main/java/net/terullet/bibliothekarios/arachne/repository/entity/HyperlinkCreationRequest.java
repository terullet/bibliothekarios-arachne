package net.terullet.bibliothekarios.arachne.repository.entity;

import java.net.URL;

public class HyperlinkCreationRequest {
	private Long id;
	private final URL link;
	private final String description;

	public HyperlinkCreationRequest(URL link, String description) {
		this.link = link;
		this.description = description;
	}

	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLink() {
		return this.link.toExternalForm();
	}
	public String getDescription() {
		return this.description;
	}
}
