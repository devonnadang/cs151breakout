package breakout.view;

import breakout.controller.Breakout;
import breakout.controller.Message;
import breakout.controller.MoveMessage;
import breakout.model.Board;
import breakout.model.Constants;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Color;

import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.BlockingQueue;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SpringLayout;
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

    private Rectangle2D paddle;
    private Ellipse2D ball;
    private Rectangle2D ballHitbox;
    private Ellipse2D previousBall;

    private double[] ballCoordinates;
    private double[] ballVelocity;
    private double[] paddleCoordinates;
    private double[] paddleVelocity;
    private Rectangle2D[][] blocks;
    private boolean[][] isDestroyed;

    private BlockingQueue<Message> queue;
    private boolean gameFinished;
    private Timer timer;
    private Insets frameInsets;
    private double circleToBoxLength;
    private double[] closestPointToCircle;

    private JButton leaderboardButton;

    public BoardView(BlockingQueue<Message> queue, Insets frameInsets) {

        // This code is NOT related to using the message system. It's just to see if JIcon would work or not.
        // PaddleIcon pi = new PaddleIcon();
        // JLabel paddleLabel = new JLabel(pi);
        // add(paddleLabel);
        // SpringLayout springLayout = new SpringLayout();
        // setLayout(springLayout);
        // springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, paddleLabel, 0, SpringLayout.HORIZONTAL_CENTER, this);
        // springLayout.putConstraint(SpringLayout.SOUTH, paddleLabel, paddleLabel.getHeight() - 10, SpringLayout.SOUTH, this);

        // This is the timer of the ball, but it shouldn't affect paddle movement. Every 50 ms, the ball will be moved and repainted.
        // The moveBall() method also checks for collision.
        timer = new Timer(17, e -> {
            moveBall();
            repaint();
        });

        blocks = new Rectangle2D[Constants.getRows()][Constants.getColumns()];
        for (int i = 0; i < Constants.getRows(); i++) {
            for (int j = 0; j < Constants.getColumns(); j++) {
                blocks[i][j] = new Rectangle2D.Double();
            }
        }

        this.queue = queue;
        this.frameInsets = frameInsets;

        ballHitbox = new Rectangle2D.Double();
        paddle = new Rectangle2D.Double();
        ball = new Ellipse2D.Double();

        isDestroyed = new boolean[Constants.getRows()][Constants.getColumns()];
        for (int i = 0; i < Constants.getRows(); i++) {
            for (int j = 0; j < Constants.getColumns(); j++) {
                isDestroyed[i][j] = false;
            }
        }
        
        // Coordinates for the ball: [0] = x coordinate and [1] = y coordinate.
        ballCoordinates = new double[2];

        // How much the ball will move in each direction: [0] = x velocity and [1] = y velocity
        // So, starting off the ball should move 5 pixels in x and y direction making it go Northwest.
        ballVelocity = new double[]{-4, -4};

        // Coordinates for the paddle: [0] = x coordinate and [1] = y coordinate.
        paddleCoordinates = new double[2];

        // Velocity for the paddle
        paddleVelocity = new double[2];

        // Calculating where the ball and paddle should be at the start of the game.
        paddleCoordinates[0] = BOARD_WIDTH / 2 - PADDLE_WIDTH / 2;
        paddleCoordinates[1] =
                BOARD_HEIGHT - PADDLE_HEIGHT - frameInsets.top - frameInsets.bottom - Constants
                        .getPaddleOffSet();
        ballCoordinates[0] = BOARD_WIDTH / 2 - BALL_WIDTH / 2;
        ballCoordinates[1] = paddleCoordinates[1] - BALL_HEIGHT;
        // paddleCoordinates[0] = Constants.getPaddleXReset();
        // paddleCoordinates[1] = Constants.getPaddleYReset();
        // ballCoordinates[0] = Constants.getBallXReset();
        // ballCoordinates[1] = Constants.getBallYReset();

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

//        am.put("pressed.left", new MoveAction(true, false));
//        am.put("pressed.right", new MoveAction(false, true));
//        am.put("released.left", new MoveAction(false, false));
//        am.put("released.right", new MoveAction(false, false));
        am.put("pressed.left", new MoveAction(Constants.getPaddleMoveLeftUnit()));
        am.put("pressed.right", new MoveAction(Constants.getPaddleMoveRightUnit()));
        am.put("released.left", new MoveAction(0));
        am.put("released.right", new MoveAction(0));
        
        // button to open new window to see leaderboard and scores
        leaderboardButton = new JButton("Leaderboard");
        leaderboardButton.setBounds(350, 100, 150, 40); // x y w h
        leaderboardButton.addActionListener(e -> {
        	LeaderboardWindow lw = new LeaderboardWindow();
        });
        
        // can't figure out how to make it align to the left
        this.add(leaderboardButton); //BorderLayout.EAST???
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
        Rectangle2D bounds = ball.getBounds2D();

        // I guess it has to be greater than 5 for each side?
        ballHitbox.setFrame(bounds.getX() - 6, bounds.getY() - 6, bounds.getWidth() + 11,
                bounds.getHeight() + 11);
//        g2d.fill(ballHitbox);

        /*
        // Drawing the blocks
        int x = 20;
        for (int i = 0; i < blocks.length; i++) {
            Rectangle2D block = blocks[i];
            if (isDestroyed[i]) {
                block.setFrame(0,0,0,0);
            } else {
                block.setFrame(x, 20, 50, 10);
            }
            g2d.fill(block);
            x += 60;
        } */

        for (int i = 0; i < Constants.getRows(); i++) {
            // for (int j = 0; j < Constants.getColumns(); j++) {
            //     Rectangle2D block = blocks[i][j];
            //     if (isDestroyed[i][j]) {
            //         block.setFrame(0, 0, 0, 0);
            //     } else {
            //         int x = 30 + (BLOCK_WIDTH * (j + 1)) + (BLOCK_SEP * j);
            //         int y = 30 + (BLOCK_HEIGHT * (i + 1)) + (BLOCK_SEP * i);
            //         block.setFrame(x, y, BLOCK_WIDTH, BLOCK_HEIGHT);
            //     }
            //     g2d.setColor(Color.RED);
            //     g2d.fill(block);
            for(int j = 0; j < Constants.getColumns(); j++) {
                int x = 30+(BLOCK_WIDTH*(j+1)) + (BLOCK_SEP*(j+1));
                int y = 30+(BLOCK_HEIGHT*(i+1)) + (BLOCK_SEP*(i+1));
                if(isDestroyed[i][j] == false)
                {
                	g2d.setColor(Color.RED);
                    g2d.fill(new Rectangle2D.Double(x, y, BLOCK_WIDTH, BLOCK_HEIGHT));
                }
            }
        }

        // Visualizing center of ball to nearest point on block
        // ballIntersects(paddle);
        // Line2D line2D = new Line2D.Double(ball.getCenterX(), ball.getCenterY(), closestPointToCircle[0] + ball.getCenterX(), closestPointToCircle[1] + ball.getCenterY());
        // g2d.draw(line2D);
    }

    // Moves the ball and will handle collision between ball and paddle and the view.
    private void moveBall() {
//        for (int i = 0; i < previousBallCoordinates.length; i++) {
//            previousBallCoordinates[i] = closestPointToCircle[i] + ball.getCenterX();
//        }

        previousBall = new Ellipse2D.Double(ball.getX() - ballVelocity[0] * 3, ball.getY() - ballVelocity[1] * 3, BALL_WIDTH, BALL_HEIGHT);

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
            ballVelocity[0] = 0;
            ballVelocity[1] = 0;
            gameFinished = true;
            timer.stop();
        }

        // Should only call this method if ball and paddle collide. This is a lot smoother than
        // intersects.
