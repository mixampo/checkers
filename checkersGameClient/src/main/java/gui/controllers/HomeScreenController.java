package gui.controllers;

import checkersGame.ICheckersGame;
import checkersGame.exceptions.CheckersGameFullException;
import gui.scenes.CheckersClientGui;
import gui.shared.SceneSwitcher;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.ScoreboardItem;
import models.User;
import service.ApiCallService;
import service.IApiCallService;

import java.io.IOException;
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

    private ObservableList<ScoreboardItem> scoreboard;
    private SceneSwitcher sceneSwitcher;
    private ICheckersGame game;
    private CheckersClientGui gui;
    private IApiCallService apiCallService;
    private User loggedInUser;
    private boolean singlePlayermode;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sceneSwitcher = new SceneSwitcher();
        apiCallService = new ApiCallService();
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
                gui = new CheckersClientGui(this.loggedInUser);
                try {
                    gui.registerPlayer(false);
                } catch (CheckersGameFullException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    gui.registerPlayer(true);
                } catch (CheckersGameFullException e) {
                    e.printStackTrace();
                }
            }
            btnJoinGame.setDisable(true);
            btnReadyUp.setDisable(false);
        } else {
            sceneSwitcher.showAlert("Error", "Please select an available game mode", "");
        }
    }

    public void notifyReady(ActionEvent actionEvent) {
        Stage stage = (Stage) btnReadyUp.getScene().getWindow();
        stage.close();
        Platform.runLater(() -> gui.start(new Stage()));
    }

    public void exitApplication(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void refreshScoreboard(ActionEvent actionEvent) {

        //TODO fix view of scoreboard
        try {
            scoreboard = FXCollections.observableList(apiCallService.getScoreboard(loggedInUser));
            System.out.println(scoreboard.get(0));
            tvScoreboard.setItems(scoreboard);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout(ActionEvent actionEvent) {
        loggedInUser = null;
        sceneSwitcher.switchScene("fxml/LoginScreen.fxml", "Checkers - Login", actionEvent);
    }
}
