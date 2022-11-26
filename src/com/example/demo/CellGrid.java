package com.example.demo;

import java.util.Arrays;

/**
 * Represents an abstract version of the game.
 * @author Alexander Tan Ka Jin
 * @version 3
 */
public class CellGrid {
	private int[][] grid;
	
	//0,0 is top left and n,n is bottom right.
	/* The alternative (getHeight, getWidth) is more confusing I think. 
	 * Especially in getValidPos methods when you could be iterating from -1 to getHeight-1 or 
	 * getHeight-2 to -1. In that situation, seeing the grid in terms of cells makes more sense.
	 */
	private int LEFTCELLS = 0; 
	private int RIGHTCELLS = 0; //Not initialized as length is not set.
	private int UPCELLS = 0; 
	private int DOWNCELLS = 0; 
	
	/**
	 * Constructor for cell grid
	 * @param gridSize - Size of this square grid
	 * @param sceneLength - Length of the game scene
	 * @param cellDistance - Distance between each cells
	 * @param root - The game root.
	 */
	public CellGrid(int gridScaleX,int gridScaleY) {
		this.grid = new int[gridScaleY][gridScaleX];
		this.RIGHTCELLS = gridScaleX-1;
		this.DOWNCELLS = gridScaleY-1;
	}
	
	/**
	 * Retrieves cell at position (x,y) between (0,0) to (n-1,n-1), n being the
	 * Length of the grid.
	 * @param x 
	 * @param y
	 * @return The specific cell at x and y
	 */
	public int getCell(int x, int y) {
		return grid[y][x];
	}
	
	public CellGrid setCell(int x, int y, int val) {
		grid[y][x] = val;
		return this;
	}
	
	/**
	 * Returns the whole grid, mainly used for testing and display.
	 * @return int[][]
	 */
	public int[][] getGrid(){
		return grid;
	}
	
	/**
	 * Sets integer grid g to this object's grid.
	 * @param g - int[][]
	 * @return regardless of whether g is compatible, returns this object.
	 */
	public CellGrid setGrid(int[][] g) {
		try {
			for (int y = 0; y <= DOWNCELLS; y++) {
				for (int x = 0; x <= RIGHTCELLS; x++) {
					setCell(x, y, g[y][x]);
				}
			}
			return this;
		}catch(Exception e) {
			e.printStackTrace();
			return this;
		}
	}
	
	/**
	 * Gets the height of the grid
	 * @return square grid length
	 */
	public int getHeight() {
		return DOWNCELLS+1;
	}
	
	/**
	 * Gets the width of the grid
	 * @return square grid length
	 */
	public int getWidth() {
		return RIGHTCELLS+1;
	}
	
