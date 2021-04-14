package breakout;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * The main system of this game. Helps control interaction between data, user, and their view.
 */
public class Breakout {

    private Board board;
    // I guess update the status using a String every once in a while?
    private String status;
    private JFrame frame;

    public Breakout() {
        status = "SYSTEM_INITIALIZED";
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
     * Ends Breakout.
     */
    public void endGame() {
        // Should Breakout check for lives?
        // If no lives or no blocks
//        if (!isAlive() || board.bricksAreCleared()) {
//            // Go to the leaderboard page
//        }
    }

    /**
     * What is this?
     */
    public void notifyPlayerGameStatus() {
        // Print out or show game status?
    }
}
