package net.terullet.bibliothekarios.arachne.repository.entity.web.com.syosetu;

import net.terullet.bibliothekarios.arachne.repository.Site;
import net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.NarouAllAgesGenre;
import net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.NarouWorkType;
import net.terullet.bibliothekarios.arachne.repository.entity.ContributionInWorkEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.UniverseOverviewEntity;

import java.time.LocalDateTime;
import java.util.List;

public final class NarouAllAgesWorkEntity extends NarouWorkEntity {
	private final NarouAllAgesGenre genre;

	private NarouAllAgesWorkEntity(long id, String ncode, long narouId, UniverseOverviewEntity universe, List<ContributionInWorkEntity> contributions, NarouWorkType workType, NarouAllAgesGenre genre, String summary, LocalDateTime registeredAt) {
		super(id, Site.NAROU_ALL_AGES, ncode, narouId, universe, contributions, workType, summary, registeredAt);
		this.genre = genre;
	}

	public NarouAllAgesGenre getGenre() {
		return this.genre;
	}

	public static class Factory extends NarouWorkEntity.Factory {
		private NarouAllAgesGenre genre;

		public final NarouAllAgesGenre getGenre() {
			return this.genre;
		}
		public final void setGenre(NarouAllAgesGenre genre) {
			this.genre = genre;
		}

		@Override
		public NarouAllAgesWorkEntity build() {
			return new NarouAllAgesWorkEntity(this.getId(), this.getNcode(), this.getNarouId(), this.getUniverse(), this.getContributions(), this.getWorkType(), this.getGenre(), this.getSummary(), this.getRegisteredAt());
		}
	}
}
