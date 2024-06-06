package labirinth.gui;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import labirinth.state.LabirinthState;
import labirinth.state.Position;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

import org.tinylog.Logger;

public class GameController {

    private LabirinthState state;

    private Pane playerPane;
    private final List<Pane> highlightedMoves = new ArrayList<>();

    private int moveCount = 0;

    @FXML
    private GridPane board;

    @FXML
    private Text moveCounter;

    @FXML
    private void initialize()
    {
        Logger.info("Initializing Game");
        state = new LabirinthState();
        loadTable();
    }

    private void loadTable()
    {
        Logger.info("Loading Table");
        Position[][] grid = state.getLabirinthState();

        for (int i = 0; i < LabirinthState.BOARD_SIZE; i++) {
            for (int j = 0; j < LabirinthState.BOARD_SIZE; j++) {
                Pane cellPane = new Pane();
                switch (grid[i][j].getObject()) {
                    case LabirinthState.WALl:
                        cellPane.setStyle("-fx-background-color: black;");
                        break;
                    case LabirinthState.STARTER_POINT:
                        cellPane.setStyle("-fx-background-color: red;");
                        break;
                    case LabirinthState.END_POINT:
                        cellPane.setStyle("-fx-background-color: red;");
                        break;
                    case LabirinthState.EMPTY:
                        cellPane.setStyle(null);
                        break;
                }
                board.add(cellPane, j, i);
            }
        }

        updatePlayerPosition();
    }

    private void updatePlayerPosition()
    {
        if(playerPane != null){
            board.getChildren().remove(playerPane);
        }

        Pane cellPane = new Pane();

        Circle playerCircle = new Circle(15);
        playerCircle.setFill(Color.BLUE);

        playerCircle.centerXProperty().bind(cellPane.widthProperty().divide(2));
        playerCircle.centerYProperty().bind(cellPane.heightProperty().divide(2));

        cellPane.getChildren().add(playerCircle);

        Position playerPosition = state.getPlayerPosition();
        playerPane = cellPane;

        board.add(cellPane, playerPosition.getCol(), playerPosition.getRow());

        heighlightLegalMoves();
        gameOver();
    }

    @FXML
    private void mouseClickMove(MouseEvent event){

        Pane clickedPane = (Pane) event.getTarget();
        var row = GridPane.getRowIndex(clickedPane);
        var col = GridPane.getColumnIndex(clickedPane);

        if (row == null || col == null) {
            return;
        }

        var direction = getDirection(row, col);

        if (state.getLegalMoves().contains(direction)) {
            moveCount++;
            setMoveCounter();
            state.movePlayer(direction);
        }else {
            Logger.info("Player invalid moving");
        }

        updatePlayerPosition();
    }

    private Position.Direction getDirection(int row, int col){
        Position playerPosition = state.getPlayerPosition();

        int playerRow = playerPosition.getRow();
        int playerCol = playerPosition.getCol();



        int rowDistance = playerRow - row;
        int colDistance = playerCol - col;

        if (-1 > rowDistance || rowDistance > 1) {
            Logger.info(playerRow + " " + playerCol + " ---> " + row + " " + col);
            Logger.info("Invalid distance");
            return null;
        }
        if (-1 > colDistance || colDistance > 1) {
            Logger.info(playerRow + " " + playerCol + " ---> " + row + " " + col);
            Logger.info("Invalid distance");
            return null;
        }
        if (colDistance ==0 && rowDistance ==0) {
            Logger.info(playerRow + " " + playerCol + " ---> " + row + " " + col);
            Logger.info("Invalid distance");
            return null;
        }

        if (playerCol < col){
            Logger.info("Player moved from: " + playerRow + " " + playerCol + " to Right " + row + " " + col);
            return Position.Direction.RIGHT;
        }
        else if (playerCol > col){
            Logger.info("Player moved from: " + playerRow + " " + playerCol + " to Left " + row + " " + col);
            return Position.Direction.LEFT;
        }
        else if (playerRow < row){
            Logger.info("Player moved from: " + playerRow + " " + playerCol + " to Down " + row + " " + col);
            return Position.Direction.DOWN;
        }
        else if (playerRow > row){
            Logger.info("Player moved from: " + playerRow + " " + playerCol + " to Up " + row + " " + col);
            return Position.Direction.UP;
        }

        return null;
    }


    private void heighlightLegalMoves() {
        for (Pane moves : highlightedMoves) {
            board.getChildren().remove(moves);
        }
        highlightedMoves.clear();

        List<Position.Direction> legalMoves = state.getLegalMoves();

        for (Position.Direction direction : legalMoves) {
            Position legalPositions = state.getPlayerPosition().getNeighbour(direction);
            Pane cellPane = new Pane();
            cellPane.setStyle("-fx-background-color: lightgreen;");

            board.add(cellPane, legalPositions.getCol(), legalPositions.getRow());
            highlightedMoves.add(cellPane);
        }
    }

    private void gameOver(){
        if (state.getLegalMoves().isEmpty() || state.isGoal()){
            Logger.info("GAME OVER");
            moveCounter.setText("You lost!");
        }else if (state.isGoal()){
            Logger.info("GAME OVER");
            moveCounter.setText("You Won!");
        }
    }

    private void setMoveCounter(){
        moveCounter.setText("Moves: " + Integer.toString(moveCount));
    }

    @FXML
    private void resetGame(){
        Logger.info("Resetting game");
        state = new LabirinthState();
        moveCount = 0;
        setMoveCounter();
        updatePlayerPosition();
    }
}
