package labirinth.state;


import java.util.*;

public class LabirinthState {
    private static final int BOARD_SIZE = 14;

    private static final int EMPTY = 0;

    private static final int WALl = 1;

    private static final int END_POINT = 2;

    private static final int STARTER_POINT = 3;

    public static final int PLAYER_POSITION = 4;


    private Position[][] board;
    private Position playerPosition;
    private Position startPoint;
    private Position endPoint;

    private Position.Direction lastDirection = Position.Direction.RIGHT;

    public LabirinthState() {
        board = new Position[BOARD_SIZE][BOARD_SIZE];
        initializeBoard();
    }

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
        board[playerPosition.getRow()][playerPosition.getCol()] = playerPosition;

        endPoint = new Position(10, BOARD_SIZE - 1, END_POINT);
        board[endPoint.getRow()][endPoint.getCol()] = endPoint;

        List<Position> insideWalls = List.of(
                new Position(1, 1, WALl), new Position(1, 7, WALl), new Position(1, 12, WALl),
                new Position(2, 5, WALl), new Position(2, 7, WALl), new Position(2, 9, WALl),
                new Position(3, 3, WALl), new Position(3, 9, WALl),
                new Position(4, 4, WALl), new Position(4, 10, WALl),
                new Position(5, 1, WALl), new Position(5, 6, WALl), new Position(5, 6, WALl),
                new Position(6, 6, WALl), new Position(6, 9, WALl), new Position(1, 1, WALl),
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

    public void movePlayer(Position.Direction direction){
        Position newPlayerPosition = playerPosition.getNeighbour(direction);

        if(board[newPlayerPosition.getRow()][newPlayerPosition.getCol()].getObject() != WALl){
            board[playerPosition.getRow()][playerPosition.getCol()].setObject(EMPTY);
            playerPosition = newPlayerPosition;
            board[playerPosition.getRow()][playerPosition.getCol()].setObject(PLAYER_POSITION);
            lastDirection = direction;
        }
    }
    public void moveForward() {
        movePlayer(lastDirection);
    }

    public void moveAndTurnRight() {
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

    public boolean isGoal(){
        return playerPosition == endPoint;
    }

    //test on console
    public static void main(String[] args) {
        LabirinthState labirinthState = new LabirinthState();
        labirinthState.printBoard();


        labirinthState.printBoard();
    }

}


