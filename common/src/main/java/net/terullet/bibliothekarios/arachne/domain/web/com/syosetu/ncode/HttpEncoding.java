package net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.ncode;

public enum HttpEncoding {
	GZIP("gzip"),
	DEFLATE("deflate");
	private final String key;
	public final String getKey() {
		return this.key;
	}
	HttpEncoding(String key) {
		this.key = key;
	}
	public static HttpEncoding fromKey(String key) {
		for (HttpEncoding e : HttpEncoding.values()) {
			if (e.getKey().equals(key)) {
				return e;
			}
		}
		return null;
	}
}
