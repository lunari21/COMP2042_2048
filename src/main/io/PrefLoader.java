package main.io;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Loader class for PrefFile objects.
 * @author Alexander Tan Ka Jin
 */
public class PrefLoader implements ILoader{
	//Reads the saved css preference from a PrefFile.
	private static String LoadCss(BufferedReader reader) {
		try {
			return reader.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Reads a given preference .txt file.
	 * @param FilePath - Path to preference .txt file.
	 * @return PrefFile that contains information about a player's preferred settings.
	 */
	public static PrefFile loadPref(String FilePath) {
		PrefFile prefs = new PrefFile();
		try {
			FileReader readLocation = new FileReader(FilePath);
			BufferedReader reader = new BufferedReader(readLocation);
			
			prefs.setCss(LoadCss(reader));
			return prefs;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
