package breakout.view;

import breakout.controller.Message;
import breakout.controller.PlayAgainMessage;
import breakout.model.Constants;
import java.awt.Dimension;

import java.util.concurrent.BlockingQueue;
import javax.swing.JButton;
import javax.swing.JFrame;

import breakout.model.Leaderboard;

/**
 * Serves as the main view for Breakout. All other views will be within this one.
 */
public class View extends JFrame {

    private BoardView boardView;
    private BlockingQueue<Message> queue;

    /**
     * Constructs the main view of Breakout.
     *
     * @param queue the queue to add message to
     */
    public View(BlockingQueue<Message> queue) {
        this.queue = queue;
        this.setPreferredSize(new Dimension(Constants.getPanelWidth(), Constants.getPanelHeight()));
        setResizable(false);
        setTitle("Breakout");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    /**
     * This method creates the board and repaints the main view to make the board visible.
     *
     * @param ballCoordinates   the coordinates of the ball
     * @param paddleCoordinates the coordinates of the paddle
     * @param lives             the lives left which should be 3
     */
    public void createBoardView(double[] ballCoordinates, double[] paddleCoordinates, int lives) {
        boardView = new BoardView(queue, getInsets(), ballCoordinates, paddleCoordinates);
        boardView.setLivesCounter(lives);
        add(boardView);
        revalidate();
        repaint();
    }

    /**
     * Updates BoardView's paddle coordinates.
     *
     * @param paddleCoordinate the new paddle coordinates
     */
    public void updateBoardView(double paddleCoordinate) {
        boardView.setPaddleCoordinates(paddleCoordinate);
        revalidate();
        repaint();
    }

    /**
     * Updates BoardView's ball coordinates.
     *
     * @param ballCoordinates the new ball coordinates
     */
    public void updateBoardView(double[] ballCoordinates) {
        boardView.setBallCoordinates(ballCoordinates);
        revalidate();
        repaint();
    }

    /**
     * Updates BoardView's isBlockDestroyed and finalScore.
     *
     * @param row    the row of the block to destroy
     * @param column the column of the block to destroy
     */
    public void updateBoardView(int row, int column) {
        boardView.setBlockDestroyed(row, column);
        boardView.setFinalScore(boardView.getFinalScore() + 5);
        repaint();
    }

    /**
     * Resets the game. This is not restarting the game, but just puts the paddle and ball in the
     * starting position without resetting the blocks or lives.
     *
     * @param startingBall   the starting ball position
     * @param startingPaddle the starting paddle position
     * @param lives          the current remaining lives
     */
    public void resetGame(double[] startingBall, double startingPaddle, int lives) {
        boardView.stopTimer(false);
        boardView.setLivesCounter(lives);
        updateBoardView(startingBall);
        updateBoardView(startingPaddle);
    }

    /**
     * Ends the game and adds several buttons: a save score button to save the score along with a
     * username, a leaderboard button to look at the top 10 highest scores, and a play again button
     * to play the game again.
     *
     * @param startingBallCoordinates   the starting ball coordinates when game is restarted
     * @param startingPaddleCoordinates starting paddle coordinates when game is restarted
     * @param lives                     the lives at the end of the game which should be 0 since the
     *                                  game has ended
     */
    public void endGame(double[] startingBallCoordinates, double[] startingPaddleCoordinates,
            int lives) {
        boardView.stopTimer(true);
        boardView.setLivesCounter(lives);
        JButton endGameButton = new JButton("Play Again");
        endGameButton.addActionListener(actionListener -> {
            try {
                queue.add(new PlayAgainMessage(startingBallCoordinates, startingPaddleCoordinates));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        boardView.add(endGameButton);
        boardView.endGame();
        revalidate();
        repaint();
    }

    /**
     * Restarts the game again by removing the current BoardView and creating a new one.
     *
     * @param startingBallCoordinates   the starting ball coordinates
     * @param startingPaddleCoordinates the starting paddle coordinates
     * @param lives                     the lives remaining which should be 3
     */
    public void playAgain(double[] startingBallCoordinates, double[] startingPaddleCoordinates,
            int lives) {
        remove(boardView);
        createBoardView(startingBallCoordinates, startingPaddleCoordinates, lives);
    }

    /**
     * Updates the Leaderboard to include the newly added score.
     *
     * @param scoreList holds the list of scores
     */
    public void updateLeaderboardView(Leaderboard scoreList) {
        boardView.setScores(scoreList);
    }
}
