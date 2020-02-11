package pl.game.monopoly.client;


import pl.game.monopoly.client.controllers.GameBoardController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerHandler implements Runnable {
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private GameBoardController gameBoardController;
    private LinkedBlockingQueue<Object> messsagesFromServer;

    public ServerHandler(Socket clientSocket) {
        this.socket = clientSocket;
        messsagesFromServer = new LinkedBlockingQueue<>();

    }

    @Override
    public void run() {
        try {
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Coś poszło nie tak przy strumieniach" + e.getMessage());
        }
        Thread reciver = new Thread(){
            public void run(){
                while (true){

                    try{
                        Object obj = objectInputStream.readObject();
                        messsagesFromServer.put(obj);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
            }
        };

        reciver.setDaemon(true);
        reciver.start();
    }


    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            //to do
        }
    }
}
