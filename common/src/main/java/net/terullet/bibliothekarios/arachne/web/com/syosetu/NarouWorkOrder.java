package net.terullet.bibliothekarios.arachne.web.com.syosetu;

public enum NarouWorkOrder {
	LAST_POST_DESC("new", "新着更新順"),
	FAVORITE_DESC("favnovelcnt", "ブックマーク数の多い順"),
	REVIEW_DESC("reviewcnt", "レビュー数の多い順"),
	TOTAL_POINT_DESC("hyoka", "総合ポイントの高い順"),
	TOTAL_POINT_ASC("hyokaasc", "総合ポイントの低い順"),
	DAILY_POINT_DESC("dailypoint", "日間ポイントの高い順"),
	WEEKLY_POINT_DESC("weeklypoint", "週間ポイントの高い順"),
	MONTHLY_POINT_DESC("monthlypoint", "月間ポイントの高い順"),
	QUARTERLY_POINT_DESC("quaterpoint", "四半期ポイントの高い順"),
	ANNUALLY_POINT_DESC("yearlypoint", "年間ポイントの高い順"),
	IMPRESSION_DESC("impressioncnt", "感想の多い順"),
	RATER_DESC("hyokacnt", "評価者数の多い順"),
	RATER_ASC("hyokacntasc", "評価者数の少ない順"),
	WEEKLY_READER_DESC("weekly", "週間ユニークユーザーの多い順"),
	CHARACTERS_DESC("lengthdesc", "作品本文の文字数が多い順"),
	CHARACTERS_ASC("lengthasc", "作品本文の文字数が少ない順"),
	FIRST_POST("generalfirstup", "初回掲載順"),
	NCODE_ASC("ncodeasc", "Nコード昇順"),
	NCODE_DESC("ncodedesc", "Nコード降順"),
	LAST_POST_ASC("old", "更新が古い順");

	private final String key;
	private final String description;
	NarouWorkOrder(String key, String description) {
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
