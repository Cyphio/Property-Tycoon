package Tiles;

import main.Player;
import misc.Coordinate;

public class Ownable extends SmallTile{
    protected int cost;
    private boolean isBuyable = false;
    protected boolean owned;
    protected Player owner;
    protected Coordinate propertySpriteCoordinate;
    private boolean isMortgaged;


    public Ownable(){

        owned = false;
        owner = null;

    }


    /**
     * getBuyable returns true if property is buyable and false if it is not.
     * @return returns isBuyable , Boolean true or false.
     */

    public boolean getBuyable() {
        return isBuyable;
    }

    /**
     * setBuyable sets isBuyable to true or false.
     * @param TrueOrFalse wether the property is buyable or not (true if yes, else false).
     */

    public void setBuyable(boolean TrueOrFalse) {
        isBuyable = TrueOrFalse;
    }

    public void sellProperty(Player player, int cost){

        player.payPlayer(cost);
        if (player.getOwnables().contains(this)) {
            player.removeOwnable(this);
        }
        owned = false;
        owner = null;
        setBuyable(true);

    }


    /**
     * setCost will set the cost of a single property within game board
     * @param cost will be used to enter the cost of the property that player will need to pay
     *             in order to own the property
     */

    public void setCost(int cost){ this.cost = cost; }

    /**
     * getCost will return an integer that will represent a cost of the property
     * @return returns cost, integer that represents the cost of property
     */

    public int getCost(){ return cost; }


    /**
     * getOwned will return a boolean value that will represent if the property is owned by any player or not
     * @return returns owned, boolean value that represent if property is owned
     */

    public boolean getOwned(){ return owned; }

    public void buy() {
        owned = true;
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

    public void buyProperty(Player player, int cost) {
        if (!getOwned() && player.getMoney()>= cost){
            player.makePurchase(cost);
            player.addProperty(this);
            owned = true;
            owner = player;
            setBuyable(false);
        }
    }

    public Coordinate getPropertySpriteCoordinate(){
        return propertySpriteCoordinate;

    }



    public void setMortgaged(Player player, int cost){
        if((getOwned())){
            isMortgaged = true;
            player.payPlayer(cost/2);

        }

    }


    public void unmortgage(Player player, int cost){
        if(isMortgaged){
            isMortgaged = false;
            player.makePurchase(cost/2);

        }

    }

    public boolean getMortgaged(){
        return isMortgaged;
    }




}



