package breakout.controller;

public class BlockDestroyedMessage implements Message {

    private int row;
    private int column;

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
