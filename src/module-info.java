module game_2048 {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	
	requires org.junit.jupiter.api;
	
	opens main.controller to javafx.fxml;
	exports main.java to javafx.graphics;
}