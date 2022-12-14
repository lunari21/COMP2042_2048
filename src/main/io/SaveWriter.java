package main.io;

import java.io.FileWriter;
import java.io.BufferedWriter;
/**
 * Saver class for SaveFile objects
 * @author Alexander Tan Ka Jin
 */
public class SaveWriter implements ISaver {
	/**
	 * Saves the seed and board state of a game.
	 * @param FilePath - The path to the save file
	 * @param save - The SaveFile object that contaisn the state of the game.
	 */
	public static void save(String FilePath, SaveFile save) {
		try {
			FileWriter writeLocation = new FileWriter(FilePath);
			BufferedWriter writer = new BufferedWriter(writeLocation);
			
			long seed = save.getGameState().getSeed();
			String board;
			if (save.getGameState() == null)
				board = "";
			else
				board = save.getGameState().BoardToString();

			/*
			 * Format is in:
			 * seed (long type)
			 * board (string, with each cell separated by | and row separated by %)
			 */
			writer.write(Long.toString(seed)+'\n');
			writer.write(board);
			writer.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
