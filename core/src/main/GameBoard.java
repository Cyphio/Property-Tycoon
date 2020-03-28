package main;

import Tiles.OpportunityKnocks;
import Tiles.PotLuck;
import Tiles.Tile;
import misc.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class GameBoard {

    private static Tile[] board;

    private static ArrayList<Card> potluckCards;
    private static ArrayList<Card> oppourtunityKnocksCards;

    private static Dice dice;
    private static Map<Player, Integer> playerPos;
    private static Player currentPlayer;


    public GameBoard(Player[] players) {


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
    }


    // Functional dice setup, logic should be working
    // Logic behind rolling doubles and when to go to jail setup
    // Need the process of going to jail to be implemented
    public void playerTurn(Player player){
        currentPlayer = player;
        movePlayer(player, dice.getValue());
        checkBoardCircumstances();

    }

    /**
     * getPlayerPos is used to return the current position of any given player
     *
     * @param player the player who's position is being searched
     * @return the position of Player player
     */
    public int getPlayerPos(Player player) {
        return playerPos.get(player);
    }

    /**
     * setPlayerPos is used to set a players position to a point given
     *
     * @param player The player to move
     * @param pos    Where to move the player
     */
    private void setPlayerPos(Player player, int pos) {
        playerPos.put(player, pos);
    }


    /**
     * movePlayer uses context to tell how far a player should move, and what space to move them to
     *
     * @param player The player to move
     * @param moves  how many spaces to move the player
     */
    private void movePlayer(Player player, int moves) {
        int position = getPlayerPos(currentPlayer);
        int moveTo = position + moves;

        if (moveTo > 39) {
            //change this based on go tile amount set (for now 200)
            player.payPlayer(200);
            this.setPlayerPos(currentPlayer, moveTo - 40);
            if (moveTo < 0) {
                this.setPlayerPos(currentPlayer, moveTo + 40);
            }

        } else {
            this.setPlayerPos(currentPlayer, moveTo);
        }
    }


    //check if the player has landed on another players properties etc
    private void checkBoardCircumstances() {

        Tile x = board[playerPos.get(currentPlayer)];

        if (x instanceof OpportunityKnocks) {

            Card card = oppourtunityKnocksCards.remove(0);
            performCardAction(card);
            oppourtunityKnocksCards.add(card);


        } else if (x instanceof PotLuck) {
            Card card = potluckCards.remove(0);
            performCardAction(card);
            potluckCards.add(card);

        }


    }


    private void performCardAction(Card card) {

        switch (card.getAction()) {
            case "pay":
                currentPlayer.payPlayer(card.getValue());
                break;

            default:
                System.out.println("no action found");


        }


    }




}


