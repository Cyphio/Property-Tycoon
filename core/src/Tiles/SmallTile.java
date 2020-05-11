package Tiles;
import misc.Coordinate;

public class SmallTile extends Tile{
    protected String tileName;

    protected Coordinate centerLabelCoordinate;


    public SmallTile(){

        tileName = "";

    }

    /**
     * Gets the label position coordinate used to display labels on the board properly.
     * @return coordinate of label position.
     */

    public Coordinate getCenterLabelCoordinate() {
        return centerLabelCoordinate;
    }


    /**
     * Returns the tile name.
     * @return Tiles.Tile name.
     */

    public String getTileName() {
        return tileName;
    }

    /**
     * Sets the tile name.
     * @param name The desired name of the tile.
     */

    public void setTileName(String name) {
        tileName = name;
    }




}
