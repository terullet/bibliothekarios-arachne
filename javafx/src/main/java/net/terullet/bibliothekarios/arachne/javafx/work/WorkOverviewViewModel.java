package net.terullet.bibliothekarios.arachne.javafx.work;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import net.terullet.bibliothekarios.arachne.javafx.PlatformObserver;
import net.terullet.bibliothekarios.arachne.ui.work.WorkOverviewModel;

public class WorkOverviewViewModel {
	private final WorkOverviewModel model;
	private final StringProperty titleProperty = new SimpleStringProperty();
	public final StringProperty titleProperty() {
		return this.titleProperty;
	}
	public final String getTitle() {
		return this.titleProperty.get();
	}

	public WorkOverviewViewModel(WorkOverviewModel model) {
		this.model = model;
		this.model.titleObservable().addListener(PlatformObserver.wrap(this.titleProperty));
	}
}
