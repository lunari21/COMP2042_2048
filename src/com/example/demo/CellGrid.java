package com.example.demo;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Enum representing direction, used in moving cells
 * @author Alexander Tan Ka Jin
 */
enum DIRECTION{
	LEFT,
	RIGHT,
	UP,
	DOWN
}
/**
 * Represents a grid of Cell objects
 * @author Alexander Tan Ka Jin
 * @version 2
 */
//Split from various methods in GameScene
//Thus it is pretty much aggregated with that class.
public class CellGrid {
	private Group root;
	private Cell[][] grid; //Always a square
	
	/**
	 * Constructor for cell grid
	 * @param gridSize - Size of this square grid
	 * @param sceneLength - Length of the game scene
	 * @param cellDistance - Distance between each cells
	 * @param root - The game root.
	 */
	public CellGrid(int gridSize, int sceneLength, int cellDistance, Group root) {
		this.root = root;
		this.grid = GenerateDefault(gridSize, sceneLength, cellDistance, root);
	}
	
	/**
	 * Gets the root of this grid
	 * @return Group
	 */ 
	public Group getRoot() {
		return root;
	}

	/**
	 * Gets the grid
	 * @return Cell[][]
	 */
	public Cell[][] getGrid() {
		return grid;
	}

	/**
	 * Directly sets the grid. Not recommended as this class has methods of editing it's own grid.
	 * @param grid - Cell[][]
	 */
	public void setGrid(Cell[][] grid) {
		this.grid = grid;
	}

	/**
	 * Creates an empty grid of cells, each rectangle separated by the parameters.
	 * @param gridSize - amount of cells in the grid
	 * @param sceneLength - Length of the scene
	 * @param cellDistance - distance between cells
	 * @param root - root group
	 * @return cell grid with default settings.
	 */
	public Cell[][] GenerateDefault(int gridSize, int sceneLength, int cellDistance, Group root) {
		Cell[][] outGrid = new Cell[gridSize][gridSize];
		
		//generate a new grid
		for (int i = 0; i < gridSize; i++)
			for (int j = 0; j < gridSize; j++) {
				int xPos = j * sceneLength + (j + 1) * cellDistance;
				int yPos = i * sceneLength + (i + 1) * cellDistance;
				
				outGrid[i][j] = new Cell(xPos,yPos, sceneLength, root, sceneLength);
			}
		
		return outGrid;
	}
	
	/**
	 * Retrieves cell at position (x,y) between (0,0) to (n-1,n-1), n being the
	 * Length of the grid.
	 * @param x 
	 * @param y
	 * @return The specific cell at x and y
	 */
	public Cell GetCell(int x, int y) {
		return grid[x][y];
	}
	
	/**
	 * Gets the length of the square grid
	 * @return square grid length
	 */
	public int GetLength() {
		return grid.length;
	}
	/**
	 * Checks if this grid has zeroes
	 * @return boolean
	 */
	public boolean HasZeroes() {
		for (int x = 0; x < GetLength(); x++) {
			for (int y = 0; y < GetLength(); y++) {
				if (GetCell(x,y).equals(0))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if this grid has a 2048 (The win condition)
	 * @return boolean
	 */
	public boolean HasWinCondition() {
		for (int x = 0; x < GetLength(); x++) {
			for (int y = 0; y < GetLength(); y++) {
				if (GetCell(x,y).equals(2048))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * Hides all zeroes in the grid.
	 */
	public void CullZeroes() {
		//Unimplemented!
	}
	
	/**
	 * Returns the valid move position left for a given cell in position x and y.
	 * The next few functions do the same for right, up and down respectively.
	 * The only valid position for 2048 is the edge (or furthest) position in a given 
	 * direction given that cells cannot overlap each other.
	 * 
	 * Due to the nature of the method, it is better to just have seperate method for
	 * each direction. The alternative is to change internal variables in a method to account for every possibility
	 * which is messier to read.
	 */
	private int validPosLeft(int posX, int posY) {
		int leftEdge = 0;
		for (int pointer = posX-1; pointer >= leftEdge; pointer--) {
			//Pointer iterates from left of this cell to the edge
			//Continuously move left until the pointer points to a non-empty cell
			if (!GetCell(pointer,posY).equals(0))
				return pointer+1; //return right of pointer
		}
		return leftEdge; //If there are no cells left of target cell that are non-zero, 
				  		 //put this cell to the edge cell
	}
	
	private int validPosRight(int posX, int posY) {
		int rightEdge = GetLength()-1;
		for (int pointer = posX+1; pointer <= rightEdge; pointer++) {
			if (!GetCell(pointer,posY).equals(0))
				return pointer-1;
		}
		return rightEdge;
	}
	
	private int validPosUp(int posX, int posY) {
		int topEdge = 0;
		for (int pointer = posY-1; pointer >= topEdge; pointer++) {
			if (!GetCell(posX,pointer).equals(0))
				return pointer+1;
		}
		return topEdge;
	}
	
	private int validPosDown(int posX, int posY) {
		int bottomEdge = GetLength()-1;
		for (int pointer = posY+1; pointer <= bottomEdge; pointer++) {
			if (!GetCell(posX,pointer).equals(0))
				return pointer-1;
		}
		return bottomEdge;
	}
	
	/**
	 * Returns a valid position to move to in the direction of of argument dir
	 * The game only allows valid move positions if it is the furthest move position 
	 * possible in that direction
	 * @param posX - Position X of cell to move
	 * @param posY - Position Y of cell to move
	 * @param dir - Direction to move to
	 * @return int - The valid move destination in the direction of dir
	 */
	public int CalculateValidMovePosition (int posX, int posY, DIRECTION dir) {
		switch (dir) {
		case LEFT:
			return validPosLeft(posX,posY);
		case RIGHT:
			return validPosRight(posX,posY);
		case UP:
			return validPosUp(posX,posY);
		case DOWN:
			return validPosDown(posX,posY);
		default:
			return -1; //impossible to get to here. Just here else java throws a fit.
					   //(Unless you tampered with the code)
		}
	}
}
