package net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.api;

import net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.NarouAllAgesGenre;
import net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.NarouAllAgesLargeGenre;
import net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.NarouQueryWordTargets;
import net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.NarouWorkOrder;

import java.net.URI;
import java.util.Collections;
import java.util.Set;

public final class NarouAllAgesApiQuery extends NarouApiQuery {
	private final Set<Item> items;
	private final Set<NarouAllAgesLargeGenre> includingLargeGenres;
	private final Set<NarouAllAgesLargeGenre> excludingLargeGenres;
	private final Set<NarouAllAgesGenre> includingGenres;
	private final Set<NarouAllAgesGenre> excludingGenres;
	private final URI uri;

	public NarouAllAgesApiQuery(
			Set<Item> items,
			int limit,
			int from,
			NarouWorkOrder order,
			String includingWords,
			String excludingWords,
			Set<NarouQueryWordTargets> wordTargets,
			Set<NarouAllAgesLargeGenre> includingLargeGenres,
			Set<NarouAllAgesLargeGenre> excludingLargeGenres,
			Set<NarouAllAgesGenre> includingGenres,
			Set<NarouAllAgesGenre> excludingGenres,
			int minCharacters,
			int maxCharacters,
			int minTalkPercentage,
			int maxTalkPercentage,
			int minIllustrations,
			int maxIllustrations,
			Set<String> ncodes,
			NarouApiQuery.WorkType workType,
			NarouApiTimestamp lastPostedAt,
			NarouApiTimestamp lastUpdatedAt,
			boolean isPickedUp,
			boolean enablesWeeklyReaders
	) {
		super(
				limit,
				from,
				order,
				includingWords,
				excludingWords,
				wordTargets,
				minCharacters,
				maxCharacters,
				minTalkPercentage,
				maxTalkPercentage,
				minIllustrations,
				maxIllustrations,
				ncodes,
				workType,
				lastPostedAt,
				lastUpdatedAt,
				isPickedUp,
				enablesWeeklyReaders
		);
		this.items = items != null ? Collections.unmodifiableSet(items) : Collections.emptySet();
		this.includingLargeGenres = includingLargeGenres != null ? Collections.unmodifiableSet(includingLargeGenres) : Collections.emptySet();
		this.excludingLargeGenres = excludingLargeGenres != null ? Collections.unmodifiableSet(excludingLargeGenres) : Collections.emptySet();
		this.includingGenres = includingGenres != null ? Collections.unmodifiableSet(includingGenres) : Collections.emptySet();
		this.excludingGenres = excludingGenres != null ? Collections.unmodifiableSet(excludingGenres) : Collections.emptySet();
		StringBuilder sb = new StringBuilder("https://api.syosetu.com/novelapi/api/?gzip=5&out=json&of=n");
		for (NarouAllAgesApiQuery.Item i : this.items) {
			sb.append('-').append(i.getOfKey());
		}
		if (!this.includingLargeGenres.isEmpty()) {
			sb.append("&biggenre=");
			for (NarouAllAgesLargeGenre lg : this.includingLargeGenres) {
				sb.append(lg.getId()).append('-');
			}
			sb.deleteCharAt(sb.length()-1);
		}
		if (!this.excludingLargeGenres.isEmpty()) {
			sb.append("&notbiggenre=");
			for (NarouAllAgesLargeGenre lg : this.excludingLargeGenres) {
				sb.append(lg.getId()).append('-');
			}
			sb.deleteCharAt(sb.length()-1);
		}
		if (!this.includingGenres.isEmpty()) {
			sb.append("&genre=");
			for (NarouAllAgesGenre g : this.includingGenres) {
				sb.append(g.getId()).append('-');
			}
			sb.deleteCharAt(sb.length()-1);
		}
		if (!this.excludingGenres.isEmpty()) {
			sb.append("&notgenre=");
			for (NarouAllAgesGenre g : this.excludingGenres) {
				sb.append(g.getId()).append('-');
			}
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append(super.getPostQuery());
		this.uri = URI.create(sb.toString());
	}

	public Set<Item> getItems() {
		return this.items;
	}

	public Set<NarouAllAgesLargeGenre> getIncludingLargeGenres() {
		return this.includingLargeGenres;
	}

	public Set<NarouAllAgesLargeGenre> getExcludingLargeGenres() {
		return this.excludingLargeGenres;
	}

	public Set<NarouAllAgesGenre> getIncludingGenres() {
		return this.includingGenres;
	}

	public Set<NarouAllAgesGenre> getExcludingGenres() {
		return this.excludingGenres;
	}

	@Override
	public URI getUri() {
		return this.uri;
	}

	public enum Item {
		TITLE("t", "作品名"),
		WRITER_ID("u", "作者のユーザーID"),
		WRITER("w", "作者名"),
		SUMMARY("s", "あらすじ"),
		GENRE("g", "ジャンル"),
		KEYWORDS("k", "キーワード"),
		FIRST_POSTED_AT("gf", "初回掲載日時"),
		LAST_POSTED_AT("gl", "最終掲載日時"),
		WORK_TYPE("nt-e", "連載中／完結済／短編"),
		NUM_EPISODES("ga", "エピソード数"),
		NUM_CHARACTERS("l", "文字数"),
		GLOBAL_POINT("gp", "総合評価ポイント"),
		DAILY_POINT("dp", "日間ポイント"),
		WEEKLY_POINT("wp", "週間ポイント"),
		MONTHLY_POINT("mp", "月間ポイント"),
		QUARTERLY_POINT("qp", "四半期ポイント"),
		ANNUALLY_POINT("yp", "年間ポイント"),
		FAVORITE_COUNT("f", "ブックマーク数"),
		NUM_IMPRESSIONS("imp", "感想数"),
		NUM_REVIEWS("r", "レビュー数"),
		TOTAL_POINT("a", "評価ポイント"),
		NUM_RATERS("ah", "評価者数"),
		NUM_ILLUSTRATIONS("sa", "挿絵の数"),
		TALK_PERCENTAGE("ka", "会話率"),
		LAST_UPDATED_AT("nu", "最終更新日時");

		private final String ofKey;
		private final String description;
		Item(String ofKey, String description) {
			this.ofKey = ofKey;
			this.description = description;
		}
		public final String getOfKey() {
			return this.ofKey;
		}
		public final String getDescription() {
			return this.description;
		}
	}
}
