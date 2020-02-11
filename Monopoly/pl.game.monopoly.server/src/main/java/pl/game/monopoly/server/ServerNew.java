package pl.game.monopoly.server;

import pl.game.monopoly.core.BoardXY;
import pl.game.monopoly.core.Player;
import pl.game.monopoly.core.PropertyField;
import pl.game.monopoly.core.TCP_Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerNew {

    private ArrayList<ConnectionToClient> clientList;
    private LinkedBlockingQueue<Object> messages;
    private ServerSocket serverSocket;

    // Game specific variables
    private ArrayList<Player> players = new ArrayList<>();
    private static int playersIDCounter = 0;
    private boolean gameStarted = false;
    private int playingPlayerID = -1;


    private HashMap<BoardXY, PropertyField> fields = new HashMap<>();
    private ArrayList<Integer> boughtFields = new ArrayList<>();
    private int[] forbiddenList = {0, 2, 5, 7, 10, 15, 17, 20, 22, 25, 30, 33, 35, 36};


    public ServerNew(int port) throws IOException {

        populateGameFields();

        clientList = new ArrayList<ConnectionToClient>();
        messages = new LinkedBlockingQueue<Object>();
        serverSocket = new ServerSocket(port);

        Thread accept = new Thread(() -> {

            while (true) {

                try {

                    System.out.println("Server up & running");

                    Socket s = serverSocket.accept();
                    clientList.add(new ConnectionToClient(s));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //accept.setDaemon(true);
        accept.start();

        Thread messageHandling = new Thread(() -> {

            while (true) {

                try {

                    Object message = messages.take();

                    System.out.println("Server: " + message.toString());

                    TCP_Message receivedMessage = (TCP_Message) message;

                    switch (receivedMessage.getAction()) {

                        case TCP_Message.ACTION_NEW_PLAYER:

                            if(gameStarted) {
                                sendToAll(new TCP_Message(TCP_Message.ACTION_GAME_ONGOING));
                                break;
                            }

                            System.out.println("New player added");
                            Player tempPlayer = new Player(receivedMessage.getMessageString());
                            int newPlayerID = playersIDCounter++;
                            tempPlayer.setPlayerID(newPlayerID);

                            players.add(tempPlayer);

                            sendToAll(new TCP_Message(TCP_Message.ACTION_SET_PLAYER_ID, newPlayerID));
                            System.out.println("New playerID sent to all clients");

                            if(players.size() > 1) {
                                gameStarted = true;
                                // Set player 0 as current player
                                playingPlayerID = 0;
                                sendToAll(new TCP_Message(TCP_Message.ACTION_GAME_START, playingPlayerID));
                            }

                            break;

                        case TCP_Message.ACTION_ROLL_DICE:

                            Player dicePlayer = new Player(receivedMessage.getMessageString());
                            dicePlayer.setPlayerPos(dicePlayer.getPlayerPos() + receivedMessage.getMessageInt());

                            System.out.println("Server player POS:" + dicePlayer.getPlayerPos());
                            System.out.println("Server player POS after move: " + (dicePlayer.getPlayerPos() + receivedMessage.getMessageInt()));

                            sendToAll(new TCP_Message(TCP_Message.ACTION_ROLL_DICE, receivedMessage.getMessageInt(), dicePlayer.playerToString()));
                            break;

                        case TCP_Message.ACTION_BUY:

                            Player buyPlayer = new Player(receivedMessage.getMessageString());
                            boolean forbidden = false;

                            for(int i : forbiddenList)
                                if(i == buyPlayer.getPlayerPos())
                                    forbidden = true;

                            if(!boughtFields.contains(buyPlayer.getPlayerPos()) && !forbidden ) {

                                boughtFields.add(buyPlayer.getPlayerPos());
                                sendToAll(new TCP_Message(TCP_Message.ACTION_BUY, buyPlayer.playerToString()));
                            }
                            break;

                        case TCP_Message.ACTION_ENDGAME:

                            sendToAll(new TCP_Message(TCP_Message.ACTION_ENDGAME, receivedMessage.getMessageString()));
                            break;

                        default:
                            // do nothing
                            break;

                    }


                } catch (InterruptedException e) {
                }
            }
        });

        //messageHandling.setDaemon(true);
        messageHandling.start();
    }

    private class ConnectionToClient {

        ObjectInputStream in;
        ObjectOutputStream out;
        Socket socket;
        int connectionID;

        ConnectionToClient(Socket socket) throws IOException {
            this.socket = socket;
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            Thread read = new Thread() {
                public void run() {

                    while (true) {

                        try {

                            Object obj = in.readObject();
                            messages.put(obj);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

            //read.setDaemon(true); // terminate when main ends
            read.start();
        }

        public void write(Object obj) {
            try {
                out.writeObject(obj);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void assignConnectionID(int mID) {
            this.connectionID = mID;
        }

    }

    public void sendToOne(int index, Object message) throws IndexOutOfBoundsException {
        clientList.get(index).write(message);
    }

    public void sendToAll(Object message) {

        for (ConnectionToClient client : clientList)
            client.write(message);

    }

    private void populateGameFields() {

        fields.put(new BoardXY(10, 10), new PropertyField(0,523230,"START"));
        fields.put(new BoardXY(10, 9), new PropertyField(1,0,"OLD KENT ROAD"));
        fields.put(new BoardXY(10, 8), new PropertyField(2,536606,"COMMUNITY CHEST"));
        fields.put(new BoardXY(10, 7), new PropertyField(3,0,"WHITECHAPEL ROAD"));
        fields.put(new BoardXY(10, 6), new PropertyField(4,0,"INCOME TAX"));
        fields.put(new BoardXY(10, 5), new PropertyField(5,0,"MARYLEBONE STATION"));
        fields.put(new BoardXY(10, 4), new PropertyField(6,741384,"THE ANGEL ISLINGTON"));
        fields.put(new BoardXY(10, 3), new PropertyField(7,0,"CHANCE"));
        fields.put(new BoardXY(10, 2), new PropertyField(8,677365,"EUSTON ROAD"));
        fields.put(new BoardXY(10, 1), new PropertyField(9,820695,"PENTONVILLE ROAD"));
        fields.put(new BoardXY(10, 0), new PropertyField(10,0,"IN JAIL"));

        fields.put(new BoardXY(9, 0), new PropertyField(11,1169667,"PALL MALL"));
        fields.put(new BoardXY(8, 0), new PropertyField(12,592,"ELECTRIC COMPANY"));
        fields.put(new BoardXY(7, 0), new PropertyField(13,1691877,"WHITEHALL"));
        fields.put(new BoardXY(6, 0), new PropertyField(14,569819,"NORTHUMBERLAND ROAD"));
        fields.put(new BoardXY(5, 0), new PropertyField(15,0,"FENCHURCH ST. STATION"));
        fields.put(new BoardXY(4, 0), new PropertyField(16,1330708,"BOW STREET"));
        fields.put(new BoardXY(3, 0), new PropertyField(17,0,"COMMUNITY CHEST"));
        fields.put(new BoardXY(2, 0), new PropertyField(18,1300242,"MALBOROUGH STREET"));
        fields.put(new BoardXY(1, 0), new PropertyField(19,1970445,"VINE STREET"));

        fields.put(new BoardXY(0, 0), new PropertyField(20,0,"FREE PARKING"));
        fields.put(new BoardXY(0, 1), new PropertyField(21,1299028,"STRAND"));
        fields.put(new BoardXY(0, 2), new PropertyField(22,0,"CHANCE"));
        fields.put(new BoardXY(0, 3), new PropertyField(23,550000,"FLEET STREET"));
        fields.put(new BoardXY(0, 4), new PropertyField(24,1417206,"TRAFALGAR SQUARE"));
        fields.put(new BoardXY(0, 5), new PropertyField(25,0,"KINGS CROSS STATION"));
        fields.put(new BoardXY(0, 6), new PropertyField(26,1687283,"LEICESTER SQUARE"));
        fields.put(new BoardXY(0, 7), new PropertyField(27,1611725,"COVENTRY STREET"));
        fields.put(new BoardXY(0, 8), new PropertyField(28,385,"WATER WORKS"));
        fields.put(new BoardXY(0, 9), new PropertyField(29,2158667,"PICADILLY"));
        fields.put(new BoardXY(0, 10), new PropertyField(30,0,"GO TO JAIL"));

        fields.put(new BoardXY(1, 10), new PropertyField(31,2059589,"REGENT STREET"));
        fields.put(new BoardXY(2, 10), new PropertyField(32,1842053,"OXFORD STREET"));
        fields.put(new BoardXY(3, 10), new PropertyField(33,0,"COMMUNITY CHEST"));
        fields.put(new BoardXY(4, 10), new PropertyField(34,1922975,"BOND STREET"));
        fields.put(new BoardXY(5, 10), new PropertyField(35,0,"LIVERPOOL ST. STATION"));
        fields.put(new BoardXY(6, 10), new PropertyField(36,0,"CHANCE"));
        fields.put(new BoardXY(7, 10), new PropertyField(37,1275752,"PARK LANE"));
        fields.put(new BoardXY(8, 10), new PropertyField(38,1300242,"SUPER TAX"));
        fields.put(new BoardXY(9, 10), new PropertyField(39,3092166,"MAYFAIR"));

    }
}