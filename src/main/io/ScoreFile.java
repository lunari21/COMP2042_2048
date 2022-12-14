package main.io;

/**
 * Class responsible for holding the hiScore of the game when it is being saved.
 * @author Alexander Tan Ka Jin
 *
 */
public class ScoreFile implements ISaveData{
	private int hiScore;

	public int getHiScore() {
		return hiScore;
	}

	public void setHiScore(int hiScore) {
		this.hiScore = hiScore;
	}
}
