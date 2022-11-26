package com.example.demo;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;

/**
 * Singleton (Use dependency injection) GameScene that sets up the scene of the game.
 * With the introduction of CellGrid, this class is now mainly responsible for displaying the game.
 * @author Unknown, refactoring made by Alexander Tan
 * @version 1
 */
//Update:Determine the responsibility of gamescene
public class GameScene {
    private final int HEIGHT = 700;
    private int n = 4;
    private final int distanceBetweenCells = 10;
    
    private Cell[][] cells = new Cell[n][n];
    private Group root;
    private long score = 0;
    
    public GameScene () {
    	//nothing
    }

    /**
     * Sets the number n to number
     * @deprecated n shouldn't be set
     * @param number - new value of n
     */
    public void setN(int number) {
        this.n = number;
    }

    /**
     * Gets the length of the game scene
     * @return length 
     */
    public double getLength() {
    	double ndouble = n; //weirdly, java rounds up when dividing with integer right side
        return (HEIGHT - ((ndouble + 1) * distanceBetweenCells)) / ndouble;
    }

    //Boss fight ahead, do you want to save?
    //All methods beyond this are deprecated (outside of game). 
    //About to be replaced by methods in CellGrid
    /**
     * Identifies empty cells, then picks one to replace with a random number
     * @deprecated
     * @param turn - Not used
     */
    @Deprecated //Just don't use this one. It is terrible
    private void randomFillNumber(int turn) {
        Cell[][] emptyCells = new Cell[n][n];
        int a = 0;
        int b = 0;
        int aForBound=0,bForBound=0;
        outer:
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (cells[i][j].getNumber() == 0) {
                    emptyCells[a][b] = cells[i][j];
                    if (b < n-1) {
                        bForBound=b;
                        b++;
                    } else {
                        aForBound=a;
                        a++;
                        b = 0;
                        if(a==n)
                            break outer;
                    }
                }
            }
        }



        Text text;
        Random random = new Random();
        boolean putTwo = true;
        if (random.nextInt() % 2 == 0)
            putTwo = false;
        int xCell, yCell;
        xCell = random.nextInt(aForBound+1);
        yCell = random.nextInt(bForBound+1);
        
        Cell targetCell = emptyCells[xCell][yCell];
        double cellPosX = targetCell.getX();
        double cellPosY = targetCell.getY();
        
        if (putTwo) {
            text = TextMaker.formatText("2", cellPosX, cellPosY, getLength());
            targetCell.setTextClass(text);
            root.getChildren().add(text);
            targetCell.setColorByVal(2);
        } else {
            text = TextMaker.formatText("4", cellPosX, cellPosY, getLength());
            targetCell.setTextClass(text);
            root.getChildren().add(text);
            targetCell.setColorByVal(4);
        }
    }

    /**
     * Checks if the grid has empty cells or has a 2048 win condition 
     * @deprecated Use CellGrid.contains
     * @return -1 if no zeroes or 2048, 1 if there is a 0, 0 if there is a 2048 on the top left
     */
    @Deprecated
    private int  haveEmptyCell() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (cells[i][j].getNumber() == 0)
                    return 1;
                if(cells[i][j].getNumber() == 2048) //isn't this unreachable?
                    return 0;
            }
        }
        return -1;
    }

    /**
     * Returns valid position in a single direction
     * @deprecated Use CellGrid.getValidPos
     * @param i - int
     * @param j - int
     * @param direct - char 
     * @return integer representing the coordinate?
     */
    @Deprecated
    private int passDestination(int i, int j, char direct) {
        int coordinate = j; //Set coordinate as j if left/right, wait is it indexed as cell[y][x]???
        if (direct == 'l') { //if left direction
            for (int k = j - 1; k >= 0; k--) { //k is from j-1 to 0
                if (cells[i][k].getNumber() != 0) { // check any non zeroes exist (cell in the left)
                    coordinate = k + 1;
                    break; //return right of cell
                } else if (k == 0) { //if run into edge
                    coordinate = 0; // return left edge
                }
            }
            return coordinate; 
        }
        coordinate = j;
        if (direct == 'r') {
            for (int k = j + 1; k <= n - 1; k++) {
                if (cells[i][k].getNumber() != 0) {
                    coordinate = k - 1;
                    break;
                } else if (k == n - 1) {
                    coordinate = n - 1;
                }
            }
            return coordinate;
        }
        coordinate = i;
        if (direct == 'd') {
            for (int k = i + 1; k <= n - 1; k++) {
                if (cells[k][j].getNumber() != 0) {
                    coordinate = k - 1;
                    break;

                } else if (k == n - 1) {
                    coordinate = n - 1;
                }
            }
            return coordinate;
        }
        coordinate = i;
        if (direct == 'u') {
            for (int k = i - 1; k >= 0; k--) {
                if (cells[k][j].getNumber() != 0) {
                    coordinate = k + 1;
                    break;
                } else if (k == 0) {
                    coordinate = 0;
                }
            }
            return coordinate;
        }
        return -1;
    }

    /**
     * Moves all cells left
     * @deprecated Use CellGrid
     */
    private void moveLeft() {
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n; j++) {
                moveHorizontally(i, j, passDestination(i, j, 'l'), -1);
            }
            for (int j = 0; j < n; j++) {
                cells[i][j].setModify(false);
            }
        }
    }

    /**
     * Moves all cells right
     * @deprecated Use CellGrid
     */
    private void moveRight() {
        for (int i = 0; i < n; i++) {
            for (int j = n - 1; j >= 0; j--) {
                moveHorizontally(i, j, passDestination(i, j, 'r'), 1);
            }
            for (int j = 0; j < n; j++) {
                cells[i][j].setModify(false);
            }
        }
    }

    /**
     * Moves all cells up
     * @deprecated Use CellGrid
     */
    private void moveUp() {
        for (int j = 0; j < n; j++) {
            for (int i = 1; i < n; i++) {
                moveVertically(i, j, passDestination(i, j, 'u'), -1);
            }
            for (int i = 0; i < n; i++) {
                cells[i][j].setModify(false);
            }
        }

    }

    /**
     * Moves all cells down
     * @deprecated Use CellGrid
     */
    private void moveDown() {
        for (int j = 0; j < n; j++) {
            for (int i = n - 1; i >= 0; i--) {
                moveVertically(i, j, passDestination(i, j, 'd'), 1);
            }
            for (int i = 0; i < n; i++) {
                cells[i][j].setModify(false);
            }
        }

    }

    /**
     * Checks if the destination horizontally has a mergable cell
     * @deprecated Use CellGrid.getValidPos
     * @param i - The index of the cell to be moved
     * @param j - The index of the cell to be moved
     * @param des - Destination horizontally
     * @param sign - Direction of the movement
     * @return boolean - Is valid destination?
     */
    private boolean isValidDesH(int i, int j, int des, int sign) {
        if (des + sign < n && des + sign >= 0) {
            if (cells[i][des + sign].getNumber() == cells[i][j].getNumber() && !cells[i][des + sign].getModify()
                    && cells[i][des + sign].getNumber() != 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Moves all cells horizontally
     * @deprecated Use CellGrid.MoveLeft or MoveRight
     * @param i - The index of the cell to be moved
     * @param j - The index of the cell to be moved
     * @param des - Destination horizontally
     * @param sign - Direction of the movement
     */
    private void moveHorizontally(int i, int j, int des, int sign) {
        if (isValidDesH(i, j, des, sign)) {
            cells[i][j].adder(cells[i][des + sign]);
            cells[i][des].setModify(true);
        } else if (des != j) {
            cells[i][j].changeCell(cells[i][des]);
        }
    }

    /**
     * Checks if vertical movement is valid
     * @deprecated Use CellGrid.getValidPos
     * @param i - The index of the cell to be moved
     * @param j - The index of the cell to be moved
     * @param des - Destination vertically
     * @param sign - Direction of the movement
     * @return boolean - Is valid destination?
     */
    private boolean isValidDesV(int i, int j, int des, int sign) {
        if (des + sign < n && des + sign >= 0)
            if (cells[des + sign][j].getNumber() == cells[i][j].getNumber() && !cells[des + sign][j].getModify()
                    && cells[des + sign][j].getNumber() != 0) {
                return true;
            }
        return false;
    }

    /**
     * Moves all cells Vertically
     * @deprecated Use CellGrid.MoveUp or MoveDown
     * @param i - The index of the cell to be moved
     * @param j - The index of the cell to be moved
     * @param des - Destination vertically
     * @param sign - Direction of the movement
     */
    private void moveVertically(int i, int j, int des, int sign) {
        if (isValidDesV(i, j, des, sign)) {
            cells[i][j].adder(cells[des + sign][j]);
            cells[des][j].setModify(true);
        } else if (des != i) {
            cells[i][j].changeCell(cells[des][j]);
        }
    }

    /**
     * Checks if right and bottom neighbours have the same value
     * @deprecated Use CellGrid.getValidPos for checking if a cell can merge
     * @param i - Position of the cell to be checked
     * @param j - Position of the cell to be checked
     * @return Returns whether a merge can be done
     */
    private boolean haveSameNumberNearly(int i, int j) {
        if (i < n - 1 && j < n - 1) {
            if (cells[i + 1][j].getNumber() == cells[i][j].getNumber())
                return true;
            if (cells[i][j + 1].getNumber() == cells[i][j].getNumber())
                return true;
        }
        return false;
    }

    /**
     * Checks if no cell can move
     * @deprecated Use CellGrid.canMove
     * @return Returns if the cell cannot move anymore
     */
    private boolean canNotMove() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (haveSameNumberNearly(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Calculate the score for the game
     */
    private void sumCellNumbersToScore() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                score += cells[i][j].getNumber();
            }
        }
    }
    
	/**
	 * Hides the texts of all zero cells in the grid. Unimplemented
	 */
	private void CullZeroes() {
		//Unimplemented!
	}
	
	/**
	 * Displays this class' CellGrid via javafx.
	 */
	public void Display() {
		//Unimplemented
	}

    /**
     * Starts game
     * @param gameScene - gamescene object
     * @param root - the root group which is needed for displaying everything
     * @param primaryStage - the stage of the game
     * @param endGameScene - the scene after the game has completed
     * @param endGameRoot - the root for the endgame scene.
     */
    public void game(Scene gameScene, Group root, Stage primaryStage, Scene endGameScene, Group endGameRoot) {
        this.root = root;
        //Replace with CellGrid.GenerateDefault
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
            	//This is a headache. It's transposed for some reasons
                cells[i][j] = new Cell((j) * getLength() + (j + 1) * distanceBetweenCells,
                        (i) * getLength() + (i + 1) * distanceBetweenCells, getLength(), root, getLength());
            }

        }

        Text text = new Text();
        root.getChildren().add(text);
        text.setText("SCORE :");
        text.setFont(Font.font(30));
        text.relocate(750, 100);
        Text scoreText = new Text();
        root.getChildren().add(scoreText);
        scoreText.relocate(750, 150);
        scoreText.setFont(Font.font(20));
        scoreText.setText("0");

        randomFillNumber(1);
        randomFillNumber(1);

        gameScene.addEventHandler(KeyEvent.KEY_PRESSED, key ->{
                Platform.runLater(() -> {
                    int haveEmptyCell;
                    if (key.getCode() == KeyCode.DOWN) {
                        GameScene.this.moveDown();
                    } else if (key.getCode() == KeyCode.UP) {
                        GameScene.this.moveUp();
                    } else if (key.getCode() == KeyCode.LEFT) {
                        GameScene.this.moveLeft();
                    } else if (key.getCode() == KeyCode.RIGHT) {
                        GameScene.this.moveRight();
                    }
                    GameScene.this.sumCellNumbersToScore();
                    scoreText.setText(score + "");
                    haveEmptyCell = GameScene.this.haveEmptyCell();
                    if (haveEmptyCell == -1) {
                        if (GameScene.this.canNotMove()) {
                            primaryStage.setScene(endGameScene);

                            EndGame.getInstance().endGameShow(endGameScene, endGameRoot, primaryStage, score);
                            root.getChildren().clear();
                            score = 0;
                        }
                    } else if(haveEmptyCell == 1)
                        GameScene.this.randomFillNumber(2);
                });
            });
    }
}
