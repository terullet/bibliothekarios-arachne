package net.terullet.net;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class URLEncoder {
	private static final Map<String, String> MAPPER = new HashMap<>() {
		{
			put(".", "%2E");
			put("-", "%2D");
			put("*", "%2A");
			put("_", "%5F");
			put("+", "%20");
		}
	};

	private static String postProcess(String s) {
		for (Map.Entry<String, String> kvp : MAPPER.entrySet()) {
			s = s.replace(kvp.getKey(), kvp.getValue());
		}
		return s;
	}

	public static String encode(String s, Charset charset) {
		return postProcess(java.net.URLEncoder.encode(s, charset));
	}
}
