package breakout;

import breakout.controller.Breakout;
import breakout.controller.Message;
import breakout.view.View;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class App {
    /**
     * Initializes and starts Breakout. Right now if you run it, the program will be using the message system, but if you want
     * to see how I set it up without using the message system, then go to my comments in BoardView's private MoveAction class
     * to see which lines to comment and which lines to uncomment.
     */
    public static void main(String[] args) {
        BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
        View view = new View(queue);
        Breakout breakout = new Breakout(view, queue);
        breakout.startGame();
    }
}
