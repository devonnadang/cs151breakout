package breakout.controller;

/**
 * Message sent to reset the ball and paddle, and set the remaining life
 */
public class ResetMessage implements Message {

    private double[] startingBall;
    private double startingPaddle;
    private int lives;

    /**
     * Resets the position of ball, paddle after ball falls past the paddle.
     *
     * @param startingBall   the starting ball coordinates
     * @param startingPaddle the starting paddle coordinates
     * @param lives          the remaining lives
     */
    public ResetMessage(double[] startingBall, double startingPaddle, int lives) {
        this.startingBall = startingBall; // set ball back to starting position
        this.startingPaddle = startingPaddle; // set paddle back to starting position
        this.lives = lives; // lives remaining
    }

    public double[] getStartingBall() {
        return startingBall.clone();
    }

    public double getStartingPaddle() {
        return startingPaddle;
    }

    public int getLives() {
        return lives;
    }
}
