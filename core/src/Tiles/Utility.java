package Tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import misc.Coordinate;

import java.util.ArrayList;

import static com.propertytycoonmakers.make.PropertyTycoon.players;

public class Utility extends GovProperties {


    public Utility(){

        Texture texture = new Texture(Gdx.files.internal("tile-images/utility.png"));
        icon = new Sprite(texture);
        icon.setOriginCenter();
        icon.setAlpha(0.5f);

    }

}
