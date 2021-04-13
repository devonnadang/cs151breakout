package breakout;

//This class is the board class
public class Board {
    Block [][] blocks;
    private static final int ROWS = 5;
    private static final int COLUMNS = 10;

    public Board(){
        createBlocks();
    }

    private void createBlocks(){
        blocks = new Block[ROWS][COLUMNS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                blocks[i][j] = new Block(); //TODO: set correct (x, y) positions
            }
        }
    }

    public boolean bricksAreCleared(){
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (blocks[i][j].getDestroyed() != true) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getRows(){
        return ROWS;
    }

    public int getColumns(){
        return COLUMNS;
    }
}
