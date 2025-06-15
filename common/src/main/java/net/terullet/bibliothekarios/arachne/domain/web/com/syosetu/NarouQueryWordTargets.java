package net.terullet.bibliothekarios.arachne.domain.web.com.syosetu;

public enum NarouQueryWordTargets {
	TITLE("title", "タイトル"),
	SUMMARY("ex", "あらすじ"),
	KEYWORDS("keyword", "キーワード"),
	WRITER("wname", "作者名");

	private final String key;
	private final String description;
	NarouQueryWordTargets(String key, String description) {
		this.key = key;
		this.description = description;
	}
	public final String getKey() {
		return this.key;
	}
	public final String getDescription() {
		return this.description;
	}
}
