package Tiles;

import main.Player;

import java.util.ArrayList;

public class Property extends Tile implements PropertyInterface {

    private String colour;
    private int cost;
    private int rent;
    private ArrayList<Integer> housePrices = new ArrayList<>();
    private boolean owned;
    private Player owner;
    private int housesOwned;

    public Property(){

        housesOwned = 0;
        owned = false;

    }


    @Override
    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public void addHousePrice(int housePrice) {
        housePrices.add(housePrice);
    }


    /**
     * setCost will set the cost of a single property within game board
     * @param cost will be used to enter the cost of the property that player will need to pay
     *             in order to own the property
     */
    @Override
    public void setCost(int cost){ this.cost = cost; }

    /**
     * getCost will return an integer that will represent a cost of the property
     * @return returns cost, integer that represents the cost of property
     */
    @Override
    public int getCost(){ return cost; }

    /**
     * buy will change the owned variable from false to true once a player buys a property
     */
    @Override
    public void buy(){ owned = true; }

    /**
     * getOwned will return a boolean value that will represent if the property is owned by any player or not
     * @return returns owned, boolean value that represent if property is owned
     */
    @Override
    public boolean getOwned(){return owned;}

    public void buyProperty(Player player) {

        if (this.getBuyable() && this.getPlayers().contains(player) && !getOwned() && player.getMoney()>= this.cost){

            player.makePurchase(this.cost);
            owner = player;
            setBuyable(false);

        }

    }



    public Player getOwner() {
        return owner;
    }

    public String getOwnerName(){

        if (owner != null){

            return owner.getName();

        }

        return "FOR SALE";


    }

}
