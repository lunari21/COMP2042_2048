package com.example.demo;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Represents a grid of Cell objects
 * @author Alexander Tan Ka Jin
 * @version 1
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
	 * Retrieves cell at position (x,y)
	 * @param x 
	 * @param y
	 * @return The specific cell at x and y
	 */
	public Cell GetCell(int x, int y) {
		return grid[x][y];
	}
	
	/**
	 * Gets the size of the square grid
	 * @return grid size
	 */
	public int GetSize() {
		return grid.length;
	}
	/**
	 * Checks if this grid has zeroes
	 * @return boolean
	 */
	public boolean HasZeroes() {
		for (int x = 0; x < GetSize(); x++) {
			for (int y = 0; y < GetSize(); y++) {
				if (GetCell(x,y).getVal() == 0)
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
}
