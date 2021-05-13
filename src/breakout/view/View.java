package breakout.view;

import breakout.controller.Message;
import breakout.controller.PlayAgainMessage;
import java.awt.Dimension;

import java.util.concurrent.BlockingQueue;
import javax.swing.JButton;
import javax.swing.JFrame;

import breakout.model.Board;
import breakout.model.Leaderboard;
/**
 * Serves as the main view for Breakout. All other views will be within this one.
 */
public class View extends JFrame{
    private BoardView boardView;
    private BlockingQueue<Message> queue;

    //ViewPanel panel;
    /**
     * Constructs the main view of Breakout.
     */
    public View(BlockingQueue<Message> queue) {
        this.queue = queue;
         this.setPreferredSize(new Dimension(Board.getBoardWidth(),Board.getBoardHeight()));
         setResizable(false);
         setTitle("Breakout");
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         pack();
         setVisible(true);
    }

    /**
     * This method creates the board and repaints the main view to make the board visible.
     */
    public void createBoardView(double[] ballCoordinates, double[] paddleCoordinates, int lives) {
        boardView = new BoardView(queue, getInsets(), ballCoordinates, paddleCoordinates);
        boardView.setLivesCounter(lives);
        add(boardView);
        revalidate();
        repaint();
    }

    public void updateBoardView(double paddleCoordinate) {
        boardView.setPaddleCoordinates(paddleCoordinate);
        revalidate();
        repaint();
    }

    public void updateBoardView(double[] ballCoordinates) {
        boardView.setBallCoordinates(ballCoordinates);
        revalidate();
        repaint();
    }

    public void updateBoardView(int row, int column) {
        boardView.setBlockDestroyed(row, column);
        boardView.setFinalScore(boardView.getFinalScore() + 5);
        repaint();
    }

    public void resetGame(double[] startingBall, double startingPaddle, int lives) {
        boardView.stopTimer();
        boardView.setLivesCounter(lives);
        updateBoardView(startingBall);
        updateBoardView(startingPaddle);
    }

    public void endGame(double[] ballCoordinates, double[] paddleCoordinates) {
        boardView.stopTimer();
        JButton endGameButton = new JButton("Play Again");
        endGameButton.addActionListener(actionListener -> {
            try {
                queue.add(new PlayAgainMessage(ballCoordinates, paddleCoordinates));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        boardView.add(endGameButton);
        boardView.endGame();
        revalidate();
        repaint();
    }

    public void playAgain(double[] ballCoordinates, double[] paddleCoordinates, int lives) {
        remove(boardView);
        createBoardView(ballCoordinates, paddleCoordinates, lives);
    }

    public void updateLeaderboardView(Leaderboard scoreList) {
    	boardView.setScores(scoreList);
    }
}
