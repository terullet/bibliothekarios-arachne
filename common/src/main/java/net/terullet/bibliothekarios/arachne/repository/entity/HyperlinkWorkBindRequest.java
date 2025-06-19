package net.terullet.bibliothekarios.arachne.repository.entity;

public final class HyperlinkWorkBindRequest {
	private final long hyperlinkId;
	private final long workId;

	public HyperlinkWorkBindRequest(long hyperlinkId, long workId) {
		this.hyperlinkId = hyperlinkId;
		this.workId = workId;
	}

	public long getHyperlinkId() {
		return this.hyperlinkId;
	}
	public long getWorkId() {
		return this.workId;
	}
}
