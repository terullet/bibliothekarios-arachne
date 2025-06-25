package net.terullet.bibliothekarios.arachne.repository.entity.web.com.syosetu;

import net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.NarouGenre;
import net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.NarouWorkType;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public final class NarouWorkCreationRequestEntity {
	private final long workId;
	private final String ncode;
	private final long narouId;
	private final NarouWorkType workType;
	private final NarouGenre genre;
	private final ZonedDateTime firstPostedAt;

	public long getWorkId() {
		return this.workId;
	}

	public String getNcode() {
		return this.ncode;
	}

	public long getNarouId() {
		return this.narouId;
	}

	public NarouWorkType getWorkType() {
		return this.workType;
	}

	public NarouGenre getGenre() {
		return this.genre;
	}

	public ZonedDateTime getFirstPostedAt() {
		return this.firstPostedAt;
	}

	public NarouWorkCreationRequestEntity(long workId, String ncode, long narouId, NarouWorkType workType, NarouGenre genre, ZonedDateTime firstPostedAt) {
		this.workId = workId;
		this.ncode = ncode;
		this.narouId = narouId;
		this.workType = workType;
		this.genre = genre;
		this.firstPostedAt = firstPostedAt;
	}
}
