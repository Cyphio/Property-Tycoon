package Tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import misc.Coordinate;

import java.util.ArrayList;

import static com.propertytycoonmakers.make.PropertyTycoon.players;

public class PotLuck extends Chests {


    public PotLuck(){


            Texture texture = new Texture(Gdx.files.internal("tile-images/potluck.png"));
            icon = new Sprite(texture);
            icon.setOriginCenter();
            setTileName("Potluck");
        icon.setAlpha(1f);

        }

}
