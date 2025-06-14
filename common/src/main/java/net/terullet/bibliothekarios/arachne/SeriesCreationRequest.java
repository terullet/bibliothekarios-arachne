package net.terullet.bibliothekarios.arachne;

public class SeriesCreationRequest {
	private Long id;
	private Universe universe;
	private String title;

	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Universe getUniverse() {
		return this.universe;
	}
	public void setUniverse(Universe universe) {
		this.universe = universe;
	}
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
