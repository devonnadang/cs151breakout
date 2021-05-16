package breakout.controller;

import breakout.model.Score;

/**
 * Message sent to save the score.
 */
public class SaveScoreMessage implements Message {

    private Score score;

    /**
     * Initializes the SaveScoreMessage with the score and username.
     *
     * @param score    score to be saved
     * @param username username to be saved
     */
    public SaveScoreMessage(int score, String username) {
        this.score = new Score();
        this.score.setScore(score);
        this.score.setUsername(username);
    }

    public Score getScore() {
        return score;
    }

}
