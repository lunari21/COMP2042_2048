package main.io;

import main.model.GameModel;

/**
 * Class responsible for handling gamedata in a txt file
 * Text file format follows as such:
 * hiscore
 * game seed
 * game board
 * @author Alexander Tan Ka Jin
 *
 */
public class SaveFile implements ISaveData{
	private GameModel gameState;
	
	public SaveFile() {
		this.gameState = null;
	}
	
	public GameModel getGameState() {
		return gameState;
	}
	public void setGameState(GameModel gameState) {
		this.gameState = gameState;
	}
}
