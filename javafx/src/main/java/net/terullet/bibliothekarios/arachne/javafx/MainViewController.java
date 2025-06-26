package net.terullet.bibliothekarios.arachne.javafx;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import net.terullet.bibliothekarios.arachne.javafx.exploration.WorkExplorationViewController;
import net.terullet.bibliothekarios.arachne.javafx.work.WorkListViewController;
import net.terullet.bibliothekarios.arachne.ui.OperationState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Set;

public class MainViewController {
	private static final Logger logger = LogManager.getLogger(MainViewController.class);
	private final MainViewModel viewModel;
	@FXML
	private Pane mainPane;
	@FXML
	private TabPane tabsPane;
	@FXML
	private Tab workListTab;
	@FXML
	private Tab episodeListTab;
	@FXML
	private Tab workExplorationTab;
	@FXML
	private Tab configurationTab;

	public MainViewController(MainViewModel viewModel) {
		this.viewModel = viewModel;
		this.viewModel.stateProperty().addListener(this::onStateSwitched);
	}

	@FXML
	public void initialize() {
		// load work list view.
		FXMLLoader workListLoader = new FXMLLoader(WorkListViewController.class.getResource("WorkListView.fxml"));
		workListLoader.setController(new WorkListViewController(this.viewModel.getWorkList()));
		try {
			Node workListNode = workListLoader.load();
			this.workListTab.setContent(workListNode);
		} catch (IOException e) {
			logger.error("Failed to load WORK LIST view!", e);
		}
		// load work exploration view.
		FXMLLoader workExplorationLoader = new FXMLLoader(WorkExplorationViewController.class.getResource("WorkExplorationView.fxml"));
		workExplorationLoader.setController(new WorkExplorationViewController());
		try {
			Node workExplorationNode = workExplorationLoader.load();
			this.workExplorationTab.setContent(workExplorationNode);
		} catch (IOException e) {
			logger.error("Failed to load WORK EXPLORATION view!", e);
		}
		// make .tab-header-area not visible.
		Platform.runLater(() -> {
			Set<Node> listTabHeaders = this.mainPane.lookupAll(".hidden-tab-pane .tab-header-area");
			for (Node lth: listTabHeaders) {
				if (lth != null) {
					lth.managedProperty().bind(lth.visibleProperty());
					lth.setVisible(false);
				}
			}
			this.tabsPane.getSelectionModel().select(this.workListTab);
		});
	}

	private void onStateSwitched(ObservableValue<? extends OperationState> observable, OperationState oldValue, OperationState newValue) {
		if (newValue == OperationState.EXPLORATION) {
			this.tabsPane.getSelectionModel().select(this.workExplorationTab);
		}
	}
}
