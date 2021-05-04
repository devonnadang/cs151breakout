package breakout.model;

public class Constants {
    private static final int BALL_RADIUS = 8;
    private final static int BLOCK_HEIGHT = 20;
    private final static int BLOCK_WIDTH = 40;
    private static final int ROWS = 5;
    private static final int COLUMNS = 8;
    private static final int PANEL_WIDTH = 500;
    private static final int PANEL_HEIGHT = 600;
    private static final int BLOCK_SEP = 2;
    private static final int PADDLE_WIDTH = 80;
    private static final int PADDLE_HEIGHT = 15;
    private static final int PADDLE_OFFSET = 30;

    public static int getBallRadius(){
        return BALL_RADIUS;
    }

    public static int getBlockHeight(){
        return BLOCK_HEIGHT;
    }

    public static int getBlockWidth(){
        return BLOCK_WIDTH;
    }

    public static int getRows(){
        return ROWS;
    }

    public static int getColumns(){
        return COLUMNS;
    }

    public static int getPanelWidth(){
        return PANEL_WIDTH;
    }

    public static int getPanelHeight() {
        return PANEL_HEIGHT;
    }

    public static int getBlockSep() {
        return BLOCK_SEP;
    }

    public static int getPaddleHeight(){
        return PADDLE_HEIGHT;
    }

    public static int getPaddleWidth(){
        return PADDLE_WIDTH;
    }
    
    public static int getPaddleOffSet(){
        return PADDLE_OFFSET;
    }
}

