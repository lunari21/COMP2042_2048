package main.io;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Loader class for ScoreFile objects.
 * @author Alexander Tan Ka Jin
 */
public class ScoreLoader implements ILoader{
	//Reads and interprets the hiscore that was saved.
	private static void LoadHiScore(BufferedReader reader, ScoreFile score) {
		try {
			String hiScoreData = reader.readLine();
			
			if (hiScoreData == null) {
				score.setHiScore(0);
			}else {
				int hiScore = Integer.parseInt(hiScoreData,10);
				score.setHiScore(hiScore);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads a given score .txt file.
	 * @param FilePath - Path to score .txt file.
	 * @return PrefFile that contains information about the hiScore attained on this game.
	 */
	public static ScoreFile loadScoreFile(String FilePath) {
		ScoreFile score = new ScoreFile();
		try {
			FileReader readLocation = new FileReader(FilePath);
			BufferedReader reader = new BufferedReader(readLocation);
			
			LoadHiScore(reader, score);
			return score;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
