package main.display;

import java.util.HashMap;

import javafx.scene.paint.Color;

public class CellColor {
	private HashMap<Integer,Color> palette;
	
	public CellColor() {
		palette = new HashMap<Integer, Color>();
	}

	public CellColor addPalette(int key, Color val) {
		palette.put(key, val);
		return this;
	}

	public Color getColorFromVal(int val) {
		return palette.get(val);
	}
}
