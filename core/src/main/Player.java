package main;

import Tiles.Property;
import com.badlogic.gdx.graphics.g2d.Sprite;
import misc.Coordinate;

import java.util.ArrayList;

/**
 * PLayer is a class that represents the players of the game. It has functionality for all basic functions a player
 * is able to carry out. A list of players is initialised in GameSetUpScreen.
 */
public class Player implements PlayerInterface {

    private ArrayList<Property> properties;
    private ArrayList<Property> mortgagedProperties;
    private int balance;
    private int tilePosition;
    private int getOutJailCards;
    private boolean isInJail;

    private Sprite gameToken;
    private String name;
    private boolean firstLap;
    private Coordinate currentCoordinates;

    /**
     * The constructor for Player
     * @param name a String that is the name of the player
     * @param token a Sprite that is the game token that represents the player in the GUI
     */
    public Player(String name, Sprite token) {
        this.name = name;
        this.gameToken = token;
        setInJail(false);
        properties = new ArrayList<>();

        getOutJailCards = 0;
        balance = 1500;
        firstLap = true;
    }

    public void addGetOutOfJailFreeCard(){

        getOutJailCards +=1;


    }

    public void removeGetOutOfJailFreeCard(){

        if(getOutJailCards > 0) {

            getOutJailCards -= 1;

        }

    }


    /**
     * getName returns the name of a player as a String
     * @return a String representation of the player's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * getPlayerToken will return the Sprite object associated with that player that represents the game piece
     * @return returns the Sprite gameToken
     */
    @Override
    public Sprite getPlayerToken() {
        return gameToken;
    }

    /**
     * setPlayerToken takes a Sprite object and sets the player's token as that Sprite object. Used for GUI display
     * @param token the Sprite object to be set as the player's token
     */
    @Override
    public void setPlayerToken(Sprite token) {
        gameToken = token;
    }

    /**
     * setTilePosition takes an Integer representation of the position of the player on the board and sets that value as
     * tilePosition
     * @param i an Integer representation of where the player is on the board
     */
    public void setTilePosition(int i) {
        tilePosition = i;
    }

    /**
     * getTilePosition returns the Integer representation of the player position on the board
     * @return an Integer representation of where the player is on the board
     */
    public int getTilePosition() {
        return tilePosition;
    }

    /**
     * getProperties returns an array list with all the properties that the player owns
     * @return returns properties, ArrayList with all the properties player owns
     */
    @Override
    public ArrayList<Property> getProperties() {
        return properties;
    }

    public int getTotalPropertyValue() {
        int sum = 0;
        for(Property p: getProperties()) {
            sum += p.getCost();
        }
        return sum;
    }

    /**
     * addProperties adds a Property object to the array list of Property Tile objects
     */
    @Override
    public void addProperty(Property property) {
        property.addPlayer(this);
        properties.add(property);
    }

    public void removeProperty(Property property) {
        property.removePlayer(this);
        properties.remove(property);
    }

    /**
     * getMoney returns an integer that represents the current balance of the player (how much money they has)
     * @return returns balance as an integer
     */
    @Override
    public int getMoney() {
        return balance;
    }

    /**
     * setMoney takes an amount of money represented by an integer and sets that as the player's balance
     */
    @Override
    public void setMoney(int amount) {
        balance = amount;
    }

    /**
     * payPlayer takes an amount of money represented by an integer and adds this to the player's balance
     */
    @Override
    public void payPlayer(int amount) {
        balance += amount;
    }

    /**
     * makePurchase takes a cost represented as an integer and subtracts this from the player's balance
     * @param cost an Integer representation of the cost of a purchase
     */
    @Override
    public void makePurchase(int cost) {
        balance -= cost;
    }

    /**
     * getOutOfJailFree will return true if player has at least one card and false otherwise
     * @return returns true or false depending if "get out of jail" card is present or not
     */
    @Override
    public boolean hasGetOutOfJailFree() {
        if(getOutJailCards > 0) {
            getOutJailCards--;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * setInJail takes a Boolean parameter that defines whether or not a player is in jail
     * @param isInJail true if player is in jail, false otherwise
     */
    public void setInJail(boolean isInJail) {
        this.isInJail = isInJail;
    }

    /**
     * getIsInJail returns a Boolean judgement as to whether a player is in jail or not
     * @return true if player is in jail, false otherwise
     */
    public boolean getIsInJail() {
        return isInJail;
    }

    /**
     * endFirstLap sets firstLap: a Boolean representation of whether or not the player has gone around the board once,
     * as false.
     */
    @Override
    public void endFirstLap() {
        firstLap = false;
    }

    /**
     * getFirstLap returns a Boolean judgement as to whether the player has gone around the board once
     * @return true if the player is on their first lap, false otherwise
     */
    @Override
    public boolean getFirstLap() {
        return firstLap;
    }

    /**
     * getCurrentCoordinates returns a Coordinate object that represents the coordinates of a player on the board
     * @return a Coordinate object that represents the coordinates of a player on a board
     */
    public Coordinate getCurrentCoordinates() {
        return currentCoordinates;
    }

    /**
     * setCurrentCoordinates takes Coordinate object that represents the new coordinates of a player and sets the
     * player's current coordinates to be that Coordinate object
     * @param currentCoordinate a Coordinate object that represents the new coordinates of a player
     */
    public void setCurrentCoordinates(Coordinate currentCoordinate) {
        this.currentCoordinates = currentCoordinate;
    }


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
//
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
//
//    boolean getIsBankrupt(int cost){
//        if (balance < cost){
//            int subcost = 0;
//            for(int i = 0; i<properties.size();i++){
//            // Need to implement properties first
//                subcost += properties[i].
//            }
//        }
//        else{
//            return false;
//        }
//    }
}