	/**
	 * If the grid has a specific value, returns true, else false
	 * @param val - int
	 * @return boolean
	 */
	public boolean contains(int val) {
		for (int y = 0; y <= DOWNCELLS; y++) {
			for (int x = 0; x <= RIGHTCELLS; x++) {
				if (getCell(x,y) == val)
					return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks every cell if they can move. If they can't then returns false.
	 * @return true if can move, false if not.
	 */
	public boolean canMove() {
		for (int y = 0; y <= DOWNCELLS; y++) {
			for (int x = 0; x <= RIGHTCELLS; x++) {
				//get their destinations if they were to move right or down.
				int cellValidRight = getValidPosRight(x,y);
				int cellValidDown = getValidPosDown(x,y);
				
				/*If 1. cell can move right, 2. cell can move down
				 *then the grid can still move, since if they can move right/down, 
				 *they can move left/up
				 */
				if (cellValidRight != x || cellValidDown != y)
					return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if two cellgrids are the same
	 * @param prevState - previous state
	 * @return true if the board state was changed, false if not
	 */
	public boolean gridEquals (CellGrid b) {
		return Arrays.deepEquals(grid, b.getGrid());
	}
	
	public CellGrid clone (){
		CellGrid out = new CellGrid(getWidth(),getHeight());
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				out.setCell(x, y, getCell(x, y));
			}
		}
		return out;
	}
	
	/*
	 * Returns the valid move position left for a given cell in position x and y.
	 * The next few functions do the same for right, up and down respectively.
	 * 
	 * Due to the nature of the method (scanning in a specific direction),
	 * it is better to just have seperate methods for each direction.
	 */
	
	/**
	 * Returns a X position in the left of a cell that complies with the rules of 2048
	 * @param posX - Cell X position
	 * @param posY - Cell Y position
	 * @return X position that is valid
	 */
	private int getValidPosLeft(int posX, int posY) {
		//To left of cell until the most left cell
		for (int pointer = posX-1; pointer >= LEFTCELLS; pointer--) {
			int pointedCell = getCell(pointer,posY);
			
			if (pointedCell != 0) {		
				return pointer+1;//else return right of pointed cell
			}
		}
		return LEFTCELLS; //If there are no cells left of target cell that are non-zero, 
				  		 //put this cell to the edge cell
	}
	
	/**
	 * Returns a X position in the right of a cell that complies with the rules of 2048
	 * @param posX - Cell X position
	 * @param posY - Cell Y position
	 * @return X position that is valid
	 */
	private int getValidPosRight(int posX, int posY) {
		//right neighbor to right
		for (int pointer = posX+1; pointer <= RIGHTCELLS; pointer++) {
			int pointedCell = getCell(pointer,posY);
			
			if (pointedCell != 0) {
				return pointer-1;
			}
		}
		return RIGHTCELLS;
	}
	
	/**
	 * Returns a Y position above a cell that complies with the rules of 2048
	 * @param posX - Cell X position
	 * @param posY - Cell Y position
	 * @return Y position that is valid
	 */
	private int getValidPosUp(int posX, int posY) {	
		//neighbor above to top
		for (int pointer = posY-1; pointer >= UPCELLS; pointer--) {
			int pointedCell = getCell(posX,pointer);
			
			if (pointedCell != 0) {
				return pointer+1;
			}
		}
		return UPCELLS;
	}
	
	/**
	 * Returns a Y position below a cell that complies with the rules of 2048
	 * @param posX - Cell X position
	 * @param posY - Cell Y position
	 * @return Y position that is valid
	 */
	private int getValidPosDown(int posX, int posY) {
		//neighbor below to bottom
		for (int pointer = posY+1; pointer <= DOWNCELLS; pointer++) {
			int pointedCell = getCell(posX,pointer);
			
			if (pointedCell != 0) {
				return pointer-1;
			}
		}
		return DOWNCELLS;
	}
	
	/**
	 * Moves all cells left 
	 * @return this object
	 */
	public CellGrid MoveLeft() {
		//from top to bottom
		for (int y = UPCELLS; y <= DOWNCELLS; y++) {
			//From left edge to right
			for (int x = LEFTCELLS; x <= RIGHTCELLS; x++) {
				if (getCell(x,y) != 0) {
					int destinationX = getValidPosLeft(x,y);//possible range [x,LEFTCELLS]
					int temp = getCell(x,y);
					grid[y][x] = 0; //reset cell first. 
					//This is because destinationX can be equal to x.
					grid[y][destinationX] = temp;
				}
			}
		}
		return this;
	}

	/**
	 * Moves all cells right
	 * @return this object
	 */
	public CellGrid MoveRight() {
		//from top to bottom
		for (int y = UPCELLS; y <= DOWNCELLS; y++) {
			//From right to left
			for (int x = RIGHTCELLS; x >= LEFTCELLS; x--) {
				if (getCell(x,y) != 0) {
					int destinationX = getValidPosRight(x,y);//possible range [x,LEFTCELLS]
					int temp = getCell(x,y);
					grid[y][x] = 0; //reset cell
					grid[y][destinationX] = temp;
				}
			}
		}
		return this;
	}
	
	/**
	 * Moves all cells up
	 * @return this object
	 */
	public CellGrid MoveUp() {
		//from top to bottom
		for (int y = UPCELLS; y <= DOWNCELLS; y++) {
			//From right to left
			for (int x = LEFTCELLS; x <= RIGHTCELLS; x++) {
				if (getCell(x,y) != 0) {
					int destinationY = getValidPosUp(x,y);//possible range [x,LEFTCELLS]
					int temp = getCell(x,y);
					grid[y][x] = 0; //reset cell
					grid[destinationY][x] = temp;
				}
			}
		}
		return this;
	}
	
	/**
	 * Moves all cells down
	 * @return this object
	 */
	public CellGrid MoveDown() {
		//from top to bottom
		for (int y = DOWNCELLS; y >= UPCELLS; y--) {
			//From right to left
			for (int x = LEFTCELLS; x <= RIGHTCELLS; x++) {
				if (getCell(x,y) != 0) {
					int destinationY = getValidPosDown(x,y);//possible range [x,LEFTCELLS]
					int temp = getCell(x,y);
					grid[y][x] = 0; //reset cell
					grid[destinationY][x] = temp;
				}
			}
		}
		return this;
	}
	
	public CellGrid MergeLeft() {
		//wip
	}
}
