package breakout.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import breakout.model.Board;
import breakout.view.View;

/**
 * The main system/controller of this game. Helps control interaction between the views and models.
 */
public class Breakout {

    private Board board;
    private View view;
    private BlockingQueue<Message> queue;
    private List<Valve> valveList;

    /**
     * Initializes Breakout and the main JFrame that will display everything.
     *
     * @param view  the main view of the game
     * @param queue the queue to add messages to
     */
    public Breakout(View view, BlockingQueue<Message> queue) {
        this.view = view;
        this.queue = queue;
        valveList = new ArrayList<>();

        // Add all Message valve classes to valveList
        valveList.add(new DestroyBlockValve());
        valveList.add(new EndGameValve());
        valveList.add(new MoveBallValve());
        valveList.add(new MovePaddleValve());
        valveList.add(new PlayAgainValve());
        valveList.add(new ResetGameValve());
        valveList.add(new SaveScoreValve());
        valveList.add(new UpdateLeaderboardValve());
    }

    /**
     * Starts Breakout. Initializes Life, Board, and any other objects that need to be initialized.
     * Calls on View's createBoardView() to create the graphics for the game.
     */
    public void startGame() {
        board = new Board(view.getInsets(), queue);
        view.createBoardView(board.getBall().getBallCoordinates(),
                board.getPaddle().getPaddleCoordinates(), board.getLives());
        ValveResponse valveResponse = ValveResponse.EXECUTED;

        while (valveResponse != ValveResponse.FINISH) {
            Message message = null;
            try {
                message = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (Valve valve : valveList) {
                valveResponse = valve.execute(message);
                if (valveResponse != ValveResponse.MISS) {
                    break;
                }
            }
        }
    }

    private class DestroyBlockValve implements Valve {

        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != BlockDestroyedMessage.class) {
                return ValveResponse.MISS;
            }
            BlockDestroyedMessage blockDestroyedMessage = (BlockDestroyedMessage) message;
            view.updateBoardView(blockDestroyedMessage.getRow(), blockDestroyedMessage.getColumn());
            return ValveResponse.EXECUTED;
        }
    }

    private class EndGameValve implements Valve {

        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != EndGameMessage.class) {
                return ValveResponse.MISS;
            }
            EndGameMessage endGameMessage = (EndGameMessage) message;
            view.endGame(endGameMessage.getStartingBall(), endGameMessage.getStartingPaddle(),
                    endGameMessage.getLives());
            return ValveResponse.EXECUTED;
        }
    }

    private class MoveBallValve implements Valve {

        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != MoveBallMessage.class) {
                return ValveResponse.MISS;
            }
            MoveBallMessage moveBallMessage = (MoveBallMessage) message;
            board.moveBall();
            view.updateBoardView(board.getBall().getBallCoordinates());
            return ValveResponse.EXECUTED;
        }
    }

    private class MovePaddleValve implements Valve {

        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != MovePaddleMessage.class) {
                return ValveResponse.MISS;
            }
            MovePaddleMessage movePaddleMessage = (MovePaddleMessage) message;
            board.movePaddle(movePaddleMessage.getNewVelocity());
            // The moveMessage will contain the new x coordinate for the paddle and give it to the
            // main view for it to update the BoardView and for the BoardView to update the paddle.
            view.updateBoardView(board.getPaddle().getPaddleCoordinates()[0]);
            return ValveResponse.EXECUTED;
        }
    }

    private class PlayAgainValve implements Valve {

        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != PlayAgainMessage.class) {
                return ValveResponse.MISS;
            }
            PlayAgainMessage playAgainMessage = (PlayAgainMessage) message;
            board = new Board(view.getInsets(), queue);
            view.playAgain(playAgainMessage.getBallCoordinates(),
                    playAgainMessage.getPaddleCoordinates(), board.getLives());
            return ValveResponse.EXECUTED;
        }
    }

    private class ResetGameValve implements Valve {

        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != ResetMessage.class) {
                return ValveResponse.MISS;
            }
            ResetMessage resetMessage = (ResetMessage) message;
            board.resetGame();
            view.resetGame(resetMessage.getStartingBall(), resetMessage.getStartingPaddle(),
                    resetMessage.getLives());
            return ValveResponse.EXECUTED;
        }
    }

    private class SaveScoreValve implements Valve {

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

    private class UpdateLeaderboardValve implements Valve {

        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != LeaderboardMessage.class) {
                return ValveResponse.MISS;
            }
            LeaderboardMessage leaderboardMessage = (LeaderboardMessage) message;
            leaderboardMessage.addScores(board.getScore());
            view.updateLeaderboardView(leaderboardMessage.getScores());
            return ValveResponse.EXECUTED;
        }
    }
}
