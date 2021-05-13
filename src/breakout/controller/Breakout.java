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
        board = new Board(view.getInsets(), queue);
        view.createBoardView(board.getBall().getBallCoordinates(), board.getPaddle().getPaddleCoordinates(), board.getLives());

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
                board.movePaddle(movePaddleMessage.getNewVelocity());
 //               System.out.println("From Paddle x: "+ board.getPaddle().getX());
                // The moveMessage will contain the new x coordinate for the paddle and give it to the
                // main view for it to update the BoardView and for the BoardView to update the paddle.
                view.updateBoardView(board.getPaddle().getPaddleCoordinates()[0]);
            }
            // Move Ball
            else if (message.getClass() == MoveBallMessage.class) {
                MoveBallMessage moveBallMessage = (MoveBallMessage) message;
                board.moveBall();
                view.updateBoardView(board.getBall().getBallCoordinates());
            }
            // Destroy Block
            else if (message.getClass() == BlockDestroyedMessage.class) {
                BlockDestroyedMessage blockDestroyedMessage = (BlockDestroyedMessage) message;
                view.updateBoardView(blockDestroyedMessage.getRow(), blockDestroyedMessage.getColumn());
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
            // When game resets
            else if (message.getClass() == ResetMessage.class) {
                ResetMessage resetMessage = (ResetMessage) message;
                board.resetGame();
                view.resetGame(resetMessage.getStartingBall(), resetMessage.getStartingPaddle(), resetMessage.getLives());
            }
            // When game ends
            else if (message.getClass() == EndGameMessage.class) {
                EndGameMessage endGameMessage = (EndGameMessage) message;
                view.endGame(endGameMessage.getStartingBall(), endGameMessage.getStartingPaddle());
            }
            // When player clicks "Play Again" button
            else if (message.getClass() == PlayAgainMessage.class) {
                PlayAgainMessage playAgainMessage = (PlayAgainMessage) message;
                board = new Board(view.getInsets(), queue);
                view.playAgain(playAgainMessage.getBallCoordinates(), playAgainMessage.getPaddleCoordinates(), board.getLives());
            }
        }
    }
}
