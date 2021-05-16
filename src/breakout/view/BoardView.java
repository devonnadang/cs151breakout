package breakout.view;

import breakout.controller.LeaderboardMessage;
import breakout.controller.Message;
import breakout.controller.MoveBallMessage;
import breakout.controller.MovePaddleMessage;
import breakout.controller.PlayAgainMessage;
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
import java.util.concurrent.BlockingQueue;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

/**
 * Represents a view of the board.
 */
public class BoardView extends JPanel {

    public static final int BALL_WIDTH = Constants.getBallRadius() * 2;
    public static final int BALL_HEIGHT = Constants.getBallRadius() * 2;
    public static final int PADDLE_WIDTH = Constants.getPaddleWidth();
    public static final int PADDLE_HEIGHT = Constants.getPaddleHeight();
    public static final int BLOCK_WIDTH = Constants.getBlockWidth();
    public static final int BLOCK_HEIGHT = Constants.getBlockHeight();
    public static final int BLOCK_SEP = Constants.getBlockSep();

    private Rectangle2D paddle;
    private Ellipse2D ball;

    private double[] ballCoordinates;
    private double[] paddleCoordinates;
    private Rectangle2D[][] blocks;
    private boolean[][] isDestroyed;
    private Leaderboard scoreList;
    private JLabel livesLeftDisplay;
    private JLabel scoreDisplay;

    private BlockingQueue<Message> queue;
    private Timer timer;

    private JButton saveScoreButton;
    private JButton leaderboardButton;
    private JLabel gameOver;
    private int finalScore = 0;

    /**
     * This is the constructor where timer, board, and the starting locations of blocks, ball, and
     * paddle are implemented.
     *
     * @param queue             the BlockingQueue queue
     * @param frameInsets       the board frame insets
     * @param ballCoordinates   input ball coordinates
     * @param paddleCoordinates input paddle coordinates
     */
    public BoardView(BlockingQueue<Message> queue, Insets frameInsets, double[] ballCoordinates,
            double[] paddleCoordinates) {
        // This is the timer of the ball, but it shouldn't affect paddle movement. Every 17 ms, the ball will be moved and repainted.
        // The moveBall() method also checks for collision.
        timer = new Timer(17, e -> {
            try {
                queue.add(new MoveBallMessage());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        // initialize blocks
        blocks = new Rectangle2D[Constants.getRows()][Constants.getColumns()];
        for (int i = 0; i < Constants.getRows(); i++) {
            for (int j = 0; j < Constants.getColumns(); j++) {
                blocks[i][j] = new Rectangle2D.Double();
            }
        }

        this.queue = queue;
        this.ballCoordinates = ballCoordinates;
        this.paddleCoordinates = paddleCoordinates;

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
        // Pressing left should make the paddle move -5 which means the paddle should move left.
        // Pressing right should make the paddle move 5 which means the paddle should move right.
        // Whenever one of these keys are pressed or released they will call the actionPerformed method
        // in MoveAction.
        am.put("pressed.left", new MoveAction(Constants.getPaddleMoveLeftUnit()));
        am.put("pressed.right", new MoveAction(Constants.getPaddleMoveRightUnit()));
        am.put("released.left", new MoveAction(0));
        am.put("released.right", new MoveAction(0));

        // opens a new window to save score; enter username
        saveScoreButton = new JButton("Save Score");
        saveScoreButton.addActionListener(e -> {
            SaveScoreView ssw = new SaveScoreView(queue, finalScore);
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

        livesLeftDisplay = new JLabel(" ");
        this.add(livesLeftDisplay);

        scoreDisplay = new JLabel(" ");
        this.add(scoreDisplay);
        scoreDisplay.setText("Score: " + finalScore);
    }

    /**
     * Once the game ends, two buttons appear: "Save Score" and "Leaderboard"
     */
    public void endGame() {
        add(saveScoreButton);
        add(leaderboardButton);
    }

    /**
     * Redraws the paddle, ball, and blocks according to the current game state.
     *
     * @param g the input Graphics component
     */
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

        for (int i = 0; i < Constants.getRows(); i++) {
            for (int j = 0; j < Constants.getColumns(); j++) {
                Rectangle2D block = blocks[i][j];
                if (isDestroyed[i][j]) {
                    block.setFrame(0, 0, 0, 0);
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

    /**
     * Returns the score that has been calculated as blocks were destroyed throughout the game.
     *
     * @return finalScore the score user has by the end of the game
     */
    public int getFinalScore() {
        return finalScore;
    }

    /**
     * Sets the x coordinate of the paddle, since it only moves left and right. Makes sure the
     * paddle stays within the board.
     *
     * @param paddleCoordinates paddleCoordinates[0] is x position, paddleCoordinates[1] is y
     *                          position
     */
    public void setPaddleCoordinates(double paddleCoordinates) {
        if (paddleCoordinates < 0) {
            paddleCoordinates = 0;
        } else if (paddleCoordinates >= getWidth() - PADDLE_WIDTH) {
            paddleCoordinates = getWidth() - PADDLE_WIDTH;
        }
        this.paddleCoordinates[0] = paddleCoordinates;
    }

    /**
     * Sets the ball coordinates according to input.
     *
     * @param ballCoordinates input ball coordinates
     */
    public void setBallCoordinates(double[] ballCoordinates) {
        this.ballCoordinates = ballCoordinates;
    }

    /**
     * Sets the input block as destroyed.
     *
     * @param row    the row block is found
     * @param column the column block is found
     */
    public void setBlockDestroyed(int row, int column) {
        isDestroyed[row][column] = true;
    }

    /**
     * Sets the final score to input.
     *
     * @param finalScore input final score
     */
    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    /**
     * Sets the lives counter display in the game based on input.
     *
     * @param livesCounter the lives left
     */
    public void setLivesCounter(int livesCounter) {
        livesLeftDisplay.setText("Lives Left: " + livesCounter);
    }

    /**
     * Stops the timer if game is over.
     *
     * @param gameFinished true if game is finished, else false
     */
    public void stopTimer(boolean gameFinished) {
        timer.stop();
        if (gameFinished) {
            timer.removeActionListener(timer.getActionListeners()[0]);
        }
    }

    /**
     * Whenever a key is pressed or released it will call MoveAction's overridden actionPerformed
     * method.
     */
    private class MoveAction extends AbstractAction {

        private int velocity;

        /**
         * This is the constructor which initializes velocity.
         *
         * @param velocity the direction in which paddle will move in
         */
        public MoveAction(int velocity) {
            this.velocity = velocity;
        }

        /**
         * While game is not over, passes the input action event into queue.
         *
         * @param e the Action event
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Starts the timer when  it isn't run. Should only run at the beginning of the game.
            // This allows the player to start the game at their input.
            if (!timer.isRunning()) {
                timer.start();
            }

            // Putting MoveMessage into queue to move the paddle.
            try {
                queue.put(new MovePaddleMessage(velocity));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * Updates the score to display on Leaderboard.
     *
     * @param scoreList the Leaderboard which holds all the scores
     */
    public void setScores(Leaderboard scoreList) {
        this.scoreList = scoreList;
    }
}
