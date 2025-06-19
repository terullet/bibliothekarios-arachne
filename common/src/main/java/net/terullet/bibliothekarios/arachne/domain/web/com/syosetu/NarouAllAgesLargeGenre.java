package net.terullet.bibliothekarios.arachne.domain.web.com.syosetu;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum NarouAllAgesLargeGenre implements NarouGenre {
	ROMANCE(1, "恋愛"),
	FANTASY(2, "ファンタジー"),
	LITERATURE(3, "文芸"),
	SCIENCE_FICTION(4, "SF"),
	OTHERS(99, "その他"),
	NON_GENRE(98, "ノンジャンル");

	private static final Map<Integer, NarouAllAgesLargeGenre> ID_GENRE_MAP = Arrays.stream(NarouAllAgesLargeGenre.values()).collect(Collectors.toMap(NarouAllAgesLargeGenre::getId, Function.identity()));

	private final int id;
	public int getId() {
		return this.id;
	}
	private final String description;
	public String getDescription() {
		return this.description;
	}
	NarouAllAgesLargeGenre(int id, String description) {
		this.id = id;
		this.description = description;
	}

	public static NarouAllAgesLargeGenre fromId(int id) {
		NarouAllAgesLargeGenre genre = ID_GENRE_MAP.get(id);
		if (genre == null) {
			throw new IllegalArgumentException(String.format("Invalid NarouAllAgesLargeGenre ID: %d. Valid IDs are: %s", id, ID_GENRE_MAP.keySet()));
		}
		return genre;
	}
}
