package main.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuSceneController implements Controller {
	@FXML
	private Button standardPlayButton;
	@FXML
	private Button fiveXfivePlayButton;
	@FXML
	private Button sevenXsevenPlayButton;
	@FXML
	private Button settingsButton;
	
	private Stage root;
	private Scene standardPlayScene;
	private Scene fiveXfivePlayScene;
	private Scene sevenXsevenPlayScene;
	private Scene settingsScene;
	
	public Stage setRoot(Stage root) {
		this.root = root;
		return root;
	}
	
	public void setStandardPlayScene(Scene standardPlayScene) {
		this.standardPlayScene = standardPlayScene;
	}
	public void setFiveXFivePlayScene(Scene fiveXfivePlayScene) {
		this.fiveXfivePlayScene = fiveXfivePlayScene;
	}
	
	public void setSevenXSevenPlayScene(Scene sevenXsevenPlayScene) {
		this.sevenXsevenPlayScene = sevenXsevenPlayScene;
	}
	public void setSettingsScene(Scene settingsScene) {
		this.settingsScene = settingsScene;
	}
	
	@FXML
	private void onStandardPlayButtonPress(ActionEvent e) {
		root.setScene(standardPlayScene);
	}
	
	@FXML
	private void onFiveXFivePlayButtonPress(ActionEvent e) {
		root.setScene(fiveXfivePlayScene);
	}
	
	@FXML
	private void onSevenXSevenPlayButtonPress(ActionEvent e) {
		root.setScene(sevenXsevenPlayScene);
	}
	
	@FXML
	private void onSettingsButtonPress(ActionEvent e) {
		root.setScene(settingsScene);
	}
	
	public Controller init() {
		//nothing
		return this;
	}
	
	public Controller bindButtons() {
		standardPlayButton.setOnAction(e -> {onStandardPlayButtonPress(e);});
		fiveXfivePlayButton.setOnAction(e -> {onFiveXFivePlayButtonPress(e);});
		sevenXsevenPlayButton.setOnAction(e -> {onSevenXSevenPlayButtonPress(e);});
		settingsButton.setOnAction(e -> {onSettingsButtonPress(e);});
		return this;
	}
	
	public void OnSceneEnter() {
		//nothing
	}
}
