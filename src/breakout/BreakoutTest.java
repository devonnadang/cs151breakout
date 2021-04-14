package breakout;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

class BreakoutTest {


    //All the blocks are cleared from board.
    @Test
    void winGameTest() {
        boolean expected = true;
        boolean actual = false;
        Board board = new Board();

        //set all blocks to destroyed
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                board.blocks[i][j].setDestroyed(true);
            }
        }
        actual = board.bricksAreCleared();

        assertEquals(expected, actual);
    }

    //User loses all lives
    @Test
    void loseGameTest() {
        int expected = 0;
        int actual;
        Life lives = new Life();

        // Subtracting all three lives
        for (int i = 0; i < 3; i++) {
            lives.subtractLife();
        }

        actual = lives.getLives();
        assertEquals(expected, actual);
    }

    @Test
    void isAliveTest() {
        // Should be alive at beginning of game
        boolean expected = true;
        boolean actual;
        Life lives = new Life();

        actual = lives.isAlive();
        assertEquals(expected, actual);

        // Should not be alive after losing all lives
        expected = false;

        // Subtracting all three lives
        for (int i = 0; i < 3; i++) {
            lives.subtractLife();
        }

        actual = lives.isAlive();
        assertEquals(expected, actual);
    }

    @Test
    void startGameTest() {
        // GUI pops up for like a split second; Should I make the system wait? But it gave
        // some errors...
        Breakout breakout = new Breakout();
        breakout.startGame();

        // How should I test this? Should this even be a test?
        assertNotNull(breakout);
    }

    @Test
    void resetGameTest() {

    }

    @Test
    void checkScoreTest() {

    }

    @Test
    void get10HighestScores() {

    }

    @Test
    void addScoreTest() {

    }

    @Test
    void createBoardTest() {

    }

    @Test
    void createBallTest() {

    }

    @Test
    void createBlockTest() {

    }

    @Test
    void createPaddleTest() {

    }

    //Check if score is saved in leaderboard
    @Test
    void saveScoreTest() {

    }

    @Test
    void compareScoreTest() {
        ArrayList<Score> scores = new ArrayList<>();
        Score s1 = new Score();
        Score s2 = new Score();
        Score s3 = new Score();
        Score s4 = new Score();
        s1.setScore(100);
        s1.setUsername("C");
        s2.setScore(500);
        s2.setUsername("C");
        s3.setScore(5);
        s3.setUsername("B");
        s4.setScore(500);
        s4.setUsername("A");
        scores.add(s1);
        scores.add(s2);
        scores.add(s3);
        scores.add(s4);
        Collections.sort(scores);

        ArrayList<Score> expectedScores = new ArrayList<>();
        expectedScores.add(s4);
        expectedScores.add(s2);
        expectedScores.add(s1);
        expectedScores.add(s3);

        assertEquals(expectedScores, scores);
    }

    @Test
    void destroyBlockTest() {

    }

    @Test
    void resetBoardTest() {

    }

    @Test
    void ballMoveTest() {

    }

    @Test
    void lifeLostTest() {

    }
    
    @Test
    void paddleMoveTest() {
    	
    }


}
