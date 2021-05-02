package breakout.model;

import java.util.ArrayList;

import breakout.view.View;

/**
 * The Board class aggregates block, ball, and paddle. 
 * Responsible for communicating with the View class.
 */
public class Board {
    Block [][] blocks;
    Ball ball;
    Paddle paddle;
    int blockCounter;
    private static final int ROWS = 5;
    private static final int COLUMNS = 10;
    private static final int WIDTH = 300;
    private static final int HEIGHT = 600;
    private static final int BLOCK_START = WIDTH/10; //30
    private static final int BLOCK_WIDTH = 10;
	private static final int BLOCK_HEIGHT = 5;

    public Board(){
        blockCounter = ROWS*COLUMNS;
        createBlocks();
        createBall();
        createPaddle();
    }

    /**
     * TODO method
     * Will notify view of changes. 
     * @param view
     */
    public void notify(View view) {
        
    }

    /**
     * Creates the ball that will destroy blocks.
     */
    private void createBall(){
        ball = new Ball(280, 550); //Ball(x coordinate, y coordinate)
    }

    public Ball getBall(){
        return ball;
    }
    
    private void createPaddle()
    {
    	paddle = new Paddle(this.getBoardWidth(), this.getBoardHeight());
		
    }

    /**
     * 
     * @return the coordinates of each block in the Block[][] blocks
     */
    public String toString(){
        String str = new String();
        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j< COLUMNS; j++){
                str += "block [" + i + "] [" + j +"] coords: (" + blocks[i][j].getXCoordinate() + ", " +  blocks[i][j].getYCoordinate() + ") \n";
            }
        }
        return str;
    }

    /**
     * TODO method
     * Checks if the ball clashes with any blocks. Should be called often. 
     */
    protected boolean checkClash(){
        /**
         * if ( brick isn't destoryed yet && ball is touching the border of any brick) {
         *      ball.destory(Block)
         *      brickCounter--;
         * }
         */
        return true;
    }

    /**
     * Creates a block set with 5 rows and 10 columns. Each block has a width of 10 and a height of 5.
     * Around this block set is space of 30 pixels on the top, left, and right.
     */
    private void createBlocks(){
        blocks = new Block[ROWS][COLUMNS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                blocks[i][j] = new Block(BLOCK_START + BLOCK_WIDTH*j, BLOCK_START + (BLOCK_HEIGHT*i) );
            }
        }
    }

    /**
     * @return if there are any bricks left
     */
    public boolean bricksAreCleared(){
        if (blockCounter == 0){
            return true;
        } else {
            return false;
        }

        /*
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (blocks[i][j].getDestroyed() != true) {
                    return false;
                }
            }
        }
        return true;
        */
    }

    public int getRows(){
        return ROWS;
    }

    public int getColumns(){
        return COLUMNS;
    }

    public int getBoardWidth(){
        return WIDTH;
    }

    public int getBoardHeight(){
        return HEIGHT;
    }

    public int getBlockCounter(){
        return blockCounter;
    }
}
