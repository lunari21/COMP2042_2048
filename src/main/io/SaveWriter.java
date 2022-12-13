package main.io;

import java.io.FileWriter;
import java.io.BufferedWriter;
/**
 * Class designed for saving preferences and game progress, 
 * I honestly don't know what to name this class
 * @author Alexander Tan Ka Jin
 *
 */
public class SaveWriter implements ISaver {
	//Save game and preferences
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

			writer.write(Long.toString(seed)+'\n');
			writer.write(board);
			writer.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
