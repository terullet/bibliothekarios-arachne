package net.terullet.bibliothekarios.arachne.javafx.work;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import net.terullet.bibliothekarios.arachne.ui.OperationState;

public class WorkListViewController {
	private final WorkListViewModel viewModel;
	@FXML
	private ListView<WorkOverviewViewModel> workListView;
	@FXML
	private Label workListEmptyLabel;
	@FXML
	private Label workDetailEmptyLabel;

	@FXML
	private Button exploreWorkButton;

	public WorkListViewController(WorkListViewModel viewModel) {
		this.viewModel = viewModel;
	}

	@FXML
	public void initialize() {
		this.workListEmptyLabel.managedProperty().bind(this.workListEmptyLabel.visibleProperty());
		this.workListView.itemsProperty().addListener((obs, oldValue, newValue) -> {
			if (oldValue != null) {
				oldValue.removeListener(this::onWorkListUpdated);
			}
			if (newValue != null) {
				newValue.addListener(this::onWorkListUpdated);
			}
		});
		this.workListView.setCellFactory(WorkOverviewViewController::new);
		this.viewModel.selectedWorkProperty().bind(this.workListView.getSelectionModel().selectedItemProperty());
		this.workDetailEmptyLabel.managedProperty().bind(this.workDetailEmptyLabel.visibleProperty());
		this.workDetailEmptyLabel.visibleProperty().bind(this.workListView.getSelectionModel().selectedItemProperty().isNull());
		this.exploreWorkButton.setOnAction(this::onExploreWorksButtonActioned);
	}

	private void onWorkListUpdated(javafx.beans.Observable observable) {
		this.workListEmptyLabel.visibleProperty().setValue(this.workListView.getItems().isEmpty());
	}
	private void onExploreWorksButtonActioned(ActionEvent event) {
		this.viewModel.transitToExplorationCommand().execute(OperationState.EXPLORATION);
	}
}
