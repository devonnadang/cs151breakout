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

    /**
     * Checking if isAlive() method works correctly. The method should be true when all the lives
     * aren't lost and be false when all lives are lost.
     */
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

    /**
     * Checking if startGame() works correctly. It should not cause any errors or exceptions.
     */
    @Test
    void startGameTest() {
        // GUI pops up for like a split second; Should I make the system wait? But it gave
        // some errors...
        Breakout breakout = new Breakout();
        breakout.startGame();

        // How should I test this? Should this even be a test?
        assertNotNull(breakout);
    }

    /**
     * Checking if the game can restart correctly after having finished. There should be no errors
     * or exceptions.
     */
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
        Board board = new Board();
        System.out.println(board.toString());
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
        Board board = new Board();
        int expectedBlockCounter = board.getRows()*board.getColumns() - 1;
        board.getBall().setCoordinates(30, 35);

        Block testBlock = new Block(30, 30);
        if (board.checkClash()){ 
            board.getBall().destroyBlock(testBlock);
        }
        int actualBlockCounter = board.getBlockCounter();
        assertEquals(expectedBlockCounter, actualBlockCounter);
    }

    /**
     * Checking if board can reset properly. There should be no errors or exceptions.
     */
    @Test
    void resetBoardTest() {
        // How to reset board? There no method to access Board's data except how many rows/columns
        // it has.
        Board board = new Board();
        board = new Board();

        assertNotNull(board);
    }

    @Test
    void ballMoveTest() {

    }

    /**
     * Checking if life is lost. Should give no errors or exception, and one life should be lost
     * everytime subtractLife() is called.
     */
    @Test
    void lifeLostTest() {
        int expected = 3;
        int actual;

        Life lives = new Life();

        // Subtracting all three lives and checking each time if life is subtracted correctly.
        for (int i = 0; i < 3; i++) {
            expected--;
            lives.subtractLife();
            actual = lives.getLives();

            assertEquals(expected, actual);
        }
    }

    @Test
    void paddleMoveTest() {

    }


}
