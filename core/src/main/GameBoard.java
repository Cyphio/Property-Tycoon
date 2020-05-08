package main;

import Tiles.*;
import misc.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *  The GameBoard class simulates the board and all the physical aspects of the board such as player position, cards and the dice.
 */
public class GameBoard implements GameBoardInterface {

    private static Tile[] board;
    private static ArrayList<Card> potluckCards;
    private static ArrayList<Card> opportunityKnocksCards;
    private static Dice dice;
    private static Map<Player, Integer> playerPos;
    private static Player currentPlayer;
    private int goPayoutAmount;
    private Player[] players;
    private Map<String, ArrayList<Property>> colPropMap;
    private ArrayList<Property> developedProperties;
    private int lastD1Rolled;
    private int lastD2Rolled;

    /**
     * The GameBoard class constructor
     * @param players Holds each player object who is in the game
     */
    public GameBoard(Player[] players) {
        goPayoutAmount = 200;
        playerPos = new HashMap<Player, Integer>();
        this.players = players;

        // sets all players position to GO tile at 0
        for (Player player : players) {
            playerPos.put(player, 0);
        }

        ConfigTranslator builder = new ConfigTranslator(); // This ceases to exist after initialization

        board = builder.getTiles();

        System.out.println(builder.getAllColours());

        System.out.println(board.length);

        colPropMap = new HashMap<>();
        for(String colour : builder.getAllColours()) {
            ArrayList<Property> properties = new ArrayList<>();
            for(Tile tile : board) {
                if(tile instanceof Property && ((Property) tile).getColourAsString().equals(colour)) {
                    properties.add((Property) tile);
                }
            }
            colPropMap.put(colour, properties);
        }

        ConfigValidator validator = new ConfigValidator(board);
        validator.validate(board);

        dice = new Dice();

        potluckCards = builder.getPotluckChestCards();
        Collections.shuffle(potluckCards);
        opportunityKnocksCards = builder.getOpportunityCards();
        Collections.shuffle(opportunityKnocksCards);

        System.out.println(players.length);
        System.out.println(players[0].getName());

        developedProperties = new ArrayList<>();

    }

    public Map<String, ArrayList<Property>> getColPropMap() {
        return colPropMap;
    }

    /**
     * handles dice rolling and landing on tile functions.
     * @param player the player to take the turn
     * @return returns checkBoardCircumstances()
     */
    @Override
    public Boolean playerTurn(Player player) {
        System.out.println("\nBOARD PLAYER TURN");

        currentPlayer = player;

        dice.rollDice();
        System.out.println("DOUBLE: " + dice.wasItADouble());
        if(player.getIsInJail()) {
            if(dice.wasItADouble() || player.getOutOfJailFree()) {
                player.setInJail(false);
                movePlayer(player, dice.getValue());
            }
        }
        else {
            movePlayer(player, dice.getValue());
        }

        System.out.println("finished");
        lastD1Rolled = dice.getD1();
        lastD2Rolled = dice.getD2();
        return checkBoardCircumstances();
    }

    public int getLastD1() {
        return lastD1Rolled;
    }

    public int getLastD2() {
        return lastD2Rolled;
    }

    /**
     * getPlayerPos is used to return the current position of any given player
     * @param player the player who's position is being searched
     * @return the position of Player player
     */
    @Override
    public int getPlayerPos(Player player) {
        return playerPos.get(player);
    }

    /**
     * setPlayerPos is used to set a players position to a point given
     * @param player The player to move
     * @param pos    Where to move the player
     */
    @Override
    public void setPlayerPos(Player player, int pos) {
        board[playerPos.get(player)].removePlayer(player);
        playerPos.put(player, pos);
        board[pos].addPlayer(player);
        System.out.println("Setting Player coordinates");

        Tile tile = board[playerPos.get(player)];

        if(player.getIsInJail() && pos == 10 && tile instanceof Jail){

            player.setCurrentCoordinates((((Jail) tile).getNextJailCoordinate()));

        }else{

        player.setCurrentCoordinates(tile.getAvailableCoordinates());

        }}

    /**
     * movePlayer uses context to tell how far a player should move, and what space to move them to
     * @param player The player to move
     * @param moves  how many spaces to move the player
     */
    @Override
    public void movePlayer(Player player, int moves) {
        int position = getPlayerPos(player);
        int moveTo = position + moves;

        if (moveTo > 39) {
            //change this based on go tile amount set (for now 200)
            player.payPlayer(goPayoutAmount);
            this.setPlayerPos(player, moveTo - 40);

            if (moveTo < 0) {
                this.setPlayerPos(player, moveTo + 40);
            }
        }
        else {
            this.setPlayerPos(player, moveTo);
        }
    }

