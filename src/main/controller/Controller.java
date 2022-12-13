package main.controller;

import javafx.stage.Stage;

public interface Controller {
	public Stage setRoot(Stage root);
	public void OnSceneEnter();
	public Controller init();
	public Controller bindButtons();
}
