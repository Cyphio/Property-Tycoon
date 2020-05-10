package Tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import misc.Coordinate;

import java.util.ArrayList;

import static com.propertytycoonmakers.make.PropertyTycoon.players;

public class Station extends Property implements StationInterface {

    private Sprite icon;
    private Coordinate iconSpriteCoordinate;

    public Station(){

        Texture texture = new Texture(Gdx.files.internal("tile-images/station.png"));
        icon = new Sprite(texture);
        icon.setOriginCenter();

    }

    @Override
    public void setCoordinates(ArrayList<Coordinate> coordinates) {
        playerPosCoordinates = new ArrayList<>();
        for(int i =0 ; i < players.size();i++) {
            if (i != 3 && i != 7 && i != 11) {
                playerPosCoordinates.add(coordinates.get(i));
            }
        }

        int tilePosition = 6; // used to determine which cell in a card is the label position one (makes it easier for us to change as we go)
        int propertyPosition = 7;// where the tile icon is placed
        int iconPosition = 9;

//
//        int iconPosition = 5;
//        Coordinate iconCoordinate = new Coordinate(0,0);
//        iconCoordinate.setCoordinate(coordinates.get(iconPosition).getX()+32,coordinates.get(iconPosition).getY()+32);
//        iconSpriteCoordinate = iconCoordinate;
//



        Coordinate iconCoordinate = new Coordinate(0,0);
        Coordinate labelCoordinate = new Coordinate(0,0);
        Coordinate tempPropertyCoordinate = new Coordinate(0,0);

        labelCoordinate.setCoordinate(coordinates.get(tilePosition).getX()+32,coordinates.get(tilePosition).getY()+32);
        tempPropertyCoordinate.setCoordinate(coordinates.get(propertyPosition).getX()+32,coordinates.get(propertyPosition).getY()+32);

        iconCoordinate.setCoordinate(coordinates.get(iconPosition).getX()+32,coordinates.get(iconPosition).getY()+32);


        centerLabelCoordinate = labelCoordinate;
        propertySpriteCoordinate = tempPropertyCoordinate;
        iconSpriteCoordinate = iconCoordinate;


        allCoordinates = coordinates;


        if (this.getTilePos() < 11){
            icon.setPosition(iconSpriteCoordinate.getX()-32,iconSpriteCoordinate.getY()+32);
            icon.rotate(-90);
        }   else   if (this.getTilePos() < 21){
            icon.setPosition(iconSpriteCoordinate.getX()-32,iconSpriteCoordinate.getY()-96);
            icon.rotate(-180);
        }   else   if (this.getTilePos() <31){
            icon.setPosition(iconSpriteCoordinate.getX()-96,iconSpriteCoordinate.getY()-32);
            icon.rotate(-270);
        }else{
            icon.setPosition(iconSpriteCoordinate.getX()-32,iconSpriteCoordinate.getY()+32);
        }



    }

    public Sprite getIcon(){return icon;}


    public Coordinate getIconSpriteCoordinate() {
        return iconSpriteCoordinate;
    }
}
