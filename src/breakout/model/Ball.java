package breakout.model;
//The Ball Class
public class Ball {

	private static final int BALL_RADIUS = 10;
	private int speed = 60; //remove if not needed
	private int x; //x coordinate of the ball object
	private int y; //y coordinate of the ball object
	//how can it interact with paddle/bounce off of it?
	
	
	/**
	 * This is the constructor which initializes x & y to its starting positions.
	 */
	public Ball(int x, int y)
	{
		//this.x = (Board.getBoardWidth()/2) - getBallRadius();
		//this.y = (Board.getBoardHeight()/2) - getBallRadius();
		this.x = x;
		this.y = y;
		setCoordinates(x, y);
	}
	
	/**
	 * @return x coordinate
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * @return y coordinate
	 */
	public int getY()
	{
		return y;
	}

	public int getBallRadius()
	{
		return BALL_RADIUS;
	}

	/**
	 * Set the placement for Block.
	 * @param x determines the x coordinate
	 * @param y determines the x coordinate
	 */
	public void setCoordinates(int x, int y){
		setX(x);
		setY(y);
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
	 * In charge of the ball movement.
	 */
	public void move()
	{
		//change x & y coord
		
		/*
		if(x is #) //out of bound 
		{
			//setX(1);
		}
		
		if(y is #) //out of bound 
		{
			//setY(0);
		}
		*/
	}
	
	/**
	 * Destroys the given block by setting the Block's boolean destroyed variable to true
	 * @param block the block that should be destroyed
	 */
	public void destroyBlock(Block block)
	{
		block.setDestroyed(true);
	}
	
	/**
	 * Resets the position of the ball
	 * @param r value indicating whether reset of ball should occur
	 */
	public void reset(boolean r)
	{
		if(r)
		{
			//set x & y to its initial coordinates
			//temp starting coordinates
			//this.x = 1;
			//this.y = 0; 
		}
	}
	
}
