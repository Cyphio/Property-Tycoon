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

    public ScrollableStage(GameScreen gs) {
        this.gs = gs;
    }


    @Override
    public boolean scrolled(int amount) {
        if (amount == -1 && scrollCount > -7) {
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
        OrthographicCamera camera = gs.getCam();
        camera.translate(-Gdx.input.getDeltaX() * (camera.viewportWidth / Gdx.graphics.getWidth()), Gdx.input.getDeltaY() * (camera.viewportHeight / Gdx.graphics.getHeight()));
        return true;
    }

    public void recenter(TiledMap map) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        Vector3 center = new Vector3(layer.getWidth() * layer.getTileWidth() / 2, layer.getHeight() * layer.getTileHeight() / 2, 0);
        gs.getCam().position.set(center);
    }
}