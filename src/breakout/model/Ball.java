package breakout.model;

import java.util.Random;

/**
 * The Ball is a class that moves and destroys blocks. It's control of ball movements.
 */
public final class Ball {

    private static final Ball INSTANCE = new Ball();
    private static final int BALL_RADIUS = Constants.getBallRadius();
    //how can it interact with paddle/bounce off of it?
    private double[] ballVelocity;
    private double[] ballCoordinates; //[0] = x coordinate, [1] y coordinate
    private Random rgen = new Random();

    /**
     * This is the constructor which initializes x & y to its starting positions.
     */
    private Ball() {
        ballVelocity = new double[]{
                Constants.getBallMaxVelocity() - rgen.nextInt(Constants.getBallMaxVelocity() * 2),
                Constants.getBallMaxVelocity() - rgen.nextInt(Constants.getBallMaxVelocity() * 2)};
        ballCoordinates = new double[]{Constants.getBallXReset(), Constants.getBallYReset()};
    }

    public static Ball getInstance() {
        return INSTANCE;
    }

    public double[] getBallCoordinates() {
        return ballCoordinates;
    }

    public double[] getBallVelocity() {
        return ballVelocity;
    }

    public void setBallVelocity(double[] ballVelocity) {
        this.ballVelocity = ballVelocity;
    }

    public double getCenterX() {
        return ballCoordinates[0] + Constants.getBallRadius();
    }

    public double getCenterY() {
        return ballCoordinates[1] + Constants.getBallRadius();
    }

    public void setBallCoordinates(double[] ballCoordinates) {
        this.ballCoordinates = ballCoordinates;
    }

    /**
     * In charge of the ball movement.
     *
     * @param boardWidth the width of the board
     */
    public void move(int boardWidth) {
        // These two statements will make sure max velocity is 4 and min velocity is -4.
        ballVelocity[0] = Math
                .max(-Constants.getBallMaxVelocity(),
                        Math.min(Constants.getBallMaxVelocity(), ballVelocity[0]));
        ballVelocity[1] = Math
                .max(-Constants.getBallMaxVelocity(),
                        Math.min(Constants.getBallMaxVelocity(), ballVelocity[1]));

        if (ballVelocity[1] == 0) {
            ballVelocity[1] = Constants.getBallMaxVelocity();
        }

        // Handles if ball is going too slow. Using .5 so that ball accelerates slowly.
        if (ballVelocity[0] > -Constants.getBallMinVelocity() && ballVelocity[0] < Constants
                .getBallMinVelocity()) {
            if (ballVelocity[0] < 0) {
                ballVelocity[0] -= .5;
            } else if (ballVelocity[0] > 0) {
                ballVelocity[0] += .5;
            }
        }

        if (ballVelocity[1] > -Constants.getBallMinVelocity() && ballVelocity[1] < Constants
                .getBallMinVelocity()) {
            if (ballVelocity[1] < 0) {
                ballVelocity[1] -= .5;
            } else if (ballVelocity[1] > 0) {
                ballVelocity[1] += .5;
            }
        }

        for (int i = 0; i < ballCoordinates.length; i++) {
            ballCoordinates[i] += ballVelocity[i];
        }

        // Handles collision between ball and left and right side of the view.
        if (ballCoordinates[0] < 0
                || ballCoordinates[0] > boardWidth - Constants.getBallRadius() * 2) {
            ballVelocity[0] *= -1;
        }

        // Handles collision between ball and top of the view.
        if (ballCoordinates[1] < 0) {
            ballVelocity[1] *= -1;
        }
    }

    /**
     * Checks if ball falls below the view.
     *
     * @param boardHeight the height of the board
     * @return true if ball falls below the view, else false
     */
    public boolean ballFallsBelow(int boardHeight) {
        if (ballCoordinates[1] >= boardHeight - (Constants.getBallRadius() * 2)) {
            return true;
        }

        return false;
    }

    /**
     * Destroys the given block by setting the Block's boolean destroyed variable to true
     *
     * @param block the block that should be destroyed
     */
    public void destroyBlock(Block block) {
        block.setDestroyed(true);
    }

    public int getHeight() {
        return Constants.getBallRadius() * 2;
    }

}
