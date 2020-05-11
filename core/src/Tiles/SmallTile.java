package Tiles;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class SmallTile extends Tile{
    protected String tileName;
    protected Sprite icon;


    public SmallTile(){

        tileName = "";

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
