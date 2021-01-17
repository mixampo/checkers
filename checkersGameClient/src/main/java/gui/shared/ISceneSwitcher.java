package gui.shared;

import javafx.event.ActionEvent;
import models.User;

public interface ISceneSwitcher {
    void switchScene(String location, String title, ActionEvent actionEvent);
    void switchToHome(String location, String title, User user, ActionEvent actionEvent);
    void showAlert(String title, String header, String contentText);
}
