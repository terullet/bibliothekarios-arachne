package net.terullet.bibliothekarios.arachne.domain.web.com.syosetu;

public enum NarouSpecificKeywords {
	R15("r15", "R15"),
	BOYS_LOVE("bl", "ボーイズラブ"),
	GIRLS_LOVE("gl", "ガールズラブ"),
	CRUEL("zankoku", "残酷な描写あり"),
	REINCARNATION("tensei", "異世界転生"),
	TELEPORTATION("tenni", "異世界転移");

	private final String key;
	private final String description;
	NarouSpecificKeywords(String key, String description) {
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
