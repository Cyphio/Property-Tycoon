package Tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Tax extends Tile implements TaxInterface {
    private Sprite icon;
    int taxAmount;

    public Tax(){

        setTileName("Tax");
        this.taxAmount = taxAmount;
        Texture texture = new Texture(Gdx.files.internal("tile-images/tax.png"));
        icon = new Sprite(texture);
        icon.setOriginCenter();


    }
    public Sprite getIcon(){return icon;}
    public int getTaxAmount(){
        return this.taxAmount;
    }

    public void setTaxAmount(int tax){

       this.taxAmount = tax;
    }
}