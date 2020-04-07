package Screens;

import com.badlogic.gdx.Screen;
import com.propertytycoonmakers.make.PropertyTycoon;

public class LoadingScreen implements Screen {

    private PropertyTycoon game;

    public LoadingScreen(PropertyTycoon game) {
        this.game = game;
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        game.changeScreen(game.MAINMENU);
    }

    @Override
    public void dispose() {}

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}
