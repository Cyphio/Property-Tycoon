package Tiles;

import main.Player;
import misc.Coordinate;

import java.util.ArrayList;

import static com.propertytycoonmakers.make.PropertyTycoon.players;


public class Go extends Tile {

    @Override
    public void setCoordinates(ArrayList<Coordinate> coordinates){
        this.playerPosCoordinates = new ArrayList<>();
        allCoordinates = coordinates;
        this.playerPosCoordinates = allCoordinates;

    }



}
