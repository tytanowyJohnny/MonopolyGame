package pl.game.monopoly.core;

import java.io.Serializable;

public class TCP_Message implements Serializable {

    public static final String ACTION_NEW_PLAYER = "new_player";
    public static final String ACTION_SET_PLAYER_ID = "set_player_id";
    public static final String ACTION_GAME_START = "game_start";
    public static final String ACTION_GAME_ONGOING = "game_ongoing";
    public static final String ACTION_ROLL_DICE = "roll_dice";
    public static final String ACTION_BUY = "buy";
    public static final String ACTION_ENDGAME = "end_game";

    private String messageString;
    private String action;
    private int messageInt;

    public TCP_Message(String mAction, String mPlayerString) {

        this.action = mAction;
        this.messageString = mPlayerString;

    }

    public TCP_Message(String mAction, int mPlayerID) {

        this.action = mAction;
        this.messageInt = mPlayerID;
    }

    public TCP_Message(String mAction) {

        this.action = mAction;
    }

    public TCP_Message(String mAction, int mInt, String mString) {

        this.action = mAction;
        this.messageInt = mInt;
        this.messageString = mString;

    }

    public String getMessageString() {
        return messageString;
    }

    public void setMessageString(String messageString) {
        this.messageString = messageString;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getMessageInt() {
        return messageInt;
    }
}
