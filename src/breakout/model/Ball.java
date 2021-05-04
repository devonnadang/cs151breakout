package breakout.model;


/**
 * The Ball is a class that moves and destroys blocks.
 * It's control of ball movements.
 */
public class Ball {

	private static final int BALL_RADIUS = Constants.getBallRadius();
	private int speed = 60; //remove if not needed
	private int x; //x coordinate of the ball object
	private int y; //y coordinate of the ball object
	//how can it interact with paddle/bounce off of it?
	private int [] ballVelocity; 
	
	
	/**
	 * This is the constructor which initializes x & y to its starting positions.
	 */
	public Ball()
	{
		this.x = Constants.getBallXReset();
		this.y = Constants.getBallYReset();
		setCoordinates(x, y);
		ballVelocity = new int [] {Constants.getBallVelocity(), Constants.getBallVelocity()};
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
	 * @return ball radius
	 */
	public static int getBallRadius()
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
		x += ballVelocity[0];
		y += ballVelocity[1];
        

        // Handles collision between ball and left and right side of the view.
        if (x < 0 || x > Constants.getPanelWidth()- (Constants.getBallRadius()*2)) {
            ballVelocity[0] *= -1;
        }

        // Handles collision between ball and top of the view.
        if (y < 0) {
            ballVelocity[1] *= -1;
        }

        // Handles collision between ball and bottom of the view.
        // Actually if ball goes below it should end game, but there is no end game implementation
        // as of now.
        if (ballFallsBelow()) {
            ballVelocity[0] = 0;
			ballVelocity[1] = 0;
			reset();
        }
	}

	public boolean ballFallsBelow(){
		if (y >= Constants.getPanelHeight() - (Constants.getBallRadius()*2)) {
            return true;
        } return false;
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
	public void reset()
	{
		setCoordinates(Constants.getBallXReset(), Constants.getBallYReset());
	}
	
}
