package breakout.model;

import breakout.controller.BlockDestroyedMessage;
import java.util.concurrent.BlockingQueue;

/**
 * The Block class can be destroyed by the Ball.
 */
public class Block {

	private boolean destroyed;
	private int x;
	private int y;
	private int row;
	private int column;
	private BlockingQueue queue;
//	private final static int BLOCK_HEIGHT = Constants.getBlockHeight();
//	private final static int BLOCK_WIDTH = Constants.getBlockWidth();
    private int blockHeight;
    private int blockWidth;


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

	public void destroy() {
	 	setDestroyed(true);
	 	setCoordinates(0,0);
	 	setBlockWidth(0);
	 	setBlockHeight(0);
	 	try {
	 		queue.add(new BlockDestroyedMessage(row, column));
		} catch (Exception e) {
	 		e.printStackTrace();
		}
	}
	 
	/**
	 * Indcate whether Block is destoryed or not
	 * @param d ]
	 */
	public void setDestroyed(boolean d) {
		destroyed = d;
	}

	/**
	 * Set the placement for Block.
	 * @param x determines the x coordinate
	 *  @param y determines the x coordinate
	 */
	public void setCoordinates(int x, int y){
		setX(x);
		setY(y);
	}

	/**
	 * @return x coordinate
	 */
	public int getXCoordinate(){
		return x;
	}

	/**
	 * @return y coordinate
	 */
	public int getYCoordinate(){
		return y;
	}
	
	/**
	 * Set the x coordinate
	 * @param x determines the x coordinate
	 */
	public void setX(int x)
	{
		this.x = x;
	}
	
	/**
	 * Set the y coordinate
	 * @param y determines the x coordinate
	 */
	public void setY(int y)
	{
		this.y = y;
	}

	/**
	 * Set the x coordinate
	 * @param x determines the x coordinate
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * Set the y coordinate
	 * @param y determines the x coordinate
	 */
	public int getY()
	{
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

	public void setBlockWidth(int i) {
		this.blockWidth = blockWidth;
	}

	public void setBlockHeight(int i) {
	    this.blockHeight = blockHeight;
	}
}
