package breakout.controller;

import java.util.LinkedList;
import java.util.List;
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
    private List<Valve> valves = new LinkedList<Valve>();

    /**
     * Initializes Breakout and the main JFrame that will display everything.
     */
    public Breakout(View view, Board board, BlockingQueue<Message> queue) {
        this.board = board;
        this.view = view;
        this.queue = queue;

        //create one object of each kind of helper (valve)
        valves.add(new DoMoveMessageValve());
        valves.add(new DoSaveScoreMessageValve());
        valves.add(new DoLeaderboardMessageValve());
    }

    /**
     * Starts Breakout. Initializes Life, Board, and any other objects that need to be initialized.
     * Calls on View's createBoardView() to create the graphics for the game.
     */
    public void startGame() {
        view.createBoardView();
        ValveResponse response = ValveResponse.EXECUTED;
        Message message = null;
        while(response != ValveResponse.FINISH) {
            try {
                message = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Look for a Valve that can process a message 
            for (Valve valve: valves) {
                response = valve.execute(message);
                //if sucessfully processed or game over, leave the loop
                if (response != ValveResponse.MISS) {
                    break;
                }
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
