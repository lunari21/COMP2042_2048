package com.example.demo;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

//Update: textmaker is now seperate of gamescene and fully static
//I accidentally changed a significant amount of textmaker's code without setting up a test case
//Thankfully, not much should change as most of it was setting up variables and stuff
public class TextMaker {
	private static double gameSceneLength;

	public static double getGameSceneLength() {
		return gameSceneLength;
	}

	public static void setGameSceneLength(double gameSceneLength) {
		TextMaker.gameSceneLength = gameSceneLength;
	}
	
    public static Text makeText(String input, double xCell, double yCell, Group root) {
    	double unknownDivider = 7.0; //Not sure what it does precisely
    	double fontScale = 3;
    	double xScale = 1.2;
    	double yScale = 2;
    	
        double fontSize = (fontScale * gameSceneLength) / unknownDivider;
        Color whiteColor = Color.WHITE;
        double xCellpos = xCell + xScale * gameSceneLength / unknownDivider;
        double yCellpos = yCell + yScale * gameSceneLength / unknownDivider;
        
        Text text = new Text(input);
        text.setFont(Font.font(fontSize));
        text.relocate(xCellpos, yCellpos);
        text.setFill(whiteColor);

        return text;
    }

    static void changeTwoText(Text first, Text second) {
        String temp;
        temp = first.getText();
        first.setText(second.getText());
        second.setText(temp);

        double tempNumber;
        tempNumber = first.getX();
        first.setX(second.getX());
        second.setX(tempNumber);

        tempNumber = first.getY();
        first.setY(second.getY());
        second.setY(tempNumber);

    }

}
