package Screens;

import Tiles.Property;
import Tiles.Tile;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.propertytycoonmakers.make.PropertyTycoon;
import main.GameController;
import misc.Coordinate;

import java.util.ArrayList;


public class GameScreen implements Screen {

    private final PropertyTycoon game;
    private OrthographicCamera camera;
    private Stage stage;
    private Texture gameScreenTexture;
    private Skin gameScreenSkin;
    private TiledMapTileLayer layer;
    private GameController gameCon;
    private Label tileNameLabel;
    private Window tilePopUpMenu;



    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;

    public GameScreen(PropertyTycoon game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.gameScreenTexture = new Texture(Gdx.files.internal("board/board.PNG"));
        this.gameScreenTexture.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
        this.gameScreenSkin = new Skin(Gdx.files.internal("skin/comic-ui.json"));

        //TILED MAP INITIALIZATION
        tiledMap = new TmxMapLoader().load("core/assets/board/board.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        layer = (TiledMapTileLayer) tiledMap.getLayers().get("Tile Layer 1");


        //GAME CONTROLLER
        gameCon = new GameController(layer);


        // POP UP MENU FOR PROPERTIES
        tileNameLabel = new Label("Name", gameScreenSkin);
        tileNameLabel.setColor(Color.BLUE);
        TextButton closePropMenu = new TextButton("Close", gameScreenSkin);
        tilePopUpMenu = new Window("Property Name", gameScreenSkin);
        tilePopUpMenu.add(tileNameLabel);
        tilePopUpMenu.row();
        tilePopUpMenu.add(closePropMenu);
        tilePopUpMenu.pack();
        float newWidth = 500, newHeight = 300;
        tilePopUpMenu.setBounds((Gdx.graphics.getWidth() - newWidth ) / 2, (Gdx.graphics.getHeight() - newHeight ) / 2, newWidth , newHeight );
        tilePopUpMenu.setVisible(false);
        stage.addActor(tilePopUpMenu);

        closePropMenu.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                tilePopUpMenu.setVisible(false);
            }
        });





//        Thread gameThread = new Thread((Runnable) gameCon);
//        gameThread.start();

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        final float w = Gdx.graphics.getWidth();
        final float h = Gdx.graphics.getHeight();
        Table buttons = new Table();
        buttons.setFillParent(true);
        buttons.setDebug(true);
        buttons.right();
        buttons.pad(0, 0, 0, 75);

        stage.addActor(buttons);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        camera.update();

        Button pause = new TextButton("Pause", gameScreenSkin);
        Button rollDice = new TextButton("Roll Dice", gameScreenSkin);


        pause.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new PauseScreen(game));
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


        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(mouse);

                System.out.println(mouse);

                try {


                    if (gameCon.retTile(layer.getCell((((int) mouse.x) / 64), (((int) mouse.y) / 64))) instanceof Property){

                        Tile tile = gameCon.retTile(layer.getCell((((int) mouse.x) / 64), (((int) mouse.y) / 64)));

                        tilePopUpMenu.getTitleLabel().setText(tile.getTileName());
                        tilePopUpMenu.getTitleLabel().setColor(Color.BLUE);

                        tilePopUpMenu.setVisible(true);




                    }
















                    boolean debugMode = true;
                    if (debugMode) {

                        ArrayList<Coordinate> cs = gameCon.retTile(layer.getCell((((int) mouse.x) / 64), (((int) mouse.y) / 64))).getCoordinates();
                        System.out.println(layer.getCell((((int) mouse.x) / 64), (((int) mouse.y) / 64)).setTile(null));
                        System.out.println(gameCon.retTile(layer.getCell((((int) mouse.x) / 64), (((int) mouse.y) / 64))));
                        System.out.println(gameCon.retTile(layer.getCell((((int) mouse.x) / 64), (((int) mouse.y) / 64))).getAvailableCoordinates().getX());


                        for (Coordinate c : cs) {

                            layer.getCell(c.getX(), c.getY()).setTile(null);

                        }
                    }


                } catch (Exception e) {
                    System.out.println(e.getMessage());
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

        camera.position.set(1120, 1120, 0);
        camera.zoom = (float) 2.5;

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();


        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {

            camera.rotate(90);
            camera.update();
        }


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



