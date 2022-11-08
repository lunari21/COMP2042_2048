package com.example.demo.test;

import com.example.demo.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class GameScene_test {
	final GameScene instance = new GameScene();
	
	@Test
	void testLength() {
		assertEquals(instance.getLength(),(700 - ((4 + 1) * 10)) / (double) 4, 0.001);
		instance.setN(6);
		assertEquals(instance.getLength(),(700 - ((6 + 1) * 10)) / (double) 6, 0.001);
	}
}
