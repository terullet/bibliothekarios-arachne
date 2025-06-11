package net.terullet.bibliothekarios.arachne;

import java.time.ZonedDateTime;

public interface Work {
	long getId();
	String getTitle();
	void setTitle(String title);
	String getSummary();
	void setSummary(String summary);
	ZonedDateTime getRegisteredAt();
}
