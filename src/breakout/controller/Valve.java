package breakout.controller;

/**
 * Acts as interface for other valves to handle messages.
 */
public interface Valve {

    /**
     * Executes the valve.
     *
     * @param message the message to check
     * @return response whether the valve is executed, missed, or finished
     */
    public ValveResponse execute(Message message);
}
