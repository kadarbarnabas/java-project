package labirinth.state;


import lombok.Getter;

import java.util.*;

public class LabirinthState {
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
     * Prints the current state of the board to the console
     */
    public void printBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                switch (board[i][j].getObject()) {
                    case WALl:
                        System.out.print("#");
                        break;
                    case STARTER_POINT:
                        System.out.print("S");
                        break;
                    case END_POINT:
                        System.out.print("E");
                        break;
                    case PLAYER_POSITION:
                        System.out.print("P");
                        break;
                    case EMPTY:
                        System.out.print(" ");
                        break;
                    default:
                        System.out.print("?");
                        break;
                }
                System.out.print(" ");
            }
            System.out.println();
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

        if(board[newPlayerPosition.getRow()][newPlayerPosition.getCol()].getObject() != WALl){
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
     * Returns a list of legal directions that the player can move based on the Position
     * The player can continue in the last direction or turn right from the last direction
     *
     * @return every legal direction that can be made
     */
    public List<Position.Direction> getLegalMoves() {
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

            if(board[newPlayerPosition.getRow()][newPlayerPosition.getCol()].getObject() != WALl){
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
        return board[playerPosition.getRow()][playerPosition.getCol()].getObject() == END_POINT;
    }

    /**
     * The main method to test the LabirinthState class on the console
     *
     * @param args Command line arguments
     */

    public static void main(String[] args) {
        LabirinthState labirinthState = new LabirinthState();
        labirinthState.printBoard();
    }

}


