package main.io;

import main.model.GameModel;

/**
 * Class responsible for holding the seed of a game and the board state of a game when it is being saved.
 * @author Alexander Tan Ka Jin
 *
 */
public class SaveFile implements ISaveData{
	private GameModel gameState;
	
	/**
	 * Instantiates new SaveFile with null gameState by default.
	 */
	public SaveFile() {
		this.gameState = null;
	}
	
	/**
	 * @return The state of the game contained within SaveFile.
	 */
	public GameModel getGameState() {
		return gameState;
	}
	
	/**
	 * Sets the gamestate that needs to be saved
	 * @param gameState - The state of the game when it was saved.
	 */
	public void setGameState(GameModel gameState) {
		this.gameState = gameState;
	}
}
