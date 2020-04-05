package main;

import Tiles.Property;
import Tiles.Tile;

import java.util.ArrayList;

public class Player implements PlayerInterface {
    private ArrayList<Tile> properties;
    private ArrayList<Tile> morgagedProperties;
    private int balance;
//    private Tile position;
//    private int tilePosition;
    private int getOfJailCards;
    private String gameToken;
    private boolean firstLap;

    public Player(){
        getOfJailCards = 0;
        balance = 1500;
//        tilePosition = 0;
        firstLap = true;
}

    /**
     * getProperties returns an array list with all the properties that the player owns
     * @return returns properties, ArrayList with all the properties player owns
     */
    @Override
    public ArrayList<Tile> getProperties(){
        return properties;
    }

    /**
     * getProperties returns an array list with all the properties that the player owns
     * @return returns properties, ArrayList with all the properties player owns
     */
    @Override
    public void addProperty(Property property){
        properties.add(property);
    }



    /**
     * getMoney returns an integer that represents the current balance of the player (how much money he has)
     * @return returns balance as an integer
     */


    @Override
    public int getMoney(){
        return balance;
    }



    /**
     * getPlayerTile will return the object tile that the player is currently standing on
     * @return returns position as an tile object
     */
    @Override
    public void setMoney(int value){
        balance = value;
    }

//
//    /**
//     * getPlayerTile will return the object tile that the player is currently standing on
//     * @return returns position as an tile object
//     */
//    @Override
//    public Tile getPlayerTile(){
//        return position;
//    }
//
//    /**
//     * getPlayerTile will return the object tile that the player is currently standing on
//     * @return returns position as an tile object
//     */
//    @Override
//    public void setPlayerTile(Tile tile){
//
//        position = tile;
//    }

//    /**
//     * getPlayerTile will return the object tile that the player is currently standing on
//     * @return returns position as an tile object
//     */
//    @Override
//    public int getPlayerPosition(){
//        return tilePosition;
//    }
//
//    /**
//     * getPlayerTile will return the object tile that the player is currently standing on
//     * @return returns position as an tile object
//     */
//    @Override
//    public void setPlayerPosition(int position){
//
//        this.tilePosition = position;
//    }


    /**
     * getOutOfJailFree will return true if player has at least one card and false otherwise
     * @return returns true or false depending if "get out of jail" card is present or not
     */
    @Override
    public boolean getOutOfJailFree(){
        if(getOfJailCards > 0){
            getOfJailCards--;
            return true;
        }
        else{
            return false;
        }
    }

    /*boolean getIsBankrupt(int cost){
        if (balance < cost){
            int subcost = 0;
            for(int i = 0; i<properties.size();i++){
            // Need to implement properties first
                subcost += properties[i].
            }
        }
        else{
            return false;
        }
    }*/

    /**
     * getPlayerToken will return the game piece that the player is using in the current game
     * @return returns gameToken as a string
     */
    @Override
    public String getPlayerToken(){
        // This needs work depending on how we want to implement game pieces
        return gameToken;
    }

    /**
     * setPlayerToken will set a token to a player that will be used to identify the player during the game
     * @param token will be used to represent player during the game
     */
    @Override
    public void setPlayerToken(String token){

        gameToken = token;


    }

    /**
     * payPlayer will add money that other player payed them to their balance.
     * @param amount will be used to specify how much money the player will receive
     */
    @Override
    public void payPlayer(int amount){

        balance += amount;


    }

    /**
     * endFirstLap sets firstLap variable to false for the current player to indicate that the player did
     * one lap around the board.
     */
    @Override
    public void endFirstLap(){firstLap = false;}

    /**
     * getFirstLap returns a boolean value that represents if the current player went once around the board or not.
     * @return returns firstLap, boolean value that represents if player completed one lap around the game-board
     */
    @Override
    public boolean getFirstLap(){return firstLap;}

    /**
     * chargePlayer will take away the cost  from the the players money balance
     * @param cost will be used to specify how much player needs to pay
     */
    @Override
    public void chargePlayer(int cost){
        balance -= cost;
    }
}
