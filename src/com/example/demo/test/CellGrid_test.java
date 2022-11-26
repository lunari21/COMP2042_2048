package com.example.demo.test;

import com.example.demo.CellGrid;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CellGrid_test {
	CellGrid instance;
	
	@Test
	void testCellGrid() {
		instance = new CellGrid(4,5);
		int[][] testGrid = new int[5][4];
		
		assertArrayEquals(instance.getGrid(), testGrid);
	}
}
