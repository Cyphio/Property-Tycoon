package Tiles;

public interface PropertyInterface extends TileInterface {
    void setColour(String colour);
    void addHousePrice(int housePrice);
    void setCost(int cost);
    int getCost();
    void buy();
    boolean getOwned();
}
