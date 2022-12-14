package main.controller;

import java.util.Optional;
import java.io.File;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import main.io.SaveFile;
import main.io.SaveLoader;
import main.io.SaveWriter;
import main.io.ScoreFile;
import main.io.ScoreLoader;
import main.io.ScoreWriter;
import main.model.GameModel;
import main.model.MoveDirection;

/**
 * Class that is responsible for acting upon the game scene.
 */
public class GameSceneController implements Controller {
    @FXML private GridPane gridDisplay;
    @FXML private Label hiScoreLabel;
    @FXML private Label scoreLabel;
    
	private GameModel game;
	private int hiScore = 0;
	private long seed;
	private int Width;
	private int Height;
	private Label[][] cellGrid;
	
	private SceneManager scenes;
	private String escScene;
	
	private String savePath;
	private String scorePath;
	
	private SaveFile save;
	private ScoreFile scoreFile;

	/**
	 * Sets the seed of the game
	 * @param seed - Random number generator seed that will be used.
	 * @return The seed that was set
	 */
	public long setSeed(long seed) {
		this.seed = seed;
		return seed;
	}
	
	/**
	 * Sets the sceneManager for this controller
	 * @param scenes - The SceneManager
	 */
	public void setSceneManager(SceneManager scenes) {
		this.scenes = scenes;
	}
	
	/**
	 * Sets the scene that is swapped when esc is pressed
	 * @param name - Name of the scene
	 * @return name
	 */
	public String setEscScene(String name) {
		this.escScene = name;
		return name;
	}
	
	/**
	 * Sets that path to the save file
	 * @param path - Path to the save file
	 * @return The path that was set.
	 */
	public String setSavePath (String path) {
		this.savePath = path;
		return savePath;
	}

	/**
	 * Sets the path to the score file
	 * @param scorePath - path to the score file
	 * @return The path that was set
	 */
	public String setScorePath(String scorePath) {
		this.scorePath = scorePath;
		return scorePath;
	}
	
	/**
	 * Sets the dimensions of the board
	 * @param width - Width of the board
	 * @param height - Height of the board
	 * @return The set dimensions as a size-2 array.
	 */
	public int[] setDimensions (int width,int height) {
		this.Width = width;
		this.Height = height;
		return new int[]{width,height};
	}
	
	/**
	 * Updates the entire grid so that it matches the gameModel in this controller.
	 */
	public void UpdateGridDisplay() {
		for (int y = 0; y < Height; y++) {
			for (int x = 0; x < Width; x++) {
				int value = game.getCellGrid().getCell(x, y);
	
				Label cellText = cellGrid[y][x];
				Rectangle cellBackground = (Rectangle)cellText.getGraphic();
	
				updateCell(cellText, cellBackground, value);
			}
		}
		updateScore();
	}

	/**
	 * Event that triggers when key is pressed, which moves the board of the game
	 * and handles exiting the game when escape is pressed.
	 * @param e - KeyEvent passed by setOnKeyPressed
	 */
	public void OnKeyPressed(KeyEvent e) {
		if (game == null)
			throw new NullPointerException();


		switch(e.getCode()) {
		case LEFT:
			game.Move(MoveDirection.LEFT);
			break;
		case RIGHT:
			game.Move(MoveDirection.RIGHT);
			break;
		case UP:
			game.Move(MoveDirection.UP);
			break;
		case DOWN:
			game.Move(MoveDirection.DOWN);
			break;
		case ESCAPE:
			promptSave();
			scenes.SwapScene(escScene);
			break;
		default:
			break;
		}
		
		UpdateGridDisplay();
		
		if (game.isWin()) {
			endGamePopup(true);
			game.Reset();
		}else if (game.isLose()) {
			endGamePopup(false);
			game.Reset();
		}
			
	}
	
	/**
	 * Finalizes gameSceneController.
	 */
	public void finalizeController() {
		loadScore(new File(scorePath));
		GetCellReferences(Width,Height);
	};
	
	/**
	 * Triggered when the scene is loaded onto the primary stage
	 */
	public void OnSceneEnter() {
		File saveFile = new File(savePath);
		File scoreFile = new File(scorePath);
		game = new GameModel(Width,Height,seed); //create new game
		
		//checks if a save file exists, then tries to load it
		loadScore(scoreFile);
		promptLoad(saveFile);
		
		UpdateGridDisplay();
	}
	
