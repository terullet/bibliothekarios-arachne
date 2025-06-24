package net.terullet.bibliothekarios.arachne.repository.entity;

public final class HyperlinkContributorBindRequestEntity {
	private final long hyperlinkId;
	private final long contributorId;

	public HyperlinkContributorBindRequestEntity(long hyperlinkId, long contributorId) {
		this.hyperlinkId = hyperlinkId;
		this.contributorId = contributorId;
	}

	public long getHyperlinkId() {
		return this.hyperlinkId;
	}
	public long getContributorId() {
		return this.contributorId;
	}
}
