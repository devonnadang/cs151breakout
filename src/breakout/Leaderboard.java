package breakout;

import java.util.ArrayList;
import java.util.Collections;

public class Leaderboard {
	private ArrayList<Score> highScores; // should already be sorted from highest score to lowest
	
	public Leaderboard() {
		//???
	}
	
	public void addNewScore(Score newScore) {
		highScores.add(newScore);
		// sort array list?
		Collections.sort(highScores);
		// or use treeset & limit number of high scores
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
	
	// do we really need this?
	public ArrayList<Score> getTop5Score() {
		ArrayList<Score> top5Score = new ArrayList<>();
		for(int i = 0; i < 5; i++)
			top5Score.add(highScores.get(i));
		return top5Score;
		
	}
	
	

}
