package breakout.controller;

import breakout.model.Board;
import breakout.model.Life;
import breakout.view.View;

/**
 * The main system of this game. Helps control interaction between the views and models.
 */
public class Breakout {

    private Board board;
    private View view;
    private Life lives;

    /**
     * Initializes Breakout and the main JFrame that will display everything.
     */
    public Breakout(View view) {
        this.view = view;
    }

    /**
     * Starts Breakout. Initializes Board and any other objects that need to be initialized.
     */
    public void startGame() {
        lives = new Life();
        // Also should probably add lives into Board constructor?
        board = new Board();
        // Component representing the board
        // view.add(board);
        // While loop at the end of this method after everything is initialized
        // while (board.isDisplayable()) {
            // Take from queue
            // Figure out what message it is and do correct action
            // If it is an endgame message, then set board to not display, so that
            // the while loop ends
        // }
        // Calling endgame after while loop ends
        endGame();
    }

    /**
     * Ends Breakout when there are no lives left or when there are no blocks
     * left.
     */
    public void endGame() {
        // Go to leaderboard page
    }

    /**
     * Initializes and starts Breakout.
     */
    public static void main(String[] args) {
        View view = new View();
        Breakout breakout = new Breakout(view);
        breakout.startGame();
    }
}
