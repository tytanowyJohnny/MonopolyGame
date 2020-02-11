package pl.game.monopoly.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import pl.game.monopoly.client.ClientNew;
import pl.game.monopoly.client.MainApp;
import pl.game.monopoly.core.Player;
import pl.game.monopoly.core.TCP_Message;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.regex.Pattern;

public class MainAppController {
    @FXML
    private Label label;
    @FXML
    private StackPane pane;
    @FXML
    private Button connectToTheServerButton;
    @FXML
    private Stage stage;
    @FXML
    private TextField ipField;

    private  Socket socket;


    public void initialize() {
        stage = MainApp.getPrimStage();
    //    connectToTheServerButton.setDisable(true);

    }


    public void connectToTheServerButtonClicked() {
        String ip;
        ip = ipField.getText();

        try {

            //socket = new Socket(ip,6666);

            // Create player instance
            MainApp.clientPlayer = new Player();
            // Add new player as connection to server
            MainApp.clientConnection = new ClientNew(InetAddress.getByName("localhost").toString().split("/")[1], 4848);
            MainApp.clientConnection.send(new TCP_Message(TCP_Message.ACTION_NEW_PLAYER, MainApp.clientPlayer.playerToString()));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GameBoard.fxml"));
            Pane pane = null;
            pane = loader.load();

            MainApp.clientGameBoardController = loader.getController();
            System.out.println(MainApp.clientGameBoardController);


            stage.setScene(new Scene(pane));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public void IpFieldKeyReleased() {
        if (Pattern.matches("(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])", ipField.getCharacters())) {
            connectToTheServerButton.setDisable(false);
        }
        else connectToTheServerButton.setDisable(true);
    }

}
