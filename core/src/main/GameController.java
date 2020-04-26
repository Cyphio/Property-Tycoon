package main;

import Tiles.Tile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import misc.CellToTileBuilder;
import misc.Coordinate;
import java.util.HashMap;
import static com.propertytycoonmakers.make.PropertyTycoon.players;

/**
 * GameController handles passing turns between players and playing out each turn.
 */
public class GameController{

    private static GameBoard board;
    private HashMap<TiledMapTileLayer.Cell, Tile> cellToTile;
    private int playerNum;
    private Boolean playAgain;
    private int currentAuction;

    /**
     * the constructor for GameController
     * @param layer the TiledMapTileLayer used to create the cell to Tile mapping
     */
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

    /**
     * Changes the current player to the next player in the list players
     */
    private void nextPlayer(){
        if (playerNum < players.length - 1) {
            playerNum += 1;
        }
        else{
            playerNum = 0;
        }
    }

    /**
     * retTile takes a cell that has been clicked on the TiledMapTileLayer and returns the associated Tile
     * @param cell the cell clicked
     * @return the Tile associated with the cell clicked
     */
    public Tile retTile(TiledMapTileLayer.Cell cell) {
        return cellToTile.get(cell);
    }

    /**
     * getCurrentPlayer provides functionality to return the current player outside of this class
     * @return returns the player who's turn it currently is
     */
    public Player getCurrentPlayer() {
        return players[playerNum];
    }

    /**
     * High level method call to execute players turns. Used by GameScreen to return the new tile the player should be
     * displayed on.
     * @return the tile the player has landed on
     */
    public Tile playerTurn(){
        playAgain = board.playerTurn(getCurrentPlayer());

        Tile tile = board.getTile(board.getPlayerPos(getCurrentPlayer()));
        System.out.println("-------------------------");
        System.out.println(board.getTile(board.getPlayerPos(getCurrentPlayer())));
        System.out.println(board.getPlayerPos(getCurrentPlayer()));
        System.out.println(getCurrentPlayer());
        System.out.println("-------------------------");

        System.out.println(tile);
        return tile;
    }

    public int getAuctionValue(){
        return currentAuction;
    }

    public void setAuctionValue(int value){
        currentAuction = value;
    }

    /**
     * getPlayAgain returns a Boolean judgement as to whether a second turn needs to be executed
     * @return returns true when 1st or 2nd doubles rolled
     */
    public boolean getPlayAgain(){
        return playAgain;
    }

    /**
     * if there is no reason for the current player to continue their turn, the endTurn function moves to the
     * next player
     */
    public void endTurn(){
        if (!playAgain){
            System.out.println("new player");
            nextPlayer();
        }
    }

    /**
     * getBoard returns the current gameBoard object
     * @return returns the current GameBoard object
     */
    public GameBoard getBoard() {
        return board;
    }

    public int getPlayerNum(){
        return playerNum;
    }
}