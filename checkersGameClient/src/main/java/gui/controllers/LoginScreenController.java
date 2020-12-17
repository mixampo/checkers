package gui.controllers;

import gui.scenes.CheckersClientGui;
import gui.shared.ISceneSwitcher;
import gui.shared.SceneSwitcher;
import gui.service.ApiCallService;
import gui.service.IApiCallService;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {
    public TextField txtUsername;
    public PasswordField txtPassword;
    public Button btnLogin;
    public CheckBox cbRegistration;
    public Button btnSwitchToRegister;

    private IApiCallService apiCallService = new ApiCallService();
    private ISceneSwitcher sceneSwitcher = new SceneSwitcher();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void login(ActionEvent actionEvent) {
        if (!txtUsername.getText().isEmpty() && !txtPassword.getText().isEmpty()) {
            if (apiCallService.login(txtUsername.getText(), txtPassword.getText()) != null) {
                CheckersClientGui.main(null);
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
