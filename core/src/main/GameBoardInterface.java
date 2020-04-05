package main;

import misc.Card;

public interface GameBoardInterface {
    void playerTurn(Player player);
    int getPlayerPos(Player player);
    void setPlayerPos(Player player, int pos);
    void movePlayer(Player player, int moves);
    void checkBoardCircumstances();
    void performCardAction(Card card);
    void purchaseProperty(Player player, Tiles.Property prop);
}
