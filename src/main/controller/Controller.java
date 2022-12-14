package main.controller;

/**
 * Controller interface with some basic events that may be used.
 * @author Alexander Tan Ka Jin
 */
public interface Controller {
	//Set root deprecated due to scenemanager
	/**
	 * Called whenever the scene is loaded onto the primary stage.
	 */
	public void OnSceneEnter();
	/**
	 * Called last when before loading the controller.
	 */
	public void finalizeController();
}