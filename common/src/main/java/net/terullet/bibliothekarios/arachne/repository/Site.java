package net.terullet.bibliothekarios.arachne.repository;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Site {
	PRINTED_BOOK(0, "書籍"),
	NAROU_ALL_AGES(1, "小説家になろう"),
	NAROU_R18(2, "小説家になろう（R18）");

	private final int id;
	private final String description;

	// パフォーマンス向上のためのキャッシュ
	private static final Map<Integer, Site> ID_SITE_MAP =
			Arrays.stream(values())
					.collect(Collectors.toMap(Site::getId, Function.identity()));

	Site(int id, String description) {
		this.id = id;
		this.description = description;
	}

	public int getId() {
		return this.id;
	}

	public String getDescription() {
		return this.description;
	}

	/**
	 * IDからSite enumを取得します。
	 *
	 * @param id Site ID
	 * @return Site enum value
	 * @throws IllegalArgumentException 無効なIDが指定された場合
	 */
	public static Site fromId(int id) {
		Site site = ID_SITE_MAP.get(id);
		if (site == null) {
			throw new IllegalArgumentException(
					String.format("Invalid Site ID: %d. Valid IDs are: %s",
							id, ID_SITE_MAP.keySet()));
		}
		return site;
	}

	/**
	 * IDからSite enumを取得します（null安全版）。
	 *
	 * @param id Site ID（nullの場合はnullを返す）
	 * @return Site enum value、またはnull
	 * @throws IllegalArgumentException 無効なIDが指定された場合
	 */
	public static Site fromIdNullable(Integer id) {
		return id == null ? null : fromId(id);
	}

	/**
	 * 指定されたIDが有効かどうかを判定します。
	 *
	 * @param id Site ID
	 * @return IDが有効な場合true
	 */
	public static boolean isValidId(int id) {
		return ID_SITE_MAP.containsKey(id);
	}
}
