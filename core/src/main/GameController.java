package main;


import Tiles.Tile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import misc.CellToTileBuilder;
import misc.Coordinate;

import java.util.HashMap;

import static com.propertytycoonmakers.make.PropertyTycoon.players;

/**
 * GameController handles passing turns between players and playing out each turn.
 *
 */

public class GameController{

    private static GameBoard board;
    private HashMap<TiledMapTileLayer.Cell, Tile> cellToTile;
    private int playerNum;
    private Player previousPlayer;

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
    private Player getCurrentPlayer() {

        return players[playerNum];
    }

    /**
     * High level method call to execute players turns. Used by GameScreen to return the new tile the player should be displayed on.
     * @return the tile the player has landed on
     */
    public Tile playerTurn(){

        Boolean playAgain = board.playerTurn(getCurrentPlayer());
        previousPlayer = getCurrentPlayer();

        Tile tile = board.getTile(board.getPlayerPos(getCurrentPlayer()));
        System.out.println("-------------------------");
        System.out.println(board.getTile(board.getPlayerPos(getCurrentPlayer())));
        System.out.println(board.getPlayerPos(getCurrentPlayer()));
        System.out.println(getCurrentPlayer());
        System.out.println("-------------------------");

        System.out.println(tile);
        if (!playAgain){
            System.out.println("new player");
            nextPlayer();
        }
        return tile;
    }

    public Player getUpdatedPlayer() {
        return previousPlayer;
    }

    public GameBoard getBoard(){

        return board;


    }

}





