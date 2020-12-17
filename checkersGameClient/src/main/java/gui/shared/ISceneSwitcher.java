package gui.shared;

import javafx.event.ActionEvent;

public interface ISceneSwitcher {
    void switchScene(String location, String title, ActionEvent actionEvent);
    void showAlert(String title, String header, String contentText);
}
