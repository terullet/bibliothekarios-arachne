package net.terullet.bibliothekarios.arachne.web.com.syosetu.api;

import net.terullet.bibliothekarios.arachne.web.com.syosetu.NarouQueryWordTargets;
import net.terullet.bibliothekarios.arachne.web.com.syosetu.NarouWorkOrder;
import net.terullet.net.URLEncoder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Set;

public abstract sealed class NarouApiQuery permits NarouAllAgesApiQuery, NarouR18ApiQuery {
	private final int limit;
	private final int from;
	private final NarouWorkOrder order;
	private final String includingWords;
	private final String excludingWords;
	private final Set<NarouQueryWordTargets> wordTargets;
	private final int minCharacters;
	private final int maxCharacters;
	private final int minTalkPercentage;
	private final int maxTalkPercentage;
	private final int minIllustrations;
	private final int maxIllustrations;
	private final Set<String> ncodes;
	private final WorkType workType;
	private final NarouApiTimestamp lastPostedAt;
	private final NarouApiTimestamp lastUpdatedAt;
	private final boolean isPickedUp;
	private final boolean enablesWeeklyReaders;

	private final StringBuilder postQuery;

	public NarouApiQuery(
			int limit,
			int from,
			NarouWorkOrder order,
			String includingWords,
			String excludingWords,
			Set<NarouQueryWordTargets> wordTargets,
			int minCharacters,
			int maxCharacters,
			int minTalkPercentage,
			int maxTalkPercentage,
			int minIllustrations,
			int maxIllustrations,
			Set<String> ncodes,
			WorkType workType,
			NarouApiTimestamp lastPostedAt,
			NarouApiTimestamp lastUpdatedAt,
			boolean isPickedUp,
			boolean enablesWeeklyReaders
	) {
		this.limit = limit;
		this.from = from;
		this.order = order;
		this.includingWords = includingWords;
		this.excludingWords = excludingWords;
		this.wordTargets = Collections.unmodifiableSet(wordTargets);
		this.minCharacters = minCharacters;
		this.maxCharacters = maxCharacters;
		this.minTalkPercentage = minTalkPercentage;
		this.maxTalkPercentage = maxTalkPercentage;
		this.minIllustrations = minIllustrations;
		this.maxIllustrations = maxIllustrations;
		this.ncodes = ncodes != null ? Collections.unmodifiableSet(ncodes) : Collections.emptySet();
		this.workType = workType;
		this.lastPostedAt = lastPostedAt;
		this.lastUpdatedAt = lastUpdatedAt;
		this.isPickedUp = isPickedUp;
		this.enablesWeeklyReaders = enablesWeeklyReaders;
		// create query.
		StringBuilder sb = new StringBuilder();
		if (this.limit > 0) {
			sb.append("&lim=").append(this.limit);
		}
		if (this.from > 0) {
			sb.append("&st=").append(this.from);
		}
		sb.append("&order=").append(this.order.getKey());
		if (this.includingWords != null && !this.includingWords.isBlank()) {
			sb.append("&word=").append(URLEncoder.encode(this.includingWords, StandardCharsets.UTF_8));
		}
		if (this.excludingWords != null && !this.excludingWords.isBlank()) {
			sb.append("&notword=").append(URLEncoder.encode(this.excludingWords, StandardCharsets.UTF_8));
		}
		for (NarouQueryWordTargets qwt : this.wordTargets) {
			sb.append("&").append(qwt.getKey()).append("=1");
		}
		if (this.minCharacters > 0 || this.maxCharacters > 0) {
			sb.append("&length=");
			if (this.minCharacters > 0) {
				sb.append(this.minCharacters);
			}
			sb.append('-');
			if (this.maxCharacters > 0) {
				sb.append(this.maxCharacters);
			}
		}
		if (this.minTalkPercentage > 0 || this.maxTalkPercentage > 0) {
			sb.append("&kaiwaritu=");
			if (this.minTalkPercentage > 0) {
				sb.append(this.minTalkPercentage);
			}
			sb.append('-');
			if (this.maxTalkPercentage > 0) {
				sb.append(this.maxTalkPercentage);
			}
		}
		if (this.minIllustrations > 0 || this.maxIllustrations > 0) {
			sb.append("&sasie=");
			if (this.minTalkPercentage > 0) {
				sb.append(this.minIllustrations);
			}
			sb.append('-');
			if (this.maxIllustrations > 0) {
				sb.append(this.maxIllustrations);
			}
		}
		if (!this.ncodes.isEmpty()) {
			sb.append("&ncode=");
			for (String n : this.ncodes) {
				sb.append(n).append('-');
			}
			sb.deleteCharAt(sb.length()-1);
		}
		if (this.workType != WorkType.ALL) {
			sb.append("&type=").append(this.workType.getKey());
		}
		if (this.lastPostedAt != null) {
			sb.append(this.lastPostedAt.toUrlEncoded("&lastup="));
		}
		if (this.lastUpdatedAt != null) {
			sb.append(this.lastUpdatedAt.toUrlEncoded("&lastupdate="));
		}
		if (this.isPickedUp) {
			sb.append("&ispickup=1");
		}
		if (this.enablesWeeklyReaders) {
			sb.append("&opt=weekly");
		}
		this.postQuery = sb;
	}

	public int getLimit() {
		return this.limit;
	}

	public int getFrom() {
		return this.from;
	}

	public NarouWorkOrder getOrder() {
		return this.order;
	}

	public String getIncludingWords() {
		return this.includingWords;
	}

	public String getExcludingWords() {
		return this.excludingWords;
	}

	public Set<NarouQueryWordTargets> getWordTargets() {
		return this.wordTargets;
	}

	public int getMinCharacters() {
		return this.minCharacters;
	}

	public int getMaxCharacters() {
		return this.maxCharacters;
	}

	public int getMinTalkPercentage() {
		return this.minTalkPercentage;
	}

	public int getMaxTalkPercentage() {
		return this.maxTalkPercentage;
	}

	public int getMinIllustrations() {
		return this.minIllustrations;
	}

	public int getMaxIllustrations() {
		return this.maxIllustrations;
	}

	public Set<String> getNcodes() {
		return this.ncodes;
	}

	public WorkType getWorkType() {
		return this.workType;
	}

	public NarouApiTimestamp getLastPostedAt() {
		return this.lastPostedAt;
	}

	public NarouApiTimestamp getLastUpdatedAt() {
		return this.lastUpdatedAt;
	}

	public boolean isPickedUp() {
		return this.isPickedUp;
	}

	public boolean isEnablesWeeklyReaders() {
		return this.enablesWeeklyReaders;
	}

	protected StringBuilder getPostQuery() {
		return this.postQuery;
	}
	public abstract URI getUri();

	public enum WorkType {
		ALL("", "すべての作品タイプ"),
		STANDALONE("t", "短編"),
		CURRENTLY_SERIALIZED("r", "連載中"),
		SERIES_ENDED("er", "完結済連載作品"),
		SERIES("re", "すべての連載作品"),
		ENDED("ter", "短編と完結済連載作品");

		private final String key;
		private final String description;
		WorkType(String key, String description) {
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
}
