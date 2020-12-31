package gui.controllers;

import checkersGame.ICheckersGame;
import checkersGame.SingleCheckersGame;
import checkersGame.exceptions.CheckersGameFullException;
import gui.CheckersWebsocketGame;
import gui.scenes.CheckersClientGui;
import gui.shared.SceneSwitcher;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.User;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeScreenController implements Initializable {
    public Button btnJoinGame;
    public Button btnReadyUp;
    public TableView tvScoreboard;
    public TableColumn id;
    public TableColumn username;
    public TableColumn score;
    public TableColumn date;
    public TableColumn win;
    public Button btnExitApplication;
    public Button btnRefreshScoreboard;
    public Button btnLogout;
    public ChoiceBox cbGameMode;
    public Label lblName;

    private SceneSwitcher sceneSwitcher;
    private ICheckersGame game;
    private User loggedInUser;
    private boolean singlePlayermode;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sceneSwitcher = new SceneSwitcher();
        cbGameMode.getItems().addAll("Singleplayer", "Multiplayer");
        btnReadyUp.setDisable(true);
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
        lblName.setText("Hello, " + loggedInUser.getUsername());
    }

    public void joinGame(ActionEvent actionEvent) {
        if (!cbGameMode.getSelectionModel().isSelected(-1)) {
            if (cbGameMode.getValue().toString().equals("Multiplayer")) {

            } else {
                //TODO add possibility to switch to singleplayer
            }
            btnJoinGame.setDisable(true);
            btnReadyUp.setDisable(false);
        } else {
            sceneSwitcher.showAlert("Error", "Please select an available game mode", "");
        }
    }

    public void notifyReady(ActionEvent actionEvent) {

        //TODO notify

        Stage stage = (Stage) btnReadyUp.getScene().getWindow();
        stage.close();
        CheckersClientGui gui = new CheckersClientGui(this.loggedInUser);
        Platform.runLater(() -> gui.start(new Stage()));
    }

    public void exitApplication(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void refreshScoreboard(ActionEvent actionEvent) {
    }

    public void logout(ActionEvent actionEvent) {
        loggedInUser = null;
        sceneSwitcher.switchScene("fxml/LoginScreen.fxml", "Checkers - Login", actionEvent);
    }
}
