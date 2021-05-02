package breakout.view;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

public class PaddleIcon implements Icon {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 20;

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        // TODO Auto-generated method stub
        g.fillRect(x, y, getIconWidth(), getIconHeight());
        // Why is it calling it twice??????
        System.out.println("icon");
    }

    @Override
    public int getIconWidth() {
        // TODO Auto-generated method stub
        return WIDTH;
    }

    @Override
    public int getIconHeight() {
        // TODO Auto-generated method stub
        return HEIGHT;
    }
    
}
