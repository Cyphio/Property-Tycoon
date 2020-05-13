package Tiles;

import misc.Coordinate;

import java.util.ArrayList;

import static com.propertytycoonmakers.make.PropertyTycoon.players;

public class Jail extends BigTiles {

    ArrayList<Coordinate> jailCoordinates;
    private int bailPrice = 50;


    public Jail(){
        tileName = "Jail";
    }


    @Override
    public void setCoordinates(ArrayList<Coordinate> coordinates){
        this.playerPosCoordinates = new ArrayList<>();
        jailCoordinates = new ArrayList<>();

        for (int i =0; i < 15 ; i++) {

            if (i == 0 || i == 4 || i == 8 || i == 12 || i == 13 || i == 14 || i == 15) {

                playerPosCoordinates.add(coordinates.get(i));

            } else {

                jailCoordinates.add(coordinates.get(i));


            }
        }

        allCoordinates = coordinates;

    }

    public Coordinate getNextJailCoordinate(){
        Coordinate coordinate = jailCoordinates.remove(0);
        jailCoordinates.add(coordinate);
        return coordinate;
    }


    public int getBailPrice() {
        return bailPrice;
    }

    public void setBailPrice(int bailPrice) {
        this.bailPrice = bailPrice;
    }
}
