package net.terullet.bibliothekarios.arachne.publication;

import net.terullet.bibliothekarios.arachne.Work;

import java.time.LocalDate;

public interface Publication {
	Work getWork();
	String getTitle();
	void setTitle(String title);
	String getIsbn();
	void setIsbn(String isbn);
	LocalDate getPublishedAt();
	void setPublishedAt(LocalDate publishedAt);
}
