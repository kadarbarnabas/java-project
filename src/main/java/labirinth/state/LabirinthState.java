package labirinth.state;

import lombok.Getter;
import puzzle.State;

import java.util.*;

/**
 * Represents the state of the labirinth wich implements the puzzle.State interface
 */
public class LabirinthState implements State<Position.Direction> {
    /**
     * The size of the board including the boarder
     */
    public static final int BOARD_SIZE = 14;

    /**
     * The index representing an empty space
     */
    public static final int EMPTY = 0;

    /**
     * The index representing the walls
     */
    public static final int WALl = 1;

    /**
     * The index representing the end point
     */
    public static final int END_POINT = 2;

    /**
     * The index representing the start point
     */
    public static final int STARTER_POINT = 3;

    /**
     * The index representing the player's position
     */
    public static final int PLAYER_POSITION = 4;


    /**
     *  The board represented as a 2D array of Position object
     */
    private Position[][] board;
    /**
     * The current position of the player
     */
    @Getter
    private Position playerPosition;
    /**
     * The starting and ending point of the labyrinth
     */
    @Getter
    private Position startPoint;

    @Getter
    private Position endPoint;

    /**
     * The last direction the player moved in
     */
    private Position.Direction lastDirection = Position.Direction.RIGHT;

    /**
     * Creates a {@code LabirinthState} and initializes the board
     */
    public LabirinthState() {
        board = new Position[BOARD_SIZE][BOARD_SIZE];
        initializeBoard();
    }

