package main.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import main.model.CellGrid;
import main.model.GameModel;
import main.model.MoveDirection;

class GameModelTest {
	GameModel instance;
	@Test
	void testConstructor() {
		instance = new GameModel(4,4,23456);
		assertTrue(instance.getCellGrid() != null);
		assertTrue(instance.getRng() != null);
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
	}
	
	@Test
	void testLackOfMove() {
		instance =  new GameModel(4,4,5555);
		CellGrid instanceGrid = instance.getCellGrid();
		int[][] Initial = new int[][] {{0,0,0,1},
									   {0,0,0,2},
									   {0,0,0,2},
									   {0,0,0,2}};

		instanceGrid.setGrid(Initial);
		instance.Move(MoveDirection.RIGHT);
		System.out.print(instance.BoardToString() + "\n");
	}

	@Test
	void testReset() {
		instance =  new GameModel(4,4,23456);
	}
}
