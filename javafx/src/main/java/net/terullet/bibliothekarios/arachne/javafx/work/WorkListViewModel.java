package net.terullet.bibliothekarios.arachne.javafx.work;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.terullet.bibliothekarios.arachne.javafx.Command;
import net.terullet.bibliothekarios.arachne.javafx.PlatformObserver;
import net.terullet.bibliothekarios.arachne.ui.OperationState;
import net.terullet.bibliothekarios.arachne.ui.work.WorkListModel;

public class WorkListViewModel {
	private final WorkListModel model;
	private final ObservableList<WorkOverviewViewModel> works = FXCollections.observableArrayList();
	public final ObservableList<WorkOverviewViewModel> getWorks() {
		return this.works;
	}
	private final ObjectProperty<WorkOverviewViewModel> selectedWorkProperty = new SimpleObjectProperty<>(this, "selectedWork", null);
	public final ObjectProperty<WorkOverviewViewModel> selectedWorkProperty() {
		return this.selectedWorkProperty;
	}
	public final WorkOverviewViewModel getSelectedWork() {
		return this.selectedWorkProperty.get();
	}
	public final void setSelectedWork(WorkOverviewViewModel selectedWork) {
		this.selectedWorkProperty.set(selectedWork);
	}
	private final Command<OperationState> transitToExplorationCommand;
	public final Command<OperationState> transitToExplorationCommand() {
		return this.transitToExplorationCommand;
	}

	public WorkListViewModel(WorkListModel model, Runnable transitToExploration) {
		this.model = model;
		this.model.getWorks().addListener(PlatformObserver.wrap(this.works, WorkOverviewViewModel::new));
		this.transitToExplorationCommand = new Command<>((s) -> transitToExploration.run());
	}
}
