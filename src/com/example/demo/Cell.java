package com.example.demo;


import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
/**
 * Cell class that represents individual cells in the game
 * @author Unknown, Refactoring by Alexander Tan Ka Jin
 *
 */
//Update: cell is composite of GameScene
public class Cell {
    private Rectangle rectangle;
    private Group root;
    private Text textClass;
    private boolean modify = false;

    public void setModify(boolean modify) {
        this.modify = modify;
    }

    public boolean getModify() {
        return modify;
    }

    /**
     * Constructor for cell objects
     * @param x - The x position when displayed
     * @param y - The y position when displayed
     * @param scale - The size of the cell when displayed
     * @param root - The root group for this cell
     */
    public Cell(double x, double y, double scale, Group root) {
    	Color startingColor = Color.rgb(224, 226, 226, 0.5);
    	//Might wanna make a class holding all colors
    	
        this.rectangle = new Rectangle();
        this.rectangle.setX(x);
        this.rectangle.setY(y);
        this.rectangle.setHeight(scale);
        this.rectangle.setWidth(scale);
        this.textClass = TextMaker.formatText("0", x, y);
        this.root = root;
        this.rectangle.setFill(startingColor);
        
        //Add this object to root
        root.getChildren().add(this.rectangle);
    }

    //Changed changeCell to MergeCell
    public void mergeCell(Cell cell) {
        TextMaker.SwapText(textClass, cell.getTextClass());
        TextMaker.SwapPos(textClass, cell.getTextClass());
        
        root.getChildren().remove(cell.getTextClass());
        //Wait was textClass supposed to be in root when made?
        root.getChildren().remove(textClass);
        
        if (!cell.getTextClass().getText().equals("0")) {
            root.getChildren().add(cell.getTextClass());
        }
        if (!textClass.getText().equals("0")) {
            root.getChildren().add(textClass);
        }
        setColorByNumber(getNumber());
        cell.setColorByNumber(cell.getNumber());
    }

    public void adder(Cell cell) {
        cell.getTextClass().setText((cell.getNumber() + this.getNumber()) + "");
        textClass.setText("0");
        root.getChildren().remove(textClass);
        cell.setColorByNumber(cell.getNumber());
        setColorByNumber(getNumber());
    }

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

    public double getX() {
        return rectangle.getX();
    }

    public double getY() {
        return rectangle.getY();
    }

    public int getNumber() {
        return Integer.parseInt(textClass.getText());
    }

    private Text getTextClass() {
        return textClass;
    }
    
    public void setTextClass(Text textClass) {
        this.textClass = textClass;
    }

}
