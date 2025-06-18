package net.terullet.bibliothekarios.arachne.repository.entity.web.com.syosetu;

import net.terullet.bibliothekarios.arachne.repository.Site;
import net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.NarouR18Genre;
import net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.NarouWorkType;
import net.terullet.bibliothekarios.arachne.repository.entity.ContributionInWorkEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.UniverseOverviewEntity;

import java.time.LocalDateTime;
import java.util.List;

public final class NarouR18WorkEntity extends NarouWorkEntity {
	private final NarouR18Genre genre;

	public NarouR18WorkEntity(long id, String ncode, long narouId, String title, UniverseOverviewEntity universe, List<ContributionInWorkEntity> contributions, NarouWorkType workType, NarouR18Genre genre, String summary, LocalDateTime registeredAt) {
		super(id, Site.NAROU_R18, ncode, narouId, title, universe, contributions, workType, summary, registeredAt);
		this.genre = genre;
	}

	public NarouR18Genre getGenre() {
		return this.genre;
	}

	public static final class Factory extends NarouWorkEntity.Factory {
		private NarouR18Genre genre;

		public final NarouR18Genre getGenre() {
			return this.genre;
		}
		public final void setGenre(NarouR18Genre genre) {
			this.genre = genre;
		}

		@Override
		public NarouR18WorkEntity build() {
			return new NarouR18WorkEntity(this.getId(), this.getNcode(), this.getNarouId(), this.getTitle(), this.getUniverse(), this.getContributions(), this.getWorkType(), this.getGenre(), this.getSummary(), this.getRegisteredAt());
		}
	}
}
