package breakout.model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Keeps track of all scores and orders scores from highest to lowest.
 */

public class Leaderboard {
	private ArrayList<Score> highScores; // sorted from highest to lowest score
	private static final Leaderboard INSTANCE = new Leaderboard(); // implement singleton

	
	/**
	 * Initialize highScores arraylist of scores.
	 */
	private Leaderboard() {
		highScores = new ArrayList<>();
	}
	
	
	/**
	 * Static method to get the Leaderboard
	 * @return Leaderboard
	 */
	public static Leaderboard getInstance() {
		return INSTANCE;
	}
	
	
	/**
	 * Returns an arraylist of scores from highest to lowest
	 * @return highScore the arraylist of scores
	 */
	public ArrayList<Score> getHighScores() {
		return highScores;
	}
	
	
	/**
	 * Adds a new Score object to ArrayList highScores, then sorts all Score objects from highest to lowest.
	 * 
	 * @param Score object newScore to be added to ArrayList highScores.
	 */
	public void addNewScore(Score newScore) {
		highScores.add(newScore);
		Collections.sort(highScores);
	}
	
	
	/**
	 * Gets the 10 highest scores from ArrayList highScores.
	 * 
	 * @return 10 highest Score objects saved.
	 */
	public ArrayList<Score> getTop10Score() {
		ArrayList<Score> top10Score = new ArrayList<>();
		for(int i = 0; i < 10; i++)
			top10Score.add(highScores.get(i));
		return top10Score;
	}
}
