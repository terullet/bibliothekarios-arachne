package net.terullet.bibliothekarios.arachne.ui.book;

import java.time.LocalDate;

public interface Book {
	Series getSeries();
	LocalDate getPublishedAt();
	void setPublishedAt(LocalDate publishedAt);
}
