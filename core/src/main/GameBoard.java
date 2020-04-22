package main;

import Tiles.*;
import misc.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class GameBoard implements GameBoardInterface {

    private static Tile[] board;

    private static ArrayList<Card> potluckCards;
    private static ArrayList<Card> oppourtunityKnocksCards;

    private static Dice dice;
    private static Map<Player, Integer> playerPos;
    private static Player currentPlayer;
    private int goPayoutAmount;


    public GameBoard(Player[] players) {


        goPayoutAmount = 200;

        playerPos = new HashMap<Player, Integer>();


        // sets all players position to GO tile at 0
        for (Player player : players) {
            playerPos.put(player, 0);
        }

        ConfigTranslator builder = new ConfigTranslator(); // This ceases to exist after initialization

        board = builder.getTiles();

        ConfigValidator validator = new ConfigValidator(board);
        validator.validate(board);

        dice = new Dice();


        potluckCards = builder.getPotluckChestCards();
        Collections.shuffle(potluckCards);
        oppourtunityKnocksCards = builder.getCommunityChestCards();
        Collections.shuffle(oppourtunityKnocksCards);


        System.out.println(players.length);
        System.out.println(players[0].getName());


    }


    // Functional dice setup, logic should be working
    // Logic behind rolling doubles and when to go to jail setup
    // Need the process of going to jail to be implemented
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
        return checkBoardCircumstances();


    }

    /**
     * getPlayerPos is used to return the current position of any given player
     *
     * @param player the player who's position is being searched
     * @return the position of Player player
     */
    @Override
    public int getPlayerPos(Player player) {
        return playerPos.get(player);
    }

    /**
     * setPlayerPos is used to set a players position to a point given
     *
     * @param player The player to move
     * @param pos    Where to move the player
     */
    @Override
    public void setPlayerPos(Player player, int pos) {


        board[playerPos.get(player)].removePlayer(player);
        playerPos.put(player, pos);
        board[pos].addPlayer(player);
        System.out.println("Setting Player coordinates");
        player.setCurrentCoordinates(board[playerPos.get(player)].getAvailableCoordinates());


    }


    /**
     * movePlayer uses context to tell how far a player should move, and what space to move them to
     *
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

        } else {
            this.setPlayerPos(player, moveTo);
        }

    }


    public Tile getTile(int i) {

        return board[i];


    }


    public void sendToJail() {
        currentPlayer.setInJail(true);
        setPlayerPos(currentPlayer, 10);
    }

    //check if the player has landed on another players properties etc
    @Override
    public Boolean checkBoardCircumstances() {

        Tile x = board[playerPos.get(currentPlayer)];

        if (x instanceof OpportunityKnocks) {

            Card card = oppourtunityKnocksCards.remove(0);
            performCardAction(card);
            oppourtunityKnocksCards.add(card);


        } else if (x instanceof PotLuck) {
            Card card = potluckCards.remove(0);
            performCardAction(card);
            potluckCards.add(card);

        } else if (x instanceof GoToJail) {
            sendToJail();

        } else if (x instanceof Go) {

            currentPlayer.payPlayer(goPayoutAmount);
        }


        if (dice.jailCheck()) {

            sendToJail();

        } else if (dice.wasItADouble()) {


            return true;


        }


        dice.reset();

        return false;


    }

    @Override
    public void performCardAction(Card card) {

        switch (card.getAction()) {
            case "pay":
                currentPlayer.payPlayer(card.getValue());
                break;

            default:
                System.out.println("no action found");


        }


    }

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


}



