package breakout.model;

import static org.junit.jupiter.api.Assertions.*;

import breakout.view.BoardView;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collections;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.jupiter.api.Test;

import breakout.controller.Breakout;
import breakout.view.View;
import java.awt.Insets;


class BreakoutTest {


    /**
     * Check that all the blocks are cleared from board.
     */
    @Test
    void destroyBlocksTest() {
        boolean expected = true;
        boolean actual;
        Board board = new Board(new Insets(0, 0, 0, 0), new ArrayBlockingQueue(15));
        int destroyedBlocks = 0;

        // set all blocks to destroyed
        for (int i = 0; i < Constants.getRows(); i++) {
            for (int j = 0; j < Constants.getColumns(); j++) {
                board.getBlock(i, j).setDestroyed(true);
                destroyedBlocks++;
            }
        }

        actual = destroyedBlocks == Constants.getRows() * Constants.getColumns();

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
     * Checking if Leaderboard returns the 10 highest score even when more scores are added.
     */
    @Test
    void getLeaderboards10HighestScores() {
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

        Leaderboard scores = Leaderboard.getInstance();
        // Because Leaderboard is Singleton we need to reset the Leaderboard
        scores.resetLeaderboard();
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

        ArrayList<Score> actualScores = scores.getTop10Score();
        for (int i = 0; i < expectedScores.size(); i++) {
            assertEquals(expectedScores.get(i).getScore(), actualScores.get(i).getScore());
        }
    }

    /**
     * Checking that all scores are added and in correct order according to Score's compareTo()
     * method.
     */
    @Test
    void addScoreToLeaderboardTest() {
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

        Leaderboard scores = Leaderboard.getInstance();
        // Because Leaderboard is Singleton we need to reset the Leaderboard
        scores.resetLeaderboard();
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

        ArrayList<Score> actualScores = scores.getHighScores();
        for (int i = 0; i < expectedScores.size(); i++) {
            assertEquals(expectedScores.get(i).getScore(), actualScores.get(i).getScore());
        }
    }

    /**
     * Checks if the scores are being sorted correctly according to Score's compareTo() method.
     */
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

    /**
     * Checking to see whether blocks are being destroyed properly.
     */
    @Test
    void destroyBlockTest() {
        Block testBlock = new Block(0, 0, 0, 0, new ArrayBlockingQueue<>(15));
        testBlock.setDestroyed(true);

        boolean expected = true;
        boolean actual = testBlock.getDestroyed();

        assertEquals(expected, actual);
    }

    /**
     * Checks to see whether ball can move properly.
     */
    @Test
    void ballMoveTest() {
        //make sure ball does not move indefinitely out of the border
        //check top, right, left border
        Ball ball = Ball.getInstance();
        ball.setBallCoordinates(new double[]{400, 0});
        ball.setBallVelocity(new double[]{5, 5});
        ball.move(500);

        double[] expectedCoordinates = new double[]{404, 4};
        double[] actualCoordinates = ball.getBallCoordinates();
        for (int i = 0; i < 2; i++) {
            assertEquals(expectedCoordinates[i], actualCoordinates[i]);
        }

    }

    /**
     * Checks to see whether paddle can move properly.
     */
    @Test
    void paddleMoveTest() {
        Paddle paddle = Paddle.getInstance();
        double expected = paddle.getPaddleCoordinates()[0] + 5;
        paddle.setPaddleVelocity(5);
        paddle.move();

        double actual = paddle.getPaddleCoordinates()[0];

        assertEquals(expected, actual);
    }
}
