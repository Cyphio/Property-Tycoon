package Screens;

import Tiles.Jail;
import Tiles.Property;
import Tiles.Tile;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.propertytycoonmakers.make.PropertyTycoon;
import main.GameController;
import main.Player;
import misc.Coordinate;
import misc.RotatableLabel;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


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
    private SpriteBatch spriteBatch;
    private Property clickedProperty;
    private TextButton buyPropertyButton;
    private Label ownerLabel;
    private Label propertyCost;


    private Sound rollDiceFX;

    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;

    Viewport view;
    Stage labelStage;


    public GameScreen(PropertyTycoon game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);
        this.gameScreenTexture = new Texture(Gdx.files.internal("board/board.PNG"));
        this.gameScreenTexture.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
        this.gameScreenSkin = new Skin(Gdx.files.internal("skin/comic-ui.json"));

        rollDiceFX = Gdx.audio.newSound(Gdx.files.internal("sound/dice_roll.mp3"));

        //TILED MAP INITIALIZATION
        tiledMap = new TmxMapLoader().load("core/assets/board/board.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        layer = (TiledMapTileLayer) tiledMap.getLayers().get("Tile Layer 1");


        //GAME CONTROLLER
        gameCon = new GameController(layer);
//        Thread controllerThread = new Thread(gameCon);
//        controllerThread.start();


        //TOKEN ADDED TO GO SCREEN
        spriteBatch = new SpriteBatch();

        for (Player p : game.players) {

            p.getPlayerToken().setPosition(p.getCurrentCoordinates().getX(), p.getCurrentCoordinates().getY());
        }


        // POP UP MENU FOR PROPERTIES
        TextButton closePropMenu = new TextButton("Close", gameScreenSkin);
        buyPropertyButton = new TextButton("Buy", gameScreenSkin);
        ownerLabel = new Label("FOR SALE", gameScreenSkin);
        propertyCost = new Label("COST",gameScreenSkin);


        tilePopUpMenu = new Window("Property Name", gameScreenSkin);
        tilePopUpMenu.add(ownerLabel);
        tilePopUpMenu.row();
        tilePopUpMenu.add(propertyCost);
        tilePopUpMenu.row();
        tilePopUpMenu.add(buyPropertyButton);
        tilePopUpMenu.row();
        tilePopUpMenu.add(closePropMenu);
        tilePopUpMenu.pack();
        float newWidth = 800, newHeight = 500;
        tilePopUpMenu.setBounds((Gdx.graphics.getWidth() - newWidth) / 2, (Gdx.graphics.getHeight() - newHeight) / 2, newWidth, newHeight);
        tilePopUpMenu.setVisible(false);


        stage.addActor(tilePopUpMenu);

        closePropMenu.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                tilePopUpMenu.setVisible(false);
            }
        });

        buyPropertyButton.addListener(new ClickListener() {


            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    if (clickedProperty.getBuyable() && clickedProperty.getPlayers().contains(gameCon.getCurrentPlayer())) {
                        clickedProperty.buyProperty(gameCon.getCurrentPlayer());
                    }
                } catch (Exception e) {
                }
            }
        });
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

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
        final TextButton rollDice = new TextButton("Roll Dice", gameScreenSkin);


        pause.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new PauseScreen(game));
            }
        });

        rollDice.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (rollDice.getText().toString().equals("Roll Dice")) {
                    if (game.getPreferences().isFxEnabled()) {
                        rollDiceFX.play(game.getPreferences().getFxVolume());
                        try {
                            TimeUnit.SECONDS.sleep(3);
                        } catch(Exception e) {
                            e.getMessage();
                        }
                    }

                    gameCon.playerTurn();

                    Player p = gameCon.getCurrentPlayer();
                    p.getPlayerToken().setPosition(p.getCurrentCoordinates().getX(), p.getCurrentCoordinates().getY());

                    if (!gameCon.getPlayAgain()) {
                        rollDice.setText("End Turn");
                    }
                } else {
                    gameCon.endTurn();
                    rollDice.setText("Roll Dice");
                }
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

                    Tile tile = gameCon.retTile(layer.getCell((((int) mouse.x) / 64), (((int) mouse.y) / 64)));


                    if (tile instanceof Property) {


                        clickedProperty = (Property) tile;


                        if (clickedProperty.getPlayers().contains(gameCon.getCurrentPlayer()) && clickedProperty.getBuyable()) {


                            buyPropertyButton.setVisible(true);

                        } else {

                            buyPropertyButton.setVisible(false);

                        }


                        ownerLabel.setText("Owned by:        "+clickedProperty.getOwnerName());
                        propertyCost.setText("Cost:      "+clickedProperty.getCost());
                        tilePopUpMenu.getTitleLabel().setText(tile.getTileName());
                        tilePopUpMenu.getTitleLabel().setColor(Color.BLUE);
                        tilePopUpMenu.setVisible(true);


                    } else if (tile instanceof Jail) {

                        System.out.print(tile.getAllCoordinates().get(1).getX());


                    } else {

                        tilePopUpMenu.setVisible(false);


                    }


                    if (false) {

                        ArrayList<Coordinate> cs = gameCon.retTile(layer.getCell((((int) mouse.x) / 64), (((int) mouse.y) / 64))).getAllCoordinates();
                        System.out.println(cs.size());
                        System.out.println(gameCon.retTile(layer.getCell((((int) mouse.x) / 64), (((int) mouse.y) / 64))));

                        for (Coordinate c : cs) {

                            layer.getCell(c.getX() / 64, c.getY() / 64).setTile(null);


                        }
                    }


                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }


            }
        });

        view = new FitViewport(w, h, camera);
        labelStage = new Stage(view);

//        Viewport v = new ScreenViewport(camera);
//        stage.setViewport(v);


        camera.position.set(1120, 1120, 0);
        camera.zoom = (float) 1.5;

        int angle = 0;
        for (int i = 0; i < 40; i++) {

            if (i == 1 || i == 11 || i == 21 || i == 31) {

                angle -= 90;

            }

            Tile tile = gameCon.getBoard().getTile(i);
            Coordinate c = tile.getCenterLabelCoordinate();

            RotatableLabel label = new RotatableLabel(new Label(tile.getTileName(), gameScreenSkin), c.getX(), c.getY(), angle, 1);

//            layer.getCell((c.getX() -32)/ 64, (c.getY()-32) / 64).setTile(null);

            labelStage.addActor(label);


        }

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.zoom = 2.5f;

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        for (Player p : game.players) {
            p.getPlayerToken().draw(spriteBatch);


        }
        spriteBatch.end();


        camera.update();

        labelStage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        labelStage.draw();

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



