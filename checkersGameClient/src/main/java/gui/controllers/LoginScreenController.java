package gui.controllers;

import gui.scenes.CheckersClientGui;
import gui.shared.ISceneSwitcher;
import gui.shared.SceneSwitcher;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import service.ApiCallService;
import service.IApiCallService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.User;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {
    public TextField txtUsername;
    public PasswordField txtPassword;
    public Button btnLogin;
    public CheckBox cbRegistration;
    public Button btnSwitchToRegister;
    public User user;

    private IApiCallService apiCallService;
    private ISceneSwitcher sceneSwitcher;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        apiCallService = new ApiCallService();
        sceneSwitcher = new SceneSwitcher();
    }

    public void login(ActionEvent actionEvent) throws IOException {
        if (!txtUsername.getText().isEmpty() && !txtPassword.getText().isEmpty()) {
            user = apiCallService.login(txtUsername.getText(), txtPassword.getText());
            if (user != null) {
                sceneSwitcher.switchToHome("fxml/HomeScreen.fxml", "Checkers - Home", user, actionEvent);
            } else {
                update();
                sceneSwitcher.showAlert("Wrong credentials", null, "Invalid username/password supplied");
            }
        } else {
            update();
            sceneSwitcher.showAlert("No credentials", null, "No username/password supplied");
        }
    }

    public void switchToRegister(ActionEvent actionEvent) {
        sceneSwitcher.switchScene("fxml/RegistrationScreen.fxml", "Checkers - Register", actionEvent);
    }

    private void update() {
        txtUsername.clear();
        txtPassword.clear();
    }
}
