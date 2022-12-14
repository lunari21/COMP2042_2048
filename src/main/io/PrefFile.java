package main.io;

/**
 * Class responsible for saving player preferences.
 * @author Alexander Tan Ka Jin
 */
public class PrefFile implements ISaveData{
	private String css;

	/**
	 * @return CSS file name that is referenced in this class
	 */
	public String getCss() {
		return css;
	}

	/**
	 * Sets the CSS file to be loaded using this class
	 * @param css - CSS file name.
	 */
	public void setCss(String css) {
		this.css = css;
	}
}