//        if (ballCoordinates[0] >= paddleCoordinates[0]
//                && ballCoordinates[0] <= paddleCoordinates[0] + PADDLE_WIDTH
//                && ballCoordinates[1] >= paddleCoordinates[1] - BALL_HEIGHT
//                && ballCoordinates[1] <= paddleCoordinates[1] + PADDLE_HEIGHT) {
//            ballAndPaddleCollision();
//        }

        // The intersects method doesn't work as great :( There's like this slight lag before it
        // changes direction. It might be because I'm being reactive instead of proactive...
        // The ball isn't moved yet, but the ballCoordinates are moved. Okay yeah I think it's better
        // now?
//        if (ball.intersects(paddle)) {
        if (ballIntersects(paddle)) {
            ballAndPaddleCollision();
        }

        boolean stop = false;
        // If ball and block collide, call this method.
//        for (int i = 0; i < Constants.getRows(); i++) {
//            for (int j = 0; j < Constants.getColumns(); j++) {
//                if (ballHitbox.intersects(blocks[i][j])) {
//                    ballVelocity[1] *= -1;
//                    isDestroyed[i][j] = true;
//                    break;
//                }
//            }
//        }

        // If ball and block collide, call this method. PAUL
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                Rectangle2D block = blocks[i][j];
                // if (ballIntersects(block)) {
               if (ball.intersects(block)) {
                    System.out.println("Collision" + i + " " + j + " !");
                    ballAndBlockCollision(block);
                    isDestroyed[i][j] = true;
                    stop = true;
                    break;
                }
            }
            if (stop) {
                break;
            }
        }

            // RASHMI
