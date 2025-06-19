package net.terullet.bibliothekarios.arachne.domain.web.com.syosetu;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * R18版なろうのジャンル（掲載サイト）を表す列挙体です
 */
public enum NarouR18Genre implements NarouGenre {
	FOR_MALES(1, "ノクターンノベルズ（男性向け）"),
	FOR_FEMALES(2, "ムーンライトノベルズ（女性向け）"),
	BOYS_LOVE(3, "ムーンライトノベルズ（BL）"),
	FOR_ADULTS(4, "ミッドナイトノベルズ（大人向け）");

	private static final Map<Integer, NarouR18Genre> ID_GENRE_MAP = Arrays.stream(NarouR18Genre.values()).collect(Collectors.toMap(NarouR18Genre::getId, Function.identity()));

	private final int id;
	@Override
	public int getId() {
		return this.id;
	}
	private final String description;
	@Override
	public String getDescription() {
		return this.description;
	}
	NarouR18Genre(int id, String description) {
		this.id = id;
		this.description = description;
	}
	public static NarouR18Genre fromId(int id) {
		NarouR18Genre genre = ID_GENRE_MAP.get(id);
		if (genre == null) {
			throw new IllegalArgumentException(String.format("Invalid NarouR18Genre ID: %d. Valid IDs are: %s", id, ID_GENRE_MAP.keySet()));
		}
		return genre;
	}
}
