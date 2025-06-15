package net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.api;

import java.time.ZonedDateTime;

public record NarouApiTimestamp(Constant constant, ZonedDateTime from, ZonedDateTime to) {
	public enum Constant {
		THIS_WEEK("thisweek", "今週"),
		LAST_WEEK("lastweek", "先週"),
		SEVEN_DAYS("sevenday", "過去7日間"),
		THIS_MONTH("thismonth", "今月"),
		LAST_MONTH("lastmonth", "先月"),
		TIMESTAMP("", "日時指定");

		private final String key;
		private final String description;
		Constant(String key, String description) {
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

	public String toUrlEncoded(String prefix) {
		if (this.constant() != NarouApiTimestamp.Constant.TIMESTAMP || this.from() != null || this.to() != null) {
			StringBuilder sb = new StringBuilder(prefix);
			sb.append("&lastupdate=");
			if (this.constant() != NarouApiTimestamp.Constant.TIMESTAMP) {
				sb.append(this.constant().getKey());
			} else {
				if (this.from() != null) {
					sb.append(this.from().toEpochSecond());
				}
				sb.append('-');
				if (this.to() != null) {
					sb.append(this.to().toEpochSecond());
				}
			}
			return sb.toString();
		}
		return "";
	}
}
