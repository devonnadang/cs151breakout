package breakout.model;

import java.util.ArrayList;

import breakout.view.View;

/**
 * The Board class aggregates block, ball, and paddle. 
 * Responsible for communicating with the View class.
 */
public class Board {
  
    private Block [][] blocks;
    private Ball ball;
    private Paddle paddle;
    private int blockCounter;
    private Leaderboard leaderboard;
    private static final int ROWS = Constants.getRows();
    private static final int COLUMNS = Constants.getColumns();
    private static final int WIDTH = Constants.getPanelWidth();
    private static final int HEIGHT = Constants.getPanelHeight();
    private static final int BLOCK_START = WIDTH/10; //30
    private static final int BLOCK_WIDTH = Constants.getBlockWidth();
    private static final int BLOCK_HEIGHT = Constants.getBlockWidth();
    private static final int BLOCK_SEP = Constants.getBlockSep();

    public Board(){
        blockCounter = ROWS*COLUMNS;
        createBlocks();

        leaderboard = new Leaderboard();
        ball = Ball.getInstance();
        paddle = Paddle.getInstance();
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

    public Paddle getPaddle() {
        return paddle;
    }

    public Block getBlock(int i, int j) {
        return blocks[i][j];
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
                int x = 30 + (BLOCK_WIDTH * (j + 1)) + (BLOCK_SEP * j);
                int y = 30 + (BLOCK_HEIGHT * (i + 1)) + (BLOCK_SEP * i);
                blocks[i][j] = new Block(x, y);
            }
        }
    }

    protected void ballCollide(Block block) {
        ball.destroyBlock(block);
        blockCounter--;
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
