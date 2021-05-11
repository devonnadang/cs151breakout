package breakout.controller;

/**
 * This is called MoveMessage, but really it's just for moving the paddle along the x-axis as of now.
 * Honestly, not sure if I should let Ball be moved using MoveMessage as well.
 */
public class MovePaddleMessage implements Message{
    private double newVelocity;

    /**
     * Intializes MoveMessage with the new coordinate.
     * @param newVelocity the new velocity to update BoardView.
     */
    public MovePaddleMessage(double newVelocity) {
        this.newVelocity = newVelocity;
    }

    public double getNewVelocity() {
        return newVelocity;
    }
}
