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

    public enum Direction {

        UP, DOWN, LEFT, RIGHT;
    }


    public Position getNeighbour(Direction direction) {
        switch (direction) {
            case UP:
                return new Position(row - 1, col, object);
            case DOWN:
                return new Position(row + 1, col, object);
            case LEFT:
                return new Position(row, col - 1, object);
            case RIGHT:
                return new Position(row, col + 1, object);
            default:
                return new Position(row, col, object);
        }
    }

}
