package main;

import Tiles.Property;
import Tiles.Tile;
import com.badlogic.gdx.graphics.g2d.Sprite;
import misc.Coordinate;

import java.util.ArrayList;

public class Player implements PlayerInterface{
    private ArrayList<Tile> properties;
    private ArrayList<Tile> morgagedProperties;
    private int balance;
//    private Tile position;
    private int tilePosition;
    private int getOfJailCards;

    private Sprite gameToken;
    private String name;
    private boolean firstLap;
    private Coordinate currentCoordinates;

    public Player(String name, Sprite token){

        this.name = name;
        this.gameToken = token;

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

    public String getName(){

        return this.name;

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
    public Sprite getPlayerToken(){
        // This needs work depending on how we want to implement game pieces
        return gameToken;
    }

    /**
            * getProperties returns an array list with all the properties that the player owns
     * @return returns properties, ArrayList with all the properties player owns
     */
    @Override
    public void setPlayerToken(Sprite token){

        gameToken = token;


    }

    /**
     * getProperties returns an array list with all the properties that the player owns
     * @return returns properties, ArrayList with all the properties player owns
     */
    @Override
    public void payPlayer(int amount){

        balance += amount;


    }



    @Override
    public void endFirstLap(){firstLap = false;}

    @Override
    public boolean getFirstLap(){return firstLap;}

    @Override
    public void makePurchase(int cost){ balance -= cost; }

    public Coordinate getCurrentCoordinates() {
        return currentCoordinates;
    }

    public void setCurrentCoordinates(Coordinate currentCoordinate) {
        this.currentCoordinates = currentCoordinate;
    }

    public void setTilePosition(int i){
        tilePosition = i;
    }

    public int getTilePosition(){
        return tilePosition;
    }


}
