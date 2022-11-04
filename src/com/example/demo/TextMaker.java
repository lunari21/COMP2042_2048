package com.example.demo;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * A static class that hosts several tools on manipulating text
 * @author Unknown, refactoring by Alexander Tan Ka Jin
 * @version 2
 */
public class TextMaker {
	private static double gameSceneLength; //This needs to be removed I think
	//Since if some rogue function comes in and changes this, it might screw up a lot of stuff

	/**
	 * Gets the length of the game scene
	 * @return length of the game scene
	 */
	public static double getGameSceneLength() {
		return gameSceneLength;
	}

	/**
	 * Updates the length of the game scene for this class
	 * @param gameSceneLength - double
	 */
	public static void setGameSceneLength(double gameSceneLength) {
		TextMaker.gameSceneLength = gameSceneLength;
	}
	
	/**
	 * Makes text in accordance with the format of the game. Docs is somewhat unfinished
	 * @param input - the text to be displayed
	 * @param xCell - ??
	 * @param yCell - ??
	 * @return text object with white font
	 */
    public static Text formatText(String input, double xCell, double yCell) {
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

    /**
     * Swaps the displayed text of two Text objects
     * @param first - Text object
     * @param second - Text object
     */
    public static void SwapText(Text first, Text second) {
        String tempText = first.getText();
        first.setText(second.getText());
        second.setText(tempText);
    }
    
    /**
     * Swaps the X and Y position of two text objects
     * @param first - Text object
     * @param second - Text object
     */
    public static void SwapPos(Text first, Text second) {
    	double tempX = first.getX();
        first.setX(second.getX());
        second.setX(tempX);

        double tempY = first.getY();
        first.setY(second.getY());
        second.setY(tempY);
    }

}
