package breakout.view;

import breakout.controller.LeaderboardMessage;
import breakout.controller.EndGameMessage;
import breakout.controller.Message;
import breakout.controller.MoveBallMessage;
import breakout.controller.MovePaddleMessage;
import breakout.model.Constants;
import breakout.model.Leaderboard;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class BoardView extends JPanel {

    public static final int BALL_WIDTH = Constants.getBallRadius() * 2;
    public static final int BALL_HEIGHT = Constants.getBallRadius() * 2;
    public static final int PADDLE_WIDTH = Constants.getPaddleWidth();
    public static final int PADDLE_HEIGHT = Constants.getPaddleHeight();
    public static final int BOARD_WIDTH = Constants.getPanelWidth();
    public static final int BOARD_HEIGHT = Constants.getPanelHeight();
    public static final int BLOCK_WIDTH = Constants.getBlockWidth();
    public static final int BLOCK_HEIGHT = Constants.getBlockHeight();
    public static final int BLOCK_SEP = Constants.getBlockSep();
    public static final int BALL_MAX_VELOCITY = Constants.getBallMaxVelocity();
    public static final int BALL_MIN_VELOCITY = Constants.getBallMinVelocity();

    private Rectangle2D paddle;
    private Ellipse2D ball;

    private double[] ballCoordinates;
    private double[] ballVelocity;
    private double[] paddleCoordinates;
    private Rectangle2D[][] blocks;
    private boolean[][] isDestroyed;
    private int livesCounter;
    private Leaderboard scoreList;
    private int paddleVelocity;
    private JLabel livesLeftDisplay;
    private JLabel scoreDisplay;

    private BlockingQueue<Message> queue;
    private boolean gameFinished;
    private Timer timer;
    private double circleToBoxLength;
    private double[] closestPointToCircle;
    private Insets frameInsets;

    private JButton saveScoreButton;
    private JButton leaderboardButton;
    private Random rgen = new Random();
    private JLabel gameOver;
    private int finalScore = 0;

    public BoardView(BlockingQueue<Message> queue, Insets frameInsets, double[] ballCoordinates, double[] paddleCoordinates) {
        // This is the timer of the ball, but it shouldn't affect paddle movement. Every 50 ms, the ball will be moved and repainted.
        // The moveBall() method also checks for collision.
        timer = new Timer(17, e -> {
//            moveBall();
            try {
                queue.add(new MoveBallMessage());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        blocks = new Rectangle2D[Constants.getRows()][Constants.getColumns()];
        for (int i = 0; i < Constants.getRows(); i++) {
            for (int j = 0; j < Constants.getColumns(); j++) {
                blocks[i][j] = new Rectangle2D.Double();
            }
        }

        this.queue = queue;
        this.frameInsets = frameInsets;
        this.ballCoordinates = ballCoordinates;
        this.paddleCoordinates = paddleCoordinates;
        this.ballVelocity = ballVelocity;

        paddle = new Rectangle2D.Double();
        ball = new Ellipse2D.Double();

        isDestroyed = new boolean[Constants.getRows()][Constants.getColumns()];
        for (int i = 0; i < Constants.getRows(); i++) {
            for (int j = 0; j < Constants.getColumns(); j++) {
                isDestroyed[i][j] = false;
            }
        }

        // This maps the left and right arrow keys to different actions.
        // I used key bindings instead of ActionListener because sometimes the panel becomes out of focus and the inputs do nothing,
        // but key bindings solve that issue of out of focus windows.
        // You can find more about Key Bindings here: https://docs.oracle.com/javase/tutorial/uiswing/misc/keybinding.html
        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "pressed.left");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "pressed.right");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "released.left");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "released.right");

        // Basically pressing keys will make paddle move and releasing keys will make paddle stop.
        // Pressing left should make the paddle move -7 which means the paddle should move left.
        // Pressing right should make the paddle move 7 which means the paddle should move right.
        // Whenever one of these keys are pressed or released they will call the actionPerformed method
        // in MoveAction.
        am.put("pressed.left", new MoveAction(Constants.getPaddleMoveLeftUnit()));
        am.put("pressed.right", new MoveAction(Constants.getPaddleMoveRightUnit()));
        am.put("released.left", new MoveAction(0));
        am.put("released.right", new MoveAction(0));

        // opens a new window to save score; enter username
        saveScoreButton = new JButton("Save Score");
        saveScoreButton.addActionListener(e -> {
            SaveScoreView ssw = new SaveScoreView(queue, finalScore); //add actual score later
        });
                
        // button to open new window to see leaderboard and scores
        scoreList = Leaderboard.getInstance();
        
        leaderboardButton = new JButton("Leaderboard");
        leaderboardButton.addActionListener(e -> {
        	try {
            	LeaderboardMessage lw = new LeaderboardMessage();
            	queue.put(lw);
            	LeaderboardView leaderboard = new LeaderboardView(scoreList);
			} catch (InterruptedException e1) {
				// do nothing
			}
        });
        
        
        gameOver = new JLabel(" ");
        this.add(gameOver);
        
        livesLeftDisplay = new JLabel (" ");
        this.add(livesLeftDisplay);

        scoreDisplay = new JLabel (" ");
        this.add(scoreDisplay);
        scoreDisplay.setText("Score: " + finalScore);
    }

    public void endGame() {
        this.add(saveScoreButton);
        this.add(leaderboardButton);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        super.paintComponent(g);
        // Draws the paddle
        paddle.setFrame(paddleCoordinates[0], paddleCoordinates[1], PADDLE_WIDTH, PADDLE_HEIGHT);
        g2d.fill(paddle);

        // Draws the ball above the paddle
        ball.setFrame(ballCoordinates[0], ballCoordinates[1], BALL_WIDTH, BALL_HEIGHT);
        g2d.fill(ball);

        int destroyedBlocks = 0;
        if (!gameFinished) {
            for (int i = 0; i < Constants.getRows(); i++) {
                for (int j = 0; j < Constants.getColumns(); j++) {
                    Rectangle2D block = blocks[i][j];
                    if (isDestroyed[i][j]) {
                        block.setFrame(0, 0, 0, 0);
                        destroyedBlocks++;
                        scoreDisplay.setText("Score: " + finalScore);
                    } else {
                        int x = 30 + (BLOCK_WIDTH * (j + 1)) + (BLOCK_SEP * j);
                        int y = 30 + (BLOCK_HEIGHT * (i + 1)) + (BLOCK_SEP * i);
                        block.setFrame(x, y, BLOCK_WIDTH, BLOCK_HEIGHT);
                        if (i == 0) {
                            g2d.setColor(Color.RED);
                        } else if (i == 1) {
                            g2d.setColor(Color.ORANGE);
                        } else if (i == 2) {
                            g2d.setColor(Color.YELLOW);
                        } else if (i == 3) {
                            g2d.setColor(Color.GREEN);
                        } else if (i == 4) {
                            g2d.setColor(Color.BLUE);
                        }
                    }
                    g2d.fill(block);
                }
            }
        }
        if (destroyedBlocks == Constants.getRows() * Constants.getColumns()) {
            gameFinished = true;
            endGame();
        }
    }

    // Moves the ball and will handle collision between ball and paddle and the view.
    private void moveBall() {
    	livesLeftDisplay.setText("Lives Left: " + livesCounter);

        // These two statements will make sure max velocity is 5 and min velocity is -5.
        ballVelocity[0] = Math.max(-BALL_MAX_VELOCITY, Math.min(BALL_MAX_VELOCITY, ballVelocity[0]));
        ballVelocity[1] = Math.max(-BALL_MAX_VELOCITY, Math.min(BALL_MAX_VELOCITY, ballVelocity[1]));

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
        ball.setFrame(ballCoordinates[0], ballCoordinates[1], BALL_WIDTH, BALL_HEIGHT);

        // Handles collision between ball and left and right side of the view.
        if (ballCoordinates[0] < 0 || ballCoordinates[0] > getWidth() - BALL_WIDTH) {
            ballVelocity[0] *= -1;
        }

        // Handles collision between ball and top of the view.
        if (ballCoordinates[1] < 0) {
            ballVelocity[1] *= -1;
        }

        // Handles collision between ball and bottom of the view.
        // Actually if ball goes below it should end game, but there is no end game implementation
        // as of now.
        if (ballCoordinates[1] >= getHeight() - BALL_HEIGHT) {
            if (livesCounter != 3) {
                livesCounter++;
                livesLeftDisplay.setText("Lives Left: " + (3-livesCounter));
                gameFinished = false;
                repaintBoard();
            } else if (livesCounter == 3) {
                gameFinished = true;
                gameOver.setText("GameOver!");
                endGame();
            }
            timer.stop();
        }

        // If ball intersects paddle then resolve collision.
        if (ballIntersects(paddle)) {
            ballAndPaddleCollision();
        }

        boolean stop = false;
        // If ball and block collide, call this method. PAUL
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                Rectangle2D block = blocks[i][j];
                boolean ballIntersection = ballIntersects(block);
                if (ballIntersection && stop) {
                    ballAndBlockCollision(block);
                } else if (ballIntersection) {
                    ballAndBlockCollision(block);
                    isDestroyed[i][j] = true;
                    finalScore += 5;
                    stop = true;
                }
            }
        }
    }

    private boolean ballIntersects(Rectangle2D block) {
        double blockTop = block.getY();
        double blockBottom = block.getY() + block.getHeight();
        double blockLeft = block.getX();
        double blockRight = block.getX() + block.getWidth();

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

    private void ballAndBlockCollision(Rectangle2D block) {

        // Third try
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
        } else {
            // If it hits side of paddle then just use ball and block collision, which also means
            // end of game when it hits the bottom of the board.
            ballAndBlockCollision(paddle);
        }
    }

    public int getFinalScore() {
        return finalScore;
    }

    /**
     * Sets the x coordinate of the paddle, since it only moves left and right.
     *
     * @param paddleCoordinates
     */
    public void setPaddleCoordinates(double paddleCoordinates) {
        if (paddleCoordinates < 0) {
            paddleCoordinates = 0;
        } else if (paddleCoordinates >= getWidth() - PADDLE_WIDTH) {
            paddleCoordinates = getWidth() - PADDLE_WIDTH;
        }
        this.paddleCoordinates[0] = paddleCoordinates;
 //       System.out.println("Front BoardView: " + this.paddleCoordinates[0]);
    }

    public void setPaddleVelocity(int paddleVelocity) {
        this.paddleVelocity = paddleVelocity;
    }

    public void setBallCoordinates(double[] ballCoordinates) {
        this.ballCoordinates = ballCoordinates;
    }

    public void setBlockDestroyed(int row, int column) {
        isDestroyed[row][column] = true;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    public void setLivesCounter(int livesCounter) {
        livesLeftDisplay.setText("Lives Left: " + livesCounter);
    }

    public void repaintBoard() {
        // Coordinates for the ball: [0] = x coordinate and [1] = y coordinate.
        ballCoordinates = new double[2];

        // How much the ball will move in each direction: [0] = x velocity and [1] = y velocity
        // So, starting off the ball should move 5 pixels in x and y direction making it go Northwest.
        ballVelocity = new double[]{BALL_MAX_VELOCITY - rgen.nextInt(BALL_MAX_VELOCITY * 2),
                BALL_MAX_VELOCITY - rgen.nextInt(BALL_MAX_VELOCITY * 2)};

        // Coordinates for the paddle: [0] = x coordinate and [1] = y coordinate.
        paddleCoordinates = new double[2];

        // Calculating where the ball and paddle should be at the start of the game.
        paddleCoordinates[0] = Constants.getPaddleXReset();
        paddleCoordinates[1] =
                BOARD_HEIGHT - PADDLE_HEIGHT - frameInsets.top - frameInsets.bottom - Constants
                        .getPaddleOffSet();
        ballCoordinates[0] = Constants.getBallXReset();
        ballCoordinates[1] = paddleCoordinates[1] - BALL_HEIGHT;
    }

    public void stopTimer() {
        timer.stop();
    }

    /**
     * So basically, whenever a key is pressed or released it will call MoveAction's overrided
     * actionPerformed method
     */
    private class MoveAction extends AbstractAction {

        private int direction;

        public MoveAction(int direction) {
            this.direction = direction;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!timer.isRunning() && !gameFinished) {
                timer.start();
            }

            try {
                queue.put(new MovePaddleMessage(direction));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
    
    public void setScores(Leaderboard scoreList) {
		this.scoreList = scoreList;
	}
}
