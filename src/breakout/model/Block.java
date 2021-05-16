package breakout.model;

import breakout.controller.BlockDestroyedMessage;
import java.util.concurrent.BlockingQueue;

/**
 * The Block class models a block that can be destroyed by Ball.
 */
public class Block {

    private boolean destroyed;
    private int x;
    private int y;
    private int row;
    private int column;
    private BlockingQueue queue;
    private int blockHeight;
    private int blockWidth;

    /**
     * Constructs Block with an x and y coordinate, row and column, and a queue to add a
     * BlockDestroyedMessage when needed.
     *
     * @param x      the x coordinate
     * @param y      the y coordinate
     * @param row    the row of the block
     * @param column the column of the block
     * @param queue  the queue to add messages
     */
    public Block(int x, int y, int row, int column, BlockingQueue queue) {
        this.x = x;
        this.y = y;
        this.row = row;
        this.column = column;
        this.queue = queue;
        setCoordinates(x, y);
        destroyed = false;
        this.blockHeight = Constants.getBlockHeight();
        this.blockWidth = Constants.getBlockWidth();
    }

    /**
     * Destroys the block by setting the destroyed boolean to true, setting the coordinates to x=0
     * and y=0, and sending a BlockDestroyedMessage to update the views and models.
     */
    public void destroy() {
        setDestroyed(true);
        setCoordinates(0, 0);
        blockWidth = 0;
        blockHeight = 0;
        try {
            queue.add(new BlockDestroyedMessage(row, column));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Indicate whether Block is destroyed or not.
     *
     * @param d the boolean to set destroyed to
     */
    public void setDestroyed(boolean d) {
        destroyed = d;
    }

    /**
     * Set the placement for Block.
     *
     * @param x determines the x coordinate
     * @param y determines the x coordinate
     */
    public void setCoordinates(int x, int y) {
        setX(x);
        setY(y);
    }

    public int getXCoordinate() {
        return x;
    }

    public int getYCoordinate() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getDestroyed() {
        return destroyed;
    }

    public int getBlockWidth() {
        return blockWidth;
    }

    public int getBlockHeight() {
        return blockHeight;
    }
}