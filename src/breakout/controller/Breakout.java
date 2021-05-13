package breakout.controller;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.Timer;

import breakout.model.Board;
import breakout.model.Life;
import breakout.model.Paddle;
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
     * Starts Breakout. Initializes Life, Board, and any other objects that need to be initialized.
     * Calls on View's createBoardView() to create the graphics for the game.
     */
    public void startGame() {
        lives = new Life();
        // Also should probably add lives into Board constructor?
        board = new Board();
        view.createBoardView();

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
            if (message.getClass() == MoveMessage.class) {
                MoveMessage moveMessage = (MoveMessage) message;
                board.getPaddle().move(moveMessage.getNewVelocity());
                // The moveMessage will contain the new x coordinate for the paddle and give it to the
                // main view for it to update the BoardView and for the BoardView to update the paddle.
                view.updateBoardView(moveMessage.getNewVelocity());
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
                view.endGame();
            }
        }
    }

    /**
     * Initializes and starts Breakout.
     */
    public static void main(String[] args) {
        BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
        View view = new View(queue);
        Breakout breakout = new Breakout(view, queue);
        breakout.startGame();
    }
}
