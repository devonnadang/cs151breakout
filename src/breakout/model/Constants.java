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
    private static final int PADDLE_OFFSET = 40;
    private static final int PADDLE_X_RESET = PANEL_WIDTH / 2 - PADDLE_WIDTH / 2;
    private static final int PADDLE_Y_RESET = PANEL_HEIGHT - PADDLE_HEIGHT - PADDLE_OFFSET;
    private static final int BALL_X_RESET = PANEL_WIDTH / 2 - ((BALL_RADIUS*2)/2);
    private static final int BALL_Y_RESET = PADDLE_Y_RESET - (BALL_RADIUS*2);
    private static final int PADDLE_MOVE_RIGHT_UNIT = 7;
    private static final int PADDLE_MOVE_LEFT_UNIT = -7;
    private static final int BALL_VELOCITY = -5;

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
    
    public static int getPaddleXReset() {
        return PADDLE_X_RESET;
    }

    public static int getPaddleYReset() {
        return PADDLE_Y_RESET;
    }

    public static int getBallXReset() {
        return BALL_X_RESET;
    }

    public static int getBallYReset(){
        return BALL_Y_RESET;
    }

    public static int getPaddleMoveRightUnit(){
        return PADDLE_MOVE_RIGHT_UNIT;
    }

    public static int getPaddleMoveLeftUnit(){
        return PADDLE_MOVE_LEFT_UNIT;
    }

    public static int getBallVelocity(){
        return BALL_VELOCITY;
    }
}

