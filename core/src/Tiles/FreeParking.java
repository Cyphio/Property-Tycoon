package Tiles;

public class FreeParking extends Tile {

    private int currentValue;


    public FreeParking() {
        currentValue = 0 ;
    }


    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }


    public void addToPot(int amount){


        currentValue += amount;


    }

    public void reset(){

        currentValue = 0;

    }



}
