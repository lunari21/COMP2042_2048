package com.example.demo;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * A class that hosts several tools on manipulating text for gamescene.
 * @author Unknown, refactoring by Alexander Tan Ka Jin
 * @version 2
 */
public class TextMaker {
	/**
	 * Makes text in accordance with the format of the game. Docs is somewhat unfinished
	 * @param input - the text to be displayed
	 * @param xCell - ??
	 * @param yCell - ??
	 * @return text object with white font
	 */
    public static Text formatText(String input, double xCell, double yCell, double sceneLength) {
    	double unknownDivider = 7.0; //Not sure what it does precisely
    	double fontScale = 3;
    	double xScale = 1.2;
    	double yScale = 2;
    	
        double fontSize = (fontScale * sceneLength) / unknownDivider;
        Color whiteColor = Color.WHITE;
        double xCellpos = xCell + xScale * sceneLength / unknownDivider;
        double yCellpos = yCell + yScale * sceneLength / unknownDivider;
        
        //Create text
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
