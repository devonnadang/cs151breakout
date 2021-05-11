package breakout.view;

import breakout.controller.Message;
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
    public void createBoardView(double[] ballCoordinates, double[] paddleCoordinates, double[] ballVelocity) {
        boardView = new BoardView(queue, getInsets(), ballCoordinates, paddleCoordinates, ballVelocity);
        add(boardView);
        revalidate();
        repaint();
    }

    public void updateBoardView(double paddleCoordinate) {
        // Board needs to call this
        boardView.setPaddleCoordinates(paddleCoordinate);
        repaint();
    }

    public void endGame(double[] ballCoordinates, double[] paddleCoordinates, double [] ballVelocity) {
        JButton endGameButton = new JButton("Play Again");
        endGameButton.addActionListener(actionListener -> {
            remove(boardView);
            createBoardView(ballCoordinates, paddleCoordinates, ballVelocity);
        });
        boardView.add(endGameButton);
        revalidate();
        repaint();
    }
    
    public void updateLeaderboardView(Leaderboard scoreList) {
    	boardView.setScores(scoreList);
    }
}
