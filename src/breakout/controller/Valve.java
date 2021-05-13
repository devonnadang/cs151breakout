package breakout.controller;

/**
 * Acts as interface for other valves to handle messages.
 */
public interface Valve {

    public ValveResponse execute(Message message);
}
