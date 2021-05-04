package breakout.model;


/**
 * Moves left and right using keyboard arrows. It will be used to keep ball in the air.
 * In charge of Paddle characteristics.
 */
public class Paddle {
	private int x; //x coordinate of the paddle object
	private int y; //y coordinate of the paddle object
	
	//dimensions of paddle
	private static final int PADDLE_WIDTH = Constants.getPaddleWidth();
	private static final int PADDLE_HEIGHT = Constants.getPaddleHeight();
	
	/**
	 * This is the constructor which initializes paddle location.
	 */
	public Paddle()
	{
		//initialize paddle location
		this.x = (Constants.getPanelWidth()/2) - (Constants.getPaddleWidth() / 2);
		this.y = Constants.getPanelHeight() - Constants.getPaddleHeight() - Constants.getPaddleOffSet();
	}

	public void setCoordinates(int x, int y){
		setX(x);
		setY(y);
	}

	/**
	 * In charge of paddle movement given that paddle moves only left and right
	 */
	public void move(int direction)
	{
		if (direction == Constants.getPaddleMoveLeftUnit()){
			moveLeft();
		} else {
			moveRight();
		}
	}

	public void moveLeft(){
		x += Constants.getPaddleMoveLeftUnit();
	}

	public void moveRight(){
		x += Constants.getPaddleMoveRightUnit();
	}
	
	/**
	 * Resets the position of the paddle
	 * @param r value indicating whether reset of ball should occur
	 */
	public void reset()
	{
		setCoordinates(Constants.getPaddleXReset(),Constants.getPaddleYReset());
	}
	public void setY(int y){
		this.y = y;
	}
	
	/**
	 * Sets the x value of the paddle
	 * @param x the new x coordinate
	 */
	public void setX(int x)
	{
		this.x = x;
	}

	/**
	 * Gets the x value of the paddle
	 * @param x the x coordinate
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * Gets the y value of the paddle
	 * @param y the y coordinate
	 */
	public int getY(){
		return y;
	}

	/**
	 * Gets the width of the paddle
	 */
	public int getPaddleWidth()
	{
		return PADDLE_WIDTH;
	}

	/**
	 * Gets the height of the paddle
	 */
	public int getPaddleHeight()
	{
		return PADDLE_HEIGHT;
	}

}
