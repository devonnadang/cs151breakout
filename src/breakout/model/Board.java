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
    Leaderboard leaderboard;
    private static final int ROWS = Constants.getRows();
    private static final int COLUMNS = Constants.getColumns();
    private static final int WIDTH = Constants.getPanelWidth();
    private static final int HEIGHT = Constants.getPanelHeight();
    private static final int BLOCK_START = WIDTH/10; //30
    private static final int BLOCK_WIDTH = Constants.getBlockWidth();
    private static final int BLOCK_HEIGHT = Constants.getBlockWidth();
    private static final int BLOCK_SEP = 2;

    public Board(){
        blockCounter = ROWS*COLUMNS;
        createBlocks();
        ball = new Ball();
        paddle = new Paddle();
        leaderboard = new Leaderboard();
    }
    
    public Leaderboard getScore() {
    	return leaderboard;
    }
    
    public void addScore(Score score) {
    	leaderboard.addNewScore(score);
    }

    public Ball getBall(){
        return ball;
    }
    
    private void createPaddle()
    {
    	paddle = new Paddle();
    }

    public Paddle getPaddle() {
        return paddle;
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
    }

    public static int getRows(){
        return ROWS;
    }

    public static int getColumns(){
        return COLUMNS;
    }

    public static int getBoardWidth(){
        return WIDTH;
    }

    public static int getBoardHeight(){
        return HEIGHT;
    }

    public int getBlockCounter(){
        return blockCounter;
    }

    public static int getBlockSep() {
        return BLOCK_SEP;
    }
}
