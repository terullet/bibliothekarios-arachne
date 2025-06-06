package net.terullet.bibliothekarios.arachne.book;

import net.terullet.bibliothekarios.arachne.Work;

import java.time.LocalDate;

public interface Book extends Work {
	LocalDate getPublishedAt();
	void setPublishedAt(LocalDate publishedAt);
}
