package breakout;

import java.util.ArrayList;

/**
 * Keeps track of all scores and orders scores from highest to lowest.
 */

import java.util.Collections;

public class Leaderboard {
	private ArrayList<Score> highScores; // sorted from highest to lowest score
	
	
	/**
	 * Class constructor.
	 */
	public Leaderboard() {
		highScores = new ArrayList<>();
	}
	
	
	/**
	 * Adds a new Score object to ArrayList highScores, then sorts all Score objects from highest to lowest.
	 * 
	 * @param Score object newScore to be added to ArrayList highScores.
	 */
	public void addNewScore(Score newScore) {
		highScores.add(newScore);
		Collections.sort(highScores);
		// using collections sort for now but maybe treeset & limit number of high scores?
			// like when adding new score, drop the lowest or cant add cuz score is too low
	}
	
	
	// might need to change after implementing view
	// just for junit testing for now
	public String getAllScores() {
		String allScores = "";
		for (Score s : highScores)
			allScores += s.toString();
		return allScores;
	}
	
	
	/**
	 * Gets the 10 highest scores from ArrayList highScores.
	 * 
	 * @return 10 highest Score objects saved.
	 */
	// for unit testing?
	public ArrayList<Score> getTop10Score() {
		ArrayList<Score> top10Score = new ArrayList<>();
		for(int i = 0; i < 10; i++)
			top10Score.add(highScores.get(i));
		return top10Score;
	}
}
