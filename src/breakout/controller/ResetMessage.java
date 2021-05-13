package breakout.controller;

public class ResetMessage implements Message {

    private double[] startingBall;
    private double startingPaddle;
    private int lives;

    public ResetMessage(double[] startingBall, double startingPaddle, int lives) {
        this.startingBall = startingBall;
        this.startingPaddle = startingPaddle;
        this.lives = lives;
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
