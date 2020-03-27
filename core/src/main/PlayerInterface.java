package main;
import Tiles.*;
import java.util.ArrayList;

public interface PlayerInterface {
    ArrayList<Tile> getProperties(); // return properties the player owns

    void addProperty(Property property);

    int getMoney(); // return how much money the player has
    void setMoney(int value);

    boolean getOutOfJailFree(); // return whether the player has GOJF card
    //boolean getIsBankrupt(); // return whether the player is bankrupt
    String getPlayerToken(); // return a string representation of the player's token

    void setPlayerToken(String token);

    void payPlayer(int amount);
}