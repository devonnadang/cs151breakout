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

	public void setScore(int score) {
		currentScore = score;
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
	/**
	 * This method sorts score from highest to lowest, then by username. 

	 	@param Score to compare with
		@return position of Score. A higher score will be placed before. 
	 */
	public int compareTo(Score other) {
		if (this.getScore() > other.getScore()) //this.score is higher
			return -1;
		else if (this.getScore() < other.getScore()) //this.score is lower
			return 1;
		else 
			return this.getUsername().compareTo(other.getUsername());
	}

	public boolean equals(Object obj) {
		Score that = (Score) obj;
		return this.compareTo(that) == 0;
	}
	
}
