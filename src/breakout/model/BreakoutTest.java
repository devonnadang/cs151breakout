package breakout.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;

import java.util.concurrent.LinkedBlockingQueue;
import org.junit.jupiter.api.Test;

import breakout.controller.Breakout;
import breakout.view.View;

class BreakoutTest {


    //All the blocks are cleared from board.
    @Test
    void winGameTest() {
        boolean expected = true;
        boolean actual = false;
        Board board = new Board();

        //set all blocks to destroyed
        for (int i = 0; i < Constants.getRows(); i++) {
            for (int j = 0; j < Constants.getColumns(); j++) {
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
        Breakout breakout = new Breakout(new View(new LinkedBlockingQueue<>()), new LinkedBlockingQueue<>());
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

    @Test // get score based on how many blocks are left
    void checkScoreTest() {
    	// Board boardTest = new Board();
    	// int blocksDestroyed = getRows() * getColumns() - getBlockCounter() = // how many blocks are out
    }

    @Test // only get 10 highest score
    void get10HighestScores() {
    	Score s1 = new Score();
        s1.setScore(65);
        s1.setUsername("A");
        
        Score s2 = new Score();
        s2.setScore(10);
        s2.setUsername("B");
        
        Score s3 = new Score();
        s3.setScore(55);
        s3.setUsername("C");
        
        Score s4 = new Score();
        s4.setScore(200);
        s4.setUsername("D");
        
        Score s5 = new Score();
        s5.setScore(250);
        s5.setUsername("E");
        
        Score s6 = new Score();
        s6.setScore(65);
        s6.setUsername("F");
        
        Score s7 = new Score();
        s7.setScore(65);
        s7.setUsername("G");
        
        Score s8 = new Score();
        s8.setScore(15);
        s8.setUsername("H");
        
        Score s9 = new Score();
        s9.setScore(0);
        s9.setUsername("I");
        
        Score s10 = new Score();
        s10.setScore(70);
        s10.setUsername("J");
        
        Score s11 = new Score();
        s11.setScore(40);
        s11.setUsername("K");
        
        Score s12 = new Score();
        s12.setScore(250);
        s12.setUsername("L");
        
        Leaderboard scores = new Leaderboard();
        scores.addNewScore(s1);
        scores.addNewScore(s2);
        scores.addNewScore(s3);
        scores.addNewScore(s4);
        scores.addNewScore(s5);
        scores.addNewScore(s6);
        scores.addNewScore(s7);
        scores.addNewScore(s8);
        scores.addNewScore(s9);
        scores.addNewScore(s10);
        scores.addNewScore(s11);
        scores.addNewScore(s12);
        
        ArrayList<Score> expectedScores = new ArrayList<>();
        expectedScores.add(s5);
        expectedScores.add(s12);
        expectedScores.add(s4);
        expectedScores.add(s10);
        expectedScores.add(s1);
        expectedScores.add(s6);
        expectedScores.add(s7);
        expectedScores.add(s3);
        expectedScores.add(s11);
        expectedScores.add(s8);

        assertEquals(expectedScores, scores.getTop10Score());

    }

    @Test // make sure all scores are added and in order
    void addScoreTest() {
    	Score s1 = new Score();
        s1.setScore(65);
        s1.setUsername("A");
        
        Score s2 = new Score();
        s2.setScore(100);
        s2.setUsername("B");
        
        Score s3 = new Score();
        s3.setScore(5);
        s3.setUsername("C");
        
        Score s4 = new Score();
        s4.setScore(20);
        s4.setUsername("D");
        
        Score s5 = new Score();
        s5.setScore(100);
        s5.setUsername("E");
        
        Leaderboard scores = new Leaderboard();
        scores.addNewScore(s1);
        scores.addNewScore(s2);
        scores.addNewScore(s3);
        scores.addNewScore(s4);
        scores.addNewScore(s5);

        ArrayList<Score> expectedScores = new ArrayList<>();
        expectedScores.add(s2);
        expectedScores.add(s5);
        expectedScores.add(s1);
        expectedScores.add(s4);
        expectedScores.add(s3);

        assertEquals(expectedScores, scores.getHighScores());
    }

    @Test
    void createBoardTest() {
        Board board = new Board();
        System.out.println(board.toString());
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
        int expectedBlockCounter = Constants.getRows()*Constants.getColumns() + 1; //one block is destroyed
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

    /**
     * Checks to see whether ball can move properly.
     */
    @Test
    void ballMoveTest() {
        //make sure ball does not move indefinitely out of the border
        //check top, right, left border
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

    /**
     * Checks to see whether paddle can move properly.
     */
    @Test
    void paddleMoveTest() {
        Paddle paddle = Paddle.getInstance();
        paddle.moveLeft();
        paddle.moveLeft();
        int xExpected = Constants.getPaddleXReset() + Constants.getPaddleMoveLeftUnit() + Constants.getPaddleMoveLeftUnit();
        int xActual = paddle.getX();
        assertEquals(xExpected, xActual);
    }


}
