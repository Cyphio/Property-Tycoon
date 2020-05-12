package misc;

import Screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class ScrollableStage extends Stage {

    private GameScreen gs;
    private int scrollCount;
    // These values likely need to be scaled according to your world coordinates.
// The left boundary of the map (x)
    int mapLeft = 1500;
    // The right boundary of the map (x + width)
    int mapRight = 4500;
    // The bottom boundary of the map (y)
    int mapBottom = 800;
    // The top boundary of the map (y + height)
    int mapTop = 2780;


    public ScrollableStage(GameScreen gs) {
        this.gs = gs;

    }


    @Override
    public boolean scrolled(int amount) {
        if (amount == -1 && scrollCount > -5) {
            gs.getCam().zoom -= .2f;
            scrollCount--;
        }
        else if (amount == 1 && scrollCount < 0) {
            gs.getCam().zoom += .2f;
            scrollCount++;
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        float cameraHalfWidth = gs.getCam().viewportWidth * .5f;
        float cameraHalfHeight = gs.getCam().viewportHeight * .5f;

        float cameraLeft = gs.getCam().position.x - cameraHalfWidth;
        float cameraRight = gs.getCam().position.x + cameraHalfWidth;
        float cameraBottom = gs.getCam().position.y - cameraHalfHeight;
        float cameraTop = gs.getCam().position.y + cameraHalfHeight;

        OrthographicCamera camera = gs.getCam();
        camera.translate(-Gdx.input.getDeltaX() * (camera.viewportWidth / Gdx.graphics.getWidth()), Gdx.input.getDeltaY() * (camera.viewportHeight / Gdx.graphics.getHeight()));
        if(mapRight < gs.getCam().viewportWidth)
        {
           gs.getCam().position.x = mapRight / 2;
        }
        else if(cameraLeft < mapLeft)
        {
            gs.getCam().position.x = mapLeft + cameraHalfWidth ;
        }
        else if(cameraRight > mapRight)
        {
            gs.getCam().position.x = mapRight - cameraHalfWidth ;
        }
        // Vertical axis
        if(mapTop < gs.getCam().viewportHeight)
        {
            gs.getCam().position.y = mapTop / 2;
        }
        else if(cameraBottom < mapBottom)
        {
            gs.getCam().position.y = mapBottom + cameraHalfHeight;
        }
        else if(cameraTop > mapTop)
        {
            gs.getCam().position.y = mapTop - cameraHalfHeight;
        }
        return true;
    }

    public void recenter(TiledMap map) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        Vector3 center = new Vector3(layer.getWidth() * layer.getTileWidth() / 2, layer.getHeight() * layer.getTileHeight() / 2, 0);
        gs.getCam().position.set(center);
    }
}