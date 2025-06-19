package net.terullet.bibliothekarios.arachne.repository.entity.web;

public final class ParagraphContentEntity {
	private final long id;
	private final String content;

	public ParagraphContentEntity(long id, String content) {
		this.id = id;
		this.content = content;
	}

	public long getId() {
		return this.id;
	}
	public String getContent() {
		return this.content;
	}
}
