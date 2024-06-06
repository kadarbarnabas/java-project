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
        state = new LabirinthState();
        loadTable();
    }

    private void loadTable()
    {
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
        }

        updatePlayerPosition();
    }

    private Position.Direction getDirection(int row, int col){
        Position playerPosition = state.getPlayerPosition();

        int playerRow = playerPosition.getRow();
        int playerCol = playerPosition.getCol();

        System.out.println(playerRow + " " + playerCol + "--->" + row + " " + col);

        int rowDistance = playerRow - row;
        int colDistance = playerCol - col;

        if (-1 > rowDistance || rowDistance > 1) {
            System.out.println("Invalid distance");
            return null;
        }
        if (-1 > colDistance || colDistance > 1) {
            System.out.println("Invalid distance");
            return null;
        }
        if (colDistance ==0 && rowDistance ==0) {
            System.out.println("Invalid distance");
            return null;
        }

        if (playerCol < col){
            return Position.Direction.RIGHT;
        }
        else if (playerCol > col){
            return Position.Direction.LEFT;
        }
        else if (playerRow < row){
            return Position.Direction.DOWN;
        }
        else if (playerRow > row){
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
        System.out.println("Game Over");
        if (state.getLegalMoves().isEmpty() || state.isGoal()){
            moveCounter.setText("You lost!");
        }else if (state.isGoal()){
            moveCounter.setText("You Won!");
        }
    }

    private void setMoveCounter(){
        moveCounter.setText("Moves: " + Integer.toString(moveCount));
    }

    @FXML
    private void resetGame(){
        state = new LabirinthState();
        moveCount = 0;
        setMoveCounter();
        updatePlayerPosition();
    }
}