//        for (int i = 0; i < Constants.getRows(); i++) {
//            for (int j = 0; j < Constants.getColumns(); j++) {
//            	int x = 30+(BLOCK_WIDTH*(j+1)) + (BLOCK_SEP*(j+1));
//                int y = 30+(BLOCK_HEIGHT*(i+1)) + (BLOCK_SEP*(i+1));
//            	Rectangle blockHitbox = new Rectangle(x, y, BLOCK_WIDTH, BLOCK_HEIGHT);
//            	Rectangle ballHitbox2 =  new Rectangle(ballCoordinates[0], ballCoordinates[1], BALL_WIDTH, BALL_HEIGHT);

//            	if(ballHitbox2.intersects(blockHitbox))
//            	{
//            		//We need the ball to change directions after it has destroyed a block
//            		Point topOfBall = new Point(ballCoordinates[0], ballCoordinates[1] - 1);
//            		Point bottomOfBall = new Point(ballCoordinates[0], ballCoordinates[1] + BALL_HEIGHT + 1);
//            		Point leftOfBall = new Point(ballCoordinates[0] - 1, ballCoordinates[1]);
//            		Point rightOfBall = new Point(ballCoordinates[0] + BALL_WIDTH + 1, ballCoordinates[1]);

//            		if(isDestroyed[i][j] == false)
//                    {
//            			if(blockHitbox.contains(topOfBall)) //top of the ball is in contact with the block
//            			{
//            				ballVelocity[1] *= -1; //if ball hits block's bottom side, it bounces down
//            			}
//            			else if(blockHitbox.contains(bottomOfBall)) //bottom of the ball is in contact with the block
//            			{
//            				ballVelocity[1] *= -1; //if ball hits block's top side, it bounces up
//            			}

//            			if(blockHitbox.contains(leftOfBall)) //left of the ball is in contact with the block
//            			{
//            				ballVelocity[0] *= -1; //if ball hits block's right side, it bounces right
//            			}
//            			else if(blockHitbox.contains(rightOfBall)) //right of the ball is in contact with the block
//            			{
//            				ballVelocity[0] *= -1; //if ball hits block's left side, it bounces left
//            			}

//            			isDestroyed[i][j] = true;
//                    }
//            	}


