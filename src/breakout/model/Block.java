package breakout.model;

/**
 * The Block class can be destroyed by the Ball.
 */
public class Block {

    private boolean destroyed;
    private int x;
    private int y;
    private final static int BLOCK_HEIGHT = Constants.getBlockHeight();
    private final static int BLOCK_WIDTH = Constants.getBlockWidth();

    /**
     * Initializes Block with x and y coordinates and destroyed set to false.
     * @param x x coordinate of Block
     * @param y y coordinate of Block
     */
    public Block(int x, int y) {
        setCoordinates(x, y);
        destroyed = false;
    }

    /**
     * Indcate whether Block is destoryed or not
     *
     * @param d ]
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

    /**
     * @return x coordinate
     */
    public int getXCoordinate() {
        return x;
    }

    /**
     * @return y coordinate
     */
    public int getYCoordinate() {
        return y;
    }

    /**
     * Set the x coordinate
     *
     * @param x determines the x coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set the y coordinate
     *
     * @param y determines the x coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Set the x coordinate
     *
     * @param x determines the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y coordinate
     */
    public int getY() {
        return y;
    }

    public boolean getDestroyed() {
        return destroyed;
    }


    public static int getBlockWidth() {
        return BLOCK_WIDTH;
    }

    public static int getBlockHeight() {
        return BLOCK_HEIGHT;
    }

}
