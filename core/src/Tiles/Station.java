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

    public int getRent(){
        if (this.owner != null) {
            int i = 0;
            for (Ownable prop : this.owner.getOwnables()) {
                if (prop instanceof Station) {
                    i++;
                }
            }
            return currentRent * i;
        }
        return 0;

    }

    public void setCurrentRent(int rent){
        this.currentRent = rent;
    }


}