    /**
     * gets the tile at int i
     * @param i tile position
     * @return tile at int i
     */
    public Tile getTile(int i) {
        return board[i];
    }

    /**
     * sends the current player to the jail tile and sets their inJail to true
     */
    public void sendToJail(Player player) {
        player.setInJail(true);


        setPlayerPos(player, 10);
    }

    /**
     * Checks to see what tile the player is on and handles their functionality
     * also checks if doubles are rolled to roll again.
     * @return returns true if the last roll was a double
     */
    @Override
    public Boolean checkBoardCircumstances() {
        Tile x = board[playerPos.get(currentPlayer)];

        if (x instanceof OpportunityKnocks) {

            Card card = opportunityKnocksCards.remove(0);
            performCardAction(card);
            opportunityKnocksCards.add(card);
        }
        else if(x instanceof Property){
                if(((Property) x).getOwned() && !((Property) x).getMortgaged() && !((Property) x).getOwner().getIsInJail()){
                    if(((Property) x).getColourAsString().equals("WHITE")){
                        int i = 0;
                        for(Property checkStation : ((Property) x).getOwner().getProperties()){
                            if(checkStation.getColourAsString().equals("WHITE")){
                                i++;
                            }

                        }
                        currentPlayer.makePurchase(i*50);
                        ((Property) x).getOwner().payPlayer(i*50);
                    }

                else{
                    currentPlayer.makePurchase(((Property) x).getCurrentRent());
                    ((Property) x).getOwner().payPlayer(((Property) x).getCurrentRent());
                }

            }
        }
        else if (x instanceof PotLuck) {
            Card card = potluckCards.remove(0);
            performCardAction(card);
            potluckCards.add(card);
        }
        else if (x instanceof GoToJail) {
            sendToJail(currentPlayer);
        }
        else if (x instanceof Go) {
            currentPlayer.payPlayer(goPayoutAmount);
        }
        if (dice.jailCheck()) {
            sendToJail(currentPlayer);
        }
        else if (dice.wasItADouble()) {
            return true;
        }

        dice.reset();
        return false;
    }

    /**
     * handles the action that each card should perform.
     * @param card the card who's action is being performed
     */
    @Override
    public void performCardAction(Card card) {
        switch (card.getAction()) {
            case "pay": // Bank pays player
            case "inherit" : // Bank pays player - Inherits
            case "student loan": // Student loan - bank pays player
            case "bank error": // Bank error - bank pays player
            case "sale": // Sale - bank pays player
            case "savings": // Savings - bank pays player

                currentPlayer.payPlayer(card.getValue());
                break;
            case "go to": // Go back to Crapper Street
                currentPlayer.setTilePosition(card.getValue());
                break;
            case "bill"://Player pays bill
            case "late": // Player pays bank
                currentPlayer.makePurchase(card.getValue());
                break;
            case "advance":
                currentPlayer.setTilePosition(0);
                break;
            case "jail": // sends player to jail
                sendToJail(currentPlayer);
                break;
            case "birthday":// Each player pays current player
                for (Player player: players) {

                    if(player != currentPlayer) {
                        player.makePurchase(card.getValue());
                        currentPlayer.payPlayer(card.getValue());
                    }
                }
                break;
            case "get out of jail free":
                currentPlayer.addGetOutOfJailFreeCard();
                break;
            case "insurance":
                currentPlayer.makePurchase(card.getValue());
                ((FreeParking) board[20]).addToPot(card.getValue());
                break;

            default:
                System.out.println("no action found");
        }
    }

    /**
     * assigns a property to a player and makes them pay for it.
     * @param player the player purchasing the property
     * @param prop the property purchased
     */
    @Override
    public void purchaseProperty(Player player, Property prop) {
        if (player.getFirstLap() == false) {
            if (player.getMoney() >= prop.getCost()) {
                prop.buy();
                player.makePurchase(prop.getCost());
                player.addProperty(prop);
            }
        }
    }


    public void checkForDevelopedProperties(){

        developedProperties.clear();

        for (Tile tile : board){

            if (tile instanceof Property) {
                 if (((Property) tile).getHousesOwned() > 0){
                     developedProperties.add((Property) tile);
                 }
            }
        }
    }

    public ArrayList<Property> getDevelopedProperties(){

        return developedProperties;

    }

    public Dice getDice() { return dice; }


}