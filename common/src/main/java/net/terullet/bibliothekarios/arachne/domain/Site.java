package net.terullet.bibliothekarios.arachne.domain;

public enum Site {
	PRINTED_BOOK("書籍"),
	NAROU_ALL_AGES("小説家になろう"),
	NAROU_R18("小説家になろう（R18）"),
	;
	private final String description;
	Site(String description) {
		this.description = description;
	}
	public String getDescription() {
		return this.description;
	}
}
