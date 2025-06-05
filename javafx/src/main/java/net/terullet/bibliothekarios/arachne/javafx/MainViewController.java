package net.terullet.bibliothekarios.arachne.javafx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

import java.util.Set;

public class MainViewController {
	@FXML
	private Pane mainPane;
	@FXML
	private TabPane listsTab;
	@FXML
	private TabPane contentsTab;
	@FXML
	private Tab workListTab;
	@FXML
	private Tab episodeListTab;

	@FXML
	public void initialize() {
		// make .tab-header-area not visible.
		Platform.runLater(() -> {
			Set<Node> listTabHeaders = this.mainPane.lookupAll(".hidden-tab-pane .tab-header-area");
			for (Node lth: listTabHeaders) {
				if (lth != null) {
					lth.managedProperty().bind(lth.visibleProperty());
					lth.setVisible(false);
				}
			}
		});
	}
}
