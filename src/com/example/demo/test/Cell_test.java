package com.example.demo.test;

import com.example.demo.*;
import javafx.scene.Group;
import javafx.scene.text.Text;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class Cell_test {
	
	@Test
	void test_Constructor() {
		Group testGroup = new Group();
		Cell test = new Cell(0,0,1,testGroup);
		assertEquals(test.getX(), 0);
		assertEquals(test.getY(), 0);
		assertEquals(test.getNumber(), 0);
	}

}
