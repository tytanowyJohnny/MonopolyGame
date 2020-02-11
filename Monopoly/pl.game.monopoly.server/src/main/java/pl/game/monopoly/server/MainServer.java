package pl.game.monopoly.server;

import java.io.IOException;


public class MainServer {

    public static void main(String[] args) {

        try {

            // Start server
            ServerNew serverInstance = new ServerNew(4848);

        } catch(IOException e) {

            e.printStackTrace();

        }


//        try (ServerSocket serverSocket = new ServerSocket(6666)) {
//            System.out.println("Server started...");
//            ExecutorService pollServers = Executors.newFixedThreadPool(20);
////            int i= 0;
////            LinkedHashMap<String, Pair<PlayerHandler, Player>> players = new LinkedHashMap<>();
////            ExecutorService pollHandlers = Executors.newFixedThreadPool(4);
////            while(true){
////                i++;
////                Player playeros = new Player();
////                PlayerHandler playerHandler = new PlayerHandler(serverSocket.accept(), playeros);
////                pollHandlers.execute(playerHandler);
////                players.put("ONE", new Pair<>(playerHandler, playeros) );
////
////                if(i % 4 == 0){
////                    GameServer gameServer = new GameServer(players);
////                    pollServers.execute(gameServer);
////                    players = new LinkedHashMap<>();
////                }
////            }
//            while (true) {
//                LinkedHashMap<String, Pair<PlayerHandler, Player>> players = new LinkedHashMap<>();
//                Player playeros = new Player();
//                players.put("ONE", new Pair<>(new PlayerHandler(serverSocket.accept(), playeros), playeros) );
//                 playeros = new Player();
//                players.put("TWO", new Pair<>(new PlayerHandler(serverSocket.accept(), playeros), playeros) );
//                playeros = new Player();
//                players.put("THREE", new Pair<>(new PlayerHandler(serverSocket.accept(), playeros), playeros) );
//                playeros = new Player();
//                players.put("FOUR", new Pair<>(new PlayerHandler(serverSocket.accept(), playeros), playeros) );
//                GameServer gameServer = new GameServer(players);
//                pollServers.execute(gameServer);
//                System.out.println("Serwer został uruchomiony");
//            }
//        } catch (IOException e) {
//            System.out.println("Serwer nie został uruchomiony: " + e.getMessage());
//        }
    }
}




