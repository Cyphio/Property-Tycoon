package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.propertytycoonmakers.make.PropertyTycoon;
import javafx.scene.control.Tab;

public class GameScreen implements Screen {

    private final PropertyTycoon game;
    private OrthographicCamera camera;
    private Stage stage;
    private Texture gameScreenTexture;
    private Skin gameScreenSkin;




    MapRenderer renderer;

    Texture img;
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;

    public GameScreen(PropertyTycoon game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.gameScreenTexture = new Texture(Gdx.files.internal("board/board.PNG"));
        this.gameScreenTexture.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
        this.gameScreenSkin = new Skin(Gdx.files.internal("skin/comic-ui.json"));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        Table buttons = new Table();
        Table mainUI = new Table();
        Table boardCells = new Table();


        mainUI.setFillParent(true);
        mainUI.setDebug(true);
        buttons.setDebug(true);



        stage.clear();
        stage.addActor(mainUI);
        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.position.set(w/2,h/2,0);
        camera.zoom = (float) 1.5;


        camera.update();
//        tiledMap = new TmxMapLoader().load("core/assets/board/board.tmx");
//        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
//
//        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
//





        //this chiggaaaa---------------------------------------------------------------------------------------

        for(int u = 0; u <25; u++) {
            for (int i = 0; i < 25; i++) {


                Button newGame = new TextButton("", gameScreenSkin);

                boardCells.add(newGame).height(64).width(64);

            }
            boardCells.row();
        }

        boardCells.setBackground(new TextureRegionDrawable(gameScreenTexture));
        boardCells.setDebug(true);

        ///-----------------------------------------------------------------------------------------------








        Button pause = new TextButton("Pause", gameScreenSkin);
        Button rollDice = new TextButton("Roll Dice", gameScreenSkin);


        pause.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(game.PAUSE);
            }
        });

        rollDice.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //roll the dice
            }
        });



        buttons.row().pad(10, 0, 0, 20);
        buttons.add(pause);
        buttons.row().pad(10, 0, 0, 20);
        buttons.add(rollDice);


        boardCells.pad(10,10,10,10);

        mainUI.add();
        mainUI.add(boardCells);
        mainUI.add(buttons);
//        System.out.println(layer.getCell((int) h/2,(int) w/2));

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

//        camera.position.set(768,768,0);
//        camera.zoom = (float) 1.5;

//        tiledMapRenderer.setView(camera);
//        tiledMapRenderer.render();

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
