package breakout;
//Paddle Class
public class Paddle {
	//how can it better work with board and ball?
	
	//is speed really needed?
	private int x; //x coordinate of the paddle object
	private int y; //y coordinate of the paddle object
	
	//dimensions of paddle
	private static final int PADDLE_WIDTH = 50;
	private static final int PADDLE_HEIGHT = 10;
	
	public Paddle()
	{
		//initialize paddle location
		//this.x = board length/2;
	}
	
	//set x-coor
	public void setX(int x)
	{
		this.x = x;
	}
		
	//moves left and right horizontally
	public void move()
	{
		//will need to use swing in order to observe direction changes through arrow keys 
		//can check for direction in another method
		//setX(#) accordingly
	}
	
	/**
	 * Resets the position of the paddle
	 */
	public void reset(boolean r)
	{
		if(r)
		{
			//reset to paddle's starting location
			//this.x = board length/2;
		}
	}
	
	//Additional possible methods can include ones that check if certain direction is pressed by key
}
