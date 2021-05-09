package breakout.controller;

import breakout.model.Score;

public class SaveScoreMessage implements Message {
	private Score score;
	
	public SaveScoreMessage(int score, String username) {
		this.score = new Score();
		
		this.score.setScore(score);
		this.score.setUsername(username);
	}
	
	public Score getScore() {
		return score;
	}

}
