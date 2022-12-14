package main.app;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import main.controller.AlertPopup;
import main.controller.GameSceneController;
import main.controller.MenuSceneController;
import main.controller.SceneManager;
import main.controller.SettingsController;

import main.io.PrefFile;
import main.io.PrefLoader;
import main.io.PrefWriter;

public class Main extends Application {
    private static final int APPWIDTH = 360;
    private static final int APPHEIGHT = 440;

    private static final String resources = "/main/resources/";
    private static final String absoluteRsc = "src/main/resources/";
    private static final String gameFXML = "scenes/GameScene.fxml";
    private static final String game5x5FXML = "scenes/Game5x5.fxml";
    private static final String game7x7FXML = "scenes/Game7x7.fxml";
    private static final String menuFXML = "scenes/MenuScene.fxml";
    private static final String settingFXML = "scenes/SettingsScene.fxml";
    private static final String savePath = "/savefiles/save.txt";
    private static final String save5x5Path = "/savefiles/save5x5.txt";
    private static final String save7x7Path = "/savefiles/save7x7.txt";
    private static final String scorePath = "/savefiles/score.txt";
    private static final String score5x5Path = "/savefiles/score5x5.txt";
    private static final String score7x7Path = "/savefiles/score7x7.txt";
    private static final String prefPath = "/savefiles/pref.txt";

    private SceneManager activeScenes;
    
    private String loadPreference() {
    	File prefFile = new File(absoluteRsc + prefPath);
    	
    	if (!prefFile.exists()) { //if not exist
    		//Check default css (pink.css)
    		File pinkCSS = new File("src/pink.css");
    		if (!pinkCSS.exists()) //If that doesn't exist then the game does not run.
    			return null;
    		
    		//else, set the theme to be pink.css by default
    		PrefFile defaultPref = new PrefFile();
    		defaultPref.setCss("pink.css");
    		PrefWriter.save(absoluteRsc + prefPath, defaultPref);
    	}
    	
    	//Load the theme
    	return PrefLoader.loadPref(absoluteRsc + prefPath).getCss();
    }
    
    private FXMLLoader load (String fxmlFile) {
    	try {
    		File SceneFile = new File(absoluteRsc + fxmlFile);
    		if (!SceneFile.exists())
    			return null;
    		
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(getClass().getResource(resources + fxmlFile));
    		return loader;
    	}catch (Exception e){
    		e.printStackTrace();
    	}
    	
    	return null;
    }
    
    private Scene loadScene(FXMLLoader loader, String name) {
    	if (loader == null)
    		return null;

    	try {
    		Scene scene = new Scene(loader.<Parent>load(),APPWIDTH,APPHEIGHT);
    		activeScenes.addActiveScene(name, scene);
    		return scene;
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    //inits controller logic and load scene
    private GameSceneController initGame(int Width, int Height, FXMLLoader loader
    					   				, String saveFilePath, String scoreFilePath, String menu, String name) { 	
		
		GameSceneController gameControl = loader.<GameSceneController>getController();
		
		if (gameControl == null)
			return null;
		
		gameControl.setSceneManager(activeScenes);
    	gameControl.setDimensions(Width, Height);
   		gameControl.setSeed((new Date()).getTime());
    		
		gameControl.setSavePath(absoluteRsc + saveFilePath);
		gameControl.setScorePath(absoluteRsc + scoreFilePath);
		gameControl.setEscScene(menu);
		
		gameControl.finalizeController();
		
		//load scene and set keybind
		try {			
			//Sets up key binding
			activeScenes.get(name).setOnKeyPressed(e -> {gameControl.OnKeyPressed(e);});
			//Sets up an event when root swaps to the game scene.
			activeScenes.BindOnEnter(name, gameControl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gameControl;
    }
    
    private MenuSceneController initMenu(FXMLLoader menuLoader, String game, String fivefive, String sevenseven, String settings) {
    	//init controller
    	MenuSceneController menuControl = menuLoader.<MenuSceneController>getController();
    	if (menuControl == null)
    		return null;
    	
    	menuControl.setSceneManager(activeScenes);
    	menuControl.setStandardPlay(game);
    	menuControl.setFiveXFivePlay(fivefive);
    	menuControl.setSevenXSevenPlay(sevenseven);
    	menuControl.setSettings(settings);
    	
    	menuControl.finalizeController();
    	
    	return menuControl;
    }
    
    private SettingsController initSettings(FXMLLoader settingsLoader, String menu , String themePrompt) {
    	SettingsController settingControl = settingsLoader.<SettingsController>getController();
    	
    	if (settingControl == null)
    		return null;
    	
    	settingControl.setScenes(activeScenes);
    	settingControl.setInitialTheme(themePrompt.split("\\.")[0]);
    	settingControl.setCssPath("src/");
    	settingControl.setPrefPath(absoluteRsc + prefPath);
    	settingControl.setMenu(menu);
    	
    	settingControl.finalizeController();
    	return settingControl;
    }
    
    @Override
    public void start(Stage primaryStage) {
    	try {
    		this.activeScenes = new SceneManager(primaryStage);
    		
    		String theme = loadPreference();
    		if (theme == null) {
    			AlertPopup.errorPopup("pink.css not present in src folder.");
    			return;
    		}
    		
    		FXMLLoader menuLoader = load(menuFXML);
    		Scene menu = loadScene(menuLoader, "menu");
    		if (menu == null || menuLoader == null) {
    			AlertPopup.errorPopup("MenuScene not found");
    			return;
    		}
    		
    		FXMLLoader gameLoader = load(gameFXML);
    		Scene game = loadScene(gameLoader, "game");
    		if (game == null || gameLoader == null) {
    			AlertPopup.errorPopup("GameScene not found");
    			return;
    		}
    		
    		FXMLLoader game5x5Loader = load(game5x5FXML);
    		Scene game5x5 = loadScene(game5x5Loader, "game5x5");
    		if (game5x5 == null || game5x5Loader == null) {
    			AlertPopup.errorPopup("Game5x5 not found");
    			return;
    		}
    		
    		FXMLLoader game7x7Loader = load(game7x7FXML);
    		Scene game7x7 = loadScene(game7x7Loader, "game7x7");
    		if (game7x7 == null || game7x7Loader == null) {
    			AlertPopup.errorPopup("Game7x7 not found");
    			return;
    		}
    		
    		FXMLLoader settingsLoader = load(settingFXML);
    		Scene settings = loadScene(settingsLoader, "settings");
    		if (settings == null || settingsLoader == null) {
    			AlertPopup.errorPopup("SettingsScene not found");
    			return;
    		}
    		
    		//Initialize controllers
    		initGame(4, 4, gameLoader, savePath, scorePath, "menu", "game");
    		initGame(5, 5, game5x5Loader, save5x5Path, score5x5Path, "menu", "game5x5");
    		initGame(7, 7, game7x7Loader, save7x7Path, score7x7Path, "menu", "game7x7");
    		initMenu(menuLoader, "game", "game5x5", "game7x7", "settings");
    		initSettings(settingsLoader, "menu", theme.split("\\.")[0]);
    		
    		activeScenes.SetActiveTheme(theme);
    		
    		primaryStage.setTitle("Too Zero For Eight");
        	primaryStage.setScene(menu);
        	primaryStage.show();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}

    }

    public static void main(String[] args) {
        launch(args);
    }
}
