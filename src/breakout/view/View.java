package breakout.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;

/**
 * Serves as the main view for Breakout. All other views will be within this one.
 */
public class View extends JFrame{

	/**
     * Constructs the main view of Breakout.
     */
    public View() {
        // Swing stuff
        setPreferredSize(new Dimension(500, 600));
        setResizable(true);
        setTitle("Breakout");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
    

}
