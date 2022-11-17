package com.example.demo;

import java.util.Random;

/**
 * Represents an abstract version of the game.
 * @author Alexander Tan Ka Jin
 * @version 3
 */
//Split from various methods in GameScene
//Thus it is pretty much aggregated with that class.
public class CellGrid {
	private int[][] grid; //Grid should actually be an integer.
	//private Cell[][] grid;
	
	//These constants represent each cell at the border of the board.
	//These variables are mostly for the sake of clarity.
	
	//0,0 is top left and n,n is bottom right.
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
	public CellGrid(int gridSize) {
		this.grid = new int[gridSize][gridSize];
		this.RIGHTCELLS = gridSize-1;
		this.DOWNCELLS = gridSize-1;
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
	 * Gets the length of the square grid
	 * @return square grid length
	 */
	public int getLength() {
		return grid.length;
	}
	
	/**
	 * If the grid has a specific value, returns true, else false
	 * @param val - int
	 * @return boolean
	 */
	public boolean contains(int val) {
		for (int y = 0; y < getLength(); y++) {
			for (int x = 0; x < getLength(); x++) {
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
				int pointedCell = getCell(x,y);
				//get their destinations if they were to move right or down.
				int cellValidRight = getValidPosRight(x,y);
				int cellValidDown = getValidPosDown(x,y);
				
				//If 1. cell is 0, 2. cell can move right, 3. cell can move down
				//then the grid can still move
				if (pointedCell == 0 || cellValidRight != x || cellValidDown != y)
					return true;
			}
		}
		return false;
	}
	
	/*
	 * Returns the valid move position left for a given cell in position x and y.
	 * The next few functions do the same for right, up and down respectively.
	 * 
	 * Due to the nature of the method, it is better to just have seperate methods for
	 * each direction.
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
				int targetCell = getCell(posX,posY);
				if (pointedCell == targetCell) //if can merge
					return pointer;
							
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
		for (int pointer = posX+1; pointer <= RIGHTCELLS; pointer++) {
			int pointedCell = getCell(pointer,posY);
			
			if (pointedCell != 0) {
				int targetCell = getCell(posX,posY);
				if (pointedCell == targetCell) //if can merge
					return pointer;
							
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
		for (int pointer = posY-1; pointer >= UPCELLS; pointer--) {
			int pointedCell = getCell(posX,pointer);
			
			if (pointedCell != 0) {
				int targetCell = getCell(posX,posY);
				if (pointedCell == targetCell) //if can merge
					return pointer;
							
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
		for (int pointer = posY+1; pointer <= DOWNCELLS; pointer++) {
			int pointedCell = getCell(posX,pointer);
			
			if (pointedCell != 0) {
				int targetCell = getCell(posX,posY);
				if (pointedCell == targetCell) //if can merge
					return pointer;
							
				return pointer-1;
			}
		}
		return DOWNCELLS;
	}
	
	/**
	 * Moves all cells left 
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
					
					
					if (getCell(destinationX,y) == temp)
						grid[y][destinationX] += temp;// merge
					else
						grid[y][destinationX] = temp;
				}
			}
		}
		return this;
	}
	
	/**
	 * Moves all cells right
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
					
					
					if (getCell(destinationX,y) == temp)
						grid[y][destinationX] += temp;// merge
					else
						grid[y][destinationX] = temp;
				}
			}
		}
		return this;
	}
	
	/**
	 * Moves all cells up
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
					
					
					if (getCell(x,destinationY) == temp)
						grid[destinationY][x] += temp;// merge
					else
						grid[destinationY][x] = temp;
				}
			}
		}
		return this;
	}
	
	/**
	 * Moves all cells down
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
					
					
					if (getCell(x,destinationY) == temp)
						grid[destinationY][x] += temp;// merge
					else
						grid[destinationY][x] = temp;
				}
			}
		}
		return this;
	}
	
	/**
	 * Move to Somewhere else?
	 * Sets a value onto a random cell
	 * @param val - int
	 * @param rand - Random
	 * @return this object
	 */
	public CellGrid SetAtRandomCell(int val, Random rand) {
		int x,y = 0;
		do { //Try to find a position
			x = rand.nextInt(getLength());
			y = rand.nextInt(getLength());
		}while (getCell(x,y) != 0);
		
		setCell(x,y,val);
		return this;
	}
}
