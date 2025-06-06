package net.terullet.bibliothekarios.arachne;

public interface Series {
	long getId();
	Universe getWorkUniverse();
	String getTitle();
	void setTitle(String title);
}
