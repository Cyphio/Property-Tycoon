package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.propertytycoonmakers.make.PropertyTycoon;

public class GameScreen implements Screen {

    private final PropertyTycoon game;
    private OrthographicCamera camera;
    private Stage stage;
    private Texture gameScreenTexture;
    private Skin gameScreenSkin;

    private int HEIGHT = 1440;
    private int WIDTH = 2560;

    MapRenderer renderer;

    Texture img;
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;

    public GameScreen(PropertyTycoon game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.gameScreenTexture = new Texture(Gdx.files.internal("mainMenuTexture.png"));
        this.gameScreenTexture.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
        this.gameScreenSkin = new Skin(Gdx.files.internal("skin/comic-ui.json"));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        Table table = new Table();
        table.setFillParent(true);
        table.right().pad(0, 0, 0, 75);
        stage.clear();
        stage.addActor(table);

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);



        camera.update();
        tiledMap = new TmxMapLoader().load("core/assets/board/board.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        Button options = new TextButton("Options", gameScreenSkin);
        Button rollDice = new TextButton("Roll Dice", gameScreenSkin);

        options.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //change screen
            }
        });

        rollDice.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //roll the dice
            }
        });

        table.row().pad(10, 0, 0, 20);
        table.add(options);
        table.row().pad(10, 0, 0, 20);
        table.add(rollDice);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        camera.position.set(768,768,0);
        camera.zoom = (float) 1.5;





        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
