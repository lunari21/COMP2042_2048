package main.controller;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import main.app.Main;
import main.io.PrefFile;
import main.io.PrefWriter;

public class SettingsController implements Controller {
	@FXML
	private Button menuButton;
	@FXML
	private ComboBox<String> themeSelection;
	
	private Stage root;
	private String theme;
	private Scene menuScene;
	private Main app;
	
	private String cssPath;
	private String prefPath;
	
	public Stage setRoot(Stage root) {
		this.root = root;
		return root;
	}
	
	public String setInitialTheme(String theme) {
		this.theme = theme;
		return theme;
	}
	
	public Scene setMenuScene(Scene menu) {
		this.menuScene = menu;
		return menuScene;
	}
	
	public Main setApp(Main app) {
		this.app = app;
		return app;
	}
	
	public String setCssPath(String cssPath) {
		this.cssPath = cssPath;
		return cssPath;
	}
	
	public String setPrefPath(String prefPath) {
		this.prefPath = prefPath;
		return prefPath;
	}
	
	private void SaveTheme() {
		PrefFile prefOverride = new PrefFile();
		prefOverride.setCss(theme);
		PrefWriter.save(prefPath, prefOverride);
	}
	
	private List<String> FindCSS(String dir) {
		List<String> out = new ArrayList<String>();
		File directory = new File(dir);
		//Find files
		File[] cssFiles = directory.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".css");
			}
		});
		//get names
		for (File css:cssFiles) {
			String name = css.getName();
			if (!name.matches("template.css"))
				out.add(name);
		}
		
		return out;
	}
	
	//Adds themes to combobox
	private SettingsController addThemes(String dir) {
		List<String> cssFiles = FindCSS(dir);
		for (String theme : cssFiles)
			//Display only name of files
			themeSelection.getItems().add(theme.split("\\.")[0]);
		
		return this;
	}
	
	public Controller init() {
		addThemes(cssPath);
		themeSelection.setPromptText(theme);
		return this;
	}
	
	public Controller bindButtons() {
		menuButton.setOnAction((e)->{onMenuButtonPress(e);});
		themeSelection.setOnAction((e)->{onComboBoxPick(e);});
		return this;
	}
	
	public void onComboBoxPick(ActionEvent e) {
		this.theme = themeSelection.getValue() + ".css";
		File selectedTheme = new File("src/" + theme);
		if (!selectedTheme.exists())
			AlertPopup.errorPopup("Selected theme does not exist: " + theme);
		else {
			app.UpdateThemes(theme);
			SaveTheme();
		}
	}
	
	public void onMenuButtonPress(ActionEvent e) {
		root.setScene(menuScene);
	}

	public void OnSceneEnter() {
		addThemes(cssPath);
	}
}