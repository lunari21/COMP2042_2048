package com.example.demo.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import com.example.demo.CellGrid;
import com.example.demo.GameModel;
import com.example.demo.MoveDirection;

import org.junit.jupiter.api.Test;

class GameModelTest {
	GameModel instance;
	@Test
	void testConstructor() {
		instance = new GameModel(4,4,23456);
		assertTrue(instance.getCellGrid() != null);
		assertTrue(instance.getRng() != null);
		assertTrue(instance.getTurn() == 0);
		assertEquals(instance.getCellGrid().getRightEdge()+1, 4);
		assertEquals(instance.getCellGrid().getDownEdge()+1, 4);
	}

	@Test
	void testSpawning() {
		instance = new GameModel(4,4,23456);
		CellGrid instanceGrid = instance.getCellGrid();
		
		
		CellGrid prev = instanceGrid.clone();
		instance.SpawnCell();
		
		assertTrue(!instanceGrid.gridEquals(prev));
		assertTrue(instanceGrid.contains(2) || instanceGrid.contains(4));
		
		//Spawn until full
		for (int i = 0; i < 16-1; i++) {
			instance.SpawnCell();
		}
		
		assertFalse(instance.isLose());
		assertTrue(!instanceGrid.contains(0));
	}
	
	@Test
	void testMove() {
		instance =  new GameModel(4,4,5555);
		CellGrid instanceGrid = instance.getCellGrid();
		int[][] Initial = new int[][] {{0,0,2,0},
									   {2,0,2,0},
									   {4,0,0,2},
									   {2,2,2,2}};
		
		instanceGrid.setGrid(Initial);
		
		instance.Move(MoveDirection.LEFT);
		System.out.print(instance.BoardToString() + "\n");
		instance.Move(MoveDirection.RIGHT);
		System.out.print(instance.BoardToString() + "\n");
		instance.Move(MoveDirection.UP);
		System.out.print(instance.BoardToString() + "\n");
		instance.Move(MoveDirection.DOWN);
		System.out.print(instance.BoardToString() + "\n");
		
		assertEquals(instance.getTurn(), 4);
	}
	
	@Test
	void testReset() {
		instance =  new GameModel(4,4,23456);
	}
}
