package labirinth.gui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import labirinth.state.LabirinthState;
import labirinth.state.Position;

import game.console.Game;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class GameController {

    @FXML
    private GridPane board;

    @FXML
    public void initialize()
    {
        loadTable();
    }

    private void loadTable()
    {
        LabirinthState state = new LabirinthState();
        Position[][] grid = state.getLabirinthsState();

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
                    case LabirinthState.PLAYER_POSITION:
                        Circle playerCircle = new Circle(10);
                        playerCircle.setFill(Color.BLUE);
                        cellPane.getChildren().add(playerCircle);
                        break;
                    case LabirinthState.EMPTY:
                        break;
                    default:
                        break;
                }
                board.add(cellPane, j, i);
            }
        }
    }
}
