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
public class CellGrid {
	private Group root;
	private Cell[][] grid; //Always a square
	
	public Group getRoot() {
		return root;
	}

	public Cell[][] getGrid() {
		return grid;
	}

	public void setGrid(Cell[][] grid) {
		this.grid = grid;
	}

	public CellGrid(int gridSize, int sceneLength, int cellDistance, Group root) {
		this.root = root;
		this.grid = GenerateDefault(gridSize, sceneLength, cellDistance, root);
	}
	
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
}
