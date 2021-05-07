package breakout.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import breakout.model.Leaderboard;
import breakout.model.Score;


public class LeaderboardView extends JFrame{
	private Leaderboard scoreList;
	GridLayout layout = new GridLayout(0,3); // rows, columns, hgap, vgap


    /**
     * Constructs the main view of Breakout.
     */
    public LeaderboardView(Leaderboard scoreList) {
    	
    	this.scoreList = scoreList;
    	
    	JPanel panel = new JPanel();
    	panel.setLayout(layout);
        panel.setPreferredSize(new Dimension(500, 600));
               
        // Headings
        panel.add(new JLabel("Ranking", SwingConstants.CENTER));
        panel.add(new JLabel("Username", SwingConstants.CENTER));
        panel.add(new JLabel("Score", SwingConstants.CENTER));
        
        // add Scores
        int i = 1;
        for (Score s : scoreList.getHighScores())
        {
        	panel.add(new JLabel(String.valueOf(i), SwingConstants.CENTER));
        	panel.add(new JLabel(s.getUsername(), SwingConstants.CENTER));
        	panel.add(new JLabel(String.valueOf(s.getScore()), SwingConstants.CENTER));
        	i++;
        }

        this.add(panel);
    	
    	this.setPreferredSize(new Dimension(500, 600));
    	this.setResizable(true);
    	this.setTitle("Leaderboard");
    	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	this.pack();
    	this.setVisible(true);
        
    }
}
