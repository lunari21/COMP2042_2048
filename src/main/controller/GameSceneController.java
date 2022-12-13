package main.controller;

import java.util.Optional;
import java.io.File;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import main.io.SaveFile;
import main.io.SaveLoader;
import main.io.SaveWriter;
import main.io.ScoreFile;
import main.io.ScoreLoader;
import main.io.ScoreWriter;
import main.model.GameModel;
import main.model.MoveDirection;

/**
 * Main class that is responsible for acting upon the game scene.
 * @throws NullPointerException
 */
public class GameSceneController implements Controller {
    @FXML
    private GridPane gridDisplay;
    @FXML
    private Label hiScoreLabel;
    @FXML
    private Label scoreLabel;
    
    private Stage root;
	private int hiScore = 0;
	private long seed;
	private GameModel game;
	private String savePath;
	private String scorePath;
	private Scene menu;
	private int Width;
	private int Height;
	private Label[][] cellGrid;
	
	private SaveFile save;
	private ScoreFile scoreFile;

	public Stage setRoot(Stage root) {
		this.root = root;
		return root;
	}

	public long setSeed(long seed) {
		this.seed = seed;
		return seed;
	}
	
	public String setSavePath (String path) {
		this.savePath = path;
		return savePath;
	}
	
	public Scene setMenuSceneReference (Scene menu) {
		this.menu = menu;
		return menu;
	}

	public String setScorePath(String scorePath) {
		this.scorePath = scorePath;
		return scorePath;
	}
	
	public int[] setDimensions (int width,int height) {
		this.Width = width;
		this.Height = height;
		return new int[]{width,height};
	}
	
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

	private void updateScore() {
		int score = game.getScore();
		scoreLabel.setText(Integer.toString(score));
	}
	
	private void updateCellColour (Node graphic, int val) {
		graphic.getStyleClass().clear();
		graphic.getStyleClass().add("cell" + val);
	}

	private void updateCell(Label cellText, Rectangle cell, int val) {
		if (val != 0)
			cellText.setText(Integer.toString(val));
		else
			cellText.setText("");
		
		updateCellColour(cellText.getGraphic(),val);
	}
	
	private void saveState(SaveFile save) {			
		save.setGameState(game);
		SaveWriter.save(savePath, save);
	}
	
	private void saveScore(ScoreFile scorefile) {
		int currentScore = game.getScore();
		this.hiScore = currentScore;
		this.hiScoreLabel.setText(Integer.toString(currentScore));
		scorefile.setHiScore(hiScore);
		ScoreWriter.save(scorePath, scorefile);
	}
	
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
	
	private void promptSave() {
		Optional<ButtonType> saveConfirmResult = AlertPopup.confirmPopup("Save your progress?");
		
		if (saveConfirmResult.get() == ButtonType.OK) {
			saveState(save);
		}else if (saveConfirmResult.get() == ButtonType.CANCEL) {
			game.Reset();
		}
	}
	
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
	
	private void loadScore(File score) {
		if (!score.exists()) {
			this.scoreFile = new ScoreFile();
			this.scoreFile.setHiScore(0);
		}else
			this.scoreFile = ScoreLoader.loadScoreFile(score.getAbsolutePath());
		
		hiScore = scoreFile.getHiScore();
		hiScoreLabel.setText(Integer.toString(hiScore));
	}
	
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
			root.setScene(menu);
		}
	}

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
			root.setScene(menu);
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
	
	public Controller init() {
		loadScore(new File(scorePath));
		GetCellReferences(Width,Height);
		return this;
	};
	//Does not require binding outside of binding OnKeyPressed
	public Controller bindButtons() {return this;};
	
	public void OnSceneEnter() {
		File saveFile = new File(savePath);
		File scoreFile = new File(scorePath);
		game = new GameModel(Width,Height,seed); //create new game
		
		//checks if a save file exists, then tries to load it
		loadScore(scoreFile);
		promptLoad(saveFile);
		
		UpdateGridDisplay();
	}
}