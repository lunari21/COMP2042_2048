package com.example.demo;


import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
/**
 * Cell class that represents individual cells in the game
 * @author Unknown, Refactoring by Alexander Tan Ka Jin
 * Doc will be added later.
 */
//Update: cell is composite of GameScene
public class Cell {
	//Rectangle that represents the graphical component of the cell
    private Rectangle rectangle;
    //Root group. You must add the cell to this group else it will not render
    private Group root;
    //the text displayed on the cell
    private Text textClass;
    //value of the cell
    private int val;
    //I'm not sure what this does
    private boolean modify = false;

    /**
     * Constructor for cell objects
     * @param x - The x position of the rectangle when displayed
     * @param y - The y position of the rectangle when displayed
     * @param scale - The size of the rectangle when displayed
     * @param root - The root group for this cell's rectangle
     * @param SceneLength - The length of the game scene. Used for making sure the text is properly aligned.
     */
    public Cell(double x, double y, double scale, Group root, double SceneLength) {
    	Color startingColor = Color.rgb(224, 226, 226, 0.5);
    	//Might wanna make a class holding all colors
    	
    	//Set up a couple variables
        this.rectangle = new Rectangle();
        this.rectangle.setX(x);
        this.rectangle.setY(y);
        this.rectangle.setHeight(scale);
        this.rectangle.setWidth(scale);
        //Initially it's value is 0
        this.val = 0;
        this.textClass = TextMaker.formatText(String.valueOf(val), x, y, SceneLength);
        this.root = root;
        this.rectangle.setFill(startingColor);
        
        //Add this object to root
        root.getChildren().add(this.rectangle);
    }
    
    /**
     * Gets the X component of the cell's position
     * @return double
     */
    public double getX() {
        return rectangle.getX();
    }

    /**
     * Gets the Y component of the cell's position
     * @return double
     */
    public double getY() {
        return rectangle.getY();
    }
    
    /**
     * Gets this object's text object
     * @return Text
     */
    public Text getTextClass() {
        return textClass;
    }

    /**
     * Sets text object
     * @deprecated
     * @param txt - Text
     */
    @Deprecated 
    public void setTextClass(Text txt) {
    	this.textClass = txt;
    }
    
    /**
     * parsed the number based on it's textclass
     * @deprecated
     * @return int
     */
    @Deprecated
    public int getNumber() {
        return Integer.parseInt(textClass.getText());
    }
    
    /**
     * Gets the value of this cell
     * @return int
     */
    public int getVal() {
        return val;
    }
    
    /**
     * Sets the value of this cell
     * @param val - int
     */
    public void setVal(int val) {
    	this.val = val;
    }
    
    /**
     * Sets the modify flag
     * @param modify - boolean
     */
    public void setModify(boolean modify) {
        this.modify = modify;
    }

    /**
     * Gets the status of the modify flag
     * @return boolean
     */
    public boolean getModify() {
        return modify;
    }
    
    /**
     * Returns whether the value of this cell equals to v
     * @param v - integer
     * @return boolean
     */
    public boolean valueEquals (int v) {
    	return val == v;
    }
    
    /**
     * Returns whether the value of this cell equals to the value of Cell c
     * @param c - Cell
     * @return boolean
     */
    public boolean valueEquals (Cell c) {
    	return val == c.getVal();
    }
    /**
     * Swaps the value of this cell with another cell
     * @param cell - Cell
     */
    public void swapVal(Cell cell) {
    	int temp = val;
    	this.val = cell.getVal();
    	cell.setVal(temp);
    }

    /**
     * Swaps the text, text position and value of this cell and another cell
     * @param cell - Cell
     */
    public void SwapCell (Cell cell) {
    	//Swap textclass positions
        TextMaker.SwapPos(textClass, cell.getTextClass());
        //Swap value
        swapVal(cell);
        //swap Text
        TextMaker.SwapText(textClass, cell.getTextClass());
    }
    
    /**
     * Does the same thing as swapCell, then removes any zeroes and updates colors
     * Use SwapCell, then remove zeroes on grid and update the colors of every cell instead.
     * @deprecated
     * @param cell - Cell
     */
    @Deprecated
    public void changeCell(Cell cell) {
    	SwapCell(cell);
        //This might go deprecated and be changed to a functionality of gamescene instead
        //Remove first. Javafx is unkind to duplicate children.
        root.getChildren().remove(cell.getTextClass());
        root.getChildren().remove(textClass);
        
        //If one or both of them is 0, then don't add it back
        if (cell.getNumber() != 0)
        	root.getChildren().add(cell.getTextClass());
          
        if (getNumber() != 0)
        	root.getChildren().add(textClass);
            
        setColorByVal(getNumber());
        cell.setColorByVal(cell.getNumber());
    }

    /**
     * Deprecated, Old version of mergeWith that manipulates the text instead of the value.
     * @param cell - Cell
     */
    @Deprecated
    public void adder(Cell cell) {
        cell.getTextClass().setText((cell.getNumber() + this.getNumber()) + "");
        textClass.setText("0");
        root.getChildren().remove(textClass);
        cell.setColorByVal(cell.getNumber());
        setColorByVal(getNumber());
    }
    
    /**
     * Merges with a cell, setting this cell's value to zero and adding it to the other cell's value.
     * @param cell - Cell
     */
    public void mergeWith(Cell cell) {
        cell.setVal(val+cell.getVal());
        setVal(0);
    }
    
    /**
     * Sets a color by it's value.
     * About to be deprecated but no other alternative has been proposed yet.
     * @param val - int
     */
    public void setColorByVal(int val) {
        switch (val) {
            case 0:
                rectangle.setFill(Color.rgb(224, 226, 226, 0.5));
                break;
            case 2:
                rectangle.setFill(Color.rgb(232, 255, 100, 0.5));
                break;
            case 4:
                rectangle.setFill(Color.rgb(232, 220, 50, 0.5));
                break;
            case 8:
                rectangle.setFill(Color.rgb(232, 200, 44, 0.8));
                break;
            case 16:
                rectangle.setFill(Color.rgb(232, 170, 44, 0.8));
                break;
            case 32:
                rectangle.setFill(Color.rgb(180, 120, 44, 0.7));
                break;
            case 64:
                rectangle.setFill(Color.rgb(180, 100, 44, 0.7));
                break;
            case 128:
                rectangle.setFill(Color.rgb(180, 80, 44, 0.7));
                break;
            case 256:
                rectangle.setFill(Color.rgb(180, 60, 44, 0.8));
                break;
            case 512:
                rectangle.setFill(Color.rgb(180, 30, 44, 0.8));
                break;
            case 1024:
                rectangle.setFill(Color.rgb(250, 0, 44, 0.8));
                break;
            case 2048:
                rectangle.setFill(Color.rgb(250,0,0,1));
        }
    }
    
    /**
     * Updates this cell's color based on it's value.
     */
    public void UpdateColor() {
    	setColorByVal(val);
    }
    
    /**
     * Update this cell's text based on it's value.
     */
    public void UpdateText() {
    	this.textClass.setText(String.valueOf(val));
    }
}
