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
    private Rectangle rectangle;
    private Group root;
    //the text displayed on the cell
    private Text textClass;
    //value of the cell, new functionality to make it less of a headache
    private int number;
    private boolean modify = false;

    /**
     * Constructor for cell objects
     * @param x - The x position of the rectangle when displayed
     * @param y - The y position of the rectangle when displayed
     * @param scale - The size of the rectangle when displayed
     * @param root - The root group for this cell's rectangle
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
        this.number = 0;
        this.textClass = TextMaker.formatText(String.valueOf(number), x, y, SceneLength);
        this.root = root;
        this.rectangle.setFill(startingColor);
        
        //Add this object to root
        root.getChildren().add(this.rectangle);
    }
    
    public double getX() {
        return rectangle.getX();
    }

    public double getY() {
        return rectangle.getY();
    }

    @Deprecated //Use UpdateText instead
    public void setTextClass(Text txt) {
    	this.textClass = txt;
    }

    //Changed to public. Don't really understand why it was private
    public Text getTextClass() {
        return textClass;
    }
    
    public int getNumber() {
        return number;
    }
    
    public void setNumber(int val) {
    	this.number = val;
    }
    
    public void setModify(boolean modify) {
        this.modify = modify;
    }

    public boolean getModify() {
        return modify;
    }
    
    public void swapValue(Cell cell) {
    	int temp = number;
    	//If number is swapped, so is the text of textclass
    	this.number = cell.getNumber();
    	cell.setNumber(temp);
    }

    public void SwapWith (Cell cell) {
    	//Swap textclass positions
        TextMaker.SwapPos(textClass, cell.getTextClass());
        //Swap value
        swapValue(cell);
        //swap Text
        TextMaker.SwapText(textClass, cell.getTextClass());
    }
    
    @Deprecated //Use SwapWith then update cell grid to remove zeros instead
    public void changeCell(Cell cell) {
    	SwapWith(cell);
        //This might go deprecated and be changed to a functionality of gamescene instead
        //Remove first. Javafx is unkind to duplicate children.
        root.getChildren().remove(cell.getTextClass());
        root.getChildren().remove(textClass);
        
        //If one or both of them is 0, then don't add it back
        if (cell.getNumber() != 0)
        	root.getChildren().add(cell.getTextClass());
          
        if (getNumber() != 0)
        	root.getChildren().add(textClass);
            
        cell.UpdateColor();
        UpdateColor();
    }

    @Deprecated //Use mergeWith instead
    public void adder(Cell cell) {
        cell.getTextClass().setText((cell.getNumber() + this.getNumber()) + "");
        textClass.setText("0");
        root.getChildren().remove(textClass);
        cell.setColorByNumber(cell.getNumber());
        setColorByNumber(getNumber());
    }
    
    //improved version
    public void mergeWith(Cell cell) {
        cell.setNumber(number+cell.getNumber());
        setNumber(0);
        root.getChildren().remove(textClass);
        cell.UpdateColor();
        UpdateColor();
    }
    //Unsure how to best clean this up atm
    public void setColorByNumber(int number) {
        switch (number) {
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
    
    public void UpdateColor() {
    	setColorByNumber(number);
    }
    
    public void UpdateText() {
    	this.textClass.setText(String.valueOf(number));
    }
}
