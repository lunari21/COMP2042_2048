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
	 * Gets the left edge of the grid
	 * @return square grid length
	 */
	public int getLeftEdge() {
		return LEFTCELLS;
	}

	/**
	 * Gets the right edge of the grid
	 * @return square grid length
	 */
	public int getRightEdge() {
		return RIGHTCELLS;
	}

	/**
	 * Gets the bottom edge of the grid
	 * @return square grid length
	 */
	public int getDownEdge() {
		return DOWNCELLS;
	}

	/**
	 * Gets the top edge of the grid
	 * @return square grid length
	 */
	public int getUpEdge() {
		return UPCELLS;
	}

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
	 * Checks if two cellgrids are the same
	 * @param prevState - previous state
	 * @return true if the board state was changed, false if not
	 */
	public boolean gridEquals (CellGrid b) {
		return Arrays.deepEquals(grid, b.getGrid());
	}

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
