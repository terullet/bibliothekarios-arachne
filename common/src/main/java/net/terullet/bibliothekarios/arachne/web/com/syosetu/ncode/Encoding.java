package net.terullet.bibliothekarios.arachne.web.com.syosetu.ncode;

public enum Encoding {
	GZIP("gzip"),
	DEFLATE("deflate");
	private final String key;
	public final String getKey() {
		return this.key;
	}
	Encoding(String key) {
		this.key = key;
	}
	public static Encoding fromKey(String key) {
		for (Encoding e : Encoding.values()) {
			if (e.getKey().equals(key)) {
				return e;
			}
		}
		return null;
	}
}
