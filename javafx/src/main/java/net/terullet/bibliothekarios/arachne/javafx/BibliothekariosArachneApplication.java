package net.terullet.bibliothekarios.arachne.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BibliothekariosArachneApplication extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(BibliothekariosArachneApplication.class.getResource("MainView.fxml"));
		Scene scene = new Scene(loader.load());
		primaryStage.setTitle("司書のアラクネさん");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
