package net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.NarouWorkType;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public abstract sealed class NarouWorkResponseMetadata permits NarouAllAgesWorkResponseMetadata, NarouR18WorkResponseMetadata {
	private final String ncode;
	private final String title;
	private final long writerId;
	private final String writer;
	private final String summary;
	private final Set<String> keywords;
	private final ZonedDateTime firstPostedAt;
	private final ZonedDateTime lastPostedAt;
	private final NarouWorkType workType;
	private final int numEpisodes;
	private final int numCharacters;
	private final int globalPoint;
	private final int dailyPoint;
	private final int weeklyPoint;
	private final int monthlyPoint;
	private final int quarterlyPoint;
	private final int annuallyPoint;
	private final int favoriteCount;
	private final int numImpressions;
	private final int numReviews;
	private final int totalPoint;
	private final int numRaters;
	private final int numIllustrations;
	private final int talkPercentage;
	private final ZonedDateTime lastUpdatedAt;
	private final int numWeeklyReaders;

	public static final DateTimeFormatter NAROU_WORK_DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public static final ZoneId NAROU_TIMEZONE = ZoneId.of("Asia/Tokyo");

	public NarouWorkResponseMetadata(
			@JsonProperty("ncode") String ncode,
			@JsonProperty("title") String title,
			@JsonProperty("userid") long writerId,
			@JsonProperty("writer") String writer,
			@JsonProperty("story") String summary,
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
		this.ncode = ncode;
		this.title = title;
		this.writerId = writerId;
		this.writer = writer;
		this.summary = summary;
		this.keywords = new LinkedHashSet<>(Arrays.asList(keywords.split(" ")));
		this.firstPostedAt = ZonedDateTime.of(LocalDateTime.parse(firstPostedAt, NAROU_WORK_DATETIME_FORMAT), NAROU_TIMEZONE);
		this.lastPostedAt = ZonedDateTime.of(LocalDateTime.parse(lastPostedAt, NAROU_WORK_DATETIME_FORMAT), NAROU_TIMEZONE);
		this.workType = novelType == 1 ? (isEnded != 0 ? NarouWorkType.SERIES_ENDED : NarouWorkType.CURRENTLY_SERIALIZED) : NarouWorkType.STANDALONE;
		this.numEpisodes = numEpisodes;
		this.numCharacters = numCharacters;
		this.globalPoint = globalPoint;
		this.dailyPoint = dailyPoint;
		this.weeklyPoint = weeklyPoint;
		this.monthlyPoint = monthlyPoint;
		this.quarterlyPoint = quarterlyPoint;
		this.annuallyPoint = annuallyPoint;
		this.favoriteCount = favoriteCount;
		this.numImpressions = numImpressions;
		this.numReviews = numReviews;
		this.totalPoint = totalPoint;
		this.numRaters = numRaters;
		this.numIllustrations = numIllustrations;
		this.talkPercentage = talkPercentage;
		this.lastUpdatedAt = ZonedDateTime.of(LocalDateTime.parse(lastUpdatedAt, NAROU_WORK_DATETIME_FORMAT), NAROU_TIMEZONE);
		this.numWeeklyReaders = numWeeklyReaders;
	}

	public String getNcode() {
		return ncode;
	}
	public String getTitle() {
		return title;
	}
	public long getWriterId() {
		return writerId;
	}
	public String getWriter() {
		return writer;
	}
	public String getSummary() {
		return summary;
	}
	public Set<String> getKeywords() {
		return keywords;
	}
	public ZonedDateTime getFirstPostedAt() {
		return firstPostedAt;
	}
	public ZonedDateTime getLastPostedAt() {
		return lastPostedAt;
	}
	public NarouWorkType getWorkType() {
		return workType;
	}
	public int getNumEpisodes() {
		return numEpisodes;
	}
	public int getNumCharacters() {
		return numCharacters;
	}
	public int getGlobalPoint() {
		return globalPoint;
	}
	public int getDailyPoint() {
		return dailyPoint;
	}
	public int getWeeklyPoint() {
		return weeklyPoint;
	}
	public int getMonthlyPoint() {
		return monthlyPoint;
	}
	public int getQuarterlyPoint() {
		return quarterlyPoint;
	}
	public int getAnnuallyPoint() {
		return annuallyPoint;
	}
	public int getFavoriteCount() {
		return favoriteCount;
	}
	public int getNumImpressions() {
		return numImpressions;
	}
	public int getNumReviews() {
		return numReviews;
	}
	public int getTotalPoint() {
		return totalPoint;
	}
	public int getNumRaters() {
		return numRaters;
	}
	public int getNumIllustrations() {
		return numIllustrations;
	}
	public int getTalkPercentage() {
		return talkPercentage;
	}
	public ZonedDateTime getLastUpdatedAt() {
		return lastUpdatedAt;
	}
	public int getNumWeeklyReaders() {
		return this.numWeeklyReaders;
	}
}
