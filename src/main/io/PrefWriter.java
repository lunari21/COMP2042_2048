package main.io;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class PrefWriter implements ISaver{
	public static void save (String FilePath, PrefFile save) {
		try {
			FileWriter writeLocation = new FileWriter(FilePath);
			BufferedWriter writer = new BufferedWriter(writeLocation);
			
			String css = save.getCss();
			writer.write(css);
			writer.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
