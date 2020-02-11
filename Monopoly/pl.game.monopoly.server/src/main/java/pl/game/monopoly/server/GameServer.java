//package pl.game.monopoly.server;
//
//import org.javatuples.Pair;
//import pl.game.monopoly.core.*;
//
//import java.util.LinkedHashMap;
//import java.util.TreeMap;
//
//public class GameServer implements Runnable {
//    LinkedHashMap<String, Pair<PlayerHandler, Player>> players;
//    TreeMap<Integer, Pair<Integer, Field>> fields;
//
//
//    public GameServer(LinkedHashMap<String, Pair<PlayerHandler, Player>> players) {
//        this.players = players;
//    }
//
////    public void run() {
////        boolean gameactive = true;
////        initializeHandlers();
////        while(gameactive) {
////            fields = new TreeMap<Integer, Pair<Integer, Field>>();
////            populateFields(fields);
////            sendToAllPlayers(new MessageFromServer(2000, -1,"Game is starting", 0, false, false));
////
////
////
////        }
////    }
////
////    public void initializeHandlers() {
////        for (Pair<PlayerHandler, Player> pair : players.values()) {
////            PlayerHandler handler = pair.getValue0();
////            handler.prepareStreams();
////
////        }
////    }
////
////    public void populateFields(TreeMap<Integer, Pair<Integer, Field>> fields) {
////        fields.put(0, new Pair<>(-1, new StartField(0)));
////        fields.put(1, new Pair<>(-1, new PropertyField(1, 100, "sdasd")));
////        fields.put(2, new Pair<>(-1, new PropertyField(2, 110, "asdasd")));
////        fields.put(3, new Pair<>(-1, new PropertyField(3, 130, "DD")));
////        fields.put(4, new Pair<>(-1, new PropertyField(4, 140, "GGs")));
////        fields.put(5, new Pair<>(-1, new PropertyField(5, 160, "asdasd")));
////        fields.put(6, new Pair<>(-1, new PropertyField(6, 170, "asdasdasd")));
////        fields.put(7, new Pair<>(-1, new PropertyField(7, 200, "DsdaD")));
////        fields.put(8, new Pair<>(-1, new PropertyField(8, 220, "GGsdasd")));
////        fields.put(9, new Pair<>(-1, new PropertyField(9, 230, "asdasd")));
////        fields.put(10, new Pair<>(-1, new PropertyField(10, 250, "asdasda")));
////        fields.put(11, new Pair<>(-1, new PropertyField(11, 300, "DdasdD")));
////        fields.put(12, new Pair<>(-1, new PropertyField(12, 320, "GasdasGs")));
////        fields.put(13, new Pair<>(-1, new PropertyField(13, 340, "asdasd")));
////        fields.put(14, new Pair<>(-1, new PropertyField(14, 500, "asdasd")));
////        fields.put(15, new Pair<>(-1, new PropertyField(15, 600, "DssadasddaD")));
////        fields.put(16, new Pair<>(-1, new PropertyField(16, 660, "GGssdasdadasd")));
////        fields.put(17, new Pair<>(-1, new PropertyField(17, 670, "asdasdasd")));
////        fields.put(18, new Pair<>(-1, new PropertyField(18, 690, "DssadsdadasddaD")));
////        fields.put(19, new Pair<>(-1, new PropertyField(19, 700, "GGssdasdasdasdadasd")));
////    }
////
////    public void sendToAllPlayers(MessageFromServer messageFromServer) {
////        for (Pair<PlayerHandler, Player> pair : players.values()) {
////            PlayerHandler handler = pair.getValue0();
////            handler.sendMessage(messageFromServer);
////
////        }
////    }
////
////    public void sendToPlayer(MessageFromServer messageFromServer, String player){
////        Pair<PlayerHandler, Player> pair = players.get(player);
////        PlayerHandler handler = pair.getValue0();
////        handler.sendMessage(messageFromServer);
////    }
//}
//
//
//
//
