package breakout;

import breakout.controller.Breakout;
import breakout.controller.Message;
import breakout.view.View;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class App {

    /**
     * Initializes and starts Breakout.
     */
    public static void main(String[] args) {
        BlockingQueue<Message> queue = new ArrayBlockingQueue<>(15);
        View view = new View(queue);
        Breakout breakout = new Breakout(view, queue);
        breakout.startGame();
    }
}
