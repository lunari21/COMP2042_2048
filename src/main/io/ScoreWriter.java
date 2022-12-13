package main.io;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class ScoreWriter implements ISaver {
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
