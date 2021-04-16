package breakout;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * The main system of this game. Helps control interaction between data, user, and their view.
 */
public class Breakout {

    private Board board;
    private JFrame frame;

    /**
     * Initializes Breakout and the main JFrame that will display everything.
     */
    public Breakout() {
        // Swing stuff
        frame = new JFrame();
        frame.setPreferredSize(new Dimension(300, 600));
        frame.setResizable(true);
        frame.setTitle("Breakout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Starts Breakout. Initializes Board and any other variable that needs to be initialized.
     */
    public void startGame() {
        status = "GAME_STARTED";
        // What's supposed be in here????????
        // Who's gonna be the JFrame?
        board = new Board();
        // frame.add(<a component that represents Board>);
    }

    /**
     * Ends Breakout. Breakout should end when there are no lives left or when there are no blocks left.
     */
    public void endGame() {
        // Should Breakout check for lives?
        // If no lives or no blocks
//        if (!isAlive() || board.bricksAreCleared()) {
//            // Go to the leaderboard page
//        }
    }
}
