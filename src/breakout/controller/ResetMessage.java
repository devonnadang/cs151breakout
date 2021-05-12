package breakout.controller;

public class ResetMessage implements Message {

    private double[] startingBall;
    private double startingPaddle;

    public ResetMessage(double[] startingBall, double startingPaddle) {
        this.startingBall = startingBall;
        this.startingPaddle = startingPaddle;
    }

    public double[] getStartingBall() {
        return startingBall.clone();
    }

    public double getStartingPaddle() {
        return startingPaddle;
    }
}
