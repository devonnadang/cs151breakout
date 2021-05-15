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
public class View extends JFrame {

    private BoardView boardView;
    private BlockingQueue<Message> queue;

    /**
     * Constructs the main view of Breakout.
     */
    public View(BlockingQueue<Message> queue) {
        this.queue = queue;
        this.setPreferredSize(new Dimension(Board.getBoardWidth(), Board.getBoardHeight()));
        setResizable(true);
        setTitle("Breakout");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    /**
     * This method creates the board and repaints the main view to make the board visible.
     */
    public void createBoardView() {
        boardView = new BoardView(queue, getInsets());
        add(boardView);
        revalidate();
        repaint();
    }

    /**
     * Updates BoardView with the new velocity.
     *
     * @param newVelocity the new velocity to update BoardView with
     */
    public void updateBoardView(int newVelocity) {
        boardView.setPaddleVelocity(newVelocity);
        repaint();
    }

    /**
     * Adds a button to play again.
     */
    public void endGame() {
        JButton endGameButton = new JButton("Play Again");
        endGameButton.addActionListener(actionListener -> {
            remove(boardView);
            createBoardView();
        });
        boardView.add(endGameButton);
        revalidate();
        repaint();
    }

    /**
     * Updates the Leaderboard to include the newly added score
     *
     * @param scoreList
     */
    public void updateLeaderboardView(Leaderboard scoreList) {
        boardView.setScores(scoreList);
    }
}
