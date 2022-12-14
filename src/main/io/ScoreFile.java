package main.io;

/**
 * Class responsible for holding the hiScore of the game when it is being saved.
 * @author Alexander Tan Ka Jin
 *
 */
public class ScoreFile implements ISaveData{
	private int hiScore;

	/**
	 * @return Currently saved hiscore
	 */
	public int getHiScore() {
		return hiScore;
	}

	/**
	 * Sets the hiscore of this file
	 * @param hiScore - High Score to be recorded
	 */
	public void setHiScore(int hiScore) {
		this.hiScore = hiScore;
	}
}
