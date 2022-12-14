package main.io;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Saver class for PrefFile objects.
 * @author Alexander Tan Ka Jin
 *
 */
public class PrefWriter implements ISaver{
	/**
	 * Creats a file at the designated file path and writes information from PrefFile.
	 * @param FilePath - The designated file path.
	 * @param save - The PrefFile
	 */
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
