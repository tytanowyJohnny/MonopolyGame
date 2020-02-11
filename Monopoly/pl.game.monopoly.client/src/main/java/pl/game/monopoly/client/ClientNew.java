package pl.game.monopoly.client;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.game.monopoly.client.controllers.GameBoardController;
import pl.game.monopoly.client.controllers.InfoMessageController;
import pl.game.monopoly.core.BoardXY;
import pl.game.monopoly.core.Player;
import pl.game.monopoly.core.PropertyField;
import pl.game.monopoly.core.TCP_Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientNew {

    private ConnectionToServer server;
    private LinkedBlockingQueue<Object> messages;
    private Socket socket;

    public static HashMap<BoardXY, PropertyField> fields = new HashMap<>();

    public ClientNew(String IPAddress, int port) throws IOException {

        socket = new Socket(IPAddress, port);
        messages = new LinkedBlockingQueue<Object>();
        server = new ConnectionToServer(socket);

        populateGameFields();

        System.out.println("New Client created!");

        Thread messageHandling = new Thread(() -> {

            while(true) {

                System.out.println("In while..");

                try {

                    Object message = messages.take();

                    System.out.println("Klient: " + message.toString());

                    TCP_Message receivedMessage = (TCP_Message) message;

                    switch(receivedMessage.getAction()) {

                        case TCP_Message.ACTION_SET_PLAYER_ID:

                            if(MainApp.clientPlayer.getPlayerID() == -1)
                                MainApp.clientPlayer.setPlayerID(receivedMessage.getMessageInt());

                            System.out.println("Registered as playerID: " + MainApp.clientPlayer.getPlayerID());
                            break;

                        case TCP_Message.ACTION_GAME_START:

                            MainApp.gameActive = true;

                            // Hide Info
                            if(GameBoardController.infoMessageStage != null) {

                                Platform.runLater(() -> {

                                    GameBoardController.infoMessageStage.close();
                                });

                            }

                            if(MainApp.clientPlayer.getPlayerID() == receivedMessage.getMessageInt()) {

                                Platform.runLater(() -> {

                                    showMessageWindow("Graczu, teraz Twoja kolej! Rzuc kostka aby wykonac ruch!");
                                    MainApp.clientGameBoardController.rollButton.setDisable(false);

                                });
                            }

                            break;

                        case TCP_Message.ACTION_GAME_ONGOING:

                            Platform.runLater(() -> {

                                showMessageWindow("Dolaczyłes w trakcie rozgrywki, poczekaj na zakonczenie rundy..");

                            });
                            break;

                        case TCP_Message.ACTION_ROLL_DICE:

                            Player dicePlayer = new Player(receivedMessage.getMessageString());
                            int newPos = 0;

                            System.out.println(dicePlayer.getPlayerPos());

                            if (dicePlayer.getPlayerPos() > 39)
                                newPos = dicePlayer.getPlayerPos() - 39;
                            else
                                newPos = dicePlayer.getPlayerPos();

                            if(dicePlayer.getPlayerID() == MainApp.clientPlayer.getPlayerID()) {

                                int finalNewPos2 = newPos;
                                Platform.runLater(() -> {
                                    MainApp.clientGameBoardController.fieldLabel.setText(fields.get(MainApp.boardXYList.get(finalNewPos2)).getName());
                                });


                                if (dicePlayer.getPlayerPos() > 39) {
                                    MainApp.clientPlayer.setPlayerPos(dicePlayer.getPlayerPos() - 39);
                                } else {
                                    MainApp.clientPlayer.setPlayerPos(dicePlayer.getPlayerPos());
                                }
                            }


                            System.out.println(MainApp.clientPlayer.getPlayerPos());

                            if(dicePlayer.getPlayerID() == 0) {

                                int finalNewPos = newPos;
                                Platform.runLater(() -> {
                                    MainApp.clientGameBoardController.GameBoardFXML.getChildren().remove(MainApp.clientGameBoardController.playerOnePawn);
                                    MainApp.clientGameBoardController.GameBoardFXML.add(MainApp.clientGameBoardController.playerOnePawn, MainApp.boardXYList.get(finalNewPos).getColY(), MainApp.boardXYList.get(finalNewPos).getRowX());
                                });
                            } else {

                                int finalNewPos1 = newPos;
                                Platform.runLater(() -> {
                                    MainApp.clientGameBoardController.GameBoardFXML.getChildren().remove(MainApp.clientGameBoardController.playerTwoPawn);
                                    MainApp.clientGameBoardController.GameBoardFXML.add(MainApp.clientGameBoardController.playerTwoPawn, MainApp.boardXYList.get(finalNewPos1).getColY(), MainApp.boardXYList.get(finalNewPos1).getRowX());
                                });

                            }

                            if(dicePlayer.getPlayerID() != MainApp.clientPlayer.getPlayerID()) {

                                Platform.runLater(() -> {

                                    showMessageWindow("Twoj ruch, wykorzystaj go madrze...");
                                    MainApp.clientGameBoardController.rollButton.setDisable(false);

                                });

                            }

                            break;

                        case TCP_Message.ACTION_BUY:

                            Player buyPlayer = new Player(receivedMessage.getMessageString());

                            if(buyPlayer.getPlayerID() == MainApp.clientPlayer.getPlayerID()) {

                                Platform.runLater(() -> {
                                    GameBoardController.items.add(ClientNew.fields.get(MainApp.boardXYList.get(MainApp.clientPlayer.getPlayerPos())).getName());
                                });

                                MainApp.clientPlayer.setMoney(MainApp.clientPlayer.getMoney() - ClientNew.fields.get(MainApp.boardXYList.get(MainApp.clientPlayer.getPlayerPos())).getPrice());

                                Platform.runLater(() -> {
                                    MainApp.clientGameBoardController.cashLabel.setText(String.valueOf(MainApp.clientPlayer.getMoney()));
                                });

                                if(MainApp.clientPlayer.getMoney() <= 0) {

                                    MainApp.clientConnection.send(new TCP_Message(TCP_Message.ACTION_ENDGAME, MainApp.clientPlayer.playerToString()));
                                }
                            }
                            break;

                        case TCP_Message.ACTION_ENDGAME:

                            Player lostPlayer = new Player(receivedMessage.getMessageString());
                            showMessageWindow("Gracz ID: " + lostPlayer.getPlayerID() + " zbankrutowal! Koniec gry..");
                            break;

                        default:
                            // Do nothing
                            break;

                    }

                }

                catch(InterruptedException ignored){ }
            }
        });

        messageHandling.setDaemon(true);
        messageHandling.start();
    }

    private void showMessageWindow(String message) {

        try {
            // Open a new one
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/infoMessage.fxml"));
            AnchorPane pane = loader.load();

            InfoMessageController infoMessageController = loader.getController();

            infoMessageController.setInfoMessageLabel(message);

            Scene scene = new Scene(pane);
            Stage stage = new Stage();

            stage.setTitle("Game Information");
            stage.setScene(scene);
            stage.show();

        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    private class ConnectionToServer {

        ObjectInputStream in;
        ObjectOutputStream out;
        Socket socket;

        ConnectionToServer(Socket socket) throws IOException {


            this.socket = socket;
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            Thread read = new Thread(() -> {

                while(true){

                    try{

                        //System.out.println("ConnectionToServer reading..");
                        Object obj = in.readObject();
                        messages.put(obj);

                    }
                    catch(Exception ignored){ }
                }

            });

            read.setDaemon(true);
            read.start();
        }

        private void write(Object obj) {

            try{

                out.writeObject(obj);

            }

            catch(IOException e){ e.printStackTrace(); }
        }


    }

    public void send(Object obj) {
        server.write(obj);
    }

    private void populateGameFields() {

        fields.put(MainApp.BoardXY_10_10, new PropertyField(0,523230,"START"));
        fields.put(MainApp.BoardXY_10_9, new PropertyField(1,0,"OLD KENT ROAD"));
        fields.put(MainApp.BoardXY_10_8, new PropertyField(2,536606,"COMMUNITY CHEST"));
        fields.put(MainApp.BoardXY_10_7, new PropertyField(3,0,"WHITECHAPEL ROAD"));
        fields.put(MainApp.BoardXY_10_6, new PropertyField(4,0,"INCOME TAX"));
        fields.put(MainApp.BoardXY_10_5, new PropertyField(5,0,"MARYLEBONE STATION"));
        fields.put(MainApp.BoardXY_10_4, new PropertyField(6,741384,"THE ANGEL ISLINGTON"));
        fields.put(MainApp.BoardXY_10_3, new PropertyField(7,0,"CHANCE"));
        fields.put(MainApp.BoardXY_10_2, new PropertyField(8,677365,"EUSTON ROAD"));
        fields.put(MainApp.BoardXY_10_1, new PropertyField(9,820695,"PENTONVILLE ROAD"));
        fields.put(MainApp.BoardXY_10_0, new PropertyField(10,0,"IN JAIL"));

        fields.put(MainApp.BoardXY_9_0, new PropertyField(11,1169667,"PALL MALL"));
        fields.put(MainApp.BoardXY_8_0, new PropertyField(12,592,"ELECTRIC COMPANY"));
        fields.put(MainApp.BoardXY_7_0, new PropertyField(13,1691877,"WHITEHALL"));
        fields.put(MainApp.BoardXY_6_0, new PropertyField(14,569819,"NORTHUMBERLAND ROAD"));
        fields.put(MainApp.BoardXY_5_0, new PropertyField(15,0,"FENCHURCH ST. STATION"));
        fields.put(MainApp.BoardXY_4_0, new PropertyField(16,1330708,"BOW STREET"));
        fields.put(MainApp.BoardXY_3_0, new PropertyField(17,0,"COMMUNITY CHEST"));
        fields.put(MainApp.BoardXY_2_0, new PropertyField(18,1300242,"MALBOROUGH STREET"));
        fields.put(MainApp.BoardXY_1_0, new PropertyField(19,1970445,"VINE STREET"));

        fields.put(MainApp.BoardXY_0_0, new PropertyField(20,0,"FREE PARKING"));
        fields.put(MainApp.BoardXY_0_1, new PropertyField(21,1299028,"STRAND"));
        fields.put(MainApp.BoardXY_0_2, new PropertyField(22,0,"CHANCE"));
        fields.put(MainApp.BoardXY_0_3, new PropertyField(23,550000,"FLEET STREET"));
        fields.put(MainApp.BoardXY_0_4, new PropertyField(24,1417206,"TRAFALGAR SQUARE"));
        fields.put(MainApp.BoardXY_0_5, new PropertyField(25,0,"KINGS CROSS STATION"));
        fields.put(MainApp.BoardXY_0_6, new PropertyField(26,1687283,"LEICESTER SQUARE"));
        fields.put(MainApp.BoardXY_0_7, new PropertyField(27,1611725,"COVENTRY STREET"));
        fields.put(MainApp.BoardXY_0_8, new PropertyField(28,385,"WATER WORKS"));
        fields.put(MainApp.BoardXY_0_9, new PropertyField(29,2158667,"PICADILLY"));
        fields.put(MainApp.BoardXY_0_10, new PropertyField(30,0,"GO TO JAIL"));

        fields.put(MainApp.BoardXY_1_10, new PropertyField(31,2059589,"REGENT STREET"));
        fields.put(MainApp.BoardXY_2_10, new PropertyField(32,1842053,"OXFORD STREET"));
        fields.put(MainApp.BoardXY_3_10, new PropertyField(33,0,"COMMUNITY CHEST"));
        fields.put(MainApp.BoardXY_4_10, new PropertyField(34,1922975,"BOND STREET"));
        fields.put(MainApp.BoardXY_5_10, new PropertyField(35,0,"LIVERPOOL ST. STATION"));
        fields.put(MainApp.BoardXY_6_10, new PropertyField(36,0,"CHANCE"));
        fields.put(MainApp.BoardXY_7_10, new PropertyField(37,1275752,"PARK LANE"));
        fields.put(MainApp.BoardXY_8_10, new PropertyField(38,1300242,"SUPER TAX"));
        fields.put(MainApp.BoardXY_9_10, new PropertyField(39,3092166,"MAYFAIR"));

    }
}
