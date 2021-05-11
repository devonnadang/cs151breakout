package breakout.controller;

import java.util.concurrent.BlockingQueue;

import breakout.model.Board;
import breakout.model.Life;
import breakout.view.View;

/**
 * The main system/controller of this game. Helps control interaction between the views and models.
 */
public class Breakout {

    private Board board;
    private View view;
    private Life lives;
    private BlockingQueue<Message> queue;

    /**
     * Initializes Breakout and the main JFrame that will display everything.
     */
    public Breakout(View view, BlockingQueue<Message> queue) {
        this.view = view;
        this.queue = queue;
    }

    /**
     * Starts Breakout. Initializes Board and any other objects that need to be initialized.
     */
    public void startGame() {
        lives = new Life();
        board = new Board(view.getInsets());
        view.createBoardView(board.getBall().getBallCoordinates(), board.getPaddle().getPaddleCoordinates(), board.getBall().getBallVelocity());

        // While for the message system. I did not implement valves yet.
        while (view.isDisplayable()) {
            // Take from queue
            Message message = null;
            try {
                message = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Figure out what message it is and do correct action
            if (message.getClass() == MovePaddleMessage.class) {
                MovePaddleMessage movePaddleMessage = (MovePaddleMessage) message;
                board.getPaddle().setPaddleVelocity(movePaddleMessage.getNewVelocity());
                board.getPaddle().move();
 //               System.out.println("From Paddle x: "+ board.getPaddle().getX());
                // The moveMessage will contain the new x coordinate for the paddle and give it to the
                // main view for it to update the BoardView and for the BoardView to update the paddle.
                view.updateBoardView(board.getPaddle().getPaddleCoordinates()[0]);
            }
            // when save score button is pressed
            // add a new score to board
            else if (message.getClass() == SaveScoreMessage.class) {
            	SaveScoreMessage saveUsernameMessage = (SaveScoreMessage) message;
            	board.addScore(saveUsernameMessage.getScore());
            }
            
            // when leaderboard button is pressed
            // update leaderboard with all scores
            else if(message.getClass() == LeaderboardMessage.class)
            {
            	LeaderboardMessage leaderboardMessage = (LeaderboardMessage) message;
            	leaderboardMessage.addScores(board.getScore());
            	view.updateLeaderboardView(leaderboardMessage.getScores());
            }
            // When game ends
            else if (message.getClass() == EndGameMessage.class) {
                EndGameMessage endGameMessage = (EndGameMessage) message;
                view.endGame(board.getBall().getBallCoordinates(), board.getPaddle().getPaddleCoordinates(), board.getBall().getBallVelocity());
            }
        }

        // Calling endgame after while loop ends
        endGame();
    }

    /**
     * Ends Breakout when there are no lives left or when there are no blocks
     * left.
     */
    public void endGame() {
        // Go to leaderboard page
    }
}
