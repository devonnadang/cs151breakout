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
    private BlockingQueue<Message> queue;

    /**
     * Initializes Breakout and the main JFrame that will display everything.
     */
    public Breakout(View view, Board board, BlockingQueue<Message> queue) {
        this.board = board;
        this.view = view;
        this.queue = queue;
    }

    /**
     * Starts Breakout. Initializes Life, Board, and any other objects that need to be initialized.
     * Calls on View's createBoardView() to create the graphics for the game.
     */
    public void startGame() {
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

    private class DoMoveMessageValve implements Valve {
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != MoveMessage.class) {
                return ValveResponse.MISS;
            }
            //otherwise it means that it is a MoveMessage message
            //actions in Model
            //actions in View
                //button updateStudentName was clicked
            MoveMessage moveMessage = (MoveMessage) message; 
            board.getPaddle().move(moveMessage.getNewVelocity());
            view.updateBoardView(moveMessage.getNewVelocity());
            return ValveResponse.EXECUTED;
        }
    }

    private class DoSaveScoreMessageValve implements Valve {
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != SaveScoreMessage.class) {
                return ValveResponse.MISS;
            }

            SaveScoreMessage saveUsernameMessage = (SaveScoreMessage) message;
            board.addScore(saveUsernameMessage.getScore());
            return ValveResponse.EXECUTED;
        }
    }

    private class DoLeaderboardMessageValve implements Valve {
        @Override 
        public ValveResponse execute (Message message) {
            if (message.getClass() != LeaderboardMessage.class) {
                return ValveResponse.MISS;
            }

            LeaderboardMessage leaderboardMessage = (LeaderboardMessage) message;
            leaderboardMessage.addScores(board.getScore());
            view.updateLeaderboardView(leaderboardMessage.getScores());
            return ValveResponse.EXECUTED;
        }
    }

    private interface Valve {
        /**
         * Performs certain action in response to message
         */
        public ValveResponse execute(Message message);
    }


    /**
     * Initializes and starts Breakout.
     */
    public static void main(String[] args) {
        BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
        View view = new View(queue);
        Board board = new Board();
        Breakout breakout = new Breakout(view, board, queue);
        breakout.startGame();
    }
}
