package breakout.controller;

public class EndGameMessage implements Message {

    private double[] startingBall;
    private double[] startingPaddle;

    public EndGameMessage(double[] startingBall, double[] startingPaddle) {
        this.startingBall = startingBall;
        this.startingPaddle = startingPaddle;
    }

    public double[] getStartingBall() {
        return startingBall.clone();
    }

    public double[] getStartingPaddle() {
        return startingPaddle.clone();
    }
}
