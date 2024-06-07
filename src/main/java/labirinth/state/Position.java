package labirinth.state;

import lombok.Getter;
import lombok.Setter;

/**
 * Represetns a 2D position and the object on that position
 * Hold the row, column of the grid position and object on
 * that grid position
 */
@Getter
public class Position implements Cloneable {
    private final int row;
    private final int col;

    @Setter
    private int object;

    /**
     *
     * @param row position in the grid
     * @param col the column position in the grid
     * @param object identifier at the specifid position
     */
    public Position(int row, int col, int object) {
        this.row = row;
        this.col = col;
        this.object = object;
    }


    /**
     * Represents the four main directions
     */
    public enum Direction {

        UP, DOWN, LEFT, RIGHT;
    }

    /**
     * @return the position from the given direction
     *
     * @param direction specifies the direction from the standing point
     */
    public Position getNeighbour(Direction direction) {

        if (direction == null) {
            return new Position(row, col, object);
        }

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
