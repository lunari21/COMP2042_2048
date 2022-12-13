package main.io;

/**
 * Class responsible for saving player preferences.
 * @author Alexander Tan Ka Jin
 *
 */
public class PrefFile implements ISaveData{
	private String css;

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}
}
