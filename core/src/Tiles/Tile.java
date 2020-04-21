package Tiles;

import main.Player;
import misc.Coordinate;

import java.util.ArrayList;
import static com.propertytycoonmakers.make.PropertyTycoon.players;

public class Tile implements TileInterface {

    private String tileName;
    private int tilePosition;
    private boolean isBuyable = false;
    private ArrayList<Player> tilePlayers = new ArrayList<>();
    private Coordinate centerLabelCoordinate;
    private ArrayList<Coordinate> playerPosCoordinates;
    private ArrayList<Coordinate> allCoordinates;

    /**
     * getBuyable returns true if property is buyable and false if it is not.
     *
     * @return returns isBuyable , Boolean true or false.
     */
    @Override
    public boolean getBuyable() {
        return isBuyable;

    }

    /**2
     * setBuyable sets isBuyable to true or false.
     *
     * @param TrueOrFalse wether the property is buyable or not (true if yes, else false).
     */
    @Override
    public void setBuyable(boolean TrueOrFalse) {

        isBuyable = TrueOrFalse;

    }

    /**
     * Returns the Players on the current tile.
     *
     * @return List of Players on the tile.
     */
    @Override
    public ArrayList<Player> getPlayers() {

        return tilePlayers;
    }

    /**
     * Adds player to list of players positioned on the tile.
     *
     * @param player The main.Player to be added to the list of players on the tile.
     */
    @Override
    public void addPlayer(Player player) {

        tilePlayers.add(player);

    }

    /**
     * Removes player from the list of players positioned on the tile.
     *
     * @param player The main.Player to remove.
     */
    @Override
    public void removePlayer(Player player) {

        tilePlayers.remove(player);

    }

    /**
     * Returns the tile name.
     *
     * @return Tiles.Tile name.
     */
    @Override
    public String getTileName() {

        return tileName;

    }

    /**
     * Sets the tile name.
     *
     * @param name The desired name of the tile.
     */
    @Override
    public void setTileName(String name) {

        tileName = name;

    }

    /**
     * Returns the position of the tile on the game board.
     *
     * @return int number of position of tile on the board.
     */

    @Override
    public int getTilePos() {

        return tilePosition;
    }

    /**
     * Sets the value of the position of the tile on the game board
     *
     * @param position The desired position.
     */
    @Override
    public void setTilePos(int position) {

        tilePosition = position;

    }


    public void setCoordinates(ArrayList<Coordinate> coordinates){

        playerPosCoordinates = new ArrayList<>();

        for(int i =0 ; i < players.length;i++) {

            playerPosCoordinates.add(coordinates.get(i));
        }

        allCoordinates = coordinates;

        centerLabelCoordinate = coordinates.get(7);

    }


    public Coordinate getAvailableCoordinates(){

        System.out.println(playerPosCoordinates);
        Coordinate coordinate = playerPosCoordinates.remove(0);
        playerPosCoordinates.add(coordinate);

        System.out.println(tileName);
        System.out.println(playerPosCoordinates);
        System.out.println("Pulled coordinate x is " + coordinate.getX());


        return coordinate;



    }


    public Coordinate getCenterLabelCoordinate() {
        return centerLabelCoordinate;
    }

    public ArrayList<Coordinate> getAllPlayerCoordinates() {
        return playerPosCoordinates;
    }


    public ArrayList<Coordinate> getAllCoordinates() {
        return allCoordinates;
    }

}

