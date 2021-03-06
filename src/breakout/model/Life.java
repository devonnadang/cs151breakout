package breakout.model;

/**
 * An object to keep track of lives. Always starts with three lives.
 */
public class Life {

    private int lives;

    /**
     * Initializes Life with 3 lives.
     */
    public Life() {
        this.lives = 3;
    }

    public int getLives() {
        return lives;
    }

    /**
     * Subtracts one life.
     */
    protected void subtractLife() {
        lives--;
    }

    /**
     * Checks if there are more than 0 lives, which means that the player is still alive.
     *
     * @return true if greater than 0 lives left otherwise false
     */
    public boolean isAlive() {
        return lives > 0;
    }
}
