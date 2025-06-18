package net.terullet.bibliothekarios.arachne.repository.entity.web.com.syosetu;

import net.terullet.bibliothekarios.arachne.repository.Site;
import net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.NarouGenre;
import net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.NarouWorkType;
import net.terullet.bibliothekarios.arachne.repository.entity.ContributionInWorkEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.UniverseOverviewEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.WorkEntity;

import java.time.LocalDateTime;
import java.util.List;

public abstract sealed class NarouWorkEntity extends WorkEntity permits NarouAllAgesWorkEntity, NarouR18WorkEntity {
	private final String ncode;
	private final long narouId;
	private final NarouWorkType workType;

	protected NarouWorkEntity(long id, Site site, String ncode, long narouId, String title, UniverseOverviewEntity universe, List<ContributionInWorkEntity> contributions, NarouWorkType workType, String summary, LocalDateTime registeredAt) {
		super(id, site, title, universe, contributions, summary, registeredAt);
		this.ncode = ncode;
		this.narouId = narouId;
		this.workType = workType;
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
	public abstract NarouGenre getGenre();

	public static sealed class Factory extends WorkEntity.Factory permits NarouAllAgesWorkEntity.Factory, NarouR18WorkEntity.Factory {
		private String ncode;
		private long narouId;
		private NarouWorkType workType;

		@Override
		public final void setSite(Site site) {
			; // ignore setter
		}
		public final String getNcode() {
			return this.ncode;
		}
		public final void setNcode(String ncode) {
			this.ncode = ncode;
		}
		public final long getNarouId() {
			return this.narouId;
		}
		public final void setNarouId(long narouId) {
			this.narouId = narouId;
		}
		public final NarouWorkType getWorkType() {
			return this.workType;
		}
		public final void setWorkType(NarouWorkType workType) {
			this.workType = workType;
		}
	}
}
