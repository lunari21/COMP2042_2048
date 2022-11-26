package com.example.demo;

import java.util.Random;

/**
 * Models game behaviour
 * This includes, turn advancement and score counting.
 * @author Alexander Tan Ka Jin
 * @version 0
 */
public class GameModel {
	/*
	 * The move methods are in CellGrid instead of here. This class puts those methods in
	 * the context of a single turn of 2048.
	 */
	private CellGrid grid;
	private Random rng;
	private int turn;
	
	public GameModel(int boardHeight, int boardWidth, long seed) {
		this.grid = new CellGrid(boardWidth, boardHeight);
		this.rng = new Random(seed);
		this.turn = 0;
	}
	
	public CellGrid getCellGrid() {
		return grid;
	}

	public Random getRng() {
		return rng;
	}

	public int getTurn() {
		return turn;
	}
	
	public int incrementTurn() {
		return turn++;
	}
	
	public int resetTurn() {
		this.turn = 0;
		return turn;
	}

	/**
	 * Sets a value onto a random cell in it's internal grid
	 * @param val - int
	 * @param rand - Random
	 * @return this object
	 */
	private GameModel SetAtRandomCell(int val) {
		int x,y = 0;
		
		if (!grid.contains(0))
			return this; //do nothing
		
		do { //Try to find a position
			x = rng.nextInt(grid.getWidth());
			y = rng.nextInt(grid.getHeight());
		}while (grid.getCell(x,y) != 0);
		
		grid.setCell(x,y,val);
		return this;
	}
	
	/**
	 * Gets the next cell on the start of a turn.
	 * @return random integer 2 or 4
	 */
	private int GetNextCell () {
		return rng.nextInt(1)*2+2; //returns 2 or 4
	}
	
	
	/**
	 * Checks if you have won the game
	 * @return true if you have won the game, false otherwise
	 */
	public boolean isWin() {
		return grid.contains(2048);
	}
	
	/**
	 * Checks if you have lost the game
	 * @return true if the game is lost, false otherwise
	 */
	public boolean isLose() {
		return !grid.canMove();
	}
	
	/**
	 * Randomly spawns a 2/4 cell at a random position at the grid
	 * @return this object
	 */
	public GameModel SpawnCell() {
		SetAtRandomCell(GetNextCell());
		return this;
	}
	
	/**
	 * Moves in accordance with dir. If the grid changed states, then advances the turn.
	 * @param dir
	 * @return
	 */
	public GameModel Move(MoveDirection dir) {
		CellGrid prev = grid.clone();
		
		switch (dir) {
		case LEFT:
			grid.MoveLeft();
			break;
		case RIGHT:
			grid.MoveRight();
			break;
		case UP:
			grid.MoveUp();
			break;
		case DOWN:
			grid.MoveDown();
			break;
		}
		
		//Check state change, which constitutes as a turn.
		if (!grid.gridEquals(prev)) {
			SpawnCell();
			incrementTurn();
		}
		return this;
	}
	
	/**
	 * Empties grid and resets turn counter
	 * @return this object
	 */
	public GameModel Reset() {
		grid.setGrid(new int[grid.getHeight()][grid.getWidth()]); //empty grid
		this.turn = 0;
		return this;
	}
	
	public String boardToString() {
		String out = "";
		
		for (int y = 0; y < grid.getHeight(); y++) {
			for (int x = 0; x < grid.getWidth(); x++) {
				out = out + "|" + grid.getCell(x, y);
			}
			out = out + "|\n";
		}
		return out;
	}
}
