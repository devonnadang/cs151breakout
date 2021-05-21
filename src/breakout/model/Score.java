package breakout.model;

/**
 * Keeps track of the user's score.
 */

public class Score implements Comparable<Score> {

    private String username;
    private int currentScore;


    /**
     * Constructs a Score object with an empty username and currentScore of zero. A Breakout game
     * starts with no username and score set to zero.
     */
    public Score() {
        username = "";
        currentScore = 0;
    }


    /**
     * Gets the String username of this Score
     *
     * @return String username of this Score
     */
    public String getUsername() {
        return username;
    }


    /**
     * Sets int new score of this Score
     *
     * @param newScore to set as currentScore
     */
    public void setScore(int newScore) {
        currentScore = newScore;
    }


    /**
     * Gets the currentScore.
     *
     * @return int currentScore.
     */
    public int getScore() {
        return currentScore;
    }


    /**
     * Sets the username and returns true if newUsername is not empty or only has white spaces.
     *
     * @param newUsername the new username to set
     * @return True if String newUsername is not empty or only has white spaces. Else, return false.
     */
    public boolean setUsername(String newUsername) {
        if (!newUsername.isBlank()) {
            username = newUsername;
            return true;
        }
        return false;
    }


    /**
     * Increments the Score by 5.
     */
    public void incrementScore() {
        currentScore += 5; // 5 points per block destroyed
    }


    @Override
    /**
     * Compares this Score with the specified Score from highest to lowest, then by username from A to Z.
     *
     * @param Score to be compared
     * @return a negative integer, zero, or a positive integer if this Score is greater than, equal to, or less than the specified Score
     */
    public int compareTo(Score other) {
        if (this.getScore() > other.getScore()) // this.score is higher
        {
            return -1;
        } else if (this.getScore() < other.getScore()) // this.score is lower
        {
            return 1;
        } else {
            return this.getUsername().compareTo(other.getUsername());
        }
    }


    @Override
    /**
     * Checks if the specified Score is equal to this Score.
     *
     * @param Score to be compared
     * @return true if this Score and the specified Score is equal.
     */
    public boolean equals(Object obj) {
        if (obj instanceof Score) {
            Score other = (Score) obj;
            return this.compareTo(other) == 0;
        }
        return false;
    }
}
