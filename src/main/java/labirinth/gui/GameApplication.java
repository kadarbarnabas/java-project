package labirinth.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GameGui.fxml"));
        stage.setTitle("Labirinth Game");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
