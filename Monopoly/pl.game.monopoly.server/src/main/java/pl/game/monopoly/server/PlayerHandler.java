package pl.game.monopoly.server;



import pl.game.monopoly.core.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class PlayerHandler implements Runnable {
    private Socket socket;
    //output przed input!!!!
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private LinkedBlockingQueue<Object> messsagesFromClient;
    private Player activePlayer;


    public PlayerHandler(Socket socket, Player activePlayer) {
        this.socket = socket;
        this.activePlayer = activePlayer;
    }

    @Override
    public void run() {

    }

    public void sendMessage(Object obj) {
        try {
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage() + " dd");
        }
    }


    public void prepareStreams(){
        messsagesFromClient = new LinkedBlockingQueue<>();

        try {
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
           // this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Error in handler" + e.getMessage());
        }

        Thread reciver = new Thread(){
            public void run(){
                try {
                    objectInputStream = new ObjectInputStream(socket.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                while (true){

                    try{
                        Object obj = objectInputStream.readObject();
                        messsagesFromClient.put(obj);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
            }
        };

        reciver.setDaemon(true);
        reciver.start();
    }

    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
