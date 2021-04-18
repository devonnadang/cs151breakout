package breakout;

//This is the Block class
public class Block {
	private static final int BLOCK_WIDTH = 10;
	private static final int BLOCK_HEIGHT = 5;
	private boolean destroyed;
	private int x;
	private int y;

	/**
	 * TODO: properly give Block x and y coordinates 
	 * public Block(int x, int y) {
		this.x = x;
		this.y = y; 
		destroyed = false;
		}
	 */
	
	public Block() {
		destroyed = false;
	}

	public void setDestroyed(boolean d) {
		destroyed = d;
	}

	public boolean getDestroyed() {
		return destroyed;
	}

	public int getBlockWidth() {
		return BLOCK_WIDTH;
	}

	public int getBlockHeight() {
		return BLOCK_HEIGHT;
	}
}
