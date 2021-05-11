package breakout.model;

import java.awt.Insets;
import java.util.Random;
import javax.swing.Timer;

/**
 * The Board class aggregates block, ball, and paddle. Responsible for communicating with the View
 * class.
 */
public class Board {

    private Block[][] blocks;
    private Ball ball;
    private Paddle paddle;
    private int blockCounter;
    private Leaderboard leaderboard;
    private static final int ROWS = Constants.getRows();
    private static final int COLUMNS = Constants.getColumns();
    private static final int WIDTH = Constants.getPanelWidth();
    private static final int HEIGHT = Constants.getPanelHeight();
    private static final int BLOCK_START = WIDTH / 10; //30
    private static final int BLOCK_WIDTH = Constants.getBlockWidth();
    private static final int BLOCK_HEIGHT = Constants.getBlockWidth();
    private static final int BLOCK_SEP = Constants.getBlockSep();


    public static final int BALL_WIDTH = Constants.getBallRadius() * 2;
    public static final int BALL_HEIGHT = Constants.getBallRadius() * 2;
    public static final int PADDLE_WIDTH = Constants.getPaddleWidth();
    public static final int PADDLE_HEIGHT = Constants.getPaddleHeight();
    public static final int BOARD_WIDTH = Constants.getPanelWidth();
    public static final int BOARD_HEIGHT = Constants.getPanelHeight();
    public static final int BALL_MAX_VELOCITY = Constants.getBallMaxVelocity();
    public static final int BALL_MIN_VELOCITY = Constants.getBallMinVelocity();

    private double[] ballCoordinates;
    private double[] ballVelocity;
    private double[] paddleCoordinates;
    private int paddleVelocity;
    private boolean[][] isDestroyed;
    private int livesCounter = 1;
    private Leaderboard scoreList;
    private boolean gameFinished;
    private double circleToBoxLength;
    private double[] closestPointToCircle;
    private Insets frameInsets;
    private Random rgen = new Random();

    public Board(Insets frameInsets) {
        this.frameInsets = frameInsets;
        blockCounter = ROWS * COLUMNS;
        createBlocks();

        leaderboard = Leaderboard.getInstance();
        ball = Ball.getInstance();
        paddle = Paddle.getInstance();
        paddle.setPaddleCoordinates(new double[]{Constants.getPaddleXReset(),
                HEIGHT - paddle.getPaddleHeight() - frameInsets.bottom - frameInsets.top - Constants
                        .getPaddleOffSet()});
        ball.setBallCoordinates(new double[]{Constants.getBallXReset(),
                paddle.getPaddleCoordinates()[1] - ball.getHeight()});
        this.ballVelocity = ball.getBallVelocity();
        this.ballCoordinates = ball.getBallCoordinates();
        this.paddleCoordinates = paddle.getPaddleCoordinates();
    }

    public Leaderboard getScore() {
        return leaderboard;
    }

    public void addScore(Score score) {
        leaderboard.addNewScore(score);
    }

