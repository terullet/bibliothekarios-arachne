package net.terullet.bibliothekarios.arachne.book;

public interface PrintedBook extends Book {
	String getIsbn();
	void setIsbn(String isbn);
}
