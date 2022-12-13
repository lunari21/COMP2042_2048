package main.io;

import java.io.BufferedReader;
import java.io.FileReader;

public class PrefLoader implements ILoader{
	private static void LoadCss(BufferedReader reader, PrefFile pref) {
		try {
			String css = reader.readLine();
			
			pref.setCss(css);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static PrefFile loadPref(String FilePath) {
		PrefFile prefs = new PrefFile();
		try {
			FileReader readLocation = new FileReader(FilePath);
			BufferedReader reader = new BufferedReader(readLocation);
			
			LoadCss(reader, prefs);
			return prefs;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
