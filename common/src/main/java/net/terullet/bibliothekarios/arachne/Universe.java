package net.terullet.bibliothekarios.arachne;

public interface Universe {
	long getId();
	String getTitle();
	void setTitle(String title);
	String getSummary();
	void setSummary(String summary);
}
