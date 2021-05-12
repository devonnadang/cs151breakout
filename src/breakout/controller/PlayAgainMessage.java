package breakout.controller;

public class PlayAgainMessage implements Message {
    private double[] ballCoordinates;
    private double[] paddleCoordinates;

    public PlayAgainMessage(double[] ballCoordinates, double[] paddleCoordinates) {
        this.ballCoordinates = ballCoordinates;
        this.paddleCoordinates = paddleCoordinates;
    }

    public double[] getBallCoordinates() {
        return ballCoordinates;
    }

    public double[] getPaddleCoordinates() {
        return paddleCoordinates;
    }
}
