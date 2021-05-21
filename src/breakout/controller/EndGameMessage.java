package breakout.controller;

/**
 * Message sent to signal the end of the game. All the lives have been lost at this point.
 */
public class EndGameMessage implements Message {

    private double[] startingBall;
    private double[] startingPaddle;
    private int lives;

    /**
     * Constructs EndGameMessage with the starting ball coordinates, the starting paddle
     * coordinates, and the lives remaining which should be 0.
     *
     * @param startingBall   the starting ball coordinates
     * @param startingPaddle the starting paddle coordinates
     * @param lives          the lives remaining which should be 0
     */
    public EndGameMessage(double[] startingBall, double[] startingPaddle, int lives) {
        this.startingBall = startingBall;
        this.startingPaddle = startingPaddle;
        this.lives = lives;
    }

    public double[] getStartingBall() {
        return startingBall.clone();
    }

    public double[] getStartingPaddle() {
        return startingPaddle.clone();
    }

    public int getLives() {
        return lives;
    }
}
