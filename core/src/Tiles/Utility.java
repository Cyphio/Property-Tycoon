package Tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class Utility extends GovProperties {


    public Utility(){

        tileName = "";
        Texture texture = new Texture(Gdx.files.internal("tile-images/utility.png"));
        icon = new Sprite(texture);
        icon.setOriginCenter();
        icon.setAlpha(1f);


    }

}
