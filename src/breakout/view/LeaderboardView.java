package breakout.view;

import breakout.model.Constants;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import breakout.model.Leaderboard;
import breakout.model.Score;

/**
 * This class shows the at most the top 10 scores.
 */
public class LeaderboardView extends JFrame {

    private Leaderboard scoreList;
    GridLayout layout = new GridLayout(0, 3);


    /**
     * Displays at most 10 of the top saved scores.
     *
     * @param scoreList the Leaderboard
     */
    public LeaderboardView(Leaderboard scoreList) {

        this.scoreList = scoreList;

        JPanel panel = new JPanel();

        // uses grid layout to display rankings, scores, and usernames in rows
        panel.setLayout(layout);
        panel.setPreferredSize(new Dimension(500, 600));

        // Headings
        panel.add(new JLabel("Ranking", SwingConstants.CENTER));
        panel.add(new JLabel("Username", SwingConstants.CENTER));
        panel.add(new JLabel("Score", SwingConstants.CENTER));

        // add Scores
        int i = 1;

        // gets top 10 values if there are more than 10 scores
        if (scoreList.getHighScores().size() > 10) {
            for (Score s : scoreList.getTop10Score()) {
                panel.add(new JLabel(String.valueOf(i), SwingConstants.CENTER));
                panel.add(new JLabel(s.getUsername(), SwingConstants.CENTER));
                panel.add(new JLabel(String.valueOf(s.getScore()), SwingConstants.CENTER));
                i++;
            }
        } else {

            // gets all scores
            for (Score s : scoreList.getHighScores()) {
                panel.add(new JLabel(String.valueOf(i), SwingConstants.CENTER));
                panel.add(new JLabel(s.getUsername(), SwingConstants.CENTER));
                panel.add(new JLabel(String.valueOf(s.getScore()), SwingConstants.CENTER));
                i++;
            }
        }

        this.add(panel);

        this.setPreferredSize(new Dimension(Constants.getPanelWidth(), Constants.getPanelHeight()));
        this.setResizable(true);
        this.setTitle("Leaderboard");

        // closes the window but does not exit the entire program
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);

    }
}
