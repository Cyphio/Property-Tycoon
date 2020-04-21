package main;

import misc.Card;

public interface GameBoardInterface {
    Boolean playerTurn(Player player);
    int getPlayerPos(Player player);
    void setPlayerPos(Player player, int pos);
    void movePlayer(Player player, int moves);
    Boolean checkBoardCircumstances();
    void performCardAction(Card card);
    void purchaseProperty(Player player, Tiles.Property prop);
}
