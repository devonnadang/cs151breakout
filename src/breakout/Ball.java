package breakout;
//Ball Class
public class Ball {

	private static final int BALL_RADIUS = 5;
	private int speed = 60; //remove if not needed
	private int x; //x coordinate of the ball object
	private int y; //y coordinate of the ball object
	//how can it interact with paddle/bounce off of it?
	
	
	/**
	 * This is the constructor which initializes x & y to its starting positions.
	 */
	public Ball(int x, int y)
	{
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
	
	//remove if not needed
	public void setSpeed(int s)
	{
		this.speed = s;
	}
	
	/**
	 * In charge of the ball movement.
	 */
	public void move()
	{
		//change x & y coord
		//should x & y be increased here or somewhere else?
		
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
	 * Destroys the given block by setting the Block's boolean destoryed variable to true
	 */
	public void destroyBlock(Block block)
	{
		block.setDestroyed(true);
	}
	
	/**
	 * Resets the position of the ball
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
