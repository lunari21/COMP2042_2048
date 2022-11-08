package com.example.demo.test;

import com.example.demo.*;
import javafx.scene.Group;
import javafx.scene.text.Text;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class Cell_test {
	private Group testRoot = new Group();
	private Cell test1 = new Cell(0,0,1,testRoot,700);
	private Cell test2  = new Cell(1,2,1,testRoot,700);
	
	@Test
	void test_Constructor() {
		assertEquals(test1.getX(), 0);
		assertEquals(test1.getY(), 0);
		assertEquals(test1.getNumber(), 0);
		
		assertEquals(test2.getX(), 1);
		assertEquals(test2.getY(), 2);
		assertEquals(test2.getNumber(), 0);
	}

	@Test
	void test_ChangeCell_ChangePos() {
		this.test2.getTextClass().setX(100);
		this.test2.getTextClass().setY(200);
		this.test1.changeCell(test2);
		
		//Test whether position has changed
		assertEquals(test1.getTextClass().getX(),100);
		assertEquals(test1.getTextClass().getY(),200);
		assertEquals(test2.getTextClass().getX(),0);
		assertEquals(test2.getTextClass().getY(),0);
	}
	
	@Test
	void test_ChangeCell_ChangeRoot_1() {
		//Test that no group has changed
		this.test1.changeCell(test2);
		assertFalse(testRoot.getChildren().contains(test1.getTextClass()));
		assertFalse(testRoot.getChildren().contains(test2.getTextClass()));
	}
	
	@Test
	void test_ChangeCell_Change_Text(){
		this.test1.setTextClass(new Text("20"));
		this.test1.changeCell(test2);
		
		//Test whether text changes
		assertEquals(Integer.parseInt(test1.getTextClass().getText()),0);
		assertEquals(Integer.parseInt(test2.getTextClass().getText()),20);
	}
	
	@Test
	void test_ChangeCell_ChangeRoot_2() {
		//Test whether group changed
		this.test1.setNumber(20);
		this.test1.changeCell(test2);
		assertFalse(testRoot.getChildren().contains(test1.getTextClass()));
		assertTrue(testRoot.getChildren().contains(test2.getTextClass()));
	}
	
	@Test
	void test_Merge() {
		this.test1.setNumber(2);
		this.test2.setNumber(4);
		test1.adder(test2);
		
		assertEquals(test1.getTextClass().getText(), "0");
		assertEquals(test2.getTextClass().getText(), "6");
		
		assertFalse(testRoot.getChildren().contains(test1));
	}
}
