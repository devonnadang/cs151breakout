package breakout.controller;

/**
 * This is called MoveMessage, but really it's just for moving the paddle along the x-axis as of
 * now.
 */
public class MoveMessage implements Message {

    private int newVelocity;

    /**
     * Intializes MoveMessage with the new coordinate.
     *
     * @param newVelocity the new velocity to update BoardView.
     */
    public MoveMessage(int newVelocity) {
        this.newVelocity = newVelocity;
    }

    public int getNewVelocity() {
        return newVelocity;
    }
}
