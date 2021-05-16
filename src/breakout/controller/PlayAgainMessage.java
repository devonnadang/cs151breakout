package breakout.controller;

/**
 * Message sent to reset board to play again
 */

public class PlayAgainMessage implements Message {

    private double[] ballCoordinates;
    private double[] paddleCoordinates;

    /**
     * Initializes the PlayAgainMessage.
     *
     * @param ballCoordinates   the input ball coordinates
     * @param paddleCoordinates the input paddle coordinates
     */
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
