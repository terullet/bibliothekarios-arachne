package net.terullet.bibliothekarios.arachne.domain.web.com.syosetu;

/**
 * R18版なろうのジャンル（掲載サイト）を表す列挙体です
 */
public enum NarouR18Genre implements NarouGenre {
	FOR_MALES(1, "ノクターンノベルズ（男性向け）"),
	FOR_FEMALES(2, "ムーンライトノベルズ（女性向け）"),
	BOYS_LOVE(3, "ムーンライトノベルズ（BL）"),
	FOR_ADULTS(4, "ミッドナイトノベルズ（大人向け）");

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
	public static NarouR18Genre valueOf(int id) {
		for (var val : NarouR18Genre.values()) {
			if (id == val.id) return val;
		}
		return null;
	}
}
