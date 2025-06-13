package net.terullet.bibliothekarios.arachne.javafx.book;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import net.terullet.bibliothekarios.arachne.book.Series;

import java.time.LocalDate;

public class PrintedBook implements net.terullet.bibliothekarios.arachne.book.PrintedBook {
	private final long id;
	private final ObjectProperty<Series> seriesProperty = new SimpleObjectProperty<>();
	private final StringProperty titleProperty = new SimpleStringProperty();
	private final StringProperty isbnProperty = new SimpleStringProperty();
	private final ObjectProperty<LocalDate> publishedAtProperty = new SimpleObjectProperty<>();

	public long getId() {
		return this.id;
	}

	public ObjectProperty<Series> seriesProperty() {
		return this.seriesProperty;
	}
	public Series getSeries() {
		return this.seriesProperty.get();
	}
	public void setSeries(Series series) {
		this.seriesProperty.set(series);
	}

	public StringProperty titleProperty() {
		return this.titleProperty;
	}
	public String getTitle() {
		return this.titleProperty.get();
	}
	public void setTitle(String title) {
		this.titleProperty.set(title);
	}

	public StringProperty isbnProperty() {
		return this.isbnProperty;
	}
	public String getIsbn() {
		return this.isbnProperty.get();
	}
	public void setIsbn(String isbn) {
		this.isbnProperty.set(isbn);
	}

	public ObjectProperty<LocalDate> publishedAtProperty() {
		return this.publishedAtProperty;
	}
	public LocalDate getPublishedAt() {
		return this.publishedAtProperty.get();
	}
	public void setPublishedAt(LocalDate publishedAt) {
		this.publishedAtProperty.set(publishedAt);
	}

	public PrintedBook(long id) {
		this.id = id;
	}
}
