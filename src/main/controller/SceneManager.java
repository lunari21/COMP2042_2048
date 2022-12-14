package main.controller;

import java.util.HashMap;
import java.util.Map.Entry;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Manages the properties of the scenes from a top-down perspective
 * @author Alexander Tan Ka Jin
 */
public class SceneManager {
	private HashMap<String, Scene> activeScenes;
	private String CSS;
	private Stage root;
	
	/**
	 * Instantiates new scene manager
	 * @param root - sets read-only root
	 */
	public SceneManager(Stage root) {
		this.root = root;
		this.activeScenes = new HashMap<String, Scene>();
	}
	
	/**
	 * adds a scene that will be updated by the SceneManager
	 * @param name - name of the scene
	 * @param scene - the scene to be added
	 * @return A hash map of active scenes with their corresponding names
	 */
	public HashMap<String, Scene> addActiveScene(String name, Scene scene){
		activeScenes.put(name, scene);
		return activeScenes;
	}
	
	/**
	 * Removes and deactivates a scene
	 * @param name - name of the scene
	 * @return A hash map of active scenes with their corresponding names
	 */
	public HashMap<String, Scene> removeActiveScene(String name){
		activeScenes.remove(name);
		return activeScenes;
	}
	
	/**
	 * @param name - Name of the scene
	 * @return The scene given from the name. Null if it does not exist
	 */
	public Scene get(String name) {
		return activeScenes.get(name);
	}
	
	/**
	 * Swaps to a given scene
	 * @param name - name of the scene
	 */
	public void SwapScene(String name) {
		root.setScene(activeScenes.get(name));
	}
	
	/**
	 * Binds onEnter events
	 * @param name - The name of the scene that will be entering
	 * @param SceneController - the controller of that scene
	 */
	public void BindOnEnter(String name, Controller SceneController) {
		root.sceneProperty().addListener((observable, oldScene, newScene) -> {
			Scene enterScene = activeScenes.get(name);
		    if (enterScene != null && newScene == enterScene) {
		    	SceneController.OnSceneEnter();
		    };
		});
	}
	
	/**
	 * Sets the active theme of the game and updates all of the active scenes
	 * @param cssPath - path/name of the CSS file
	 */
	public void SetActiveTheme (String cssPath) {
		CSS = cssPath;
    	for (Entry<String, Scene> scene:activeScenes.entrySet())
    		setStyle(scene.getValue(),cssPath);
    }
	
	/**
	 * @return The currently active CSS file
	 */
	public String GetActiveTheme () {
		return CSS;
	}
	
	//Sets the style of a scene
	private void setStyle(Scene target, String cssPath) {
    	if (cssPath == "")
    		return; //do nothing
    	
    	target.getStylesheets().clear();
    	target.getStylesheets().addAll(cssPath);
    }
}
