package main.tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import main.model.CellGrid;

class CellGrid_test {
	CellGrid instance;

	@Test
	void testCellGrid() {
		instance = new CellGrid(4,5);
		int[][] testGrid = new int[5][4];

		assertArrayEquals(instance.getGrid(), testGrid);
	}
}
