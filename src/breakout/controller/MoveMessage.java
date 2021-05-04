package breakout.controller;

/**
 * This is called MoveMessage, but really it's just for movin the paddle along the x-axis as of now.
 * Honestly, not sure if I should let Ball be moved using MoveMessage as well.
 */
public class MoveMessage implements Message{
    private int direction;

    /**
     * Intializes MoveMessage with the new coordinate.
     * @param newCoordinate the new coordinate to update BoardView.
     */
    public MoveMessage(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }
}
