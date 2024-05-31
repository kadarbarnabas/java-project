package labirinth.state;

public class Position {
    private int row;
    private int col;
    private int object;

    public Position(int row, int col, int object) {
        this.row = row;
        this.col = col;
        this.object = object;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getObject() {
        return object;
    }

    public void setObject(int object) {
        this.object = object;
    }
}