	//Retrieves references to the cells of the board within the FXML
	private void GetCellReferences(int width, int height) {
		cellGrid = new Label[height][width];
		ObservableList<Node> gridChildren = gridDisplay.getChildren();
		
		for (Node cell : gridChildren) {
			if (GridPane.getRowIndex(cell) != null && GridPane.getColumnIndex(cell) != null) {
				int y = GridPane.getRowIndex(cell);
				int x = GridPane.getColumnIndex(cell);
				cellGrid[y][x] = (Label) cell;
			}
		}
	}

	//Updates the score display of the game.
	private void updateScore() {
		int score = game.getScore();
		scoreLabel.setText(Integer.toString(score));
	}
	
	//Updates a single cell's colour by updating it's css with it's 
	//corresponding value given the graphic provided 
	private void updateCellColour (Node graphic, int val) {
		graphic.getStyleClass().clear();
		graphic.getStyleClass().add("cell" + val);
	}

	//Updates a given cell's text and colour
	private void updateCell(Label cellText, Rectangle cell, int val) {
		if (val != 0)
			cellText.setText(Integer.toString(val));
		else
			cellText.setText("");
		
		updateCellColour(cellText.getGraphic(),val);
	}
	
	//Saves the current state of the game
	private void saveState(SaveFile save) {			
		save.setGameState(game);
		SaveWriter.save(savePath, save);
	}
	
	//Svaes the score as the hiscore
	private void saveScore(ScoreFile scorefile) {
		int currentScore = game.getScore();
		this.hiScore = currentScore;
		this.hiScoreLabel.setText(Integer.toString(currentScore));
		scorefile.setHiScore(hiScore);
		ScoreWriter.save(scorePath, scorefile);
	}
	
	//Loads the saved state of the game. An error popup is shown if there was some issues reading the savefile.
	private void loadState(File saveFile) {
		this.save = SaveLoader.loadSave(saveFile.getAbsolutePath(), Width, Height);
		//If savefile cannot be parsed correctly
		if (save == null) {
			AlertPopup.errorPopup("Error loading savefile.");
			this.save = new SaveFile();
			this.save.setGameState(new GameModel(Width,Height,0));
		}
		
		this.game = save.getGameState();
	}
	
	//Loads the current hiscore.
	private void loadScore(File score) {
		if (!score.exists()) {
			this.scoreFile = new ScoreFile();
			this.scoreFile.setHiScore(0);
		}else
			this.scoreFile = ScoreLoader.loadScoreFile(score.getAbsolutePath());
		
		hiScore = scoreFile.getHiScore();
		hiScoreLabel.setText(Integer.toString(hiScore));
	}
	
	//Asks the player if they want to save their progress.
	private void promptSave() {
		Optional<ButtonType> saveConfirmResult = AlertPopup.confirmPopup("Save your progress?");
		
		if (saveConfirmResult.get() == ButtonType.OK) {
			saveState(save);
		}else if (saveConfirmResult.get() == ButtonType.CANCEL) {
			game.Reset();
		}
	}
	
	//Asks the player if they want to load their saved progress
	private void promptLoad(File saveFile) {
		if (saveFile.exists()) {
			Optional<ButtonType> loadConfirmResult = AlertPopup.confirmPopup("Save file found. Continue your progress?");
			
			if (loadConfirmResult.get() == ButtonType.OK) {// load game
				loadState(saveFile);
			}else if (loadConfirmResult.get() == ButtonType.CANCEL) {
				save = new SaveFile();
			}
		}else {
			save = new SaveFile();
		}
	}
	
	
	
	//Popup that is shown that the end of the game. Either they win (true) or lose (false)
	private void endGamePopup(boolean win) {
		String outcomeMsg = (win) ? "You won the game!" : "You lost!";
		String hiScoreNotice = "";
		
		if (game.getScore() > hiScore) {
			hiScoreNotice = " New hiscore reached!";
			saveScore(scoreFile);
		}
		
		String endGameMsg = "Game Over! " + outcomeMsg + " Score: " + game.getScore() + hiScoreNotice + " Start a new game?";
		Optional<ButtonType> newGameConfirmResult = AlertPopup.confirmPopup(endGameMsg);
		
		if (newGameConfirmResult.get() == ButtonType.OK) {
			game.Reset();
			UpdateGridDisplay();
		}else if (newGameConfirmResult.get() == ButtonType.CANCEL) {
			scenes.SwapScene(escScene);
		}
	}

	
}