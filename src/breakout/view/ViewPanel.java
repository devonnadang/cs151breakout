package breakout.view;

import javax.swing.JPanel;
import javax.swing.Timer;
import breakout.model.Ball;
import breakout.model.Block;
import breakout.model.Board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;


public class ViewPanel extends JPanel implements ActionListener{
    final int PANEL_WIDTH = 500;
    final int PANEL_HEIGHT = 600;
    final int BALL_HEIGHT= Ball.getBallRadius()*2;
    final int BALL_WIDTH= Ball.getBallRadius()*2;
    final int BLOCK_HEIGHT= Block.getBlockHeight();
    final int BLOCK_WIDTH= Block.getBlockWidth();
    final int BLOCK_SEP = Board.getBlockSep();
    
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

        /* create blocks */
        for (int i = 0; i < Board.getRows(); i++) {
            for(int j = 0; j < Board.getColumns(); j++) {
                int x = 30+(BLOCK_WIDTH*(j+1)) + (BLOCK_SEP*(j+1));
                int y = 30+(BLOCK_HEIGHT*(i+1)) + (BLOCK_SEP*(i+1));
                g2D.setColor(Color.RED);
                g2D.fill(new Rectangle2D.Double(x, y, BLOCK_WIDTH, BLOCK_HEIGHT));
            }
        }      
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
