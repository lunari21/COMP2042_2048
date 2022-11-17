package com.example.demo.test;

import com.example.demo.CellGrid;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CellGrid_test {
	CellGrid instance;
	
	@Test
	void testCellGrid() {
		instance = new CellGrid(4);
		int[][] testGrid = new int[4][4];
		
		assertArrayEquals(instance.getGrid(), testGrid);
	}

	@Test
	void testMove() {
		instance = new CellGrid(4);
		int[][] Initial = new int[][] {{0,0,2,0},
									   {2,0,2,0},
									   {4,0,0,2},
									   {2,2,2,2}};
		
		instance.setGrid(Initial);
		
		int[][] IdealLeft = new int[][] {{2,0,0,0},
										 {4,0,0,0},
										 {4,2,0,0},
										 {4,4,0,0}};
		
		instance.MoveLeft();
		
		assertArrayEquals(instance.getGrid(),IdealLeft);
		
		int[][] IdealRight = new int[][] {{0,0,0,2},
										  {0,0,0,4},
										  {0,0,4,2},
										  {0,0,0,8}};
										  
		instance.MoveRight();
											
		assertArrayEquals(instance.getGrid(),IdealRight);
		
		int[][] IdealUp = new int[][] {{0,0,4,2},
			  						   {0,0,0,4},
			  						   {0,0,0,2},
			  						   {0,0,0,8}};
			  
		instance.MoveUp();
				
		assertArrayEquals(instance.getGrid(),IdealUp);
		
		int[][] IdealDown = new int[][] {{0,0,0,2},
										 {0,0,0,4},
										 {0,0,0,2},
										 {0,0,4,8}};

		instance.MoveDown();
		
		assertArrayEquals(instance.getGrid(),IdealDown);
	}
	
	@Test
	void testCanMove() {
		instance = new CellGrid(4);
		
		int[][] Test1 = new int[][] {{4,4,4,4},
									 {4,4,4,4},
									 {4,4,4,4},
									 {4,4,4,4}};
		
		instance.setGrid(Test1);
		
		assertTrue(instance.canMove());
		
		int[][] Test2 = new int[][] {{1,2,3,4},
									 {2,3,4,5},
									 {3,4,5,6},
									 {4,5,6,7}};
		
	    instance.setGrid(Test2);
	    assertFalse(instance.canMove());
	    
	    int[][] Test3 = new int[][] {{1,2,3,4},
									 {2,3,4,5},
									 {3,4,5,6},
									 {4,5,6,0}};
		
		instance.setGrid(Test3);
		assertTrue(instance.canMove());
		
		int[][] Test4 = new int[][] {{4,2,4,2},
			 						 {2,4,2,4},
			 						 {4,2,4,2},
			 						 {2,4,2,4}};

        instance.setGrid(Test4);
        assertFalse(instance.canMove());
	}
}
