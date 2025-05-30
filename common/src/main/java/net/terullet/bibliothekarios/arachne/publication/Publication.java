package net.terullet.bibliothekarios.arachne.publication;

import java.time.LocalDate;

public interface Publication {
	String getTitle();
	void setTitle(String title);
	String getIsbn();
	void setIsbn(String isbn);
	LocalDate getPublishedAt();
	void setPublishedAt(LocalDate publishedAt);
}
