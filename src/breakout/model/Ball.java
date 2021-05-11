package breakout.model;

import java.util.Random;

/**
 * The Ball is a class that moves and destroys blocks.
 * It's control of ball movements.
 */
public final class Ball {

	private static final Ball INSTANCE = new Ball();
	private static final int BALL_RADIUS = Constants.getBallRadius();
	private int x; //x coordinate of the ball object
	private int y; //y coordinate of the ball object
	//how can it interact with paddle/bounce off of it?
	private double [] ballVelocity;
	private double [] ballCoordinates; //[0] = x coordinate, [1] y coordinate
	private Random rgen = new Random();
	
	/**
	 * This is the constructor which initializes x & y to its starting positions.
	 */
	private Ball()
	{
		this.x = Constants.getBallXReset();
		this.y = Constants.getBallYReset();
		setCoordinates(x, y);
		ballVelocity = new double [] {Constants.getBallMaxVelocity() - rgen.nextInt(Constants.getBallMaxVelocity() * 2),
				Constants.getBallMaxVelocity() - rgen.nextInt(Constants.getBallMaxVelocity() * 2)};
		ballCoordinates = new double [] {Constants.getBallXReset(), Constants.getBallYReset()};
	}

	public static Ball getInstance() {
		return INSTANCE;
	}

	public double[] getBallCoordinates() {
		return ballCoordinates;
	}

	public double[] getBallVelocity() {
		return ballVelocity;
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


	public double getCenterX() {
		return ballCoordinates[0] + getHeight() / 2.0;
	}

	public double getCenterY() {
		return ballCoordinates[1] + getHeight() / 2.0;
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

	public void setBallCoordinates(double[] ballCoordinates) {
		this.ballCoordinates = ballCoordinates;
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
	 */
	public void reset()
	{
		setCoordinates(Constants.getBallXReset(), Constants.getBallYReset());
	}

	public int getHeight() {
		return Constants.getBallRadius() * 2;
	}

}
