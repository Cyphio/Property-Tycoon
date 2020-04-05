package Tiles;

import java.util.ArrayList;

public class Property extends Tile implements PropertyInterface {


    private String colour;
    private int cost;
    private int rent;
    private ArrayList<Integer> housePrices = new ArrayList<>();
    private boolean owned;

    @Override
    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public void addHousePrice(int housePrice) {
        housePrices.add(housePrice);
    }



    @Override
    public void setCost(int cost){ this.cost = cost; }

    @Override
    public int getCost(){ return cost; }

    @Override
    public void buy(){ owned = true; }

    @Override
    public boolean getOwned(){return owned;}
}
