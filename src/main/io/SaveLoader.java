package main.io;

import java.io.FileReader;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import main.controller.AlertPopup;
import main.model.GameModel;

import java.io.BufferedReader;

public class SaveLoader implements ILoader{

	private static int[][] readGrid(String grid, int Width, int Height) {
		int[][] out = new int[Height][Width];
		
		int x = 0;
		int y = 0;
		
		//split rows
		String[] rowTokens = grid.split("%");
		
		//If incompatible height
		if (rowTokens.length > Height)
			return null;
		
		//for each row
		for (String row : rowTokens) {
			String[] valueTokens = row.split("\\|");
			
			//If incompatible width
			if (valueTokens.length > Width)
				return null;
			
			//for each cell
			for (String valueString : valueTokens) {
				int val = Integer.parseInt(valueString,10);
				out[y][x] = val;
				x += 1;
			}
			x = 0;
			y += 1;
		}
		
		return out;
	}
	
	private static void LoadGame(BufferedReader reader, int gridWidth, int gridHeight, SaveFile save) {
		try {
			//Load GameState
			//Read seed
			String seedString = reader.readLine();
			long seed;
			if (seedString.matches("[0-9]+"))
				seed = Long.parseLong(seedString,10);
			else
				seed = 0;
			
			GameModel game = new GameModel(gridWidth,gridHeight,seed);
			
			//Load Grid
			String gridString = "";
			String row = "";
			//Reads the rest of the savefile.
			while ((row = reader.readLine())!=null && row.length() != 0) {
				gridString += row;
			}
			int[][] grid = readGrid(gridString, gridWidth, gridHeight);
			
			if (grid == null) {
				AlertPopup.errorPopup("Error loading game. Will present new game.");
			}else {
				game.getCellGrid().setGrid(grid);
			}
			
			save.setGameState(game);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads a savefile of a given filePath, if width/height is set to 0, then it does not read the seed/board
	 * @param FilePath
	 * @param Width
	 * @param Height
	 * @return
	 */
	public static SaveFile loadSave(String FilePath, int Width, int Height) {
		SaveFile save = new SaveFile();
		try {
			FileReader readLocation = new FileReader(FilePath);
			BufferedReader reader = new BufferedReader(readLocation);
			
			if (Width > 0 && Height > 0)
				LoadGame(reader, Width, Height,save);
			return save;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
