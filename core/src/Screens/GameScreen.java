package Screens;

import Tiles.Jail;
import Tiles.Property;
import Tiles.Tile;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.propertytycoonmakers.make.PropertyTycoon;
import main.GameController;
import main.Player;
import misc.Coordinate;
import misc.RotatableLabel;

import java.util.ArrayList;
import java.util.Arrays;
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
    private SpriteBatch spriteBatch;
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private Viewport view;
    private Stage labelStage;

    private Window propertyPopUpWindow;
    private Player auctionPlayer;
    private int auctionPlayerNum;
    private Player highestBidder;
    private ArrayList<Player> auctionList;
    private Table auctionInfoBox;
    private TextButton auctionPropertyButton;
    private TextButton bidButton;
    private TextButton leaveButton;

    private Window tilePopUpMenu;
    private Window auctionMenu;
    private Table propInfoBox;
    private Property clickedProperty;
    private TextButton buyPropertyButton;
    private TextButton sellPropertyButton;
    private TextButton mortgagePropertyButton;
    private TextButton auctionPropertyButton;
    private Label propNameLabel;
    private Label propOwnerLabel;
    private Label propCostLabel;
    private Label propRentLabel;
    private Label propHouseCostLabel;
    private Label propHotelCostLabel;
    private ArrayList<Label> developmentPrices;

    private Window jailPopUpWindow;

    private Sound rollDiceFX;

    public GameScreen(PropertyTycoon game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);
        this.gameScreenTexture = new Texture(Gdx.files.internal("board/board.PNG"));
        this.gameScreenTexture.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
        this.gameScreenSkin = new Skin(Gdx.files.internal("skin/comic-ui.json"));

        //SOUNDS
        rollDiceFX = Gdx.audio.newSound(Gdx.files.internal("sound/dice_roll.mp3"));

        //TILED MAP INITIALIZATION
        tiledMap = new TmxMapLoader().load("core/assets/board/board.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        layer = (TiledMapTileLayer) tiledMap.getLayers().get("Tile Layer 1");


        //GAME CONTROLLER
        gameCon = new GameController(layer);
        //Thread controllerThread = new Thread(gameCon);
        //controllerThread.start();


        //TOKEN ADDED TO GO SCREEN
        spriteBatch = new SpriteBatch();
        for (Player p : game.players) {
            p.getPlayerToken().setPosition(p.getCurrentCoordinates().getX(), p.getCurrentCoordinates().getY());
        }

        // POP UP WINDOW SET UP
        propertyPopUpWindowSetUp();
        jailPopUpWindowSetUp();
        auctionPopUpWindowSetUp();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        //stage.setDebugAll(true);

        Table buttons = new Table();
        buttons.setFillParent(true);
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

                    Tile tile = gameCon.playerTurn();

                    Player p = gameCon.getCurrentPlayer();
                    p.getPlayerToken().setPosition(p.getCurrentCoordinates().getX(), p.getCurrentCoordinates().getY());

                    openPopUpWindow(tile);

                    if (!gameCon.getPlayAgain()) {
                        rollDice.setText("End Turn");
                    }
                }
                else if (rollDice.getText().toString().equals("End Turn")) {
                    closeAllWindows();
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
                try {
                    Tile tile = gameCon.retTile(layer.getCell((((int) mouse.x) / 64), (((int) mouse.y) / 64)));
                    if (!(tile instanceof Jail)) {
                        openPopUpWindow(tile);
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        });

        view = new FitViewport(w, h, camera);
        labelStage = new Stage(view);

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

    private TextureRegionDrawable getColouredBackground(Color colour) {
        Pixmap pm = new Pixmap(1,1, Pixmap.Format.RGB565);
        pm.setColor(colour);
        pm.fill();
        return new TextureRegionDrawable(new TextureRegion(new Texture(pm)));
    }

    private void openPopUpWindow(Tile tile) {
        if (tile instanceof Property) {
            clickedProperty = (Property) tile;

            if (clickedProperty.getPlayers().contains(gameCon.getCurrentPlayer()) && clickedProperty.getBuyable()) {
                buyPropertyButton.setVisible(true);
                auctionPropertyButton.setVisible(true);
            }
            else {
                buyPropertyButton.setVisible(false);
                auctionPropertyButton.setVisible(false);
            }

            if(clickedProperty.getOwner() == gameCon.getCurrentPlayer()) {
                sellPropertyButton.setVisible(true);
                mortgagePropertyButton.setVisible(true);
            }
            else {
                sellPropertyButton.setVisible(false);
                mortgagePropertyButton.setVisible(false);
            }

            propNameLabel.setText(clickedProperty.getTileName());
            propOwnerLabel.setText(clickedProperty.getOwnerName());
            propCostLabel.setText(clickedProperty.getCost());
            propInfoBox.setBackground(getColouredBackground(clickedProperty.getColor()));

            propRentLabel.setText(clickedProperty.getRent());
            for (int i = 0; i < clickedProperty.getDevPrices().size(); i++) {
                developmentPrices.get(i).setText(clickedProperty.getDevPrices().get(i));
            }
            propHouseCostLabel.setText(clickedProperty.getHousePrice());
            propHotelCostLabel.setText(clickedProperty.getHotelPrice());

            closeAllWindows();
            propertyPopUpWindow.setVisible(true);
        }
        else if (tile instanceof Jail && gameCon.getCurrentPlayer().getIsInJail()) {
            closeAllWindows();
            jailPopUpWindow.setVisible(true);
        }
        /**if (false) {

         ArrayList<Coordinate> cs = gameCon.retTile(layer.getCell((((int) mouse.x) / 64), (((int) mouse.y) / 64))).getAllCoordinates();
         System.out.println(cs.size());
         System.out.println(gameCon.retTile(layer.getCell((((int) mouse.x) / 64), (((int) mouse.y) / 64))));

         for (Coordinate c : cs) {

         layer.getCell(c.getX() / 64, c.getY() / 64).setTile(null);

         }
         }**/
    }

    private void closeAllWindows() {
        propertyPopUpWindow.setVisible(false);
        jailPopUpWindow.setVisible(false);
    }

    private void propertyPopUpWindowSetUp() {
        buyPropertyButton = new TextButton("Buy", gameScreenSkin);
        sellPropertyButton = new TextButton("Sell", gameScreenSkin);
        mortgagePropertyButton = new TextButton("Mortgage", gameScreenSkin);
        auctionPropertyButton = new TextButton("Auction", gameScreenSkin);

        TextButton closePropertyButton = new TextButton("Close", gameScreenSkin);

        propInfoBox = new Table();

        propNameLabel = new Label("", gameScreenSkin, "big");
        propNameLabel.setAlignment(Align.center);
        propOwnerLabel = new Label("", gameScreenSkin);
        propCostLabel = new Label("",gameScreenSkin);

        propInfoBox.add(propNameLabel).colspan(2).width(350);
        propInfoBox.row().pad(10, 0, 0, 0);
        propInfoBox.add(new Label("Owner:", gameScreenSkin)).left();
        propInfoBox.add(propOwnerLabel).right();
        propInfoBox.row().pad(5, 0, 0, 0);;
        propInfoBox.add(new Label("Cost:", gameScreenSkin)).left();
        propInfoBox.add(propCostLabel).right();
        propInfoBox.setBackground(getColouredBackground(Color.WHITE));

        Table propInfoBox2 = new Table();

        propRentLabel = new Label("", gameScreenSkin);
        propHouseCostLabel = new Label("", gameScreenSkin);
        propHotelCostLabel = new Label("", gameScreenSkin);

        developmentPrices = new ArrayList<>();
        for(int i=0; i<5; i++) {
            developmentPrices.add(new Label("", gameScreenSkin));
        }

        propInfoBox2.add(new Label("Rent:", gameScreenSkin)).left().width(300);
        propInfoBox2.add(propRentLabel).right();
        propInfoBox2.row().pad(20, 0, 0, 0);
        propInfoBox2.add(new Label("Rent with 1 house:", gameScreenSkin)).left();
        propInfoBox2.add(developmentPrices.get(0)).right();
        propInfoBox2.row().pad(20, 0, 0, 0);
        propInfoBox2.add(new Label("Rent with 2 houses:", gameScreenSkin)).left();
        propInfoBox2.add(developmentPrices.get(1)).right();
        propInfoBox2.row().pad(20, 0, 0, 0);
        propInfoBox2.add(new Label("Rent with 3 houses:", gameScreenSkin)).left();
        propInfoBox2.add(developmentPrices.get(2)).right();
        propInfoBox2.row().pad(20, 0, 0, 0);
        propInfoBox2.add(new Label("Rent with 4 houses:", gameScreenSkin)).left();
        propInfoBox2.add(developmentPrices.get(3)).right();
        propInfoBox2.row().pad(20, 0, 0, 0);
        propInfoBox2.add(new Label("Rent with a hotel", gameScreenSkin)).left();
        propInfoBox2.add(developmentPrices.get(4)).right();
        propInfoBox2.row().pad(40, 0, 0, 0);
        propInfoBox2.add(new Label("House cost:", gameScreenSkin)).left();
        propInfoBox2.add(propHouseCostLabel).right();
        propInfoBox2.row().pad(20, 0, 0, 0);
        propInfoBox2.add(new Label("Hotel cost:", gameScreenSkin)).left();
        propInfoBox2.add(propHotelCostLabel).right();

        propertyPopUpWindow = new Window("", gameScreenSkin);

        propertyPopUpWindow.add(propInfoBox).width(380).height(100).colspan(2);
        propertyPopUpWindow.row().pad(25, 0, 0, 0);
        propertyPopUpWindow.add(propInfoBox2).colspan(2);
        propertyPopUpWindow.row().pad(25, 0, 0, 0);
        propertyPopUpWindow.add(buyPropertyButton).left();
        propertyPopUpWindow.add(auctionPropertyButton).right();
        propertyPopUpWindow.row().pad(10, 0, 0, 0);
        propertyPopUpWindow.add(sellPropertyButton).left();
        propertyPopUpWindow.add(mortgagePropertyButton).right();
        propertyPopUpWindow.row().pad(10, 0, 0, 0);
        propertyPopUpWindow.add(closePropertyButton).colspan(2);
        propertyPopUpWindow.pack();

        float width = 400, height = 600;
        propertyPopUpWindow.setBounds((Gdx.graphics.getWidth() - width) / 2, (Gdx.graphics.getHeight() - height) / 2, width, height);
        propertyPopUpWindow.setVisible(false);

        stage.addActor(propertyPopUpWindow);

        buyPropertyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    if (clickedProperty.getBuyable() && clickedProperty.getPlayers().contains(gameCon.getCurrentPlayer())) {
                        clickedProperty.buyProperty(gameCon.getCurrentPlayer());
                        openPopUpWindow(clickedProperty);
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        });

        auctionPropertyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });

        sellPropertyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });

        mortgagePropertyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });

        closePropertyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                propertyPopUpWindow.setVisible(false);
            }
        });
    }

    private void auctionPopUpWindowSetUp() {
        final TextField auctionBid = new TextField("", gameScreenSkin);
        auctionBid.setMessageText("Enter Bid");
        bidButton = new TextButton("Bid", gameScreenSkin);
        leaveButton = new TextButton("Leave", gameScreenSkin);
        auctionMenu = new Window("Auction", gameScreenSkin);
        float newWidth = 300, newHeight = 500;
        auctionMenu.setBounds((Gdx.graphics.getWidth() - newWidth) / 2, (Gdx.graphics.getHeight() - newHeight) / 2, newWidth, newHeight);
        auctionMenu.setVisible(false);
        stage.addActor(auctionMenu);

        auctionBid.setTextFieldFilter(new TextField.TextFieldFilter() {
            @Override
            public boolean acceptChar(TextField textField, char c) {
                if (Character.toString(c).matches("^[0-9]")) {
                    return true;
                }
                return false;
            }
        });

        auctionPropertyButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                auctionPlayer = gameCon.getCurrentPlayer();
                auctionList = new ArrayList<>(Arrays.asList(game.players));
                auctionList.remove(auctionPlayer);
                auctionList.add(0, auctionPlayer);
                auctionMenu.setVisible(true);
            }

        });

        bidButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Integer.parseInt(auctionBid.getText()) > gameCon.getAuctionValue() && auctionPlayer.getMoney() > Integer.parseInt(auctionBid.getText())) {
                    gameCon.setAuctionValue(Integer.parseInt(auctionBid.getText()));
                    highestBidder = auctionPlayer;

                    if(auctionList.indexOf(auctionPlayer) < auctionList.size() - 1 ){
                        auctionPlayer = auctionList.get(auctionList.indexOf(auctionPlayer) + 1);
                    }
                    else
                    {
                        auctionPlayer = auctionList.get(0);
                    }
                }

                else{
                    System.out.println("not enough money");

                }

            }
        });

        leaveButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                auctionList.remove(auctionPlayer);
                if(auctionList.size() == 1){
                    auctionMenu.setVisible(false);
                    highestBidder.addProperty(clickedProperty);
                    highestBidder.makePurchase(gameCon.getAuctionValue());
                    gameCon.setAuctionValue(0);


                }

                if (auctionList.indexOf(auctionPlayer) < auctionList.size() - 1){
                    auctionPlayer = auctionList.get(auctionList.indexOf(auctionPlayer) + 1);
                }
                else {
                    auctionPlayer = auctionList.get(0);
                }

            }
        });

        auctionMenu.add(auctionBid).row();
        auctionMenu.add(bidButton);
        auctionMenu.add(leaveButton);
    }

    private void jailPopUpWindowSetUp() {
        Label jailInfoLabel = new Label("Either buy your way out of Jail for $50 or roll a double on your next go!", gameScreenSkin, "title");
        jailInfoLabel.setWrap(true);
        jailInfoLabel.setWidth(875);
        jailInfoLabel.setAlignment(Align.center);
        TextButton buyOutOfJailButton = new TextButton("Buy way out of Jail", gameScreenSkin);
        TextButton closeJailButton = new TextButton("Close", gameScreenSkin);

        jailPopUpWindow = new Window("", gameScreenSkin);

        jailPopUpWindow.add(jailInfoLabel).width(850);
        jailPopUpWindow.row().pad(10, 0, 0, 0);
        jailPopUpWindow.add(buyOutOfJailButton);
        jailPopUpWindow.row().pad(10, 0, 0, 0);
        jailPopUpWindow.add(closeJailButton);
        jailPopUpWindow.pack();

        float width = 875, height = 300;
        jailPopUpWindow.setBounds((Gdx.graphics.getWidth() - width) / 2, (Gdx.graphics.getHeight() - height) / 2, width, height);
        jailPopUpWindow.setVisible(false);

        stage.addActor(jailPopUpWindow);

        buyOutOfJailButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });

        closeJailButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jailPopUpWindow.setVisible(false);
            }
        });
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