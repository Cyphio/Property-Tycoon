package Tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import main.Player;
import misc.Coordinate;

import java.util.ArrayList;

import static com.propertytycoonmakers.make.PropertyTycoon.players;

public class Station extends GovProperties implements StationInterface {

    private int currentRent;

    public Station(){
        currentRent = 50;
        Texture texture = new Texture(Gdx.files.internal("tile-images/station.png"));
        icon = new Sprite(texture);
        icon.setOriginCenter();
        icon.setAlpha(1f);

    }

    public int getRent(Player p){
        int i = 0;
        for(Ownable prop : p.getOwnables()){
           if(prop instanceof Station){
               i++;
           }
        }
        return currentRent*i;

    }

    public void setCurrentRent(int rent){
        this.currentRent = rent;
    }


}
