package net.terullet.bibliothekarios.arachne.book;

import net.terullet.bibliothekarios.arachne.Work;

import java.time.LocalDate;

public interface Book {
	Series getSeries();
	LocalDate getPublishedAt();
	void setPublishedAt(LocalDate publishedAt);
}
