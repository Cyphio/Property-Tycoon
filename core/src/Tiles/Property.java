package Tiles;

import java.util.ArrayList;

public class Property extends Tile implements PropertyInterface {


    private String colour;
    private int rent;
    private ArrayList<Integer> housePrices = new ArrayList<>();


    @Override
    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public void addHousePrice(int housePrice) {
        housePrices.add(housePrice);
    }
}
