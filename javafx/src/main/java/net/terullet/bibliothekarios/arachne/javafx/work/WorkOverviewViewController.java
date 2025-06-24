package net.terullet.bibliothekarios.arachne.javafx.work;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class WorkOverviewViewController extends ListCell<WorkOverviewViewModel> {
	private final static Logger logger = LogManager.getLogger(WorkOverviewViewController.class);
	@FXML
	private Pane pane;
	@FXML
	private Label titleLabel;

	public WorkOverviewViewController(ListView<WorkOverviewViewModel> container) {
		FXMLLoader fxmlLoader = new FXMLLoader(WorkOverviewViewController.class.getResource("WorkOverviewView.fxml"));
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
			this.pane.maxWidthProperty().bind(container.widthProperty().subtract(24));
		} catch (IOException e) {
			logger.error("Failed to load WorkOverViewView.fxml.", e);
		}
	}

	@Override
	protected void updateItem(WorkOverviewViewModel item, boolean empty) {
		super.updateItem(item, empty);
		if (empty || item == null) {
			this.setGraphic(null);
		} else {
			this.titleLabel.textProperty().unbind();
			this.titleLabel.textProperty().bind(item.titleProperty());
			this.setGraphic(pane);
		}
	}
}
