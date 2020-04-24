package Tiles;

import main.Player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import com.badlogic.gdx.graphics.Color;

/**
 * the Property class implements functionality relating to a property in the game. It allows for the setting of rent,
 * cost and colour amongst functionality for buying and getting the owner.
 */
public class Property extends Tile implements PropertyInterface {

    private String colour;
    private int cost;
    private int rent;
    private ArrayList<Integer> developmentPrices = new ArrayList<>();
    private boolean owned;
    private Player owner;
    private int housesOwned;

    /**
     * The constructor for Property
     */
    public Property(){
        housesOwned = 0;
        owned = false;
    }

    /**
     * setColour takes a String and sets that as the colour of the property
     * @param colour the colour of a property represented as a String
     */
    @Override
    public void setColour(String colour) {
        this.colour = colour;
    }

    /**
     * getColour returns the colour of the property as a Color object. This requires finding the Color object with the
     * associated String label in com.badlogic.gdx.graphics.Color
     * @return the Color object associated with the String label, Color.WHITE if not found
     */
    public Color getColor() {
        try {
            Field field = Class.forName("com.badlogic.gdx.graphics.Color").getField(colour);
            return (Color)field.get(null);
        } catch(Exception e) {
            return Color.WHITE;
        }
    }

    /**
     * addHousePrice adds a house/hotel price to the ArrayList development prices
     * @param housePrice the house/hotel price to be added
     */
    @Override
    public void addHousePrice(int housePrice) {
        developmentPrices.add(housePrice);
    }

    /**
     * getHousePrice returns the ArrayList developmentPrices which stores the cost for development in increasing order.
     * I.e. developmentPrices.get(0) returns the cost to add 1 house, developmentPrices.get(4) returns the cost to add
     * a hotel
     * @return the ArrayList developmentPrices
     */
    public ArrayList<Integer> getHousePrice() {
        return developmentPrices;
    }

    /**
     * setRent takes a rent value and sets the rent of the property (with 0 development) to be that value
     * @param rent the rent of the property
     */
    public void setRent(String rent) {
         try {
             this.rent = Integer.parseInt(rent);
         } catch(Exception e) {
             e.getMessage();
         }
    }

    /**
     * getRent returns the rent of the property with 0 development
     * @return the rent of the property with 0 development
     */
    public int getRent() {
        return rent;
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
    public boolean getOwned(){ return owned; }

    /**
     * Checks if a property is owned, and if not, allows it to be assigned to a player
     * @param player the player buying the property
     */
    public void buyProperty(Player player) {
        if (this.getBuyable() && this.getPlayers().contains(player) && !getOwned() && player.getMoney()>= this.cost){
            player.makePurchase(this.cost);
            owner = player;
            setBuyable(false);
        }
    }

    /**
     * @return returns the player object that is assigned to the property
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * @return returns the name assigned to the player object that owns the property
     */
    public String getOwnerName(){
        if (owner != null){
            return owner.getName();
        }
        return "Nobody";
    }
}
