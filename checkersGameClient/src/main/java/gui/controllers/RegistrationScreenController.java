package gui.controllers;

import gui.service.ApiCallService;
import gui.service.IApiCallService;
import gui.shared.ISceneSwitcher;
import gui.shared.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistrationScreenController {
    public Button btnRegister;
    public PasswordField txtPassword;
    public PasswordField txtRepeatPassword;
    public TextField txtUsername;
    public Button btnSwitchToLogin;

    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private IApiCallService apiCallService = new ApiCallService();
    private ISceneSwitcher sceneSwitcher = new SceneSwitcher();


    public void register(ActionEvent actionEvent) {
        if (txtPassword.getText().equals(txtRepeatPassword.getText()) && !txtPassword.getText().equals("") && !txtRepeatPassword.getText().equals("") && !txtUsername.getText().equals("")) {
            if (apiCallService.register(txtUsername.getText(), txtPassword.getText())) {
                update();
                sceneSwitcher.showAlert("User created", null, "Your account has been created");
                sceneSwitcher.switchScene("fxml/LoginScreen.fxml", "Checkers - Login", actionEvent);
            } else {
                update();
                sceneSwitcher.showAlert("Username in use", null, "An user with this username already exists. Please create a different one");
            }
        } else {
            update();
            sceneSwitcher.showAlert("Passwords do not match", null, "Please make sure the passwords match and that the requested fields are not empty");
        }
    }

    public void switchToLogin(ActionEvent actionEvent) {
        sceneSwitcher.switchScene("fxml/LoginScreen.fxml", "Checkers - Login", actionEvent);
    }

    private void update() {
        txtUsername.clear();
        txtPassword.clear();
        txtRepeatPassword.clear();
    }
}
