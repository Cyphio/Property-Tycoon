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
    private ArrayList<Player> players;
    private Map<String, ArrayList<Ownable>> identityPropMap;
    private ArrayList<Property> developedProperties;
    private int lastD1Rolled;
    private int lastD2Rolled;

    /**
     * The GameBoard class constructor
     * @param players Holds each player object who is in the game
     */
    public GameBoard(ArrayList<Player> players) {
        goPayoutAmount = 200;
        playerPos = new HashMap<>();

        this.players = players;

        // sets all players position to GO tile at 0
        for (Player player : players) {
            playerPos.put(player, 0);
        }

        ConfigTranslator builder = new ConfigTranslator(); // This ceases to exist after initialization

        board = builder.getTiles();

        System.out.println(builder.getTileIdentities());

        System.out.println(board.length);

        identityPropMap = new HashMap<>();
        for(String identity : builder.getTileIdentities()) {
            ArrayList<Ownable> properties = new ArrayList<>();
            
            
            
            for(Tile tile : board) {
                if(tile instanceof Property && ((Property) tile).getColourAsString().equals(identity)) {
                    properties.add((Ownable) tile);
                }else if(tile instanceof Station && identity == "STATION"){
                    properties.add((Ownable) tile);
                }else if(tile instanceof Utility && identity == "UTILITY"){
                    properties.add((Ownable) tile);
                }
            }
            identityPropMap.put(identity, properties);
        }

        ConfigValidator validator = new ConfigValidator(board);
        validator.validate(board);

        dice = new Dice();

        potluckCards = builder.getPotluckChestCards();
        Collections.shuffle(potluckCards);
        opportunityKnocksCards = builder.getOpportunityCards();
        Collections.shuffle(opportunityKnocksCards);

        System.out.println(players.size());

        developedProperties = new ArrayList<>();

    }

    public Map<String, ArrayList<Ownable>> getIdentityPropMap() {
        return identityPropMap;
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
            if(dice.wasItADouble() || player.hasGetOutOfJailFree()) {
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
                    currentPlayer.makePurchase(((Property) x).getCurrentRent());
                    ((Property) x).getOwner().payPlayer(((Property) x).getCurrentRent());
            }
        }else if(x instanceof GovProperties) {
            if (((Ownable) x).getOwned() && !((Ownable) x).getOwner().getIsInJail()) {
                String type = "UTILITY";
                if (x instanceof Station) {
                    type = "STATION";
                }

                int rentMultiplier = howManyStationUtilityDoesPlayerOwn(((Ownable) x).getOwner(), type);

                if (rentMultiplier > 0) {
                    currentPlayer.makePurchase(50 * rentMultiplier);
                    ((Ownable) x).getOwner().payPlayer(50 * rentMultiplier);
                } else {
                    currentPlayer.makePurchase(50);
                    ((Ownable) x).getOwner().payPlayer(50);
                }
            }
        }
        else if (x instanceof PotLuck) {
            Card card = potluckCards.remove(0);
            performCardAction(card);
            potluckCards.add(card);
        }
        else if (x instanceof FreeParking) {
            currentPlayer.payPlayer(((FreeParking) board[20]).getCurrentValue());
            ((FreeParking) board[20]).setCurrentValue(0);
        }
        else if (x instanceof Tax) {
            currentPlayer.makePurchase(((Tax) x).getTaxAmount());
            ((FreeParking) board[20]).addToPot(((Tax) x).getTaxAmount());
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
            case "You have won 2nd prize in a beauty contest, collect":
            case "You inherit" : // Bank pays player - Inherits
            case "Student loan refund. Collect ": // Student loan - bank pays player
            case "Bank error in your favour. Collect": // Bank error - bank pays player
            case "From sale of Bitcoin you get": // Sale - bank pays player
            case "Savings bond matures, collect": // Savings - bank pays player
            case "Received interest on shares of":

                currentPlayer.payPlayer(card.getValue());
                break;
            case "Go back to": // Go back to Crapper Street
                currentPlayer.setTilePosition(card.getValue());
                break;
            case "Pay bill for text books of"://Player pays bill
            case "Mega late night taxi bill pay": // Player pays bank
                currentPlayer.makePurchase(card.getValue());
                break;
            case "Advance to go": // go to Go tile
                currentPlayer.setTilePosition(0);
                break;
            case "Go to jail. Do not pass GO, do not collect": // sends player to jail
                sendToJail(currentPlayer);
                break;
            case "It's your birthday. Each player pays you":// Each player pays current player
                for (Player player: players) {

                    if(player != currentPlayer) {
                        player.makePurchase(card.getValue());
                        currentPlayer.payPlayer(card.getValue());
                    }
                }
                break;
            case "Get out of jail free":
                currentPlayer.addGetOutOfJailFreeCard();
                break;
            case "Pay insurance fee of":
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


    public boolean doesPlayerOwnAllOfIdentity(Player player , String identity){
        if(identityPropMap.containsKey(identity)){
            for (Ownable ownable : identityPropMap.get(identity)) {
                if (ownable.getOwner() != player) {
                    return false;
                }
            }
        }
        return true;
    }

    public int howManyStationUtilityDoesPlayerOwn(Player playerOwner , String identity){
        int i = 0;

        if(identityPropMap.containsKey(identity)){
            for (Ownable ownable : identityPropMap.get(identity)) {
                if (ownable.getOwner() == playerOwner) {
                    i++;
                }
            }
        }
        return i;
    }





}