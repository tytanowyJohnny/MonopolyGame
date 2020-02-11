package pl.game.monopoly.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.game.monopoly.client.controllers.GameBoardController;
import pl.game.monopoly.core.BoardXY;
import pl.game.monopoly.core.Player;

import java.util.ArrayList;

public class MainApp extends Application {

    private static Stage primaryStage;
    public static Stage getPrimStage(){return primaryStage;}

    public static Player clientPlayer;
    public static ClientNew clientConnection;

    public static GameBoardController clientGameBoardController;
    public static ArrayList<BoardXY> boardXYList = new ArrayList<>();
    
    public static BoardXY BoardXY_10_10 = new BoardXY(10, 10);
    public static BoardXY BoardXY_10_9 = new BoardXY(10, 9);
    public static BoardXY BoardXY_10_8 = new BoardXY(10, 8);
    public static BoardXY BoardXY_10_7 = new BoardXY(10, 7);
    public static BoardXY BoardXY_10_6 = new BoardXY(10, 6);
    public static BoardXY BoardXY_10_5 = new BoardXY(10, 5);
    public static BoardXY BoardXY_10_4 = new BoardXY(10, 4);
    public static BoardXY BoardXY_10_3 = new BoardXY(10, 3);
    public static BoardXY BoardXY_10_2 = new BoardXY(10, 2);
    public static BoardXY BoardXY_10_1 = new BoardXY(10, 1);
    public static BoardXY BoardXY_10_0 = new BoardXY(10, 0);
    
    public static BoardXY BoardXY_9_0 = new BoardXY(9, 0);
    public static BoardXY BoardXY_8_0 = new BoardXY(8, 0);
    public static BoardXY BoardXY_7_0 = new BoardXY(7, 0);
    public static BoardXY BoardXY_6_0 = new BoardXY(6, 0);
    public static BoardXY BoardXY_5_0 = new BoardXY(5, 0);
    public static BoardXY BoardXY_4_0 = new BoardXY(4, 0);
    public static BoardXY BoardXY_3_0 = new BoardXY(3, 0);
    public static BoardXY BoardXY_2_0 = new BoardXY(2, 0);
    public static BoardXY BoardXY_1_0 = new BoardXY(1, 0);

    public static BoardXY BoardXY_0_0 = new BoardXY(0, 0);
    public static BoardXY BoardXY_0_1 = new BoardXY(0, 1);
    public static BoardXY BoardXY_0_2 = new BoardXY(0, 2);
    public static BoardXY BoardXY_0_3 = new BoardXY(0, 3);
    public static BoardXY BoardXY_0_4 = new BoardXY(0, 4);
    public static BoardXY BoardXY_0_5 = new BoardXY(0, 5);
    public static BoardXY BoardXY_0_6 = new BoardXY(0, 6);
    public static BoardXY BoardXY_0_7 = new BoardXY(0, 7);
    public static BoardXY BoardXY_0_8 = new BoardXY(0, 8);
    public static BoardXY BoardXY_0_9 = new BoardXY(0, 9);
    public static BoardXY BoardXY_0_10 = new BoardXY(0, 10);

    public static BoardXY BoardXY_1_10 = new BoardXY(1, 10);
    public static BoardXY BoardXY_2_10 = new BoardXY(2, 10);
    public static BoardXY BoardXY_3_10 = new BoardXY(3, 10);
    public static BoardXY BoardXY_4_10 = new BoardXY(4, 10);
    public static BoardXY BoardXY_5_10 = new BoardXY(5, 10);
    public static BoardXY BoardXY_6_10 = new BoardXY(6, 10);
    public static BoardXY BoardXY_7_10 = new BoardXY(7, 10);
    public static BoardXY BoardXY_8_10 = new BoardXY(8, 10);
    public static BoardXY BoardXY_9_10 = new BoardXY(9, 10);


    public static boolean gameActive = false;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        Scene scene = new Scene(root);

        stage.setTitle("MONOPOLY");
        stage.setScene(scene);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.show();

        boardXYList.add(BoardXY_10_10);
        boardXYList.add(BoardXY_10_9);
        boardXYList.add(BoardXY_10_8);
        boardXYList.add(BoardXY_10_7);
        boardXYList.add(BoardXY_10_6);
        boardXYList.add(BoardXY_10_5);
        boardXYList.add(BoardXY_10_4);
        boardXYList.add(BoardXY_10_3);
        boardXYList.add(BoardXY_10_2);
        boardXYList.add(BoardXY_10_1);
        boardXYList.add(BoardXY_10_0);

        boardXYList.add(BoardXY_9_0);
        boardXYList.add(BoardXY_8_0);
        boardXYList.add(BoardXY_7_0);
        boardXYList.add(BoardXY_6_0);
        boardXYList.add(BoardXY_5_0);
        boardXYList.add(BoardXY_4_0);
        boardXYList.add(BoardXY_3_0);
        boardXYList.add(BoardXY_2_0);
        boardXYList.add(BoardXY_1_0);

        boardXYList.add(BoardXY_0_0);
        boardXYList.add(BoardXY_0_1);
        boardXYList.add(BoardXY_0_2);
        boardXYList.add(BoardXY_0_3);
        boardXYList.add(BoardXY_0_4);
        boardXYList.add(BoardXY_0_5);
        boardXYList.add(BoardXY_0_6);
        boardXYList.add(BoardXY_0_7);
        boardXYList.add(BoardXY_0_8);
        boardXYList.add(BoardXY_0_9);
        boardXYList.add(BoardXY_0_10);

        boardXYList.add(BoardXY_1_10);
        boardXYList.add(BoardXY_2_10);
        boardXYList.add(BoardXY_3_10);
        boardXYList.add(BoardXY_4_10);
        boardXYList.add(BoardXY_5_10);
        boardXYList.add(BoardXY_6_10);
        boardXYList.add(BoardXY_7_10);
        boardXYList.add(BoardXY_8_10);
        boardXYList.add(BoardXY_9_10);
    }



    public static void main(String[] args) {
        launch(args);
    }

}
