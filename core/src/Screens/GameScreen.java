package Screens;

import Tiles.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.propertytycoonmakers.make.PropertyTycoon;
import main.GameBoard;
import main.GameController;
import main.Player;
import misc.Coordinate;
import misc.RotatableLabel;
import misc.ScrollableStage;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class GameScreen implements Screen {

    private final PropertyTycoon game;
    private OrthographicCamera camera;
    private ScrollableStage stage;
    private Texture gameScreenTexture;
    private Skin gameScreenSkin;
    private TiledMapTileLayer layer;
    private GameController gameCon;
    private SpriteBatch spriteBatch;
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private Viewport view;
    private Stage labelStage;

    private TextButton rollDice;

    private Window propertyPopUpWindow;
    private Table propInfoBox;
    private Ownable clickedProperty;
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

    private Window servicePopUpWindow;
    private TextButton buyServiceButton;
    private TextButton sellServiceButton;
    private TextButton mortgageServiceButton;
    private TextButton auctionServiceButton;
    private TextButton closeServiceButton;
    private Label serviceNameLabel;
    private Label serviceOwnerLabel;
    private Label serviceCostLabel;
    private Table serviceInfoBox2;
    private Image serviceImg;

    private Window auctionPopUpWindow;
    private Player currBidder;
    private Player highestBidder;
    private ArrayList<Player> bidderList;
    private Label highestBidderNameLabel;
    private Label highestBid;
    private Label currBidderNameLabel;

    private Window jailPopUpWindow;

    private ArrayList<Label> playerBalanceLabels;

    private Texture oneHouseTexture;
    private Texture twoHouseTexture;
    private Texture threeHouseTexture;
    private Texture fourHouseTexture;
    private Texture hotelTexture;

    ArrayList<Sprite> propertyHouseAndHotelSprites;
    private ArrayList<Sprite> ownedProperties;

    ArrayList<Sprite> propertyIcons;

    private Sound rollDiceFX;

    private ClickListener clickListener;
    private TextField auctionBid;

    private float gameLength;
    private float reverseTime;
    private Label timerLabel;

    public GameScreen(PropertyTycoon game) {
        this.game = game;
        stage = new ScrollableStage(this);

        Gdx.input.setInputProcessor(stage);

        gameLength = 0;
        reverseTime = 0;

        serviceInfoBox2 = new Table();
        serviceImg = new Image();

        gameScreenSkin = new Skin(Gdx.files.internal("skin/comic-ui.json"));

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
        servicePopUpWindowSetUp();
        gameInfoTableSetUp();
        jailPopUpWindowSetUp();
        auctionPopUpWindowSetUp();

        oneHouseTexture = new Texture(Gdx.files.internal("property-icons/1-house.png"));
        twoHouseTexture = new Texture(Gdx.files.internal("property-icons/2-house.png"));
        threeHouseTexture = new Texture(Gdx.files.internal("property-icons/3-house.png"));
        fourHouseTexture = new Texture(Gdx.files.internal("property-icons/4-house.png"));
        hotelTexture = new Texture(Gdx.files.internal("property-icons/hotel.png"));

        ownedProperties = new ArrayList<>();
        propertyIcons = new ArrayList<>();

        propertyHouseAndHotelSprites = new ArrayList<>();
        updatePropertyDevelopmentSprites();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        view = new FitViewport(w, h, camera);
        labelStage = new Stage(view);
        camera.position.set(2880, 1760, 0);
        camera.zoom = (float) (((64 * 90) / h) / 2);

        int angle = 0;
        for (int i = 0; i < 40; i++) {
            if (i == 1 || i == 11 || i == 21 || i == 31) {
                angle -= 90;
            }
            Tile tile = gameCon.getBoard().getTile(i);
            if (tile instanceof SmallTile) {
                Coordinate c = ((SmallTile) tile).getCenterLabelCoordinate();
                RotatableLabel label = new RotatableLabel(new Label(((SmallTile) tile).getTileName(), gameScreenSkin), c.getX(), c.getY(), angle, 1);
                labelStage.addActor(label);
            }
            if (tile instanceof GovProperties) {
                propertyIcons.add(((GovProperties) tile).getIcon());
            }
            if (tile instanceof Tax) {
                propertyIcons.add(((Tax) tile).getIcon());
            }
            if (tile instanceof PotLuck) {
                propertyIcons.add(((PotLuck) tile).getIcon());
            }
            if (tile instanceof OpportunityKnocks) {
                propertyIcons.add(((OpportunityKnocks) tile).getIcon());
            }
        }

        stage.addListener(clickListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(mouse);
                try {
                    Tile tile = gameCon.retTile(layer.getCell((((int) mouse.x) / 64), (((int) mouse.y) / 64)));
                    //if((tile instanceof Property|| tile instanceof Station)) {
                    openPopUpWindow(tile);
                    //}
                }
                catch (Exception e) {
                    e.getMessage();
                }
            }
        });

        setTileCellColors();
    }

    public void setTileCellColors() {
        TiledMapTileSet set = tiledMap.getTileSets().getTileSet(0);
        for (int i = 0; i < 40; i++) {

            GameBoard board = gameCon.getBoard();
            Tile t = board.getTile(i);

            int id = 0;

            if (t instanceof Property) {

                ArrayList<Coordinate> coords = board.getTile(i).getAllCoordinates();

                switch (((Property) t).getColourAsString().toUpperCase()) {
                    case "BLUE":
                        id = 15;
                        break;
                    case "SKY":
                        id = 16;
                        break;
                    case "YELLOW":
                        id = 17;
                        break;
                    case "GREEN":
                        id = 18;
                        break;
                    case "ORANGE":
                        id = 19;
                        break;
                    case "BROWN":
                        id = 20;
                        break;
                    case "PURPLE":
                        id = 21;
                        break;
                    case "RED":
                        id = 22;
                        break;
                    default:
                        id = 1;
                        break;

                }
                layer.getCell(coords.get(3).getX() / 64, coords.get(3).getY() / 64).setTile(set.getTile(id));
                layer.getCell(coords.get(7).getX() / 64, coords.get(7).getY() / 64).setTile(set.getTile(id));
                layer.getCell(coords.get(11).getX() / 64, coords.get(11).getY() / 64).setTile(set.getTile(id));
            }
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        //stage.setDebugAll(true);
    }

    private TextureRegionDrawable getColouredBackground(Color colour) {
        Pixmap pm = new Pixmap(1, 1, Pixmap.Format.RGB565);
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
            } else {
                buyPropertyButton.setVisible(false);
                auctionPropertyButton.setVisible(false);
            }

            if (clickedProperty.getOwner() == gameCon.getCurrentPlayer()) {
                sellPropertyButton.setVisible(true);
                if ((clickedProperty).getMortgaged()) {
                    mortgagePropertyButton.setText("Unmortgage");
                } else {
                    mortgagePropertyButton.setText("Mortgage");
                }
                mortgagePropertyButton.setVisible(true);
                developPropertyButton.setVisible(true);
            } else {
                sellPropertyButton.setVisible(false);
                mortgagePropertyButton.setVisible(false);
                developPropertyButton.setVisible(false);
            }

            if (clickedProperty.getPlayers().contains(gameCon.getCurrentPlayer())) {
                closePropertyButton.setVisible(false);
            } else {
                closePropertyButton.setVisible(true);
            }

            if (clickedProperty.getOwned()) {
                closePropertyButton.setVisible(true);
            }

            propNameLabel.setText(clickedProperty.getTileName());
            propOwnerLabel.setText(clickedProperty.getOwnerName());
            propCostLabel.setText("$" + clickedProperty.getCost());
            propInfoBox.setBackground(getColouredBackground(((Property)clickedProperty).getColor()));

            for (int i = 0; i < ((Property)clickedProperty).getDevPrices().size(); i++) {
                developmentPrices.get(i).setText("$" + ((Property)clickedProperty).getDevPrices().get(i));
            }
            propHouseCostLabel.setText("$" + ((Property)clickedProperty).getHousePrice());
            propHotelCostLabel.setText("$" + ((Property)clickedProperty).getHotelPrice());

            closeAllWindows();
            propertyPopUpWindow.setVisible(true);
        }
        else if (tile instanceof GovProperties) {
            if (tile instanceof Station) {
                serviceInfoBox2.clear();
                serviceInfoBox2.add(new Label("Rent with one station owned:", gameScreenSkin)).left().width(350);
                serviceInfoBox2.add(new Label("$50", gameScreenSkin)).right();
                serviceInfoBox2.row().pad(20, 0, 0, 0);
                serviceInfoBox2.add(new Label("Rent with two stations owned:", gameScreenSkin)).left();
                serviceInfoBox2.add(new Label("$100", gameScreenSkin)).right();
                serviceInfoBox2.row().pad(20, 0, 0, 0);
                serviceInfoBox2.add(new Label("Rent with three stations owned:", gameScreenSkin)).left();
                serviceInfoBox2.add(new Label("$150", gameScreenSkin)).right();
                serviceInfoBox2.row().pad(20, 0, 0, 0);
                serviceInfoBox2.add(new Label("Rent with four stations owned:", gameScreenSkin)).left();
                serviceInfoBox2.add(new Label("$200", gameScreenSkin)).right();
                serviceInfoBox2.row().pad(20, 0, 0, 0);
                serviceImg.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("trainImage.png")))));
                servicePopUpWindowSetUp();
            }
            else if (tile instanceof Utility){
                serviceInfoBox2.clear();
                serviceInfoBox2.add(new Label("Rent with 1 utility owned:", gameScreenSkin)).left().width(270);
                serviceInfoBox2.add(new Label("4 times dice value", gameScreenSkin)).right();
                serviceInfoBox2.row().pad(20, 0, 0, 0);
                serviceInfoBox2.add(new Label("Rent with > 1 utility owned:", gameScreenSkin)).left();
                serviceInfoBox2.add(new Label("10 times dice value", gameScreenSkin)).right();
                serviceInfoBox2.row();
                serviceInfoBox2.add(new Label("", gameScreenSkin)).height(30);
                serviceImg.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("utilityImage.png")))));
                servicePopUpWindowSetUp();
            }

            clickedProperty = (GovProperties) tile;
            if (clickedProperty.getPlayers().contains(gameCon.getCurrentPlayer()) && clickedProperty.getBuyable()) {
                buyServiceButton.setVisible(true);
                auctionServiceButton.setVisible(true);
            }
            else {
                buyServiceButton.setVisible(false);
                auctionServiceButton.setVisible(false);
            }

            if (clickedProperty.getOwner() == gameCon.getCurrentPlayer()) {
                sellServiceButton.setVisible(true);
                if (clickedProperty.getMortgaged()) {
                    mortgageServiceButton.setText("Unmortgage");
                }
                else {
                    mortgageServiceButton.setText("Mortgage");
                }
                mortgageServiceButton.setVisible(true);
            }
            else {
                sellServiceButton.setVisible(false);
                mortgageServiceButton.setVisible(false);
            }

            if (clickedProperty.getPlayers().contains(gameCon.getCurrentPlayer())) {
                closeServiceButton.setVisible(false);
            }
            else {
                closeServiceButton.setVisible(true);
            }

            if (clickedProperty.getOwned()) {
                closeServiceButton.setVisible(true);
            }

            serviceNameLabel.setText(clickedProperty.getTileName());
            serviceOwnerLabel.setText(clickedProperty.getOwnerName());
            serviceCostLabel.setText("$" + clickedProperty.getCost());

            closeAllWindows();
            servicePopUpWindow.setVisible(true);
        }
        else if(tile instanceof Jail &&gameCon.getCurrentPlayer().getIsInJail()) {
            closeAllWindows();
            jailPopUpWindow.setVisible(true);
        }
        else if(tile instanceof OpportunityKnocks) {
            closeAllWindows();
            quickPopUpWindow("Opportunity knocks", 100, 200, 1);
        }
        else if(tile instanceof PotLuck) {
            closeAllWindows();
            quickPopUpWindow("Pot luck", 100, 200, 1);
        }
        else if(tile instanceof Tax &&tile.getPlayers().contains(gameCon.getCurrentPlayer())) {
            closeAllWindows();
            quickPopUpWindow(gameCon.getCurrentPlayer().getName() + " paid $" + ((Tax) tile).getTaxAmount() + " worth of tax!", 100, 350, 2);
        }
        else if(tile instanceof FreeParking) {
            closeAllWindows();
            if (tile.getPlayers().contains(gameCon.getCurrentPlayer())) {
                quickPopUpWindow(gameCon.getCurrentPlayer().getName() + " picked up $" + ((FreeParking) tile).getCurrentValue() + "!", 100, 350, 2);
            } else {
                quickPopUpWindow("Free parking value stands at $" + ((FreeParking) tile).getCurrentValue(), 100, 350, 1.5f);
            }
        }
        else if(tile instanceof GoToJail) {
            closeAllWindows();
            quickPopUpWindow("Go to jail", 100, 200, 1);
        }
    }

    private void closeAllWindows() {
        propertyPopUpWindow.setVisible(false);
        servicePopUpWindow.setVisible(false);
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
        propertyPopUpWindow.add(sellPropertyButton).left().width(120);
        propertyPopUpWindow.add(mortgagePropertyButton).right().width(230);
        propertyPopUpWindow.row().pad(10, 0, 0, 0);
        propertyPopUpWindow.add(developPropertyButton).left();
        propertyPopUpWindow.add(closePropertyButton).right();
        propertyPopUpWindow.pack();

        float width = 425, height = 600;
        propertyPopUpWindow.setBounds((Gdx.graphics.getWidth() - width) / 2, (Gdx.graphics.getHeight() - height) / 2, width, height);
        propertyPopUpWindow.setVisible(false);

        stage.addActor(propertyPopUpWindow);

        buyPropertyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (gameCon.getCurrentPlayer().getFirstLap()) {
                    try {
                        if (clickedProperty.getBuyable() && clickedProperty.getPlayers().contains(gameCon.getCurrentPlayer())) {
                            if (gameCon.getCurrentPlayer().getMoney() >= clickedProperty.getCost()) {
                                clickedProperty.buyProperty(gameCon.getCurrentPlayer(), clickedProperty.getCost());
                                updatePropertyOwnerIcons();
                                closeAllWindows();
                                openPopUpWindow(clickedProperty);
                                rollDice.setVisible(true);
                            } else {
                                quickPopUpWindow("Not enough money", 100, 350, 0.5f);
                            }
                        }
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
                else{
                    quickPopUpWindow("You have not gone round the board yet!", 100, 300, 2);
                }
            }
        });

        auctionPropertyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (gameCon.getCurrentPlayer().getFirstLap()) {
                    auctionPopUpWindowSetUp(); //called to add the title and colour of the property to the auction window
                    auctionBid.setVisible(true);
                    currBidder = gameCon.getCurrentPlayer();
                    bidderList = new ArrayList<>(game.players);

                    for (int i = 0; i < bidderList.indexOf(currBidder) - 1; i++) {
                        bidderList.add(bidderList.get(i));
                        bidderList.remove(i);
                    }

                    currBidderNameLabel.setText(gameCon.getCurrentPlayer().getName());
                    closeAllWindows();
                    auctionPopUpWindow.setVisible(true);
                }
                else{quickPopUpWindow("You have not gone round the board yet!", 100, 300, 2);
                }
            }
        });

        sellPropertyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if((clickedProperty).getMortgaged()){
                    (clickedProperty).unmortgage(gameCon.getCurrentPlayer(), 0);
                    clickedProperty.sellProperty(gameCon.getCurrentPlayer(), clickedProperty.getCost()/2);
                    updatePropertyOwnerIcons();
                    updatePropertyDevelopmentSprites();
                    closeAllWindows();
                }
                else {
                    clickedProperty.sellProperty(gameCon.getCurrentPlayer(), clickedProperty.getCost());
                    updatePropertyOwnerIcons();
                    updatePropertyDevelopmentSprites();
                    closeAllWindows();
                }
            }
        });

        mortgagePropertyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(mortgagePropertyButton.getText().toString().equals("Mortgage")) {
                    ((Property)clickedProperty).setMortgaged(gameCon.getCurrentPlayer(), clickedProperty.getCost());
                    mortgagePropertyButton.setText("Unmortgage");
                }
                else{
                    ((Property)clickedProperty).unmortgage(gameCon.getCurrentPlayer(), clickedProperty.getCost());
                    mortgagePropertyButton.setText("Mortgage");
                }
            }
        });

        developPropertyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(gameCon.developProperty(((Property)clickedProperty), gameCon.getCurrentPlayer())) {
                    quickPopUpWindow("Able to develop", 100, 350, 0.5f);
                    updatePropertyDevelopmentSprites();
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

    private void servicePopUpWindowSetUp() {
        buyServiceButton = new TextButton("Buy", gameScreenSkin);
        sellServiceButton = new TextButton("Sell", gameScreenSkin);

        mortgageServiceButton = new TextButton("Mortgage", gameScreenSkin);
        auctionServiceButton = new TextButton("Auction", gameScreenSkin);

        closeServiceButton = new TextButton("Close", gameScreenSkin);

        Table serviceInfoBox = new Table();

        serviceNameLabel = new Label("", gameScreenSkin, "big");
        serviceNameLabel.setAlignment(Align.center);
        serviceOwnerLabel = new Label("", gameScreenSkin);
        serviceCostLabel = new Label("",gameScreenSkin);

        serviceInfoBox.add(serviceNameLabel).colspan(2).width(350);
        serviceInfoBox.row().pad(10, 0, 0, 0);
        serviceInfoBox.add(new Label("Owner:", gameScreenSkin)).left();
        serviceInfoBox.add(serviceOwnerLabel).right();
        serviceInfoBox.row().pad(5, 0, 0, 0);
        serviceInfoBox.add(new Label("Cost:", gameScreenSkin)).left();
        serviceInfoBox.add(serviceCostLabel).right();

        servicePopUpWindow = new Window("", gameScreenSkin);

        servicePopUpWindow.add(serviceInfoBox).colspan(2).expand().fill();
        servicePopUpWindow.row().pad(20, 0, 0, 0);
        servicePopUpWindow.add(serviceImg).colspan(2).width(300).height(100);
        servicePopUpWindow.row().pad(60, 0, 0, 0);
        servicePopUpWindow.add(serviceInfoBox2).colspan(2);
        servicePopUpWindow.row().pad(20, 0, 0, 0);
        servicePopUpWindow.add(buyServiceButton).left();
        servicePopUpWindow.add(auctionServiceButton).right();
        servicePopUpWindow.row().pad(10, 0, 0, 0);
        servicePopUpWindow.add(sellServiceButton).left().width(120);
        servicePopUpWindow.add(mortgageServiceButton).right().width(230);
        servicePopUpWindow.row().pad(10, 0, 0, 0);
        servicePopUpWindow.add(closeServiceButton).colspan(2);
        servicePopUpWindow.pack();

        float width = 425, height = 600;
        servicePopUpWindow.setBounds((Gdx.graphics.getWidth() - width) / 2, (Gdx.graphics.getHeight() - height) / 2, width, height);
        servicePopUpWindow.setVisible(false);

        stage.addActor(servicePopUpWindow);

        buyServiceButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(gameCon.getCurrentPlayer().getFirstLap()) {
                    try {
                        if (clickedProperty.getBuyable() && clickedProperty.getPlayers().contains(gameCon.getCurrentPlayer())) {
                            if (gameCon.getCurrentPlayer().getMoney() >= clickedProperty.getCost()) {
                                clickedProperty.buyProperty(gameCon.getCurrentPlayer(), clickedProperty.getCost());
                                updatePropertyOwnerIcons();
                                closeAllWindows();
                                openPopUpWindow(clickedProperty);
                                rollDice.setVisible(true);
                            } else {
                                quickPopUpWindow("Not enough money", 100, 350, 0.5f);
                            }
                        }
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            }
        });

        auctionServiceButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(gameCon.getCurrentPlayer().getFirstLap()) {
                    auctionPopUpWindowSetUp(); //called to add the title and colour of the property to the auction window

                    currBidder = gameCon.getCurrentPlayer();
                    bidderList = new ArrayList<>(game.players);

                    for (int i = 0; i < bidderList.indexOf(currBidder) - 1; i++) {
                        bidderList.add(bidderList.get(i));
                        bidderList.remove(i);
                    }

                    currBidderNameLabel.setText(gameCon.getCurrentPlayer().getName());
                    closeAllWindows();
                    auctionPopUpWindow.setVisible(true);
                }
                else{
                    quickPopUpWindow("You have not gone round the board yet!", 100, 300, 2);
                }
            }
        });

        sellServiceButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if((clickedProperty).getMortgaged()){
                    (clickedProperty).unmortgage(gameCon.getCurrentPlayer(), 0);
                    clickedProperty.sellProperty(gameCon.getCurrentPlayer(), clickedProperty.getCost()/2);
                    updatePropertyOwnerIcons();
                    closeAllWindows();
                }
                else {
                    clickedProperty.sellProperty(gameCon.getCurrentPlayer(), clickedProperty.getCost());
                    updatePropertyOwnerIcons();
                    closeAllWindows();
                }
            }
        });

        mortgageServiceButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(mortgageServiceButton.getText().toString().equals("Mortgage")) {
                    (clickedProperty).setMortgaged(gameCon.getCurrentPlayer(), clickedProperty.getCost());
                    mortgageServiceButton.setText("Unmortgage");
                }
                else{
                    (clickedProperty).unmortgage(gameCon.getCurrentPlayer(), clickedProperty.getCost());
                    mortgageServiceButton.setText("Mortgage");
                }
            }
        });

        closeServiceButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                servicePopUpWindow.setVisible(false);
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

        auctionBid = new TextField("", gameScreenSkin);

        auctionBid.setMessageText("Enter Bid");
        auctionBid.setAlignment(Align.center);
        final TextButton bidButton = new TextButton("Bid", gameScreenSkin);
        final TextButton leaveButton = new TextButton("Leave", gameScreenSkin);

        Table auctionPopUpTable = new Table();

        if(clickedProperty != null) {
            if (clickedProperty instanceof Property) {
                auctionPopUpTable.setBackground(getColouredBackground(((Property) clickedProperty).getColor()));
            }
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

        float width = 610, height = 500;
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
                    if(bidderList.size() == 1 && gameCon.getAuctionValue() != 0) {
                        bidButton.setVisible(false);
                        currBidderNameLabel.setText(currBidder.getName());
                        leaveButton.setText("Buy");
                    }
                }
                currBidderNameLabel.setText(currBidder.getName());
            }
        });

        leaveButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int index = bidderList.indexOf(currBidder);
                bidderList.remove(currBidder);
                if (bidderList.size() == 0 && highestBidder == null) {
                    auctionPopUpWindow.setVisible(false);
                    gameCon.setAuctionValue(0);
                    rollDice.setVisible(true);
                }

                if (bidderList.size() == 0 && highestBidder != null) {
                    auctionPopUpWindow.setVisible(false);
                    clickedProperty.buyProperty(highestBidder, gameCon.getAuctionValue());
                    highestBidder = null;
                    updatePropertyOwnerIcons();
                    gameCon.setAuctionValue(0);
                    rollDice.setVisible(true);
                }

                if (bidderList.size() != 0) {
                    if (index < bidderList.size()) {
                        currBidder = bidderList.get(index);
                    } else {
                        currBidder = bidderList.get(0);
                    }
                    currBidderNameLabel.setText(currBidder.getName());
                }

                if(bidderList.size() == 1 && gameCon.getAuctionValue() != 0) {
                    bidButton.setVisible(false);
                    auctionBid.setVisible(false);
                    leaveButton.setText("Buy");
                }
            }
        });
    }

    private void gameInfoTableSetUp() {
        Table currPlayerTable = new Table();

        Label currPlayerTitle = new Label("Current player: ", gameScreenSkin, "title");
        final Label currPlayerLabel = new Label(gameCon.getCurrentPlayer().getName(), gameScreenSkin, "title");

        currPlayerTable.add(currPlayerTitle).left().width(320);
        currPlayerTable.add(currPlayerLabel).right().width(170);

        Table diceTable = new Table();

        final Image die1 = new Image();
        final Image die2 = new Image();

        diceTable.add(die1).height(100).width(100).padRight(10);
        diceTable.add(die2).height(100).width(100);

        Table playerInfoTable = new Table();
        playerBalanceLabels = new ArrayList<>();
        for (Player player: game.players) {
            Label playerNameLabel = new Label(player.getName() + ": ", gameScreenSkin, "title");
            Label playerBalanceLabel = new Label("$"+ player.getMoney(), gameScreenSkin, "title");
            playerBalanceLabels.add(playerBalanceLabel);
            playerInfoTable.row().pad(10, 0, 0, 0);
            playerInfoTable.add(playerNameLabel).left().width(180);
            playerInfoTable.add(playerBalanceLabel).right().width(130);
        }

        Table gameInfoTable = new Table();

        rollDice = new TextButton("Roll dice", gameScreenSkin);
        TextButton centerButton = new TextButton("Recenter view", gameScreenSkin);
        TextButton pauseButton = new TextButton("Pause", gameScreenSkin);

        gameInfoTable.row().pad(10, 0, 0, 0);
        gameInfoTable.add(currPlayerTable).left();
        gameInfoTable.row().pad(10, 0, 0, 0);
        gameInfoTable.add(diceTable).left();
        gameInfoTable.row();
        gameInfoTable.add(playerInfoTable).left();
        gameInfoTable.row().pad(10, 0, 0, 0);
        gameInfoTable.add(rollDice).left();
        gameInfoTable.row().pad(40, 0, 0, 0);
        gameInfoTable.add(centerButton).left();
        gameInfoTable.row().pad(10, 0, 0, 0);
        gameInfoTable.add(pauseButton).left();

        gameInfoTable.setFillParent(true);
        gameInfoTable.left();
        gameInfoTable.padLeft(10);

        stage.addActor(gameInfoTable);

        Table timerTable = new Table();
        timerLabel = new Label("", gameScreenSkin, "title");
        timerTable.add(timerLabel).width(380);
        timerTable.setFillParent(true);
        timerTable.right().padRight(10);
        stage.addActor(timerTable);

        rollDice.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (rollDice.getText().toString().equals("Roll dice")) {
                    if (game.getPreferences().isFxEnabled()) {
                        rollDiceFX.play(game.getPreferences().getFxVolume());
                        try {
                            TimeUnit.MILLISECONDS.sleep(1750);
                        } catch(Exception e) {
                            e.getMessage();
                        }
                    }
                    System.out.println("clicked");

                    Tile tile = gameCon.playerTurn();

                    Player p = gameCon.getCurrentPlayer();
                    p.getPlayerToken().setPosition(p.getCurrentCoordinates().getX(), p.getCurrentCoordinates().getY());

                    die1.setDrawable(getDiceImage(gameCon.getLastD1()));
                    die2.setDrawable(getDiceImage(gameCon.getLastD2()));

                        if (tile instanceof Ownable && ((Ownable) tile).getOwned() && ((Ownable) tile).getOwner() != gameCon.getCurrentPlayer()) {
                            if (tile instanceof Property) {
                                Property prop = (Property) tile;
                                quickPopUpWindow(gameCon.getCurrentPlayer().getName() + " paid " + prop.getOwner().getName() + " $" + prop.getCurrentRent() + " for landing on " + prop.getTileName(), 100, 450, 3);
                            } else if (tile instanceof Station) {
                                Station stat = (Station) tile;
                                quickPopUpWindow(gameCon.getCurrentPlayer().getName() + " paid " + stat.getOwner().getName() + " $" + stat.getRent() + " for landing on " + stat.getTileName(), 100, 450, 3);
                            } else if (tile instanceof Utility) {
                                Utility util = (Utility) tile;
                                quickPopUpWindow(gameCon.getCurrentPlayer().getName() + " paid " + util.getOwner().getName() + " $" + util.getRent(gameCon.getLastD1() + gameCon.getLastD2()) + " for landing on " + util.getTileName(), 100, 450, 3);
                            }
                        } else {
                            openPopUpWindow(tile);
                        }

                        if (tile instanceof Property) {
                            if (!((Property) tile).getOwned()) {
                                if(gameCon.getCurrentPlayer().getFirstLap()) {
                                    rollDice.setVisible(false);
                                }
                            }
                        }

                    if (!gameCon.getPlayAgain()) {
                        rollDice.setText("End turn");
                    }
                }
                else if (rollDice.getText().toString().equals("End turn")) {
                    closeAllWindows();
                        while (gameCon.getCurrentPlayer().getMoney() < 0 && gameCon.getCurrentPlayer().getOwnables().size() > 0){
                            gameCon.getCurrentPlayer().getOwnables().get(0).sellProperty(gameCon.getCurrentPlayer(), gameCon.getCurrentPlayer().getOwnables().get(0).getCost());
                        }
                        updatePropertyOwnerIcons();
                        if(gameCon.getCurrentPlayer().getMoney() < 0) {
                            game.players.remove(gameCon.getCurrentPlayer());
                            gameCon.getPlayerOrder().remove(0);
                            if (game.players.size() == 1) {
                                game.getPreferences().setAbridged(false);
                                congratsPopUpWindow(gameCon.getFinalStandings(game.players, 0, game.players.size()-1));
                                Timer.schedule(new Timer.Task() {
                                    @Override
                                    public void run() {
                                        game.setScreen(new MainMenu(game));
                                    }
                                }, 7.5f);
                            }
                        }

                    if(gameCon.getCurrentPlayer().getMoney() + gameCon.getCurrentPlayer().getTotalPropertyValue() <= 0) { //need to add a check to see if their cumulative property worth also results in < $0
                        game.players.remove(gameCon.getCurrentPlayer());
                        gameCon.getPlayerOrder().remove(0);
                        if (game.players.size() == 1) {
                            game.getPreferences().setAbridged(false);
                            congratsPopUpWindow(gameCon.getFinalStandings(game.players, 0, game.players.size()-1));
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    game.setScreen(new MainMenu(game));
                                }
                            }, 7.5f);
                        }
                    }
                    gameCon.endTurn();
                    die1.setDrawable(null);
                    die2.setDrawable(null);
                    currPlayerLabel.setText(gameCon.getCurrentPlayer().getName());
                    rollDice.setText("Roll dice");
                }
            }
        });

        centerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.recenter(tiledMap);
            }
        });

        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PauseScreen(game));
            }
        });
    }

    private void jailPopUpWindowSetUp() {
        Label jailInfoLabel = new Label("Either buy your way out of Jail for $50, use a get out of jail free card or roll a double on your next go!", gameScreenSkin, "title");
        jailInfoLabel.setWrap(true);
        jailInfoLabel.setWidth(875);
        jailInfoLabel.setAlignment(Align.center);
        TextButton buyOutOfJailButton = new TextButton("Buy way out of Jail", gameScreenSkin);
        TextButton useJailFreeButton = new TextButton("Use get out of jail free card", gameScreenSkin);

        Table jailPopUpTable = new Table();

        float width = 800, height = 350;

        jailPopUpTable.add(jailInfoLabel).width(width-50);
        jailPopUpTable.row().pad(10, 0, 0, 0);
        jailPopUpTable.add(buyOutOfJailButton);
        jailPopUpTable.row().pad(10, 0, 0, 0);
        jailPopUpTable.add(useJailFreeButton);
        jailPopUpTable.pack();

        jailPopUpTable.setBackground(getColouredBackground(Color.PINK));

        jailPopUpWindow = new Window("", gameScreenSkin);
        //jailPopUpWindow.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("gameScreenJail.png")))));

        jailPopUpWindow.add(jailPopUpTable).expand().fill();

        jailPopUpWindow.setBounds((Gdx.graphics.getWidth() - width) / 2, (Gdx.graphics.getHeight() - height) / 2, width, height);
        jailPopUpWindow.setVisible(false);

        stage.addActor(jailPopUpWindow);

        buyOutOfJailButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closeAllWindows();
                Player p = gameCon.getCurrentPlayer();
                p.makePurchase(50);
                gameCon.freePlayerFromJail(p);
            }
        });

        useJailFreeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Player p = gameCon.getCurrentPlayer();
                if(p.hasGetOutOfJailFree()) {
                    closeAllWindows();
                    p.removeGetOutOfJailFreeCard();
                    gameCon.freePlayerFromJail(p);
                }
                else {
                    quickPopUpWindow("You do not have a get out of jail free card!", 150, 300, 1);
                }
            }
        });
    }

    private void congratsPopUpWindow(ArrayList<Player> players) {
        Collections.reverse(players);
        Table congratsTable = new Table();
        Label congratsLabel = new Label("Congratulations!", gameScreenSkin, "title");
        congratsTable.add(congratsLabel);
        congratsTable.row().pad(10, 0, 0, 0);
        for(int i=0; i<players.size(); i++) {
            Player p = players.get(i);
            int value = 0;
            for(Ownable o : p.getOwnables()) {
                value += o.getCost();
            }
            value += p.getMoney();
            if(i == 0) {
                Table winnerTable = new Table();
                winnerTable.add(new Label("", gameScreenSkin)).width(625);
                winnerTable.row();
                winnerTable.add(new Label("First place goes to " + p.getName(), gameScreenSkin, "title"));
                winnerTable.row();
                winnerTable.add(new Label("Total wealth: $" + value, gameScreenSkin, "title"));
                winnerTable.setBackground(getColouredBackground(Color.GOLD));
                congratsTable.add(winnerTable);
                congratsTable.row().pad(20, 0, 0, 0);
            }
            else {
                congratsTable.add(new Label(i + 1 + ". " + p.getName() + " finished with $" + value, gameScreenSkin, "title"));
                congratsTable.row().pad(10, 0, 0, 0);
            }
        }
        Window congratsWindow = new Window("", gameScreenSkin);
        congratsWindow.add(congratsTable);
        float width = 650, height = 700;
        congratsWindow.setBounds((Gdx.graphics.getWidth() - width) / 2, (Gdx.graphics.getHeight() - height) / 2, width, height);
        //congratsWindow.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("gameScreenJail.png")))));
        stage.addActor(congratsWindow);
        congratsWindow.setVisible(true);
    }

    /**
     * Updates the balance values shown in the gameInfoTable. Call this in render for frequent updates.
     */
    private void updateBalances(){
        for (int i = 0; i < game.players.size(); i++) {
            Player player = game.players.get(i);
            playerBalanceLabels.get(i).setText("$" + player.getMoney());
        }
    }

    private Drawable getDiceImage(int num) {
        Texture oneDie = new Texture(Gdx.files.internal("dice/oneDie.png"));
        Texture twoDie = new Texture(Gdx.files.internal("dice/twoDie.png"));
        Texture threeDie = new Texture(Gdx.files.internal("dice/threeDie.png"));
        Texture fourDie = new Texture(Gdx.files.internal("dice/fourDie.png"));
        Texture fiveDie = new Texture(Gdx.files.internal("dice/fiveDie.png"));
        Texture sixDie = new Texture(Gdx.files.internal("dice/sixDie.png"));
        switch (num) {
            case 1:
                return new TextureRegionDrawable(oneDie);
            case 2:
                return new TextureRegionDrawable(twoDie);
            case 3:
                return new TextureRegionDrawable(threeDie);
            case 4:
                return new TextureRegionDrawable(fourDie);
            case 5:
                return new TextureRegionDrawable(fiveDie);
            case 6:
                return new TextureRegionDrawable(sixDie);
        }
        return null;
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

    public void updatePropertyDevelopmentSprites(){
        ArrayList<Property> developedProperties = gameCon.getDevelopedProperties();
        propertyHouseAndHotelSprites.clear();
        for (Property prop: developedProperties) {
            Sprite sprite;

            switch (prop.getHousesOwned()) {
                default:
                    sprite = null;
                    break;
                case 1:
                    sprite = new Sprite(oneHouseTexture);
                    break;
                case 2:
                    sprite = new Sprite(twoHouseTexture);
                    break;
                case 3:
                    sprite = new Sprite(threeHouseTexture);
                    break;
                case 4:
                    sprite = new Sprite(fourHouseTexture);
                    break;
                case 5:
                    sprite = new Sprite(hotelTexture);
                    break;
            }

            if (sprite != null) {
            sprite.setSize(192, 64);
            sprite.setOriginCenter();
            if (prop.getTilePos() < 11) {
                sprite.rotate(-90);
            } else if (prop.getTilePos() < 21) {
                sprite.rotate(-180);
            } else if (prop.getTilePos() < 31) {
                sprite.rotate(-270);
            }
            sprite.setPosition(prop.getPropertySpriteCoordinate().getX() - 192 / 2, prop.getPropertySpriteCoordinate().getY() - 64 / 2);
            propertyHouseAndHotelSprites.add(sprite);
        }else{


            }
        }
    }

    private void updatePropertyOwnerIcons(){
        ownedProperties.clear();

        for(Player p: game.players){

            ArrayList<Ownable> props = p.getOwnables(); // gets all the players owned properties

            for (Ownable property: props) {

                Sprite s = new Sprite(p.getPlayerToken().getTexture());
                s.setAlpha(0.5f);
                s.setOriginCenter();
                if (property.getTilePos() < 11){
                    s.setPosition(property.getPropertySpriteCoordinate().getX()+32,property.getPropertySpriteCoordinate().getY()-32);
                    s.rotate(-90);
                }   else   if (property.getTilePos() < 21){
                    s.setPosition(property.getPropertySpriteCoordinate().getX()-32,property.getPropertySpriteCoordinate().getY()-96);
                    s.rotate(-180);
                }   else   if (property.getTilePos() <31){
                    s.setPosition(property.getPropertySpriteCoordinate().getX()-96,property.getPropertySpriteCoordinate().getY()-32);
                    s.rotate(-270);
                }else{
                    s.setPosition(property.getPropertySpriteCoordinate().getX()-32,property.getPropertySpriteCoordinate().getY()+32);
                }
                ownedProperties.add(s);
            }
        }
    }

    public OrthographicCamera getCam() {
        return camera;
    }

    /**
     * render() is called when the Screen should render itself
     * @param delta the time in seconds since the last render
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        if(propertyPopUpWindow.isVisible() || servicePopUpWindow.isVisible() || jailPopUpWindow.isVisible() || auctionPopUpWindow.isVisible()) {
            stage.removeListener(clickListener);
        }
        else {
            stage.addListener(clickListener);
        }

        if(game.getPreferences().getAbridged()) {
            gameLength += Gdx.graphics.getRawDeltaTime();
            reverseTime = (game.getPreferences().getAbridgedLength()*60) - gameLength;
            timerLabel.setText("Time left: " + LocalTime.MIN.plusSeconds(Math.round(reverseTime)).toString());
            if(gameLength >= game.getPreferences().getAbridgedLength() * 60) {
                game.getPreferences().setAbridged(false);
                congratsPopUpWindow(gameCon.getFinalStandings(game.players, 0, game.players.size()-1));
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        game.setScreen(new MainMenu(game));
                    }
                }, 7.5f);
            }
        }

        updateBalances();

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();

        for (Sprite sprite: propertyHouseAndHotelSprites) {
            sprite.draw(spriteBatch);
        }

        for (Sprite sprite: ownedProperties) {
            sprite.draw(spriteBatch);
        }


        for (Sprite sprite: propertyIcons){
            sprite.draw(spriteBatch);
        }
        for (Player p : game.players) {
            p.getPlayerToken().draw(spriteBatch);
        }

        spriteBatch.end();

        labelStage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        labelStage.draw();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    /**
     * Called when GameScreen() should release all resources
     */
    @Override
    public void dispose() { stage.dispose(); }

    /**
     * Called when the Application is resized. Will never be called before a call to create()
     * @param width the width of the screen
     * @param height the height of the screen
     */
    @Override
    public void resize(int width, int height) { stage.getViewport().update(width, height, true); }

    /**
     * Called when the Application is paused. An Application is paused before it is destroyed
     */
    @Override
    public void pause() {}

    /**
     * Called when the Application is resumed from a paused state
     */
    @Override
    public void resume() {}

    /**
     * Called when this GameScreen() is no longer the current screen for PropertyTycoon()
     */
    @Override
    public void hide() {}
}