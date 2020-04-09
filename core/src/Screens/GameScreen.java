package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.propertytycoonmakers.make.PropertyTycoon;
import main.GameController;
import main.Player;

import java.util.ArrayList;


public class GameScreen implements Screen {

    private final PropertyTycoon game;
    private OrthographicCamera camera;
    private Stage stage;
    private Texture gameScreenTexture;
    private Skin gameScreenSkin;
    private GameController gameController;
    private SpriteBatch sb;
    private Sprite playerSprite;

    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;

    public GameScreen(PropertyTycoon game) {
        this.game = game;
        this.gameController = new GameController();
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.gameScreenTexture = new Texture(Gdx.files.internal("board/board.PNG"));
        this.gameScreenTexture.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
        this.gameScreenSkin = new Skin(Gdx.files.internal("skin/comic-ui.json"));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        final float w = Gdx.graphics.getWidth();
        final float h = Gdx.graphics.getHeight();
        Table buttons = new Table();
        buttons.setFillParent(true);
        buttons.right();
        buttons.pad(0, 0, 0, 75);

        stage.clear();
        stage.addActor(buttons);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        camera.update();

        tiledMap = new TmxMapLoader().load("core/assets/board/board.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        final TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("Tile Layer 1");


        sb = new SpriteBatch();

        //This creates the Player object used for testing, in future the Player will be acquired via: gameController.getCurrentPlayer()
        Player currPlayer = new Player();

        //This sets and gets the sprite used for testing, in future players will be assigned sprites already and getPlayerToken() can be called on currPlayer
        Sprite testSprite = new Sprite(new Texture(Gdx.files.internal("tokens/token1.png")));
        currPlayer.setPlayerToken(testSprite);

        playerSprite = currPlayer.getPlayerToken();




        Button pause = new TextButton("Pause", gameScreenSkin);
        pause.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(game.PAUSE);
            }
        });

        Button rollDice = new TextButton("Roll Dice", gameScreenSkin);
        rollDice.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                /**
                //This creates the Player object used for testing, in future the Player will be acquired via: gameController.getCurrentPlayer()
                Player currPlayer = new Player();

                //This sets and gets the sprite used for testing, in future players will be assigned sprites already and getPlayerToken() can be called on currPlayer
                Sprite testSprite = new Sprite(new Texture(Gdx.files.internal("tokens/token1.png")));
                currPlayer.setPlayerToken(testSprite);

                playerSprite = currPlayer.getPlayerToken();**/

                playerSprite.setPosition(100, 100);
            }
        });

        buttons.row().pad(10, 0, 0, 20);
        buttons.add(pause);
        buttons.row().pad(10, 0, 0, 20);
        buttons.add(rollDice);

        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(mouse);
                System.out.println(mouse);
                try {
                    System.out.println(layer.getCell((((int) mouse.x) / 64), (((int) mouse.y) / 64)).setTile(null));
                } catch (Exception e) {
                    System.out.println("No tile");
                }
            }
        });
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        camera.position.set(768, 768, 0);
        camera.zoom = (float) 1.75;

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        sb.begin();
        playerSprite.draw(sb);
        sb.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {

            camera.rotate(90);
            camera.update();
        }
    }

    private void movePlayer(int x, int y) {

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



