package breakout.model;


/**
 * Moves left and right using keyboard arrows. It will be used to keep ball in the air.
 * In charge of Paddle characteristics.
 */
public final class Paddle {
	private double[] paddleCoordinates;
	private double paddleVelocity;
	
	//dimensions of paddle
	private static final int PADDLE_WIDTH = Constants.getPaddleWidth();
	private static final int PADDLE_HEIGHT = Constants.getPaddleHeight();
	
	private static final Paddle INSTANCE = new Paddle();

	/**
	 * This is the constructor which initializes paddle location.
	 */
	private Paddle()
	{
		//initialize paddle location
		this.paddleCoordinates = new double[]{Constants.getPaddleXReset(), Constants.getPaddleYReset()};
		this.paddleVelocity = 0;
	}

	public static Paddle getInstance() {
		return INSTANCE;
	}

	public void move() {
		paddleCoordinates[0] += paddleVelocity;
	}

	public double[] getPaddleCoordinates() {
		return paddleCoordinates;
	}

	public void setPaddleVelocity(double paddleVelocity) {
		this.paddleVelocity = paddleVelocity;
	}

	public void setPaddleCoordinates(double[] paddleCoordinates) {
		this.paddleCoordinates = paddleCoordinates;
	}

//	/**
//	 * Resets the position of the paddle
//	 */
//	public void reset()
//	{
//		setCoordinates(Constants.getPaddleXReset(),Constants.getPaddleYReset());
//	}

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
