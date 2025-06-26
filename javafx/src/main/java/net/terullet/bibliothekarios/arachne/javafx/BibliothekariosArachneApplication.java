package net.terullet.bibliothekarios.arachne.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.terullet.bibliothekarios.arachne.ui.MainModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BibliothekariosArachneApplication extends Application {
	private static final Logger logger = LogManager.getLogger(BibliothekariosArachneApplication.class);
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(BibliothekariosArachneApplication.class.getResource("MainView.fxml"));
		loader.setController(new MainViewController(new MainViewModel(new MainModel())));
		Scene scene = new Scene(loader.load());
		primaryStage.setTitle("司書のアラクネさん");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		logger.info("Launching bibliothekarios-arachne...");
		launch(args);
	}
}
