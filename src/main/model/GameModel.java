package main.model;

import java.util.Arrays;
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
	private CellGrid grid;// game board
	private boolean[][] blocked; // Temporary grid representing cells that have already been merged
	private long seed;
	private Random rng;

	public GameModel(int boardHeight, int boardWidth, long seed) {
		this.grid = new CellGrid(boardWidth, boardHeight);
		this.blocked = new boolean[boardHeight][boardWidth];
		this.seed = seed;
		this.rng = new Random(seed);
		SpawnCell();//Initialize starting state.
	}

	public CellGrid getCellGrid() {
		return grid;
	}
	
	public long getSeed() {
		return seed;
	}

	public Random getRng() {
		return rng;
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
		return !canMove() && !grid.contains(0);
	}

	/**
	 * Randomly spawns a 2/4 cell at a random position at the grid
	 * @return this object
	 */
	public GameModel SpawnCell() {
		SetAtRandomCell(getNextCell());
		return this;
	}

	/**
	 * Empties grid and resets turn counter
	 * @return this object
	 */
	public GameModel Reset() {
		grid.setGrid(new int[grid.getRightEdge()+1][grid.getDownEdge()+1]); //empty grid
		SpawnCell();//Initialize again
		return this;
	}

	/**
	 * Converts the board into string
	 * @return string
	 */
	public String BoardToString() {
		String out = "";

		for (int y = 0; y <= grid.getDownEdge(); y++) {
			for (int x = 0; x <= grid.getRightEdge(); x++) {
				out = out + grid.getCell(x, y)+'|';
			}
			out = out + "%";
		}
		return out;
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
			MoveLeft();
			break;
		case RIGHT:
			MoveRight();
			break;
		case UP:
			MoveUp();
			break;
		case DOWN:
			MoveDown();
			break;
		}

		//Check state change, which constitutes as a turn.
		if (!grid.gridEquals(prev)) {
			SpawnCell();
			flushBlocked();
		}
		return this;
	}

	public int getScore() {
		int score = 0;
		for (int y = 0; y <= grid.getDownEdge(); y++) {
			for (int x = 0; x <= grid.getRightEdge(); x++) {
				score += grid.getCell(x, y);
			}
		}
		return score;
	}

	/**
	 * Gets the next cell on the start of a turn.
	 * @return random integer 2 or 4
	 */
	private int getNextCell () {
		return rng.nextInt(1)*2+2; //returns 2 or 4
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
			x = rng.nextInt(grid.getRightEdge()+1);
			y = rng.nextInt(grid.getDownEdge()+1);
		}while (grid.getCell(x,y) != 0);

		grid.setCell(x,y,val);
		return this;
	}

	private boolean canMove() {
		for (int y = 0; y <= grid.getDownEdge(); y++) {
			for (int x = 0; x <= grid.getRightEdge(); x++) {
				//No need for generalizing these two conditions. They are only used in this method.
				if (x != grid.getRightEdge()) {
					boolean rightZero = grid.getCell(x+1, y) == 0;
					boolean rightCanMerge = cellsCanMerge(x, y, x+1, y);
					if (rightZero || rightCanMerge)
						return true;
				}

				if (y != grid.getDownEdge()) {
					boolean downZero = grid.getCell(x, y+1) == 0;
					boolean downCanMerge = cellsCanMerge(x, y, x, y+1);
					if (downZero || downCanMerge)
						return true;
				}
			}
		}
		return false;
	}
	/**
	 * Returns furthest movable position left without merging
	 * The following functions do the same. They are split
	 * Due to the need for scanning in certain directions.
	 */
	private int getFurthestPosLeft(int posX, int posY) {
		//To left of cell until the most left cell
		for (int pointer = posX-1; pointer >= grid.getLeftEdge(); pointer--) {
			int pointedCell = grid.getCell(pointer,posY);

			if (pointedCell != 0) {
				return pointer+1;//return right of pointed cell
			}
		}
		return grid.getLeftEdge(); //If there are no cells left of target cell that are non-zero,
				  		 		   //put this cell to the edge cell
	}

	private int getFurthestPosRight(int posX, int posY) {
		for (int pointer = posX+1; pointer <= grid.getRightEdge(); pointer++) {
			int pointedCell = grid.getCell(pointer,posY);

			if (pointedCell != 0) {
				return pointer-1;
			}
		}
		return grid.getRightEdge();
	}

	private int getFurthestPosUp(int posX, int posY) {
		for (int pointer = posY-1; pointer >= grid.getUpEdge(); pointer--) {
			int pointedCell = grid.getCell(posX,pointer);

			if (pointedCell != 0) {
				return pointer+1;
			}
		}
		return grid.getUpEdge();
	}

	private int getFurthestPosDown(int posX, int posY) {
		for (int pointer = posY+1; pointer <= grid.getDownEdge(); pointer++) {
			int pointedCell = grid.getCell(posX,pointer);

			if (pointedCell != 0) {
				return pointer-1;
			}
		}
		return grid.getDownEdge();
	}

	//Gets the furthest position in a certain direction and returns as a 2d vector
	private int[] getFurthestPos(int posX, int posY, MoveDirection dir){
		switch (dir) {
		case LEFT:
			int furthestLeft = getFurthestPosLeft(posX,posY);
			return new int[]{furthestLeft,posY};
		case RIGHT:
			int furthestRight = getFurthestPosRight(posX,posY);
			return new int[]{furthestRight,posY};
		case UP:
			int furthestUp = getFurthestPosUp(posX,posY);
			return new int[]{posX,furthestUp};
		case DOWN:
			int furthestDown = getFurthestPosDown(posX,posY);
			return new int[]{posX,furthestDown};
		}
		return null;
	}

	// Checks if a given cell is at the edge of a certain direction.
	private boolean isEdge(int posX, int posY, MoveDirection dir) {
		switch(dir) {
		case LEFT:
			return posX == grid.getLeftEdge();
		case RIGHT:
			return posX == grid.getRightEdge();
		case UP:
			return posY == grid.getUpEdge();
		case DOWN:
			return posY == grid.getDownEdge();
		}
		return false; //can't get to here anyways
	}

	//Checks if a given cell can merge with a given cell
	private boolean cellsCanMerge(int fromX, int fromY, int toX, int toY) {
		int fromCell = grid.getCell(fromX,fromY);
		int toCell = grid.getCell(toX,toY);
		boolean isToCellBlocked = blocked[toY][toX];

		return fromCell == toCell && !isToCellBlocked;
	}

	/**
	 * Gets a valid vector position for moving in 2048 in the rows
	 */
	private int[] getValidPos(int posX, int posY, MoveDirection dir) {
		int[] furthest = getFurthestPos(posX,posY,dir); //position if not merging
		int offsetX = 0;
		int offsetY = 0;

		switch(dir) {
		case LEFT:
			offsetX = -1;
			break;
		case RIGHT:
			offsetX = +1;
			break;
		case UP:
			offsetY = -1;
			break;
		case DOWN:
			offsetY = +1;
			break;
		}

		if (!isEdge(furthest[0],furthest[1],dir)) {
			//position of a neighbour cell in given direction
			int[] neighbourPos = new int[]{furthest[0]+offsetX,furthest[1]+offsetY};
			boolean canMerge = cellsCanMerge(posX,posY,neighbourPos[0],neighbourPos[1]);

			if (canMerge)
				return neighbourPos;
			else
				return furthest;
		}

		return furthest;
	}

	private GameModel MoveCell(int x, int y, MoveDirection dir) {
		int pointedCell = grid.getCell(x, y);
		if (pointedCell != 0) {
			int[] dest = getValidPos(x,y,dir);
			int newValue = grid.getCell(x,y);
			boolean isDestBlocked = blocked[dest[1]][dest[0]];

			grid.setCell(x, y, 0); //reset cell first.
			//This is because destination can be equal to pointed position.

			if (grid.getCell(dest[0], dest[1]) == newValue && !isDestBlocked) {
				blocked[dest[1]][dest[0]] = true;
				newValue = newValue * 2;
			}

			grid.setCell(dest[0], dest[1], newValue);
		}

		return this;
	}

	private GameModel MoveLeft() {
		//from top to bottom
		for (int y = grid.getUpEdge(); y <= grid.getDownEdge(); y++) {
			//From left edge to right
			for (int x = grid.getLeftEdge(); x <= grid.getRightEdge(); x++) {
				MoveCell(x,y,MoveDirection.LEFT);
			}
		}
		return this;
	}

	private GameModel MoveRight() {
		//from top to bottom
		for (int y = grid.getUpEdge(); y <= grid.getDownEdge(); y++) {
			//From right to left
			for (int x = grid.getRightEdge(); x >= grid.getLeftEdge(); x--) {
				MoveCell(x,y,MoveDirection.RIGHT);
			}
		}
		return this;
	}

	private GameModel MoveUp() {
		//from top to bottom
		for (int y = grid.getUpEdge(); y <= grid.getDownEdge(); y++) {
			//From left edge to right
			for (int x = grid.getLeftEdge(); x <= grid.getRightEdge(); x++) {
				MoveCell(x,y,MoveDirection.UP);
			}
		}
		return this;
	}

	private GameModel MoveDown() {
		//from bottom to top
		for (int y = grid.getDownEdge(); y >= grid.getUpEdge(); y--) {
			//From left edge to right
			for (int x = grid.getLeftEdge(); x <= grid.getRightEdge(); x++) {
				MoveCell(x,y,MoveDirection.DOWN);
			}
		}
		return this;
	}

	private boolean[][] flushBlocked() {
		for (int y = 0; y < blocked.length; y++) {
			for (int x = 0; x < blocked[0].length; x++) {
				blocked[y][x] = false;
			}
		}
		return blocked;
	}
}
