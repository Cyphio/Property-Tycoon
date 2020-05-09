package Tiles;

public class Tax extends Tile implements TaxInterface {
    int taxAmount;

    public Tax(){
        this.taxAmount = taxAmount;

    }

    public int getTaxAmount(){
        return this.taxAmount;
    }

    public void setTaxAmount(int tax){

       this.taxAmount = tax;
    }

}
