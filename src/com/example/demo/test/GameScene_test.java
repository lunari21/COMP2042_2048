package com.example.demo.test;

import com.example.demo.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class GameScene_test {
	@Test
	void testLength() {
		assertEquals(GameScene.getLENGTH(),(700 - ((4 + 1) * 10)) / (double) 4, 0.001);
		GameScene.setN(6);
		assertEquals(GameScene.getLENGTH(),(700 - ((6 + 1) * 10)) / (double) 6, 0.001);
	}
}
