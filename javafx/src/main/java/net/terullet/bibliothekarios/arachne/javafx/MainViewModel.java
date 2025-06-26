package net.terullet.bibliothekarios.arachne.javafx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import net.terullet.bibliothekarios.arachne.javafx.work.WorkListViewModel;
import net.terullet.bibliothekarios.arachne.ui.MainModel;
import net.terullet.bibliothekarios.arachne.ui.OperationState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainViewModel {
	private static final Logger logger = LogManager.getLogger(MainViewModel.class);
	private final MainModel model;
	private final WorkListViewModel workList;
	public WorkListViewModel getWorkList() {
		return this.workList;
	}
	private final ObjectProperty<OperationState> stateProperty;
	public final ReadOnlyObjectProperty<OperationState> stateProperty() {
		return this.stateProperty;
	}
	public final OperationState getState() {
		return this.stateProperty.get();
	}

	public MainViewModel(MainModel model) {
		this.model = model;
		this.workList = new WorkListViewModel(model.getWorkList(), this::switchToExploration);
		this.stateProperty = new SimpleObjectProperty<>(this, "state", model.getState());
		model.stateObservable().addListener(PlatformObserver.wrap(this.stateProperty));
	}

	private void switchToExploration() {
		logger.trace("Switching to EXPLORATION...");
		this.model.transitState(OperationState.EXPLORATION);
	}
}