    public Ball getBall() {
        return ball;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public Block getBlock(int i, int j) {
        return blocks[i][j];
    }

    /**
     * @return the coordinates of each block in the Block[][] blocks
     */
    public String toString() {
        String str = new String();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                str += "block [" + i + "] [" + j + "] coords: (" + blocks[i][j].getXCoordinate()
                        + ", " + blocks[i][j].getYCoordinate() + ") \n";
            }
        }
        return str;
    }

    /**
     * TODO method Checks if the ball clashes with any blocks. Should be called often.
     */
    protected boolean checkClash() {
        /**
         * if ( brick isn't destoryed yet && ball is touching the border of any brick) {
         *      ball.destory(Block)
         *      brickCounter--;
         * }
         */
        return true;
    }

    /**
     * Creates a block set with 5 rows and 10 columns. Each block has a width of 10 and a height of
     * 5. Around this block set is space of 30 pixels on the top, left, and right.
     */
    private void createBlocks() {
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
    public boolean bricksAreCleared() {
        if (blockCounter == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static int getRows() {
        return ROWS;
    }

    public static int getColumns() {
        return COLUMNS;
    }

    public static int getBoardWidth() {
        return WIDTH;
    }

    public static int getBoardHeight() {
        return HEIGHT;
    }

    public int getBlockCounter() {
        return blockCounter;
    }

    public static int getBlockSep() {
        return BLOCK_SEP;
    }

    // Moves the ball and will handle collision between ball and paddle and the view.
    private void moveBall() {
//        livesLeftDisplay.setText("Lives Left: " + (3-livesCounter));

        // These two statements will make sure max velocity is 5 and min velocity is -5.
        ballVelocity[0] = Math
                .max(-BALL_MAX_VELOCITY, Math.min(BALL_MAX_VELOCITY, ballVelocity[0]));
        ballVelocity[1] = Math
                .max(-BALL_MAX_VELOCITY, Math.min(BALL_MAX_VELOCITY, ballVelocity[1]));

        if (ballVelocity[1] == 0) {
            ballVelocity[1] = BALL_MAX_VELOCITY;
        }

        // Handles if ball is going too slow. Using .5 so that ball accelerates slowly.
        if (ballVelocity[0] > -BALL_MIN_VELOCITY && ballVelocity[0] < BALL_MIN_VELOCITY) {
            if (ballVelocity[0] < 0) {
                ballVelocity[0] -= .5;
            } else if (ballVelocity[0] > 0) {
                ballVelocity[0] += .5;
            }
        }

        if (ballVelocity[1] > -BALL_MIN_VELOCITY && ballVelocity[1] < BALL_MIN_VELOCITY) {
            if (ballVelocity[1] < 0) {
                ballVelocity[1] -= .5;
            } else if (ballVelocity[1] > 0) {
                ballVelocity[1] += .5;
            }
        }

        for (int i = 0; i < ballCoordinates.length; i++) {
            ballCoordinates[i] += ballVelocity[i];
        }

        // I think this makes the intersects method work better?
        ball.setBallCoordinates(ballCoordinates);

        int boardWidth = BOARD_WIDTH - frameInsets.left - frameInsets.right;
        // Handles collision between ball and left and right side of the view.
        if (ballCoordinates[0] < 0 || ballCoordinates[0] > boardWidth - BALL_WIDTH) {
            ballVelocity[0] *= -1;
        }

        // Handles collision between ball and top of the view.
        if (ballCoordinates[1] < 0) {
            ballVelocity[1] *= -1;
        }

        // Handles collision between ball and bottom of the view.
        // Actually if ball goes below it should end game, but there is no end game implementation
        // as of now.
        if (ballCoordinates[1] >= boardWidth - BALL_HEIGHT) {
//            if (livesCounter != 3) {
//                livesCounter++;
//  //              livesLeftDisplay.setText("Lives Left: " + (3 - livesCounter));
//                gameFinished = false;
//                repaintBoard();
//            } else if (livesCounter == 3) {
//                gameFinished = true;
//                gameOver.setText("GameOver!");
//                endGame();
//            }
        }

        // If ball intersects paddle then resolve collision.
        if (ballIntersects()) {
            ballAndPaddleCollision();
        }

        boolean stop = false;
        // If ball and block collide, call this method. PAUL
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                Block block = blocks[i][j];
                boolean ballIntersection = ballIntersects(block);
                if (ballIntersection && stop) {
                    ballAndBlockCollision(block);
                } else if (ballIntersection) {
                    ballAndBlockCollision(block);
                    isDestroyed[i][j] = true;
//                    finalScore += 5;
                    stop = true;
                }
            }
        }
    }

    private boolean ballIntersects(Block block) {
        double blockTop = block.getY();
        double blockBottom = block.getY() + Constants.getBlockHeight();
        double blockLeft = block.getX();
        double blockRight = block.getX() + Constants.getBlockWidth();

        closestPointToCircle = new double[]{ball.getCenterX(), ball.getCenterY()};

        // The same as the if/else statements, but shorter.
        closestPointToCircle[0] = Math
                .max(blockLeft, Math.min(blockRight, closestPointToCircle[0]));
        closestPointToCircle[1] = Math
                .max(blockTop, Math.min(blockBottom, closestPointToCircle[1]));

        closestPointToCircle[0] -= ball.getCenterX();
        closestPointToCircle[1] -= ball.getCenterY();

        circleToBoxLength = Math.hypot(closestPointToCircle[0], closestPointToCircle[1]);

        // If the length of the line from the center of the circle to the point on the box closest
        // to the ball is less than or equal to the ball radius then there is collision.
        return circleToBoxLength <= Constants.getBallRadius();
    }

    private boolean ballIntersects() {
        double blockTop = paddleCoordinates[1];
        double blockBottom = paddleCoordinates[1] + Constants.getBlockHeight();
        double blockLeft = paddleCoordinates[0];
        double blockRight = paddleCoordinates[0] + Constants.getBlockWidth();

        closestPointToCircle = new double[]{ball.getCenterX(), ball.getCenterY()};

        // The same as the if/else statements, but shorter.
        closestPointToCircle[0] = Math
                .max(blockLeft, Math.min(blockRight, closestPointToCircle[0]));
        closestPointToCircle[1] = Math
                .max(blockTop, Math.min(blockBottom, closestPointToCircle[1]));

        closestPointToCircle[0] -= ball.getCenterX();
        closestPointToCircle[1] -= ball.getCenterY();

        circleToBoxLength = Math.hypot(closestPointToCircle[0], closestPointToCircle[1]);

        // If the length of the line from the center of the circle to the point on the box closest
        // to the ball is less than or equal to the ball radius then there is collision.
        return circleToBoxLength <= Constants.getBallRadius();
    }

    private void ballAndBlockCollision(Block block) {

        double overlap = Constants.getBallRadius() - circleToBoxLength;
        double collisionResolution1 = closestPointToCircle[0] / circleToBoxLength * overlap;
        double collisionResolution2 = closestPointToCircle[1] / circleToBoxLength * overlap;

        ballCoordinates[0] -= collisionResolution1;
        ballCoordinates[1] -= collisionResolution2;

        // Got it from here:
        // https://gamedev.stackexchange.com/questions/10911/a-ball-hits-the-corner-where-will-it-deflect
        double x = closestPointToCircle[0];
        double y = closestPointToCircle[1];
        double c = -2 * (ballVelocity[0] * x + ballVelocity[1] * y) / (x * x + y * y);
        ballVelocity[0] += c * x;
        ballVelocity[1] += c * y;
    }

    /**
     * This method is for handling any collisions that happen between ball and paddle. This method
     * shouldn't affect any other part of the program.
     */
    private void ballAndPaddleCollision() {
        if (!gameFinished && ballVelocity[0] == 0) {
            ballVelocity[0] = -5;
        }

        double paddleRight = paddleCoordinates[0] + PADDLE_WIDTH;
        // This is the top of the paddle relative to the ball's coordinates. The actual top
        // of paddle is just paddleCoordinates[1].
        double paddleTop = paddleCoordinates[1] - BALL_HEIGHT;

        // Hitting on left side makes ball go left. Hitting on middle (Giving it about 10 pixels of
        // space) makes ball go straight up. Hitting on right side makes ball go right.
        if (ballCoordinates[0] >= paddleCoordinates[0] && ballCoordinates[0] <= paddleRight
                && ballCoordinates[1] >= paddleTop && ballCoordinates[1] < paddleTop + Constants
                .getBallRadius()) {
            ballVelocity[0] += BALL_MAX_VELOCITY - rgen.nextInt(
                    BALL_MAX_VELOCITY * 2); // creates random direction on paddle from -5 to 5
            ballVelocity[1] *= -1;
            ballCoordinates[1] = paddleTop - 1;
        }
        // TODO
//        else {
//            // If it hits side of paddle then just use ball and block collision, which also means
//            // end of game when it hits the bottom of the board.
//            ballAndBlockCollision();
//        }
    }
}
