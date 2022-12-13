package main.model;

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
	 * @param gridScaleX - the width of the grid
	 * @param gridScaleY - the height of the grid
	 */
	public CellGrid(int gridScaleX,int gridScaleY) {
		this.grid = new int[gridScaleY][gridScaleX];
		this.RIGHTCELLS = gridScaleX-1;
		this.DOWNCELLS = gridScaleY-1;
	}

	/**
	 * Retrieves cell at position (x,y) between (0,0) to (n-1,n-1), n being the
	 * Length of the grid.
	 * @param x - x position of the cell
	 * @param y - y position of the cell
	 * @return The specific cell at x and y
	 */
	public int getCell(int x, int y) {
		return grid[y][x];
	}

	/**
	 * Sets the value of a cell on coordinates x and y
	 * @param x - x position of the cell
	 * @param y - y position of the cell
	 * @param val - New value of the cell
	 * @return Returns this object.
	 */
	public CellGrid setCell(int x, int y, int val) {
		grid[y][x] = val;
		return this;
	}

	/**
	 * Returns the whole grid, mainly used for testing and display.
	 * @return int[][] - The grid represented as a 2d array of integers
	 */
	public int[][] getGrid(){
		return grid;
	}

	/**
	 * Sets a 2d integer grid as this object's grid.
	 * @param newGrid - The new value of this object's 2d array
	 * @return Regardless of whether newGrid is compatible, returns this object.
	 */
	public CellGrid setGrid(int[][] newGrid) {
		try {
			for (int y = 0; y <= DOWNCELLS; y++) {
				for (int x = 0; x <= RIGHTCELLS; x++) {
					setCell(x, y, newGrid[y][x]);
				}
			}
			return this;
		}catch(Exception e) {
			e.printStackTrace();
			return this;
		}
	}

	/**
	 * Gets the left edge of the grid
	 * @return The first index of the grid when going from left to right.
	 */
	public int getLeftEdge() {
		return LEFTCELLS;
	}

	/**
	 * Gets the right edge of the grid
	 * @return The last index of the grid when going from left to right.
	 */
	public int getRightEdge() {
		return RIGHTCELLS;
	}

	/**
	 * Gets the bottom edge of the grid
	 * @return The last index of the grid when going from top to bottom.
	 */
	public int getDownEdge() {
		return DOWNCELLS;
	}

	/**
	 * Gets the top edge of the grid
	 * @return The first index of the grid when going from top to bottom.
	 */
	public int getUpEdge() {
		return UPCELLS;
	}

	/**
	 * Gets a specified edge based on the direction dir.
	 * @param dir - The direction of the edge (LEFT, RIGHT, UP, DOWN)
	 * @return The value of the edge based on dir.
	 */
	public int getEdge(MoveDirection dir) {
		switch (dir) {
		case LEFT:
			return LEFTCELLS;
		case RIGHT:
			return RIGHTCELLS;
		case UP:
			return UPCELLS;
		case DOWN:
			return DOWNCELLS;
		}
		return -1;
	}

	/**
	 * Checks if the grid has a specified val.
	 * @param val - the value that is going to be checked.
	 * @return Whether the value is present in the grid.
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
	 * Checks if two cellGrids are the same
	 * @param b - A second cellGrid to be compared.
	 * @return True if it is equivalent. False if not.
	 */
	public boolean gridEquals (CellGrid b) {
		return Arrays.deepEquals(grid, b.getGrid());
	}

	/**
	 * Override from java.lang.Object.
	 * @return A copy of this object.
	 */
	@Override
	public CellGrid clone (){
		CellGrid out = new CellGrid(RIGHTCELLS+1,DOWNCELLS+1);
		for (int y = 0; y <= DOWNCELLS; y++) {
			for (int x = 0; x <= RIGHTCELLS; x++) {
				out.setCell(x, y, getCell(x,y));
			}
		}
		return out;
	}
}
