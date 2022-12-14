package main.io;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Saver class for ScoreFile objects
 * @author Alexander Tan Ka Jin
 */
public class ScoreWriter implements ISaver {
	/**
	 * Saves the hiscore of a game.
	 * @param FilePath - File to the score .txt file.
	 * @param save - ScoreFile holding the highscore attained.
	 */
	public static void save (String FilePath, ScoreFile save) {
		try {
			FileWriter writeLocation = new FileWriter(FilePath);
			BufferedWriter writer = new BufferedWriter(writeLocation);
			
			int hiScore = save.getHiScore();
			writer.write(Integer.toString(hiScore));
			writer.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
