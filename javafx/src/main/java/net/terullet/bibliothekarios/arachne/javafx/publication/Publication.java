package net.terullet.bibliothekarios.arachne.javafx.publication;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import net.terullet.bibliothekarios.arachne.Work;

import java.time.LocalDate;

public class Publication implements net.terullet.bibliothekarios.arachne.publication.Publication {
	private final ObjectProperty<Work> workProperty = new SimpleObjectProperty<>();
	private final StringProperty titleProperty = new SimpleStringProperty();
	private final StringProperty isbnProperty = new SimpleStringProperty();
	private final ObjectProperty<LocalDate> publishedAtProperty = new SimpleObjectProperty<>();

	public ObjectProperty<Work> workProperty() {
		return this.workProperty;
	}
	public Work getWork() {
		return this.workProperty.get();
	}
	public void setWork(Work work) {
		this.workProperty.set(work);
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
}
