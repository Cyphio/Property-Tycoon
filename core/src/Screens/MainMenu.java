package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.propertytycoonmakers.make.PropertyTycoon;

public class MainMenu implements Screen {

    private final PropertyTycoon game;
    private OrthographicCamera camera;
    private Texture mainMenuTexture;
    private TextureRegion mainMenuTextureRegion;
    private TextureRegionDrawable mainMenuTextureRegionDrawable;
    private Skin mainMenuSkin;
    private Stage stage;

    public MainMenu(PropertyTycoon game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        this.stage = new Stage(new ScreenViewport());
        this.mainMenuTexture = new Texture(Gdx.files.internal("mainMenuTexture.png"));
        this.mainMenuSkin = new Skin(Gdx.files.internal("skin/comic-ui.json"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(mainMenuTexture, 0, 0);
        game.font.draw(game.batch, "Property Tycoon", 100, 150);
        game.font.draw(game.batch, "Tap anywhere to begin", 100, 100);
        game.batch.end();

        /**Button button1 = new TextButton("Start", mainMenuSkin, "small");
        button1.setSize(100, 100);
        button1.setPosition(400, 400);
        button1.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
                dispose();
                return true;
            }
        });
        stage.addActor(button1);**/


    }

    @Override
    public void dispose() {}

    @Override
    public void show() {}

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}