    /**
     * Initializes the board with walls, the start and the end point
     */
    private void initializeBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (i == 0 || i == BOARD_SIZE - 1 || j == 0 || j == BOARD_SIZE - 1) {
                    board[i][j] = new Position(i, j, WALl);
                } else {
                    board[i][j] = new Position(i, j, EMPTY);
                }
            }
        }

        //starter and end point
        startPoint = new Position(3, 0, STARTER_POINT);
        board[startPoint.getRow()][startPoint.getCol()] = startPoint;

        playerPosition = new Position(3, 0, PLAYER_POSITION);

        endPoint = new Position(10, BOARD_SIZE - 1, END_POINT);
        board[endPoint.getRow()][endPoint.getCol()] = endPoint;

        List<Position> insideWalls = List.of(
                new Position(1, 1, WALl), new Position(1, 7, WALl), new Position(1, 12, WALl),
                new Position(2, 5, WALl), new Position(2, 7, WALl), new Position(2, 9, WALl),
                new Position(3, 3, WALl), new Position(3, 9, WALl),
                new Position(4, 4, WALl), new Position(4, 10, WALl),
                new Position(5, 1, WALl), new Position(5, 6, WALl), new Position(5, 7, WALl),
                new Position(6, 6, WALl), new Position(6, 9, WALl), new Position(6, 11, WALl),
                new Position(7, 3, WALl),
                new Position(8, 2, WALl), new Position(8, 5, WALl), new Position(8, 8, WALl), new Position(8, 12, WALl),
                new Position(9, 7, WALl),
                new Position(10, 2, WALl), new Position(10, 3, WALl), new Position(10, 10, WALl),
                new Position(11, 4, WALl), new Position(11, 6, WALl), new Position(11, 8, WALl), new Position(11, 9, WALl),
                new Position(12, 1, WALl), new Position(12, 12, WALl)
        );

        for (Position wall : insideWalls) {
            board[wall.getRow()][wall.getCol()] = wall;
        }
    }

    /**
     * Returns the current state of the labyrinth as a 2D array of Positions
     *
     * @return a 2D array representing the current state of the labyrinth
     */
    public Position[][]  getLabirinthState()
    {
        return board;
    }

    /**
     * Moves the player in the specified direction if the new position in not a wall
     *
     * @param direction to move the player
     */
    public void movePlayer(Position.Direction direction){
        Position newPlayerPosition = playerPosition.getNeighbour(direction);

        if(getLegalMovesList().contains(direction)){
            board[playerPosition.getRow()][playerPosition.getCol()].setObject(EMPTY);
            playerPosition = newPlayerPosition;
            board[playerPosition.getRow()][playerPosition.getCol()].setObject(PLAYER_POSITION);
            lastDirection = direction;
        }
    }

    /**
     * Moves the player in the last direction they moved
     */
    public void moveForward() {
        movePlayer(lastDirection);
    }

    /**
     * Turn the player to the right and moves them
     */
    public void turnAndMoveRight() {
        Position.Direction newDirection = null;
        switch (lastDirection) {
            case UP:
                newDirection = Position.Direction.RIGHT;
                break;
            case RIGHT:
                newDirection = Position.Direction.DOWN;
                break;
            case DOWN:
                newDirection = Position.Direction.LEFT;
                break;
            case LEFT:
                newDirection = Position.Direction.UP;
                break;
        }
        movePlayer(newDirection);
    }

    /**
     * Sets the player's position to a new specified location and direction
     * on the board and update the state with these new position and direction
     *
     * @param newPlayerPosition specified new positions for the player
     * @param newDirection specified last directions for the player
     */
    public void setPlayerPositionToSpecifiedPosition(Position newPlayerPosition, Position.Direction newDirection) {
        board[playerPosition.getRow()][playerPosition.getCol()].setObject(EMPTY);
        playerPosition = newPlayerPosition;
        board[playerPosition.getRow()][playerPosition.getCol()].setObject(PLAYER_POSITION);
        lastDirection = newDirection;
    }

    /**
     * Returns a list of legal directions that the player can move based on the Position
     * The player can continue in the last direction or turn right from the last direction
     *
     * @return every legal direction that can be made
     */
    public List<Position.Direction> getLegalMovesList() {
        List<Position.Direction> legalMoves = new ArrayList<>();
        List<Position.Direction> validDirections = new ArrayList<>();

        validDirections.add(lastDirection);
        switch (lastDirection) {
            case UP:
                validDirections.add(Position.Direction.RIGHT);
                break;
            case RIGHT:
                validDirections.add(Position.Direction.DOWN);
                break;
            case DOWN:
                validDirections.add(Position.Direction.LEFT);
                break;
            case LEFT:
                validDirections.add(Position.Direction.UP);
                break;
        }

        for (Position.Direction direction : validDirections) {
            Position newPlayerPosition = playerPosition.getNeighbour(direction);

            if(newPlayerPosition.getRow() != 14 && newPlayerPosition.getCol() != 14
                    && newPlayerPosition.getRow() != -1 && newPlayerPosition.getCol() != -1
                    && board[newPlayerPosition.getRow()][newPlayerPosition.getCol()].getObject() != WALl){

                legalMoves.add(direction);
            }
        }
        return legalMoves;
    }

    /**
     * Checks if the player has reached the end point.
     *
     * @return true if the player has reached the end point, false otherwise
     */
    public boolean isGoal(){
        return playerPosition.getRow() == 10 && playerPosition.getCol() == 13;
    }

    /**
     * Checks if the labyrinth puzzel is solved by the solver
     *
     * @return true if it reached the end point, false otherwise
     */
    @Override
    public boolean isSolved(){
        return isGoal();
    }

    /**
     * Checks if the solver moves in the specified direction is it legal
     *
     * @param direction in which the solver moves
     * @return true if move is legal, false otherwise
     */
    @Override
    public boolean isLegalMove(Position.Direction direction) {
        return getLegalMovesList().contains(direction);
    }

    /**
     * Makes a move in the specified direction if its legal
     *
     * @param direction in which the solver moves
     */
    @Override
    public void makeMove(Position.Direction direction) {
        if(isLegalMove(direction)){

            movePlayer(direction);
        }
    }

    /**
     * Gets a set of legal moves that the solver can make in the current state
     *
     * @return a set of legal move directions
     */
    @Override
    public Set<Position.Direction> getLegalMoves(){
        return new HashSet<>(getLegalMovesList());
    }

    /**
     * Comapres the labyrinth state to another object equality
     *
     * @param o the object to compare to
     * @return true if the object are equal, false otherwise
     *
     * Two labyrinth is true if their boards and player positions... and last directions are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LabirinthState that = (LabirinthState) o;
        return Arrays.deepEquals(board, that.board) &&
                Objects.equals(playerPosition, that.playerPosition) &&
                Objects.equals(startPoint, that.startPoint) &&
                Objects.equals(endPoint, that.endPoint) &&
                lastDirection == that.lastDirection;
    }

    /**
     * Generates a hash code value
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(playerPosition, startPoint, endPoint, lastDirection);
        result = 31 * result + Arrays.deepHashCode(board);
        return result;
    }


    /**
     * Creates a deep copy of the labyrinth state
     *
     * @return a new labyrinth state object copy
     */
    @Override
    public LabirinthState clone() {
        LabirinthState copy;
        try{
            copy = (LabirinthState) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }

        copy.board = new Position[BOARD_SIZE][BOARD_SIZE];
        copy.startPoint = new Position(startPoint.getRow(), startPoint.getCol(), STARTER_POINT);
        copy.endPoint = new Position(endPoint.getRow(), endPoint.getCol(), END_POINT);
        copy.playerPosition = new Position(playerPosition.getRow(), playerPosition.getCol(), PLAYER_POSITION);
        copy.lastDirection = lastDirection;

        for(int i = 0; i<BOARD_SIZE; i++){
            for(int j = 0; j<BOARD_SIZE; j++){
                copy.board[i][j] = new Position(i, j, board[i][j].getObject());
            }
        }
        return copy;
    }
}


