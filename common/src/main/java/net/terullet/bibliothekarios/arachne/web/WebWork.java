package net.terullet.bibliothekarios.arachne.web;

import net.terullet.bibliothekarios.arachne.Series;
import net.terullet.bibliothekarios.arachne.Work;

public interface WebWork extends Work {
	long getId();
	Series getSeries();
	String getTitle();
	void setTitle(String title);
}
