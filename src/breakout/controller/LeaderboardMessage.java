package breakout.controller;


import breakout.model.Leaderboard;

/**
 * Message sent to signal score update.
 */
public class LeaderboardMessage implements Message {
	private Leaderboard scoreList;

	/**
	 * Add a score to the score list.
	 * @param score score to be added
	 */
	public void addScores(Leaderboard score) {
		scoreList = score;
	}
	
	public Leaderboard getScores() {
		return scoreList;
	}
	
}
