package net.terullet.bibliothekarios.arachne.web.com.syosetu.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.terullet.bibliothekarios.arachne.web.com.syosetu.NarouR18Genre;

public final class NarouR18WorkResponseMetadata extends NarouWorkResponseMetadata {
	private final NarouR18Genre genre;

	public NarouR18WorkResponseMetadata(
			@JsonProperty("ncode") String ncode,
			@JsonProperty("title") String title,
			@JsonProperty("userid") long writerId,
			@JsonProperty("writer") String writer,
			@JsonProperty("story") String summary,
			@JsonProperty("genre") int genre,
			@JsonProperty("keyword") String keywords,
			@JsonProperty("general_firstup") String firstPostedAt,
			@JsonProperty("general_lastup") String lastPostedAt,
			@JsonProperty("noveltype") int novelType,
			@JsonProperty("end") int isEnded,
			@JsonProperty("general_all_no") int numEpisodes,
			@JsonProperty("length") int numCharacters,
			@JsonProperty("global_point") int globalPoint,
			@JsonProperty("daily_point") int dailyPoint,
			@JsonProperty("weekly_point") int weeklyPoint,
			@JsonProperty("monthly_point") int monthlyPoint,
			@JsonProperty("quarter_point") int quarterlyPoint,
			@JsonProperty("yearly_point") int annuallyPoint,
			@JsonProperty("fav_novel_cnt") int favoriteCount,
			@JsonProperty("impression_cnt") int numImpressions,
			@JsonProperty("review_cnt") int numReviews,
			@JsonProperty("all_point") int totalPoint,
			@JsonProperty("all_hyoka_cnt") int numRaters,
			@JsonProperty("sasie_cnt") int numIllustrations,
			@JsonProperty("kaiwaritu") int talkPercentage,
			@JsonProperty("novelupdated_at") String lastUpdatedAt,
			@JsonProperty("weekly_unique") int numWeeklyReaders
	) {
		super(
				ncode,
				title,
				writerId,
				writer,
				summary,
				keywords,
				firstPostedAt,
				lastPostedAt,
				novelType,
				isEnded,
				numEpisodes,
				numCharacters,
				globalPoint,
				dailyPoint,
				weeklyPoint,
				monthlyPoint,
				quarterlyPoint,
				annuallyPoint,
				favoriteCount,
				numImpressions,
				numReviews,
				totalPoint,
				numRaters,
				numIllustrations,
				talkPercentage,
				lastUpdatedAt,
				numWeeklyReaders
		);
		this.genre = NarouR18Genre.valueOf(genre);
	}

	public NarouR18Genre getGenre() {
		return this.genre;
	}
}
