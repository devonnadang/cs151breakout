package breakout.controller;


import breakout.model.Leaderboard;

public class LeaderboardMessage implements Message {
	private Leaderboard scoreList;

	public void addScores(Leaderboard score) {
		scoreList = score;
	}
	
	public Leaderboard getScores() {
		return scoreList;
	}
	
}
