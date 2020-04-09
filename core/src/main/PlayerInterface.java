package main;
import Tiles.*;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public interface PlayerInterface {
    ArrayList<Tile> getProperties(); // return properties the player owns

    void addProperty(Property property);

    int getMoney(); // return how much money the player has
    void setMoney(int value);

    boolean getOutOfJailFree(); // return whether the player has GOJF card
    //boolean getIsBankrupt(); // return whether the player is bankrupt
    Sprite getPlayerToken(); // return a string representation of the player's token

    void setPlayerToken(Sprite token);

    void payPlayer(int amount);
}