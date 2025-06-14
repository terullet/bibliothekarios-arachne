package net.terullet.bibliothekarios.arachne.web.com.syosetu;

public class NarouParagraphCreationRequest {
	private Long id;
	private NarouEpisode episode;
	private int paragraphNumber;
	private String content;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public NarouEpisode getEpisode() {
		return this.episode;
	}

	public void setEpisode(NarouEpisode episode) {
		this.episode = episode;
	}

	public int getParagraphNumber() {
		return this.paragraphNumber;
	}

	public void setParagraphNumber(int paragraphNumber) {
		this.paragraphNumber = paragraphNumber;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
