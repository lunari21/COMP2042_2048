package main.model;

import java.util.Random;

/**
 * Encapsulates and puts into context the functionality of CellGrid.
 * Manages aspects of the game such as movement of cells, cell spawning and score tracking.
 * @author Alexander Tan Ka Jin
 * @version 2
 */
public class GameModel {
	private CellGrid grid;// game board
	private boolean[][] blocked; // Temporary grid representing cells that have already been merged
	private long seed;
	private Random rng;

	/**
	 * Creates new a model of 2048
	 * @param boardHeight - The height of the grid
	 * @param boardWidth - The width of the grid
	 * @param seed - The rng seed of the game
	 */
	public GameModel(int boardHeight, int boardWidth, long seed) {
		this.grid = new CellGrid(boardWidth, boardHeight);
		this.blocked = new boolean[boardHeight][boardWidth];
		this.seed = seed;
		this.rng = new Random(seed);
		SpawnCell();//Initialize starting state.
	}

	/**
	 * @return This game's cell grid.
	 */
	public CellGrid getCellGrid() {
		return grid;
	}
	
	/**
	 * @return This game's rng seed
	 */
	public long getSeed() {
		return seed;
	}

	/**
	 * @return This game's Random Number Generator
	 */
	public Random getRng() {
		return rng;
	}
	
	/**
	 * @return The score of the game which is caluclated by summing up all the cells on the board.
	 */
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
	 * Checks if you have won the game
	 * @return True if you have won the game, False otherwise
	 */
	public boolean isWin() {
		return grid.contains(2048);
	}

	/**
	 * Checks if you have lost the game
	 * @return True if the game is lost, False otherwise
	 */
	public boolean isLose() {
		return !canMove() && !grid.contains(0);
	}

	/**
	 * Randomly spawns a 2 or 4 valued cell at a random position at the grid
	 * @return This object
	 */
	public GameModel SpawnCell() {
		SetAtRandomCell(getNextCell());
		return this;
	}

	/**
	 * Empties grid
	 * @return This object
	 */
	public GameModel Reset() {
		grid.setGrid(new int[grid.getRightEdge()+1][grid.getDownEdge()+1]); //empty grid
		SpawnCell();//Initialize again
		return this;
	}

	/**
	 * Converts the board into string for ease of saving.
	 * @return A representation of the board as a string. Bars (|) represent cell borders while Percents (%) represent a new row.
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
	 * Moves in accordance with a given direction. If the grid changed states, then advances the turn.
	 * @param dir - The direction to move to (LEFT,RIGHT,UP,DOWN)
	 * @return This object
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
	
	/**
	 * Gets the next cell on the start of a turn.
	 * @return random integer 2 or 4
	 */
	private int getNextCell () {
		return rng.nextInt(1)*2+2; //returns 2 or 4
	}

	/**
	 * Sets a value onto a random cell in it's internal grid
	 * @param val - value
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

	/**
	 * Checks if the board can move by checking if each cell can move right/down
	 * @return True if can move, false elsewise.
	 */
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
	/*
	 * Returns furthest movable position left without merging
	 * The following functions do the same. They are split
	 * Due to the need for scanning in certain directions.
	 */
	
	//Gets the furthest movable position left without merging
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

	//Gets the furthest movable position right without merging
	private int getFurthestPosRight(int posX, int posY) {
		for (int pointer = posX+1; pointer <= grid.getRightEdge(); pointer++) {
			int pointedCell = grid.getCell(pointer,posY);

			if (pointedCell != 0) {
				return pointer-1;
			}
		}
		return grid.getRightEdge();
	}

	//Gets the furthest movable position up without merging
	private int getFurthestPosUp(int posX, int posY) {
		for (int pointer = posY-1; pointer >= grid.getUpEdge(); pointer--) {
			int pointedCell = grid.getCell(posX,pointer);

			if (pointedCell != 0) {
				return pointer+1;
			}
		}
		return grid.getUpEdge();
	}

	//Gets the furthest movable position down without merging
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

	// Gets a valid vector position for moving in 2048 given a certain direction.
	private int[] getValidPos(int posX, int posY, MoveDirection dir) {
		int[] furthest = getFurthestPos(posX,posY,dir); //furthest position if not merging
		int offsetX = 0;
		int offsetY = 0;

		//Retrieve merge offsets in a given direction.
		//These will be used when a merge can occur.
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
		
		int furthestX = furthest[0];
		int furthestY = furthest[1];
		//Check if moving places the cell at the edge
		if (!isEdge(furthestX,furthestY,dir)) {
			//position of a neighbour cell in given direction
			int neighbourPosX = furthestX+offsetX;
			int neighbourPosY = furthestY+offsetY;
			//Check can merge
			boolean canMerge = cellsCanMerge(posX,posY,neighbourPosX,neighbourPosY);

			if (canMerge)
				//Returns neighbour if can merge
				return new int[] {neighbourPosX, neighbourPosY};
			else
				//Returns empty cell next to neighbour elsewise.
				return furthest;
		}

		//If cell is about to go to edge, then the furthest position is returned instead.
		return furthest;
	}
	
	//Moves cell top direction
	private GameModel MoveCell(int x, int y, MoveDirection dir) {
		int pointedCell = grid.getCell(x, y); //get the cell to be moved
		if (pointedCell != 0) {
			int[] dest = getValidPos(x,y,dir); //calculated destination
			boolean destNotBlocked = !blocked[dest[1]][dest[0]];

			grid.setCell(x, y, 0); //reset cell first.
			//This is because destination can be equal to pointed position.

			//If destination is equivalent to the pointedCell, merge.
			if (grid.getCell(dest[0], dest[1]) == pointedCell && destNotBlocked) {
				blocked[dest[1]][dest[0]] = true; //block so that chain merges can't happen.
				pointedCell = pointedCell * 2; //double value.
			}

			grid.setCell(dest[0], dest[1], pointedCell);
		}

		return this;
	}

	/*
	 * Following functions all describe movement. Again, seperated because they need to scan at different directions.
	 */
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

	//Flush the blocked grid and resets it to false.
	private boolean[][] flushBlocked() {
		for (int y = 0; y < blocked.length; y++) {
			for (int x = 0; x < blocked[0].length; x++) {
				blocked[y][x] = false;
			}
		}
		return blocked;
	}
}
