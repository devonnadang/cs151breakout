package breakout.controller;

/**
 * Message sent to signal the end of the game.
 */
public class EndGameMessage implements Message {

    private double[] startingBall;
    private double[] startingPaddle;
    private int lives;

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
