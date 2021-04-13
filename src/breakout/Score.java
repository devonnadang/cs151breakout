package breakout;

// This Class keeps track of the user's score
// game start: no username & score set to 0
// game ends: set name if want to add to leaderboard

// *should score also store time?

public class Score implements Comparable<Score> {
	private String username;
	private int currentScore;
	
	
	public Score() {
		username = "";
		currentScore = 0;
	}
	
	public String getUsername() {
		return username;
	}
	
	public int getScore() {
		return currentScore;
	}
	
	// make boolean??? like if newUsername = "" or " " then return false username is not accepted/added
	public void setUsername(String newUsername) {
		
		username = newUsername;
	}
	
	public void incrementScore() {
		currentScore += 5; // may change, depends on points per block
	}
	
	// might need to change after implementing view
	// for junit test
	public String toString() {
		return this.getUsername() + " " + this.getScore();
	}

	
	@Override
	// compare by score, then time?, then username?
	// maybe don't need
	public int compareTo(Score other) {
		if (this.getScore() > other.getScore())
			return 1;
		else if (this.getScore() > other.getScore())
			return -1;
		// else compare time? compare username?
			
		return 0;
	}
	
}
