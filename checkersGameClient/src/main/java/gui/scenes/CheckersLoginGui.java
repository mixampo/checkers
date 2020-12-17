package gui.scenes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CheckersLoginGui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        try {
            AnchorPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/LoginScreen.fxml")));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Checkers - Login");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
