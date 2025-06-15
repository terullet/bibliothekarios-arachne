package net.terullet.bibliothekarios.arachne.domain.web.com.syosetu;

public enum NarouWorkType {
	CURRENTLY_SERIALIZED("連載中"),
	SERIES_ENDED("完結済"),
	STANDALONE("短編");

	private final String description;
	NarouWorkType(String description) {
		this.description = description;
	}
	@Override
	public final String toString() {
		return this.description;
	}
}
