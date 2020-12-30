package gui.shared;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;

public class SceneSwitcher implements ISceneSwitcher {

    public void switchScene(String location, String title, ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource(location));
        Parent mainScreenParent;
        try {
            mainScreenParent = loader.load();
            Scene mainScreenScene = new Scene(mainScreenParent);

            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(mainScreenScene);
            window.setTitle(title);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAlert(String title, String header, String contentText) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(title);
                alert.setHeaderText(header);
                alert.setContentText(contentText);

                alert.showAndWait();
            }
        });
    }
}
