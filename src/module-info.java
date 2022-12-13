module game_2048 {
    requires javafx.controls;
    requires javafx.fxml;
	requires org.junit.jupiter.api;
	requires javafx.graphics;
	requires javafx.base;
	requires java.xml;
	
	opens main.controller to javafx.fxml;
	exports main.app to javafx.graphics;
}