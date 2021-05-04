package breakout.view;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class LeaderboardWindow extends JFrame{
	
	GridLayout layout = new GridLayout(0,3); // rows, columns, hgap, vgap
    final static int maxGap = 20;


    /**
     * Constructs the main view of Breakout.
     */
    public LeaderboardWindow() {
    	
    	JPanel panel = new JPanel();
    	panel.setLayout(layout);
        panel.setPreferredSize(new Dimension(500, 600));
               
        // Headings
        panel.add(new JLabel("Ranking", SwingConstants.CENTER));
        panel.add(new JLabel("Username", SwingConstants.CENTER));
        panel.add(new JLabel("Score", SwingConstants.CENTER));
        
        // add Scores

        this.add(panel);
    	
    	this.setPreferredSize(new Dimension(500, 600));
    	this.setResizable(true);
    	this.setTitle("Leaderboard");
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.pack();
    	this.setVisible(true);
        
    }
}
