package net.terullet.bibliothekarios.arachne.javafx.work;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class WorkViewController {
	@FXML
	private ListView<Object> workListView;
	@FXML
	private Label workListEmptyLabel;
	@FXML
	private Label workDetailEmptyLabel;
}
