package net.terullet.bibliothekarios.arachne.web.com.syosetu;

public enum NarouAllAgesLargeGenre implements NarouGenre {
	ROMANCE(1, "恋愛"),
	FANTASY(2, "ファンタジー"),
	LITERATURE(3, "文芸"),
	SCIENCE_FICTION(4, "SF"),
	OTHERS(99, "その他"),
	NON_GENRE(98, "ノンジャンル");

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

	public static NarouAllAgesLargeGenre valueOf(int id) {
		for (var val : NarouAllAgesLargeGenre.values()) {
			if (id == val.getId()) {
				return val;
			}
		}
		return null;
	}
}
