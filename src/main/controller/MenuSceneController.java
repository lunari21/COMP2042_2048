package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Controller responsible for handling top-level actions from the Menu Scene.
 * @author Alexander Tan Ka Jin
 *
 */
public class MenuSceneController implements Controller {
	@FXML
	private Button standardPlayButton;
	@FXML
	private Button fiveXfivePlayButton;
	@FXML
	private Button sevenXsevenPlayButton;
	@FXML
	private Button settingsButton;
	
	private SceneManager scenes;
	private String standardPlay;
	private String fiveXfivePlay;
	private String sevenXsevenPlay;
	private String settings;
	
	/**
	 * Sets the sceneManager for this controller
	 * @param scenes - The SceneManager
	 */
	public void setSceneManager(SceneManager scenes) {
		this.scenes = scenes;
	}
	/**
	 * Sets the name of the scene to be swapped when pressing Standard Play
	 * @param standardPlay - The name of the scene that will be assigned to Standard Play button
	 */
	public void setStandardPlay(String standardPlay) {
		this.standardPlay = standardPlay;
	}
	
	/**
	 * Sets the name of the scene to be swapped when pressing 5x5 play
	 * @param fiveXfivePlay - The name of the scene that will be assigned to 5x5 Play button
	 */
	public void setFiveXFivePlay(String fiveXfivePlay) {
		this.fiveXfivePlay = fiveXfivePlay;
	}
	
	/**
	 * Sets the name of the scene to be swapped when pressing 7x7 play
	 * @param sevenXsevenPlay - The name of the scene that will be assigned to 7x7 play button
	 */
	public void setSevenXSevenPlay(String sevenXsevenPlay) {
		this.sevenXsevenPlay = sevenXsevenPlay;
	}
	
	/**
	 * Sets the name of the scene to be swapped when pressing Settings
	 * @param settings - The name of the scene that will be assigned to Settings button
	 */
	public void setSettings(String settings) {
		this.settings = settings;
	}
	
	//Events for button presses
	@FXML
	private void onStandardPlayButtonPress(ActionEvent e) {
		scenes.SwapScene(standardPlay); //Swap to scene
	}
	
	@FXML
	private void onFiveXFivePlayButtonPress(ActionEvent e) {
		scenes.SwapScene(fiveXfivePlay);
	}
	
	@FXML
	private void onSevenXSevenPlayButtonPress(ActionEvent e) {
		scenes.SwapScene(sevenXsevenPlay);
	}
	
	@FXML
	private void onSettingsButtonPress(ActionEvent e) {
		scenes.SwapScene(settings);
	}
	
	/**
	 * Binds the buttons in menu such that they swap to the scene that they are assigned to.
	 */
	public void finalizeController() {
		standardPlayButton.setOnAction(e -> {onStandardPlayButtonPress(e);});
		fiveXfivePlayButton.setOnAction(e -> {onFiveXFivePlayButtonPress(e);});
		sevenXsevenPlayButton.setOnAction(e -> {onSevenXSevenPlayButtonPress(e);});
		settingsButton.setOnAction(e -> {onSettingsButtonPress(e);});
	}
	
	/**
	 * At the moment does nothing.
	 */
	public void OnSceneEnter() {
		//nothing
	}
}