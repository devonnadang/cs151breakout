package breakout.model;

import breakout.controller.EndGameMessage;
import breakout.controller.ResetMessage;
import java.awt.Insets;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * The Board class aggregates block, ball, and paddle. Responsible for communicating with the View
 * class.
 */
public class Board {

    private BlockingQueue queue;
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
    private static final int BLOCK_HEIGHT = Constants.getBlockHeight();
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
    private final double[] startingBall;
    private final double[] startingPaddle;
    private int paddleVelocity;
    private int blocksDestroyed;
    private Life lives;
    private boolean gameFinished;
    private double circleToBoxLength;
    private double[] closestPointToCircle;
    private Insets frameInsets;
    private Random rgen = new Random();

    public Board(Insets frameInsets, BlockingQueue queue) {
        blocksDestroyed = 0;
        lives = new Life();
        this.frameInsets = frameInsets;
        this.queue = queue;
        blockCounter = ROWS * COLUMNS;
        createBlocks();

        leaderboard = Leaderboard.getInstance();
        ball = Ball.getInstance();
        paddle = Paddle.getInstance();

        startingPaddle = new double[]{Constants.getPaddleXReset(),
                HEIGHT - Constants.getPaddleHeight() - frameInsets.bottom - frameInsets.top
                        - Constants
                        .getPaddleOffSet()};
        startingBall = new double[]{Constants.getBallXReset(),
                startingPaddle[1] - ball.getHeight()};

        paddle.setPaddleCoordinates(startingPaddle.clone());
        ball.setBallCoordinates(startingBall.clone());
        this.ballVelocity = ball.getBallVelocity();
        this.ballCoordinates = ball.getBallCoordinates();
        this.paddleCoordinates = paddle.getPaddleCoordinates();
    }

    /**
     * Gets the score.
     * @return the leaderboard which should be holding the score.
     */
    public Leaderboard getScore() {
        return leaderboard;
    }

    /**
     * Adds score to the leaderboard
     * @param score score to add to leaderboard
     */
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

    public void movePaddle(double paddleVelocity) {
        paddle.setPaddleVelocity(paddleVelocity);
        paddle.move();
    }

//    public void moveBall(double[] ballVelocity) {
//        ball.setBallVelocity(ballVelocity);
//        ball.move();
//    }

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
     * Creates a block set with 5 rows and 10 columns. Each block has a width of 10 and a height of
     * 5. Around this block set is space of 30 pixels on the top, left, and right.
     */
    private void createBlocks() {
        blocks = new Block[ROWS][COLUMNS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                int x = 30 + (BLOCK_WIDTH * (j + 1)) + (BLOCK_SEP * j);
                int y = 30 + (BLOCK_HEIGHT * (i + 1)) + (BLOCK_SEP * i);
                blocks[i][j] = new Block(x, y, i, j, queue);
            }
        }
    }

    /**
     * When ball collides with a block, it destroys it.
     * @param block the block that the ball collided with
     */
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

    public int getLives() {
        return lives.getLives();
    }

    public void endGame() {
        try {
            queue.add(new EndGameMessage(startingBall, startingPaddle));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Not restarting game, but just resetting ball and paddle when a life is lost.
     */
    public void resetGame() {
        ballCoordinates = startingBall.clone();
        paddleCoordinates = startingPaddle.clone();
        ball.setBallCoordinates(ballCoordinates);
        ballVelocity = new double[]{BALL_MAX_VELOCITY - rgen.nextInt(BALL_MAX_VELOCITY * 2),
                BALL_MAX_VELOCITY - rgen.nextInt(BALL_MAX_VELOCITY * 2)};
        ball.setBallVelocity(ballVelocity);
        paddle.setPaddleCoordinates(paddleCoordinates);
    }

    // Moves the ball and will handle collision between ball and paddle and the view.
    public void moveBall() {

        ball.move();
        ballCoordinates = ball.getBallCoordinates();
        ballVelocity = ball.getBallVelocity();

        int boardWidth = BOARD_WIDTH - frameInsets.left - frameInsets.right;
        // Handles collision between ball and left and right side of the view.
        if (ballCoordinates[0] < 0 || ballCoordinates[0] > boardWidth - BALL_WIDTH) {
            ballVelocity[0] *= -1;
        }

        // Handles collision between ball and top of the view.
        if (ballCoordinates[1] < 0) {
            ballVelocity[1] *= -1;
        }

        int boardHeight = BOARD_HEIGHT - frameInsets.top - frameInsets.bottom;
        // Handles collision between ball and bottom of the view.
        // Actually if ball goes below it should end game, but there is no end game implementation
        // as of now.
        if (ballCoordinates[1] >= boardHeight - BALL_HEIGHT) {
            if (lives.isAlive()) {
                lives.subtractLife();
                gameFinished = false;
                // Restart ball and paddle, but this isn't ending game or playing again
                try {
                    queue.add(new ResetMessage(startingBall.clone(), startingPaddle[0], lives.getLives()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (!lives.isAlive() && !gameFinished) {
                gameFinished = true;
                endGame();
            }
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
                    ballAndBlockCollision();
                } else if (ballIntersection) {
                    ballAndBlockCollision();
                    block.destroy();
                    stop = true;
                    blocksDestroyed++;
                }
            }
        }
        ball.setBallVelocity(ballVelocity);
        ball.setBallCoordinates(ballCoordinates);

        if (blocksDestroyed == Constants.getColumns() * Constants.getRows()) {
            gameFinished = true;
            endGame();
        }
    }

    private boolean ballIntersects(Block block) {
        double blockTop = block.getY();
        double blockBottom = block.getY() + block.getBlockHeight();
        double blockLeft = block.getX();
        double blockRight = block.getX() + block.getBlockWidth();

        closestPointToCircle = new double[]{ballCoordinates[0] + Constants.getBallRadius(),
                ballCoordinates[1] + Constants.getBallRadius()};

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
        double blockBottom = paddleCoordinates[1] + Constants.getPaddleHeight();
        double blockLeft = paddleCoordinates[0];
        double blockRight = paddleCoordinates[0] + Constants.getPaddleWidth();

        closestPointToCircle = new double[]{ballCoordinates[0] + Constants.getBallRadius(),
                ballCoordinates[1] + Constants.getBallRadius()};

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

    private void ballAndBlockCollision() {

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
                    BALL_MAX_VELOCITY * 2); // creates random direction on paddle from -4 to 4
            ballVelocity[1] *= -1;
            ballCoordinates[1] = paddleTop - 1;
        } else {
            // If it hits side of paddle then just use ball and block collision, which also means
            // end of game when it hits the bottom of the board.
            ballAndBlockCollision();
        }
    }
}