// //                if (ballHitbox.intersects(blocks[i][j])) {
// //                    System.out.println("Intersection!");
// //                }
// //            	  break;
// 
        //    }
        // }
    }

    private boolean ballIntersects(Rectangle2D block) {
        double blockTop = block.getY();
        double blockBottom = block.getY() + block.getHeight();
        double blockLeft = block.getX();
        double blockRight = block.getX() + block.getWidth();

        closestPointToCircle = new double[]{ball.getCenterX(), ball.getCenterY()};
//        if (closestPointToCircle[0] < blockLeft) {
//            closestPointToCircle[0] = blockLeft;
//        } else if  (closestPointToCircle[0] > blockRight) {
//            closestPointToCircle[1] = blockRight;
//        }
//
//        if (closestPointToCircle[1] < blockBottom) {
//            closestPointToCircle[1] = blockBottom;
//        } else if (closestPointToCircle[1] > blockTop) {
//            closestPointToCircle[1] = blockTop;
//        }

        // The same as the if/else statements, but shorter.
        closestPointToCircle[0] = Math.max(blockLeft, Math.min(blockRight, closestPointToCircle[0]));
        closestPointToCircle[1] = Math.max(blockTop, Math.min(blockBottom, closestPointToCircle[1]));

        closestPointToCircle[0] -= ball.getCenterX();
        closestPointToCircle[1] -= ball.getCenterY();

        circleToBoxLength = Math.hypot(closestPointToCircle[0], closestPointToCircle[1]);

//        return Math.pow(closestPointToCircle[0] - ball.getCenterX(), 2) + Math.
//        pow(closestPointToCircle[1] - ball.getCenterY(),2) <= Math.pow(Constants.getBallRadius(),2);
        // If the length of the line from the center of the circle to the point on the box closest
        // to the ball is less than or equal to the ball radius then there is collision.
        return circleToBoxLength <= Constants.getBallRadius();
    }

    private void ballAndBlockCollision(Rectangle2D block) {
        System.out.println(circleToBoxLength);
        // First try
        // Check if top of ball is hitting block or bottom of ball is hitting block or sides as well
//        double blockTop = block.getY() - ball.getHeight();
//        double blockBottom = block.getY() + block.getHeight();
//        double blockLeft = block.getX() - ball.getWidth();
//        double blockRight = block.getX() + block.getWidth();
//
//        if (ballCoordinates[1] >= blockTop) {
//            ballCoordinates[1] = blockTop - 1;
//            ballVelocity[1] *= -1;
//        } else if (ballCoordinates[1] <= blockBottom) {
//            ballCoordinates[1] = blockBottom + 1;
//            ballVelocity[1] *= -1;
//        } else if (ballCoordinates[0] >= blockLeft) {
//            ballCoordinates[0] = blockLeft - 1;
//            ballVelocity[0] *= -1;
//        } else if (ballCoordinates[0] <= blockRight) {
//            ballCoordinates[0] = blockRight + 1;
//            ballVelocity[0] *= -1;
//        }

        // Second try
//        int ballLeft = (int) ball.getBounds2D().getMinX();
//        int ballHeight = (int) ball.getBounds2D().getHeight();
//        int ballWidth = (int) ball.getBounds2D().getWidth();
//        int ballTop = (int) ball.getBounds2D().getMinY();
//
//        Point pointRight = new Point(ballLeft + ballWidth, (int) ball.getCenterY());
//        Point pointLeft = new Point(ballLeft, (int) ball.getCenterY());
//        Point pointTop = new Point(ballLeft, ballTop);
//        Point pointBottom = new Point(ballLeft, ballTop + ballHeight);
//
//        if (block.getBounds().contains(pointTop)) {
//            ballCoordinates[1] = block.getY() + block.getHeight() + 1;
//            ballVelocity[1] *= -1;
//        } else if (block.getBounds().contains(pointBottom)) {
//            ballCoordinates[1] = block.getY() - ballHeight - 1;
//            ballVelocity[1] *= -1;
//        } else if (block.getBounds().contains(pointLeft)) {
//            ballCoordinates[0] = block.getX() + block.getWidth() + 1;
//            ballVelocity[0] *= -1;
//        } else if (block.getBounds().contains(pointRight)) {
//            ballCoordinates[0] = block.getX() - 1;
//            ballVelocity[0] *= -1;
//        }

        double blockTop = block.getY();
        double blockBottom = block.getY() + block.getHeight();
        double blockLeft = block.getX();
        double blockRight = block.getX() + block.getWidth();

        // Third try
        double overlap = Constants.getBallRadius() - circleToBoxLength;
        double collisionResolution1 = closestPointToCircle[0] / circleToBoxLength * overlap;
        double collisionResolution2 = closestPointToCircle[1] / circleToBoxLength * overlap;
        ballCoordinates[0] -= collisionResolution1;
        ballCoordinates[1] -= collisionResolution2;

        System.out.println(collisionResolution1);
        System.out.println(collisionResolution2);
        System.out.println();

        if (collisionResolution1 == 0) {
            ballVelocity[1] *= -1;
        } else if (collisionResolution2 == 0) {
            ballVelocity[0] *= -1;
        } else if (previousBall.getCenterX() >= blockLeft && previousBall.getCenterX() <= blockRight) {
            ballVelocity[1] *= -1;
        } else if (previousBall.getCenterY() >= blockTop && previousBall.getCenterY() <= blockBottom) {
            ballVelocity[0] *= -1;
        }
    }

    /**
     * This method is for handling any collisions that happen between ball and paddle. This method
     * shouldn't affect any other part of the program.
     */
    private void ballAndPaddleCollision() {
        if (!gameFinished && ballVelocity[0] == 0) {
            ballVelocity[0] = -5;
        }

        double paddleLeft = paddleCoordinates[0];
        double paddleMiddleLeft = paddleLeft + PADDLE_WIDTH / 2 - 5;
        double paddleMiddleRight = paddleLeft + PADDLE_WIDTH / 2 + 5;
        double paddleRight = paddleCoordinates[0] + PADDLE_WIDTH;
        // This is the top of the paddle relative to the ball's coordinates. The actual top
        // of paddle is just paddleCoordinates[1].
        double paddleTop = paddleCoordinates[1] - BALL_HEIGHT;
        double paddleBottom = paddleCoordinates[1] + PADDLE_HEIGHT;

        // Hitting on left side makes ball go left. Hitting on middle (Giving it about 10 pixels of
        // space) makes ball go straight up. Hitting on right side makes ball go right.
        if (ballCoordinates[0] >= paddleCoordinates[0] && ballCoordinates[0] < paddleMiddleLeft
                && ballCoordinates[1] >= paddleTop && ballCoordinates[1] <= paddleBottom) {
            if (ballVelocity[0] > 0) {
                ballVelocity[0] *= -1;
            }
            ballVelocity[1] *= -1;
        } else if (ballCoordinates[0] >= paddleMiddleLeft && ballCoordinates[0] <= paddleMiddleRight
                && ballCoordinates[1] >= paddleTop && ballCoordinates[1] <= paddleBottom) {
            ballVelocity[0] = 0;
            ballVelocity[1] *= -1;
        } else if (ballCoordinates[0] > paddleMiddleRight && ballCoordinates[0] <= paddleRight
                && ballCoordinates[1] >= paddleTop && ballCoordinates[1] <= paddleBottom) {
            if (ballVelocity[0] < 0) {
                ballVelocity[0] *= -1;
            }
            ballVelocity[1] *= -1;
        }

        ballCoordinates[1] = paddleTop - 1;
    }

    /**
     * Sets the x and y coordinates of the ball.
     */
    public void setBallCoordinates(double[] ballCoordinates) {
        this.ballCoordinates = ballCoordinates;
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
    }

    /**
     * So basically, whenever a key is pressed or released it will call MoveAction's overrided
     * actionPerformed method
     */
    private class MoveAction extends AbstractAction {

        private int direction;

        public MoveAction(int direction) {
            // This line is for moving the paddle WITH the message system. Uncomment if you want to use message system.
            this.direction = direction;
            // This line is for moving the paddle WITHOUT the message system. Uncomment if you want to not use message system.
            //    this.direction = direction;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!timer.isRunning()) {
                timer.start();
            }

            // This code doesn't really work in moving the paddle, but uses the message system.
            // Uncomment this code block and Line 163 to use the message system and comment the other block
            // (Lines 186 - 191) below this one and comment Line 164.
            // This println below is to see what happens to the direction variable when this method is called.
           try {
               queue.put(new MoveMessage(direction + paddleCoordinates[0]));
               //queue.put(new MoveMessage(direction));
           } catch (InterruptedException exception) {
               exception.printStackTrace();
           }

            // This code works in moving the paddle, but this doesn't use the message
            // system and doesn't interact with Breakout, the controller.
            // If you want to see how the program works without the message system, uncomment this code block and Line 164.
            // If you want to see how the program work with the message system, comment this code block and uncomment the
            // code block above this
            // paddleCoordinates[0] += direction;
            // if (paddleCoordinates[0] < 0) {
            //     paddleCoordinates[0] = 0;
            // } else if (paddleCoordinates[0] >= getWidth() - PADDLE_WIDTH) {
            //     paddleCoordinates[0] = getWidth() - PADDLE_WIDTH;
            // }
        }
    }

//    /**
//     * So basically, whenever a key is pressed or released it will call MoveAction's overrided
//     * actionPerformed method
//     */
//    private class MoveAction extends AbstractAction {
//        private int speed;
//        private boolean leftPressed;
//        private boolean rightPressed;
//
//        public MoveAction(boolean leftPressed, boolean rightPressed) {
//            this.leftPressed = leftPressed;
//            this.rightPressed = rightPressed;
//        }
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            if (!timer.isRunning()) {
//                timer.start();
//            }
//
//            if (!leftPressed && !rightPressed) {
//                speed *= 0.8;
//            } else if (leftPressed && !rightPressed) {
//                speed--;
//            } else if (rightPressed && !leftPressed){
//                speed++;
//            }
//
//            if (speed >= -0.75 && speed < 0 || speed <= 0.75 && speed > 0) {
//                speed = 0;
//            } else if (speed < -7) {
//                speed = -7;
//            } else if (speed > 7) {
//                speed = 7;
//            }
//
//            try {
//                queue.put(new MoveMessage(speed + paddleCoordinates[0]));
//            } catch (InterruptedException exception) {
//                exception.printStackTrace();
//            }
//        }
//    }
}
