package Tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import main.Player;


public class Utility extends GovProperties {


    public Utility(){

        tileName = "";
        Texture texture = new Texture(Gdx.files.internal("tile-images/utility.png"));
        icon = new Sprite(texture);
        icon.setOriginCenter();
        icon.setAlpha(1f);


    }

    public int getRent(Player p, int dice){
        int i = 0;
        for(Ownable prop : p.getOwnables()){
            if(prop instanceof Utility){
                i++;
            }
        }
        if(i == 1){
            return 4*dice;
        }
        else{
            return 10*dice;
        }

    }

}
