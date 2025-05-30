module net.terullet.bibliothekarios.arachne.javafx {
	requires java.desktop;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;

	opens net.terullet.bibliothekarios.arachne.javafx to javafx.fxml;
	exports net.terullet.bibliothekarios.arachne.javafx;
}