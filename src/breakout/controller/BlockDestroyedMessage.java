package breakout.controller;

/**
 * Message sent to signal when a block is destroyed
 */
public class BlockDestroyedMessage implements Message {

    private int row;
    private int column;

    /**
     * Initialize BlockDestroyedMessage with the position of the block
     *
     * @param row    the row where the destroyed block is located
     * @param column the column where the destroyed block is located
     */
    public BlockDestroyedMessage(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
