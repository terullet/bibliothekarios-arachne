package net.terullet.bibliothekarios.arachne.domain.web.com.syosetu;

/**
 * 全年齢版なろうのジャンルを表す列挙型です．
 */
public enum NarouAllAgesGenre implements NarouGenre {
	ROMANCE_IN_ANOTHER_WORLD(101, "異世界〔恋愛〕"),
	ROMANCE_IN_REAL_WORLD(102, "現実世界〔恋愛〕"),
	HIGH_FANTASY(201, "ハイファンタジー〔ファンタジー〕"),
	LOW_FANTASY(202, "ローファンタジー〔ファンタジー〕"),
	CLASSIC(301, "純文学〔文芸〕"),
	HUMAN_DRAMAS(302, "ヒューマンドラマ〔文芸〕"),
	HISTORICAL(303, "歴史〔文芸〕"),
	MYSTERY(304, "推理〔文芸〕"),
	HORROR(305, "ホラー〔文芸〕"),
	ACTION(306, "アクション〔文芸〕"),
	COMEDY(307, "コメディ〔文芸〕"),
	VR(401, "VRゲーム〔SF〕"),
	COSMIC(402, "宇宙〔SF〕"),
	SCIENCE_FICTION(403, "空想科学〔SF〕"),
	PANIC(404, "パニック〔SF〕"),
	FAIRY_TAIL(9901, "童話〔その他〕"),
	POETRY(9902, "詩〔その他〕"),
	ESSAY(9903, "エッセイ〔その他〕"),
	REPLAY(9904, "リプレイ〔その他〕"),
	OTHERS(9999, "その他〔その他〕"),
	NON_GENRE(9801, "ノンジャンル〔ノンジャンル〕");

	private final int id;
	@Override
	public final int getId() {
		return this.id;
	}
	private final String description;
	@Override
	public final String getDescription() {
		return this.description;
	}
	NarouAllAgesGenre(int id, String description) {
		this.id = id;
		this.description = description;
	}

	public static NarouAllAgesGenre valueOf(int id) {
		for (var val : NarouAllAgesGenre.values()) {
			if (id == val.getId()) return val;
		}
		return null;
	}
	public NarouAllAgesLargeGenre getLargeGenre() {
		return NarouAllAgesLargeGenre.valueOf(this.id / 100);
	}
}
