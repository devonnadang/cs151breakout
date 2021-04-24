package breakout.model;

//This is the Block class
public class Block {
	private boolean destroyed;
	private int x;
	private int y;


	 public Block(int x, int y) {
		setCoordinates(x, y);
		destroyed = false;
	}
	 
	/**
	 * 
	 * @param d indcate
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

	public int getXCoordinate(){
		return x;
	}

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

	/*
	public int getBlockWidth() {
		return BLOCK_WIDTH;
	}

	public int getBlockHeight() {
		return BLOCK_HEIGHT;
	}
	*/
}
