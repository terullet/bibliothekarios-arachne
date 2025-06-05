package net.terullet.bibliothekarios.arachne.web;

import net.terullet.bibliothekarios.arachne.Work;

public interface WebWork {
	Work getWork();
	String getTitle();
	void setTitle(String title);
}
