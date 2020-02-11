package pl.game.monopoly.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.game.monopoly.client.MainApp;
import pl.game.monopoly.client.ServerHandler;
import pl.game.monopoly.core.BoardXY;
import pl.game.monopoly.core.TCP_Message;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class GameBoardController {
    @FXML
    public Text cashText;
    @FXML
    public Button buyButton;
    @FXML
    public Button sellButton;

    @FXML
    public Label fieldLabel;

    @FXML
    public Label cashLabel;

    private Stage stage;

    public static Stage infoMessageStage;

    private HashMap<BoardXY, ImageView> imageViewFields = new HashMap<>();

    public static ObservableList<String> items = FXCollections.observableArrayList ();


    @FXML
    public Circle playerOnePawn;
    @FXML
    public Circle playerTwoPawn;
    @FXML
    public GridPane GameBoardFXML;
    @FXML
    public Button rollButton;
    @FXML
    public TextField rollResultField;
    @FXML
    public ListView<String> listOfFields;

    public Socket socket;
    private LinkedBlockingQueue<Object> messagesFromServer;
    ServerHandler serverHandler;

    public void initialize() throws FileNotFoundException {
        //
        stage = MainApp.getPrimStage();
        stage.setWidth(1000);
        stage.setHeight(700);
        stage.setMinHeight(700);
        stage.setMinWidth(1000);
        //
        playerOnePawn.setVisible(true);
        playerTwoPawn.setVisible(true);
        //
        //buyButton.setDisable(true);
        //sellButton.setDisable(true);
        rollResultField.setDisable(true);
        //
        GameBoardFXML.getStylesheets().addAll(getClass().getResource("/css/GameBoard.css").toString());
        //
        listOfFields.setItems(items);

        //Disable roll button as default
        rollButton.setDisable(true);

        //
        fieldLabel.setText("START");
        //
        cashLabel.setText(String.valueOf(MainApp.clientPlayer.getMoney()));

        // Show info about waiting for all players to join
        if(!MainApp.gameActive) {

            try {
                // Open a new one
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/infoMessage.fxml"));
                AnchorPane pane = loader.load();

                InfoMessageController infoMessageController = loader.getController();

                infoMessageController.setInfoMessageLabel("Oczekiwanie na co najmniej 2 graczy w lobby...");

                Scene scene = new Scene(pane);
                infoMessageStage = new Stage();

                infoMessageStage.initStyle(StageStyle.UNDECORATED);
                infoMessageStage.setTitle("Game Information");
                infoMessageStage.setScene(scene);
                infoMessageStage.setAlwaysOnTop(true);
                infoMessageStage.show();

            } catch(IOException e) {
                e.printStackTrace();
            }
        }

    }


    public void rollButtonClicked() {

        System.out.println("rollButtonClicked");

        // Get number
        Random rand = new Random();
        int diceResult = rand.nextInt(6) + 1;

        rollResultField.setText(String.valueOf(diceResult));

        // Disable button after rolling dice
        rollButton.setDisable(true);

        // Send dice result to server
        MainApp.clientConnection.send(new TCP_Message(TCP_Message.ACTION_ROLL_DICE, diceResult, MainApp.clientPlayer.playerToString()));

    }

    public void buyButtonClicked(ActionEvent actionEvent) {

        System.out.println("BUY BUTTON CLICKED");

        MainApp.clientConnection.send(new TCP_Message(TCP_Message.ACTION_BUY, MainApp.clientPlayer.playerToString()));
        //items.add(ClientNew.fields.get(MainApp.boardXYList.get(MainApp.clientPlayer.getPlayerPos())).getName());

    }

    public void sellButtonClicked(ActionEvent actionEvent) {
    }


}
