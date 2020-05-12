package main;

import Tiles.Property;
import Tiles.Tile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import misc.CellToTileBuilder;
import misc.Coordinate;

import java.util.ArrayList;
import java.util.HashMap;

import static com.propertytycoonmakers.make.PropertyTycoon.players;

/**
 * Acts as a bridge between game logic and the render phase.
 */
public class GameController{

    private static GameBoard board;
    private HashMap<TiledMapTileLayer.Cell, Tile> cellToTile;
    private Boolean playAgain;
    private int currentAuction;
    private ArrayList<Player> playerOrders;


    /**
     * the constructor for GameController
     * @param layer the TiledMapTileLayer used to create the cell to Tile mapping
     */
    public GameController(TiledMapTileLayer layer) {

        playerOrders = new ArrayList<>(players);


    cellToTile = new HashMap<>();
    board = new GameBoard(players);

    CellToTileBuilder builder = new CellToTileBuilder(layer,board);
    cellToTile = builder.getReferenceList();

    Tile tile = board.getTile(0);

    for (Player p : playerOrders) {
        Coordinate coord =  tile.getAvailableCoordinates();
        p.setCurrentCoordinates(coord);
    }
}

    /**
     * Changes the current player to the next player in the list players
     */
    private void nextPlayer(){

        Player p = playerOrders.remove(0);
        playerOrders.add(p);

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
        return playerOrders.get(0);
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

    /**
     * getPlayerOrder returns playerOrders
     * @return returns an ArrayList of players in their current play order (current player is at index 0, next player at index 1 etc.)
     */
    public ArrayList<Player> getPlayerOrder(){

        return playerOrders;

    }

    public boolean developProperty(Property prop, Player player) {
        if(player.getOwnables().containsAll(board.getIdentityPropMap().get(prop.getColourAsString())) && prop.getOwner() == player && prop.getHousesOwned() < 5) {
            prop.develop();
            return true;
        }
        else {
            return false;
        }
    }

    public int getLastD1() {
        return board.getLastD1();
    }

    public int getLastD2() {
        return board.getLastD2();
    }


    public ArrayList<Property> getDevelopedProperties(){
        return board.getDevelopedProperties();
    }

    public void freePlayerFromJail(Player player){
        player.setInJail(false);
        Coordinate c = board.getTile(10).getAvailableCoordinates();
        player.getPlayerToken().setPosition(c.getX(),c.getY());
    }

    // Quick sort algorithm
    public ArrayList<Player> getFinalStandings(ArrayList<Player> players, int start, int end) {
        if(start < end) {
            int partitionPoint = partition(players, start, end);
            getFinalStandings(players, start, partitionPoint - 1);
            getFinalStandings(players, partitionPoint + 1, end);
        }
        return players;
    }

    public int partition(ArrayList<Player> players, int start, int end) {
            int pivot = players.get(end).getWealth();
            int i = start - 1;
            for(int j=start; j<end; j++) {
                if(players.get(j).getWealth() < pivot) {
                    i++;
                    Player tempI = players.get(i);
                    Player tempJ = players.get(j);
                    players.set(i, tempJ);
                    players.set(j, tempI);
                }
            }
            Player tempIplus1 = players.get(i+1);
            Player tempEnd = players.get(end);
            players.set(i+1, tempEnd);
            players.set(end, tempIplus1);
            return i+1;
    }
}