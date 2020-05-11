package Tiles;

import misc.Coordinate;

import java.util.ArrayList;

import static com.propertytycoonmakers.make.PropertyTycoon.players;

public class BigTiles extends Tile{

    public void setCoordinates(ArrayList<Coordinate> coordinates){
        playerPosCoordinates = new ArrayList<>();
        for(int i =0 ; i < players.size();i++) {

                playerPosCoordinates.add(coordinates.get(i));
        }
        allCoordinates = coordinates;
    }
}
