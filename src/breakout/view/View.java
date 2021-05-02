package breakout.view;

import breakout.controller.Message;
import java.awt.Dimension;

import java.util.concurrent.BlockingQueue;
import javax.swing.JFrame;

/**
 * Serves as the main view for Breakout. All other views will be within this one.
 */
public class View extends JFrame{
    private BoardView boardView;
    private BlockingQueue<Message> queue;

    /**
     * Constructs the main view of Breakout.
     */
    public View(BlockingQueue<Message> queue) {
        this.queue = queue;
        // Swing stuff
        setPreferredSize(new Dimension(500, 600));
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
        boardView = new BoardView(queue);
        add(boardView);
        revalidate();
        repaint();
    }

    /**
     * Updates BoardView with a new coordinate. This coordinate is going to be for the paddle's x coordinates, since
     * that's the only coordinate that take in input and needs to be changed. This method is used with the message system.
     * So, if you need to use the message system, then leave this method uncommented.
     * @param newCoordinate
     */
    public void updateBoardView(int newCoordinate) {
        boardView.setPaddleCoordinates(newCoordinate);
        repaint();
    }
}