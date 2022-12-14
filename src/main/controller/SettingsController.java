package main.controller;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import main.io.PrefFile;
import main.io.PrefWriter;

/**
 * Controller responsible for handling top-level actions in the Settings scene
 * @author Alexander Tan Ka Jin
 */
public class SettingsController implements Controller {
	@FXML
	private Button menuButton;
	@FXML
	private ComboBox<String> themeSelection;
	
	private SceneManager scenes;
	
	private String theme;
	private String menu;
	
	private String cssPath;
	private String prefPath;
	
	/**
	 * Sets the SceneManager for this controller
	 * @param scenes - SceneManager
	 */
	public void setScenes(SceneManager scenes) {
		this.scenes = scenes;
	}

	/**
	 * Sets the initial theme for when the game is loaded
	 * @param theme - The initial theme
	 * @return The theme that was set
	 */
	public String setInitialTheme(String theme) {
		this.theme = theme;
		return theme;
	}
	
	/**
	 * Sets the reference to the menu scene
	 * @param menu - The name of the scene to return to
	 * @return menu
	 */
	public String setMenu(String menu) {
		this.menu = menu;
		return menu;
	}
	
	/**
	 * Sets the path to the CSS files
	 * @param cssPath - path to CSS files
	 * @return cssPath
	 */
	public String setCssPath(String cssPath) {
		this.cssPath = cssPath;
		return cssPath;
	}
	
	/**
	 * Sets the path to the preference savefile
	 * @param prefPath - path to the preference savefile
	 * @return prefPath
	 */
	public String setPrefPath(String prefPath) {
		this.prefPath = prefPath;
		return prefPath;
	}
	
	
	//Saves the theme selected
	private void SaveTheme() {
		PrefFile prefOverride = new PrefFile();
		prefOverride.setCss(scenes.GetActiveTheme());
		PrefWriter.save(prefPath, prefOverride);
	}
	
	//retrieves all css files in a directory
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
			if (!name.matches("template.css")) //template.css is excluded.
				out.add(name);
		}
		
		return out;
	}
	
	//Adds applicable css files to combobox
	private SettingsController addThemes(String dir) {
		List<String> cssFiles = FindCSS(dir);
		for (String theme : cssFiles)
			//Display only name of files
			themeSelection.getItems().add(theme.split("\\.")[0]);
		
		return this;
	}
	
	/**
	 * Finalize this controller
	 */
	public void finalizeController() {
		addThemes(cssPath);
		themeSelection.setPromptText(theme);
		menuButton.setOnAction((e)->{onMenuButtonPress(e);});
		themeSelection.setOnAction((e)->{onComboBoxPick(e);});
	}
	
	/**
	 * Event when combo box picks a certain value
	 * @param e - Passed from setOnAction
	 */
	public void onComboBoxPick(ActionEvent e) {
		this.theme = themeSelection.getValue() + ".css";
		File selectedTheme = new File("src/" + theme);
		if (!selectedTheme.exists())
			AlertPopup.errorPopup("Selected theme does not exist: " + theme);
		else {
			scenes.SetActiveTheme(theme);
			SaveTheme();
		}
	}
	
	/**
	 * Event when menu button was pressed
	 * @param e - Passed from setOnAction
	 */
	public void onMenuButtonPress(ActionEvent e) {
		scenes.SwapScene(menu);
	}

	/**
	 * Event when scene enters
	 */
	public void OnSceneEnter() {
		addThemes(cssPath);
	}
}