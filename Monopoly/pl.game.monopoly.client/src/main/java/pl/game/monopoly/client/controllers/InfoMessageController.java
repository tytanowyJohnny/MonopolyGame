package pl.game.monopoly.client.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class InfoMessageController implements Initializable {

    @FXML
    Button infoMessageButton;

    @FXML
    Label infoMessageLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //infoMessageButton.setVisible(false);

    }

    public void setInfoMessageLabel(String message) {

        Platform.runLater(() -> {
            infoMessageLabel.setText(message);
        });
    }

    @FXML
    private void handleInfoMessageButton(ActionEvent actionEvent) {

        Stage stage = (Stage) infoMessageButton.getScene().getWindow();
        stage.close();

    }
}
