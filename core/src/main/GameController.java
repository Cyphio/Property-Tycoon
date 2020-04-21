package main;


import Tiles.Tile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import misc.CellToTileBuilder;
import misc.Coordinate;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.propertytycoonmakers.make.PropertyTycoon.players;

public class GameController{

    private static GameBoard board;
    private HashMap<TiledMapTileLayer.Cell, Tile> cellToTile;
    private int playerNum;

    public GameController(TiledMapTileLayer layer) {

        playerNum = 0;
        cellToTile = new HashMap<>();
        board = new GameBoard(players);

        CellToTileBuilder builder = new CellToTileBuilder(layer,board);
        cellToTile = builder.getReferenceList();

        
        Tile tile = board.getTile(0);
        for (Player p : players) {
            Coordinate coord =  tile.getAvailableCoordinates();
            p.setCurrentCoordinates(coord);
        }
    }

    private void nextPlayer(){
        if (playerNum < players.length - 1){
            playerNum += 1;
        }else{
            playerNum = 0;
        }
        }
    public Tile retTile(TiledMapTileLayer.Cell cell) {
        return cellToTile.get(cell);
    }

    /**
     * getCurrentPlayer provides functionality to return the current player outside of this class
     *
     * @return returns the player who's turn it currently is
     */
    public Player getCurrentPlayer() {

        return players[playerNum];
    }

    public Tile playerTurn(){

        Boolean playAgain = board.playerTurn(getCurrentPlayer());
        System.out.println(board.getPlayerPos(getCurrentPlayer()));
        Tile tile = board.getTile(board.getPlayerPos(getCurrentPlayer()));

        System.out.println(playAgain);
        if (!playAgain){
            nextPlayer();
        }
        return tile;
    }

}





