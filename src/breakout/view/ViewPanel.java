package breakout.view;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;


public class ViewPanel extends JPanel implements ActionListener{
    final int PANEL_WIDTH = 500;
    final int PANEL_HEIGHT = 600;
    final int BALL_HEIGHT= 20;
    final int BALL_WIDTH= 20;

    int xBallCoord = 0;
    int yBallCoord = 0;
    
    int xVelocity = 10;
    int yVelocity = 10;

    Timer timer; 
    
    Ellipse2D.Double ball;

    ViewPanel() {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        timer = new Timer(100, this); 
		timer.start();
    }

    public void paint(Graphics g) {
        super.paint(g); //paint background
        Graphics2D g2D = (Graphics2D) g;

        /* create blue ball */
		ball = new  Ellipse2D.Double(xBallCoord, yBallCoord, BALL_WIDTH, BALL_HEIGHT);
		g2D.setColor(Color.BLUE);
		g2D.fill(ball);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (xBallCoord >= PANEL_WIDTH-ball.getWidth() || xBallCoord<0) { //ball does not go past right and left border
			xVelocity *= -1;
		} 
		xBallCoord = xBallCoord + xVelocity;
		
		if (yBallCoord<0) { //ball does not go past top border
			yVelocity *= -1;
		} 
		yBallCoord  = yBallCoord  + yVelocity;
		repaint();
    }
}
