package pl.game.monopoly.core;

public class BoardXY {

    private int rowX;
    private int colY;

    public BoardXY(int mRowX, int mColY) {

        this.rowX = mRowX;
        this.colY = mColY;
    }

    public int getRowX() {
        return rowX;
    }

    public void setRowX(int rowX) {
        this.rowX = rowX;
    }

    public int getColY() {
        return colY;
    }

    public void setColY(int colY) {
        this.colY = colY;
    }
}
