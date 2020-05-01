package Tiles;

import main.Player;
import misc.Coordinate;

import java.util.ArrayList;
import static com.propertytycoonmakers.make.PropertyTycoon.players;

/**
 * The tile class is the superclass for all further tiles, this allows all tiles to have in general the same
 * functionality
 */
public class Tile implements TileInterface {

    private String tileName;
    private int tilePosition;
    private boolean isBuyable = false;
    ArrayList<Player> tilePlayers = new ArrayList<>();
    private Coordinate centerLabelCoordinate;
    protected ArrayList<Coordinate> playerPosCoordinates;
    protected ArrayList<Coordinate> allCoordinates;
    public Tile(){
        tileName = "";
    }

    /**
     * getBuyable returns true if property is buyable and false if it is not.
     * @return returns isBuyable , Boolean true or false.
     */
    @Override
    public boolean getBuyable() {
        return isBuyable;
    }

    /**
     * setBuyable sets isBuyable to true or false.
     * @param TrueOrFalse wether the property is buyable or not (true if yes, else false).
     */
    @Override
    public void setBuyable(boolean TrueOrFalse) {
        isBuyable = TrueOrFalse;
    }

    /**
     * Returns the Players on the current tile.
     * @return List of Players on the tile.
     */
    @Override
    public ArrayList<Player> getPlayers() {
        return tilePlayers;
    }

    /**
     * Adds player to list of players positioned on the tile.
     * @param player The main.Player to be added to the list of players on the tile.
     */
    @Override
    public void addPlayer(Player player) {
        tilePlayers.add(player);
    }

    /**
     * Removes player from the list of players positioned on the tile.
     * @param player The main.Player to remove.
     */
    @Override
    public void removePlayer(Player player) {
        tilePlayers.remove(player);
    }

    /**
     * Returns the tile name.
     * @return Tiles.Tile name.
     */
    @Override
    public String getTileName() {
        return tileName;
    }

    /**
     * Sets the tile name.
     * @param name The desired name of the tile.
     */
    @Override
    public void setTileName(String name) {
        tileName = name;
    }

    /**
     * Returns the position of the tile on the game board.
     * @return int number of position of tile on the board.
     */

    @Override
    public int getTilePos() {
        return tilePosition;
    }

    /**
     * Sets the value of the position of the tile on the game board.
     * @param position The desired position.
     */
    @Override
    public void setTilePos(int position) {
        tilePosition = position;
    }

    /**
     * sets the coordinates of tiles on the GameBoard, this allows for mouse interactivity.
     * @param coordinates the arraylist of coordinates for each tile.
     */

    public void setCoordinates(ArrayList<Coordinate> coordinates){
        playerPosCoordinates = new ArrayList<>();
        for(int i =0 ; i < players.length;i++) {
            if (i != 3 && i != 7 && i != 11) {
                playerPosCoordinates.add(coordinates.get(i));
            }
        }
        allCoordinates = coordinates;
        Coordinate labelCoordinate = new Coordinate(0,0);
        labelCoordinate.setCoordinate(coordinates.get(7).getX()+32,coordinates.get(7).getY()+32);
        centerLabelCoordinate = labelCoordinate;
    }

    /**
     * Gets the next available coordinate a player can be positioned.
     * @return the next available coordinate for a player to be positioned.
     */
    public Coordinate getAvailableCoordinates(){
        Coordinate coordinate = playerPosCoordinates.remove(0);
        playerPosCoordinates.add(coordinate);
        return coordinate;
    }

    /**
     * Gets the label position coordinate used to display labels on the board properly.
     * @return coordinate of label position.
     */

    public Coordinate getCenterLabelCoordinate() {
        return centerLabelCoordinate;
    }

    /**
     * @return returns the arraylist of all player coordinates
     */
    public ArrayList<Coordinate> getAllPlayerCoordinates() {
        return playerPosCoordinates;
    }

    /**
     * @return returns the arraylist of all coordinates
     */
    public ArrayList<Coordinate> getAllCoordinates() {
        return allCoordinates;
    }
}