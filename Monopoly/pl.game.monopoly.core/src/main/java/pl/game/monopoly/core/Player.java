package pl.game.monopoly.core;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {

    private final String[] playerColor = {"BLACK", "RED", "PURPLE", "GREEN"};

    private int playerID = -1;
    private int money = 100000000;
    private ArrayList<Field> ownedFields = new ArrayList<>();
    private int playerPos = 0;

    public Player() {}

    public Player(String playerString) {

        String[] playerFields = playerString.split(";");

        System.out.println("playerFields[0]: " + playerFields[0]);

        this.playerID = Integer.parseInt(playerFields[0]);
        this.money = Integer.parseInt(playerFields[1]);
        this.playerPos = Integer.parseInt(playerFields[2]);

        for(int i = 3; i < playerFields.length; i++) {
            // Dummy values for now
            ownedFields.add(new Field(Integer.parseInt(playerFields[i])));
        }

    }

    public void addMoney(int cash) {
        this.money += cash;
    }

    public boolean takeMoney(int cash) {
        if (this.money > cash) {
            this.money -= cash;
            return true;
        } else {
            this.money = 0;
            return false;
        }
    }

    public void addField(Field field){
        this.ownedFields.add(field);
    }

    public void setPlayerID(int mPlayerID) {
        this.playerID = mPlayerID;
    }

    private String ownedFieldsToString() {

        StringBuilder stringBuilder = new StringBuilder();

        for(Field field : ownedFields) {
            stringBuilder.append(field.getID()).append(";");
        }

        return stringBuilder.toString();
    }

    public String playerToString() {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(playerID).append(";").append(money).append(";").append(playerPos).append(";").append(ownedFieldsToString());

        return stringBuilder.toString();

    }

    public int getPlayerID() {

        return this.playerID;
    }

    public String[] getPlayerColor() {
        return playerColor;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public ArrayList<Field> getOwnedFields() {
        return ownedFields;
    }

    public void setOwnedFields(ArrayList<Field> ownedFields) {
        this.ownedFields = ownedFields;
    }

    public int getPlayerPos() {
        return playerPos;
    }

    public void setPlayerPos(int playerPos) {
        this.playerPos = playerPos;
    }
}
