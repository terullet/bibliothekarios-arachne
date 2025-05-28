package net.terullet.bibliothekarios.arachne.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class BibliothekariosArachneApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Label label = new Label("Hello JavaFX!");
        Scene scene = new Scene(label, 400, 300);
        primaryStage.setTitle("Bibliothekarios Arachne");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
