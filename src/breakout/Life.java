package breakout;

/**
 * An object to keep track of lives. Always starts with three lives.
 */
public class Life {

    private int lives;

    public Life() {
        this.lives = 3;
    }

    public int getLives() {
        return lives;
    }

    public void subtractLife() {
        lives--;
    }

    public boolean isAlive() {
        return lives > 0;
    }
}
