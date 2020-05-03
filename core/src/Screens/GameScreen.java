package Screens;

import Tiles.*;
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
import com.badlogic.gdx.utils.Timer;
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
    private SpriteBatch spriteBatch;
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private Viewport view;
    private Stage labelStage;

    private Window propertyPopUpWindow;
    private Table propInfoBox;
    private Property clickedProperty;
    private TextButton buyPropertyButton;
    private TextButton sellPropertyButton;
    private TextButton mortgagePropertyButton;
    private TextButton auctionPropertyButton;
    private TextButton developPropertyButton;
    private TextButton closePropertyButton;
    private Label propNameLabel;
    private Label propOwnerLabel;
    private Label propCostLabel;
    private Label propHouseCostLabel;
    private Label propHotelCostLabel;
    private ArrayList<Label> developmentPrices;

    private Window stationPopUpWindow;
    private TextButton buyStationButton;
    private TextButton sellStationButton;
    private TextButton mortgageStationButton;
    private TextButton auctionStationButton;
    private TextButton closeStationButton;
    private Label stationNameLabel;
    private Label stationOwnerLabel;
    private Label stationCostLabel;

    private Window auctionPopUpWindow;
    private Player currBidder;
    private Player highestBidder;
    private ArrayList<Player> bidderList;
    private Label highestBidderNameLabel;
    private Label highestBid;
    private Label currBidderNameLabel;

    private Window jailPopUpWindow;

    private Table balances;
    private ArrayList<Label> playerBalanceLabels;

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
        stationPopUpWindowSetUp();
        jailPopUpWindowSetUp();
        auctionPopUpWindowSetUp();
        balanceTableSetUp();
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
                    /**if(tile instanceof Property || tile instanceof Station) {
                        openPopUpWindow(tile);
                    }**/
                    openPopUpWindow(tile);
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        });

        view = new FitViewport(w, h, camera);
        labelStage = new Stage(view);

        camera.position.set(2880, 1760, 0);
        camera.zoom = (float) 1.5;

        int angle = 0;
        for (int i = 0; i < 40; i++) {
            if (i == 1 || i == 11 || i == 21 || i == 31) {
                angle -= 90;
            }

            Tile tile = gameCon.getBoard().getTile(i);

            if(tile instanceof Property) {
                Coordinate c = tile.getCenterLabelCoordinate();

                RotatableLabel label = new RotatableLabel(new Label(tile.getTileName(), gameScreenSkin), c.getX(), c.getY(), angle, 1);

                labelStage.addActor(label);
            }
        }

        camera.zoom = (float) (((64*90)/h)/2);
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

            if(!clickedProperty.getColourAsString().equals("WHITE")) {

                if (clickedProperty.getPlayers().contains(gameCon.getCurrentPlayer()) && clickedProperty.getBuyable()) {
                    buyPropertyButton.setVisible(true);
                    auctionPropertyButton.setVisible(true);
                } else {
                    buyPropertyButton.setVisible(false);
                    auctionPropertyButton.setVisible(false);
                }

                if (clickedProperty.getOwner() == gameCon.getCurrentPlayer()) {
                    sellPropertyButton.setVisible(true);
                    mortgagePropertyButton.setVisible(true);
                    developPropertyButton.setVisible(true);
                } else {
                    sellPropertyButton.setVisible(false);
                    mortgagePropertyButton.setVisible(false);
                    developPropertyButton.setVisible(false);
                }

                if (clickedProperty.getPlayers().contains(gameCon.getCurrentPlayer())) {
                    closePropertyButton.setVisible(false);
                }

                if (clickedProperty.getOwned()) {
                    closePropertyButton.setVisible(true);
                }

                propNameLabel.setText(clickedProperty.getTileName());
                propOwnerLabel.setText(clickedProperty.getOwnerName());
                propCostLabel.setText("$" + clickedProperty.getCost());
                propInfoBox.setBackground(getColouredBackground(clickedProperty.getColor()));

                for (int i = 0; i < clickedProperty.getDevPrices().size(); i++) {
                    developmentPrices.get(i).setText("$" + clickedProperty.getDevPrices().get(i));
                }
                propHouseCostLabel.setText("$" + clickedProperty.getHousePrice());
                propHotelCostLabel.setText("$" + clickedProperty.getHotelPrice());

                closeAllWindows();
                propertyPopUpWindow.setVisible(true);
            }
            else {
                if (clickedProperty.getPlayers().contains(gameCon.getCurrentPlayer()) && clickedProperty.getBuyable()) {
                    buyStationButton.setVisible(true);
                    auctionStationButton.setVisible(true);
                } else {
                    buyStationButton.setVisible(false);
                    auctionStationButton.setVisible(false);
                }

                if (clickedProperty.getOwner() == gameCon.getCurrentPlayer()) {
                    sellStationButton.setVisible(true);
                    mortgageStationButton.setVisible(true);
                } else {
                    sellStationButton.setVisible(false);
                    mortgageStationButton.setVisible(false);
                }

                if (clickedProperty.getPlayers().contains(gameCon.getCurrentPlayer())) {
                    closeStationButton.setVisible(false);
                }

                if (clickedProperty.getOwned()) {
                    closeStationButton.setVisible(true);
                }

                stationNameLabel.setText(clickedProperty.getTileName());
                stationOwnerLabel.setText(clickedProperty.getOwnerName());
                stationCostLabel.setText("$" + clickedProperty.getCost());

                closeAllWindows();
                stationPopUpWindow.setVisible(true);
            }

        }
        else if (tile instanceof Jail && gameCon.getCurrentPlayer().getIsInJail()) {
            closeAllWindows();
            jailPopUpWindow.setVisible(true);
        }
        else if (tile instanceof OpportunityKnocks) {
            closeAllWindows();
            quickPopUpWindow("This is placeholder text but will eventually hold the info of the Opportunity knocks card", 300, 800, 0.5f);
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
        stationPopUpWindow.setVisible(false);
        auctionPopUpWindow.setVisible(false);
        jailPopUpWindow.setVisible(false);
    }

    private void propertyPopUpWindowSetUp() {
        buyPropertyButton = new TextButton("Buy", gameScreenSkin);
        sellPropertyButton = new TextButton("Sell", gameScreenSkin);

        mortgagePropertyButton = new TextButton("Mortgage", gameScreenSkin);
        auctionPropertyButton = new TextButton("Auction", gameScreenSkin);

        developPropertyButton = new TextButton("Develop", gameScreenSkin);
        closePropertyButton = new TextButton("Close", gameScreenSkin);

        propInfoBox = new Table();

        propNameLabel = new Label("", gameScreenSkin, "big");
        propNameLabel.setAlignment(Align.center);
        propOwnerLabel = new Label("", gameScreenSkin);
        propCostLabel = new Label("",gameScreenSkin);

        propInfoBox.add(propNameLabel).colspan(2).width(350);
        propInfoBox.row().pad(10, 0, 0, 0);
        propInfoBox.add(new Label("Owner:", gameScreenSkin)).left();
        propInfoBox.add(propOwnerLabel).right();
        propInfoBox.row().pad(5, 0, 0, 0);
        propInfoBox.add(new Label("Cost:", gameScreenSkin)).left();
        propInfoBox.add(propCostLabel).right();
        propInfoBox.setBackground(getColouredBackground(Color.WHITE));

        Table propInfoBox2 = new Table();

        propHouseCostLabel = new Label("", gameScreenSkin);
        propHotelCostLabel = new Label("", gameScreenSkin);

        developmentPrices = new ArrayList<>();
        for(int i=0; i<6; i++) {
            developmentPrices.add(new Label("", gameScreenSkin));
        }

        propInfoBox2.add(new Label("Rent:", gameScreenSkin)).left().width(350);
        propInfoBox2.add(developmentPrices.get(0)).right();
        propInfoBox2.row().pad(20, 0, 0, 0);
        propInfoBox2.add(new Label("Rent with 1 house:", gameScreenSkin)).left();
        propInfoBox2.add(developmentPrices.get(1)).right();
        propInfoBox2.row().pad(20, 0, 0, 0);
        propInfoBox2.add(new Label("Rent with 2 houses:", gameScreenSkin)).left();
        propInfoBox2.add(developmentPrices.get(2)).right();
        propInfoBox2.row().pad(20, 0, 0, 0);
        propInfoBox2.add(new Label("Rent with 3 houses:", gameScreenSkin)).left();
        propInfoBox2.add(developmentPrices.get(3)).right();
        propInfoBox2.row().pad(20, 0, 0, 0);
        propInfoBox2.add(new Label("Rent with 4 houses:", gameScreenSkin)).left();
        propInfoBox2.add(developmentPrices.get(4)).right();
        propInfoBox2.row().pad(20, 0, 0, 0);
        propInfoBox2.add(new Label("Rent with a hotel", gameScreenSkin)).left();
        propInfoBox2.add(developmentPrices.get(5)).right();
        propInfoBox2.row().pad(40, 0, 0, 0);
        propInfoBox2.add(new Label("House cost:", gameScreenSkin)).left();
        propInfoBox2.add(propHouseCostLabel).right();
        propInfoBox2.row().pad(20, 0, 0, 0);
        propInfoBox2.add(new Label("Hotel cost:", gameScreenSkin)).left();
        propInfoBox2.add(propHotelCostLabel).right();

        propertyPopUpWindow = new Window("", gameScreenSkin);

        propertyPopUpWindow.add(propInfoBox).colspan(2).expand().fill();
        propertyPopUpWindow.row().pad(25, 0, 0, 0);
        propertyPopUpWindow.add(propInfoBox2).colspan(2);
        propertyPopUpWindow.row().pad(25, 0, 0, 0);
        propertyPopUpWindow.add(buyPropertyButton).left();
        propertyPopUpWindow.add(auctionPropertyButton).right();
        propertyPopUpWindow.row().pad(10, 0, 0, 0);
        propertyPopUpWindow.add(sellPropertyButton).left();
        propertyPopUpWindow.add(mortgagePropertyButton).right();
        propertyPopUpWindow.row().pad(10, 0, 0, 0);
        propertyPopUpWindow.add(developPropertyButton).left();
        propertyPopUpWindow.add(closePropertyButton).right();

        float width = 425, height = 600;

        propertyPopUpWindow.setBounds((Gdx.graphics.getWidth() - width) / 2, (Gdx.graphics.getHeight() - height) / 2, width, height);
        propertyPopUpWindow.setVisible(false);

        stage.addActor(propertyPopUpWindow);

        buyPropertyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    if (clickedProperty.getBuyable() && clickedProperty.getPlayers().contains(gameCon.getCurrentPlayer())) {
                        clickedProperty.buyProperty(gameCon.getCurrentPlayer(), clickedProperty.getCost());
                        closeAllWindows();
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
                auctionPopUpWindowSetUp(); //called to add the title and colour of the property to the auction window

                currBidder = gameCon.getCurrentPlayer();
                bidderList = new ArrayList<>(Arrays.asList(game.players));

                for(int i = 0; i < bidderList.indexOf(currBidder) -1; i++){
                    bidderList.add(bidderList.get(i));
                    bidderList.remove(i);
                }

                currBidderNameLabel.setText(gameCon.getCurrentPlayer().getName());
                closeAllWindows();
                auctionPopUpWindow.setVisible(true);
            }
        });

        sellPropertyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickedProperty.sellProperty(gameCon.getCurrentPlayer(), clickedProperty.getCost());
                propertyPopUpWindow.setVisible(false);
            }
        });

        mortgagePropertyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickedProperty.setMortgaged(gameCon.getCurrentPlayer(), clickedProperty.getCost());
            }
        });

        developPropertyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(gameCon.developProperty(clickedProperty, gameCon.getCurrentPlayer())) {
                    quickPopUpWindow("Able to develop", 100, 350, 0.5f);
                }
                else {
                    quickPopUpWindow("Not able to develop", 100, 350, 0.5f);
                }
            }
        });

        closePropertyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                propertyPopUpWindow.setVisible(false);
            }
        });
    }

    private void stationPopUpWindowSetUp() {
        buyStationButton = new TextButton("Buy", gameScreenSkin);
        sellStationButton = new TextButton("Sell", gameScreenSkin);

        mortgageStationButton = new TextButton("Mortgage", gameScreenSkin);
        auctionStationButton = new TextButton("Auction", gameScreenSkin);

        closeStationButton = new TextButton("Close", gameScreenSkin);

        Table stationInfoBox = new Table();

        stationNameLabel = new Label("", gameScreenSkin, "big");
        stationNameLabel.setAlignment(Align.center);
        stationOwnerLabel = new Label("", gameScreenSkin);
        stationCostLabel = new Label("",gameScreenSkin);

        stationInfoBox.add(stationNameLabel).colspan(2).width(350);
        stationInfoBox.row().pad(10, 0, 0, 0);
        stationInfoBox.add(new Label("Owner:", gameScreenSkin)).left();
        stationInfoBox.add(stationOwnerLabel).right();
        stationInfoBox.row().pad(5, 0, 0, 0);
        stationInfoBox.add(new Label("Cost:", gameScreenSkin)).left();
        stationInfoBox.add(stationCostLabel).right();

        Image trainImg = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("trainImage.png")))));

        Table stationInfoBox2 = new Table();

        stationInfoBox2.add(new Label("Rent with one station owned:", gameScreenSkin)).left().width(350);
        stationInfoBox2.add(new Label("$50", gameScreenSkin)).right();
        stationInfoBox2.row().pad(20, 0, 0, 0);
        stationInfoBox2.add(new Label("Rent with two stations owned:", gameScreenSkin)).left();
        stationInfoBox2.add(new Label("$100", gameScreenSkin)).right();
        stationInfoBox2.row().pad(20, 0, 0, 0);
        stationInfoBox2.add(new Label("Rent with three stations owned:", gameScreenSkin)).left();
        stationInfoBox2.add(new Label("$150", gameScreenSkin)).right();
        stationInfoBox2.row().pad(20, 0, 0, 0);
        stationInfoBox2.add(new Label("Rent with four stations owned:", gameScreenSkin)).left();
        stationInfoBox2.add(new Label("$200", gameScreenSkin)).right();
        stationInfoBox2.row().pad(20, 0, 0, 0);

        stationPopUpWindow = new Window("", gameScreenSkin);

        stationPopUpWindow.add(stationInfoBox).colspan(2).expand().fill();
        stationPopUpWindow.row().pad(30, 0, 0, 0);
        stationPopUpWindow.add(trainImg).colspan(2).width(392.92f).height(100);
        stationPopUpWindow.row().pad(50, 0, 0, 0);
        stationPopUpWindow.add(stationInfoBox2).colspan(2);
        stationPopUpWindow.row().pad(20, 0, 0, 0);
        stationPopUpWindow.add(buyStationButton).left();
        stationPopUpWindow.add(auctionStationButton).right();
        stationPopUpWindow.row().pad(10, 0, 0, 0);
        stationPopUpWindow.add(sellStationButton).left();
        stationPopUpWindow.add(mortgageStationButton).right();
        stationPopUpWindow.row().pad(10, 0, 0, 0);
        stationPopUpWindow.add(closeStationButton).colspan(2);
        stationPopUpWindow.pack();

        float width = 425, height = 600;
        stationPopUpWindow.setBounds((Gdx.graphics.getWidth() - width) / 2, (Gdx.graphics.getHeight() - height) / 2, width, height);
        stationPopUpWindow.setVisible(false);

        stage.addActor(stationPopUpWindow);

        buyStationButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    if (clickedProperty.getBuyable() && clickedProperty.getPlayers().contains(gameCon.getCurrentPlayer())) {
                        clickedProperty.buyProperty(gameCon.getCurrentPlayer(), clickedProperty.getCost());
                        closeAllWindows();
                        openPopUpWindow(clickedProperty);
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        });

        auctionStationButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                auctionPopUpWindowSetUp(); //called to add the title and colour of the property to the auction window

                currBidder = gameCon.getCurrentPlayer();
                bidderList = new ArrayList<>(Arrays.asList(game.players));

                for(int i = 0; i < bidderList.indexOf(currBidder) -1; i++){
                    bidderList.add(bidderList.get(i));
                    bidderList.remove(i);
                }

                currBidderNameLabel.setText(gameCon.getCurrentPlayer().getName());
                closeAllWindows();
                auctionPopUpWindow.setVisible(true);
            }
        });

        sellStationButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickedProperty.sellProperty(gameCon.getCurrentPlayer(), clickedProperty.getCost());
                propertyPopUpWindow.setVisible(false);
            }
        });

        mortgageStationButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickedProperty.setMortgaged(gameCon.getCurrentPlayer(), clickedProperty.getCost());
            }
        });

        closeStationButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stationPopUpWindow.setVisible(false);
            }
        });
    }

    private void auctionPopUpWindowSetUp() {
        final Label highestBidderLabel = new Label("Highest bidder: ", gameScreenSkin, "big");
        highestBidderNameLabel = new Label("", gameScreenSkin, "big");
        Label atLabel = new Label(" at ", gameScreenSkin, "big");
        highestBid = new Label("", gameScreenSkin, "big");

        final Table highestBidderTable = new Table();
        highestBidderTable.add(highestBidderLabel);
        highestBidderTable.add(highestBidderNameLabel);
        highestBidderTable.add(atLabel);
        highestBidderTable.add(highestBid);

        highestBidderTable.setVisible(false);

        final Label currBidderLabel = new Label("Current bidder: ", gameScreenSkin, "big");
        currBidderNameLabel = new Label("", gameScreenSkin, "big");

        Table currBidderTable = new Table();
        currBidderTable.add(currBidderLabel);
        currBidderTable.add(currBidderNameLabel);

        final TextField auctionBid = new TextField("", gameScreenSkin);

        auctionBid.setMessageText("Enter Bid");
        auctionBid.setAlignment(Align.center);
        TextButton bidButton = new TextButton("Bid", gameScreenSkin);
        TextButton leaveButton = new TextButton("Leave", gameScreenSkin);

        Table auctionPopUpTable = new Table();

        if(clickedProperty != null) {
            auctionPopUpTable.setBackground(getColouredBackground(clickedProperty.getColor()));
            auctionPopUpTable.add(new Label(clickedProperty.getTileName(), gameScreenSkin, "big"));
            auctionPopUpTable.row().pad(40, 0, 0, 0);
        }
        auctionPopUpTable.add(highestBidderTable);
        auctionPopUpTable.row().pad(40, 0, 0, 0);
        auctionPopUpTable.add(currBidderTable);
        auctionPopUpTable.row().pad(20, 0, 0, 0);
        auctionPopUpTable.add(auctionBid).colspan(2);
        auctionPopUpTable.row().pad(10, 0, 0, 0);
        auctionPopUpTable.add(bidButton).colspan(2);
        auctionPopUpTable.row().pad(10, 0, 0, 0);
        auctionPopUpTable.add(leaveButton).colspan(2);

        auctionPopUpWindow = new Window("", gameScreenSkin);
        auctionPopUpWindow.add(auctionPopUpTable).expand().fill();

        float width = 600, height = 500;
        auctionPopUpWindow.setBounds((Gdx.graphics.getWidth() - width) / 2, (Gdx.graphics.getHeight() - height) / 2, width, height);
        auctionPopUpWindow.setVisible(false);

        stage.addActor(auctionPopUpWindow);

        auctionBid.setTextFieldFilter(new TextField.TextFieldFilter() {
            @Override
            public boolean acceptChar(TextField textField, char c) {
                return Character.toString(c).matches("^[0-9]");
            }
        });

        bidButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!auctionBid.getText().equals("")){
                    if (Integer.parseInt(auctionBid.getText()) > gameCon.getAuctionValue() && currBidder.getMoney() >= Integer.parseInt(auctionBid.getText())) {
                        gameCon.setAuctionValue(Integer.parseInt(auctionBid.getText()));
                        highestBidder = currBidder;
                        highestBid.setText("$" + gameCon.getAuctionValue());
                        highestBidderTable.setVisible(true);

                        auctionBid.setText("");

                        if(bidderList.indexOf(currBidder) < bidderList.size() - 1 ){
                            currBidder = bidderList.get(bidderList.indexOf(currBidder) + 1);
                        }
                        else
                        {
                            currBidder = bidderList.get(0);
                        }
                        highestBidderNameLabel.setText(highestBidder.getName());
                        currBidderNameLabel.setText(currBidder.getName());
                    }
                    else if (Integer.parseInt(auctionBid.getText()) <= gameCon.getAuctionValue()) {
                        quickPopUpWindow("Bid not high enough", 100, 350, 0.5f);
                    }
                    else if (currBidder.getMoney() < Integer.parseInt(auctionBid.getText())) {
                        quickPopUpWindow("Not enough money", 100, 350,0.5f);
                    }
                }
                currBidderNameLabel.setText(currBidder.getName());
            }
        });

        leaveButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                bidderList.remove(currBidder);
                if(bidderList.size() == 1 && highestBidder == null) {
                    auctionPopUpWindow.setVisible(false);
                    gameCon.setAuctionValue(0);
                }

                if(bidderList.size() == 1 && highestBidder != null){
                    auctionPopUpWindow.setVisible(false);
                    highestBidder.addProperty(clickedProperty);
                    clickedProperty.buyProperty(highestBidder, gameCon.getAuctionValue());
                    gameCon.setAuctionValue(0);
                }

                if (bidderList.indexOf(currBidder) < bidderList.size() - 1){
                    currBidder = bidderList.get(bidderList.indexOf(currBidder) + 1);
                }
                else {
                    currBidder = bidderList.get(0);
                }
                currBidderNameLabel.setText(currBidder.getName());
            }
        });
    }

    private void jailPopUpWindowSetUp() {
        Label jailInfoLabel = new Label("Either buy your way out of Jail for $50 or roll a double on your next go!", gameScreenSkin, "title");
        jailInfoLabel.setWrap(true);
        jailInfoLabel.setWidth(875);
        jailInfoLabel.setAlignment(Align.center);
        TextButton buyOutOfJailButton = new TextButton("Buy way out of Jail", gameScreenSkin);

        jailPopUpWindow = new Window("", gameScreenSkin);
        jailPopUpWindow.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("gameScreenJail.png")))));

        jailPopUpWindow.add(jailInfoLabel).width(850);
        jailPopUpWindow.row().pad(10, 0, 0, 0);
        jailPopUpWindow.add(buyOutOfJailButton);
        jailPopUpWindow.pack();

        float width = 875, height = 220;
        jailPopUpWindow.setBounds((Gdx.graphics.getWidth() - width) / 2, (Gdx.graphics.getHeight() - height) / 2, width, height);
        jailPopUpWindow.setVisible(false);

        stage.addActor(jailPopUpWindow);

        buyOutOfJailButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });
    }

    public void balanceTableSetUp(){
        balances = new Table();
        playerBalanceLabels = new ArrayList<>();
        for (Player player: game.players) {
            Label l = new Label(player.getName() + ": $"+ player.getMoney(), gameScreenSkin, "title");
            playerBalanceLabels.add(l);

            balances.add(l).left();
            balances.row();
        }
        balances.setSize(100,100);
        balances.left();
        balances.pad(0,10,0,0);
        balances.setFillParent(true);
        //balances.setDebug(true);
        //balances.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("gameScreenJail.png")))));
        stage.addActor(balances);
    }

    private void quickPopUpWindow(String msg, float height, float width, float time) {
        final Window window = new Window("", gameScreenSkin);
        final Label label = new Label(msg, gameScreenSkin, "big");
        label.setWrap(true);
        label.setAlignment(Align.center);
        window.add(label).width(width-50);
        window.setBounds((Gdx.graphics.getWidth() - width) / 2, (Gdx.graphics.getHeight() - height) / 2, width, height);
        stage.addActor(window);
        window.setVisible(true);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                window.setVisible(false);
            }
        }, time);
    }

    public void updateBalances(){
        for (int i = 0 ; i < game.players.length; i++){
            Player player = game.players[i];
            playerBalanceLabels.get(i).setText(player.getName() + ": $"+ player.getMoney());
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        for (Player p : game.players) {
            p.getPlayerToken().draw(spriteBatch);
        }
        spriteBatch.end();

        camera.update();

        updateBalances();

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