package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.propertytycoonmakers.make.PropertyTycoon;
import main.Player;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * GameSetUpScreen is a GUI class that allows for the user to make choices regarding the number of players, their names
 * and their game pieces. It is displayed before the game starts allowing for user customization.
 */
public class GameSetUpScreen implements Screen {

    private PropertyTycoon game;
    private Texture gameSetUpScreenTexture;
    private Skin gameSetUpScreenSkin;
    private Stage stage;
    private Viewport viewport;

    private Sprite sprite1;
    private Sprite sprite2;
    private Sprite sprite3;
    private Sprite sprite4;
    private Sprite sprite5;
    private Sprite sprite6;

    private ArrayList<Sprite> spriteList;
    private SelectBox[] tokenSBList;
    private Image[] tokenImageList;

    private TextField player1Field;
    private TextField player2Field;
    private TextField player3Field;
    private TextField player4Field;
    private TextField player5Field;
    private TextField player6Field;

    private Image token1Image;
    private Image token2Image;
    private Image token3Image;
    private Image token4Image;
    private Image token5Image;
    private Image token6Image;

    private SelectBox<String> token1SB;
    private SelectBox<String> token2SB;
    private SelectBox<String> token3SB;
    private SelectBox<String> token4SB;
    private SelectBox<String> token5SB;
    private SelectBox<String> token6SB;

    private Table table;
    private Label numPlayers;

    private TextField[] playerNames;
    private SelectBox<Integer> numPlayersBox;
    private TextButton startGame;
    private TextButton back;

    /**
     * The constructor for GameSetUpScreen
     * @param game The PropertyTycoon parent class upon which the GUI is built
     */
    public GameSetUpScreen(PropertyTycoon game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        this.gameSetUpScreenTexture = new Texture(Gdx.files.internal("mainMenuTexture.png"));
        this.gameSetUpScreenSkin = new Skin(Gdx.files.internal("skin/comic-ui.json"));

        Texture texture1 = new Texture(Gdx.files.internal("tokens/token1.png"));
        Texture texture2 = new Texture(Gdx.files.internal("tokens/token2.png"));
        Texture texture3 = new Texture(Gdx.files.internal("tokens/token3.png"));
        Texture texture4 = new Texture(Gdx.files.internal("tokens/token4.png"));
        Texture texture5 = new Texture(Gdx.files.internal("tokens/token5.png"));
        Texture texture6 = new Texture(Gdx.files.internal("tokens/token6.png"));

        this.sprite1 = new Sprite(texture1);
        this.sprite2 = new Sprite(texture2);
        this.sprite3 = new Sprite(texture3);
        this.sprite4 = new Sprite(texture4);
        this.sprite5 = new Sprite(texture5);
        this.sprite6 = new Sprite(texture6);

        this.spriteList = new ArrayList<>(Arrays.asList(sprite1, sprite2, sprite3, sprite4, sprite5, sprite6));

        player1Field = new TextField("Player 1", gameSetUpScreenSkin);
        player2Field = new TextField("Player 2", gameSetUpScreenSkin);
        player3Field = new TextField("Player 3", gameSetUpScreenSkin);
        player4Field = new TextField("Player 4", gameSetUpScreenSkin);
        player5Field = new TextField("Player 5", gameSetUpScreenSkin);
        player6Field = new TextField("Player 6", gameSetUpScreenSkin);

        token1Image = new Image(getTokenDrawable(spriteList.get(0)));
        token2Image = new Image(getTokenDrawable(spriteList.get(1)));
        token3Image = new Image(getTokenDrawable(spriteList.get(2)));
        token4Image = new Image(getTokenDrawable(spriteList.get(3)));
        token5Image = new Image(getTokenDrawable(spriteList.get(4)));
        token6Image = new Image(getTokenDrawable(spriteList.get(5)));

        token1SB = new SelectBox(gameSetUpScreenSkin);
        token2SB = new SelectBox(gameSetUpScreenSkin);
        token3SB = new SelectBox(gameSetUpScreenSkin);
        token4SB = new SelectBox(gameSetUpScreenSkin);
        token5SB = new SelectBox(gameSetUpScreenSkin);
        token6SB = new SelectBox(gameSetUpScreenSkin);

        String[] valueList = new String[]{"white", "blue", "green", "yellow", "orange", "rainbow"};

        token1SB.setItems(valueList);
        token2SB.setItems(valueList);
        token3SB.setItems(valueList);
        token4SB.setItems(valueList);
        token5SB.setItems(valueList);
        token6SB.setItems(valueList);

        tokenImageList = new Image[]{token1Image, token2Image, token3Image, token4Image, token5Image, token6Image};
        tokenSBList = new SelectBox[]{token1SB, token2SB, token3SB, token4SB, token5SB, token6SB};

        table = new Table();
        table.setFillParent(true);

        numPlayers = new Label("Number of players:", gameSetUpScreenSkin);
        numPlayersBox = new SelectBox(gameSetUpScreenSkin);
        numPlayersBox.setItems(new Integer[]{2, 3, 4, 5, 6});

        startGame = new TextButton("Start", gameSetUpScreenSkin);
        playerNames = new TextField[]{player1Field, player2Field, player3Field, player4Field, player5Field, player6Field};
        back = new TextButton("Back", gameSetUpScreenSkin);

        numPlayersBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setUIVisibility();
            }
        });

        token1SB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                swapSprites(0, token1SB.getSelected());
                updateSB();
                updateTokenImageList();
            }
        });

        token2SB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                swapSprites(1, token2SB.getSelected());
                updateSB();
                updateTokenImageList();
            }
        });

        token3SB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                swapSprites(2, token3SB.getSelected());
                updateSB();
                updateTokenImageList();
            }
        });

        token4SB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                swapSprites(3, token4SB.getSelected());
                updateSB();
                updateTokenImageList();
            }
        });

        token5SB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                swapSprites(4, token5SB.getSelected());
                updateSB();
                updateTokenImageList();
            }
        });

        token6SB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                swapSprites(5, token6SB.getSelected());
                updateSB();
                updateTokenImageList();
            }
        });

        table.row().pad(10, 0, 0, 20);
        table.add(numPlayers).left();
        table.add(numPlayersBox);
        table.row().pad(10, 0, 0, 20);
        table.add(player1Field);
        table.add(token1SB);
        table.add(token1Image);
        table.row().pad(10, 0, 0, 20);
        table.add(player2Field);
        table.add(token2SB);
        table.add(token2Image);
        table.row().pad(10, 0, 0, 20);
        table.add(player3Field);
        table.add(token3SB);
        table.add(token3Image);
        table.row().pad(10, 0, 0, 20);
        table.add(player4Field);
        table.add(token4SB);
        table.add(token4Image);
        table.row().pad(10, 0, 0, 20);
        table.add(player5Field);
        table.add(token5SB);
        table.add(token5Image);
        table.row().pad(10, 0, 0, 20);
        table.add(player6Field);
        table.add(token6SB);
        table.add(token6Image);
        table.row().pad(10, 0, 0, 20);
        table.add(startGame).colspan(3);
        table.row().pad(10, 0, 0, 20);
        table.add(back).colspan(3);

        updateSB();
        setUIVisibility();
    }

    /**
     * show() defines the layout, elements and interactivity of the GUI
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage.clear();
        stage.addActor(table);

        startGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.players = null;
                game.players = new Player[numPlayersBox.getSelected()];
                for (int i = 0; i < numPlayersBox.getSelected(); i++) {
                    game.players[i] = new Player(playerNames[i].getText(), spriteList.get(i));
                }
                game.changeScreen(game.GAME);
            }
        });

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
            }
        });
    }

    /**
     * render() is called when the Screen should render itself
     * @param delta the time in seconds since the last render
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(gameSetUpScreenTexture, 0, 0);
        game.font.getData().setScale(2);
        game.font.draw(game.batch, "Property Tycoon Options", 100, 100);
        game.batch.draw(gameSetUpScreenTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        game.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    /**
     * setUIVisibility() makes calls to setUI() according to how many players have been chosen via the drop down box
     */
    private void setUIVisibility(){
        switch (numPlayersBox.getSelected()) {
            case 2:
                setUI(false, false, false, false);
                break;
            case 3:
                setUI(true, false, false, false);
                break;
            case 4:
                setUI(true, true, false, false);
                break;
            case 5:
                setUI(true, true, true, false);
                break;
            case 6:
                setUI(true, true, true, true);
                break;
        }
    }

    /**
     * setUI() is used to define whether or not a player field (consisting of a name box and game piece
     * selector) should be shown
     * @param selection3 true if the third player field should be shown, false if not
     * @param selection4 true if the fourth player field should be shown, false if not
     * @param selection5 true if the fifth player field should be shown, false if not
     * @param selection6 true if the sixth player field should be shown, false if not
     */
    private void setUI(Boolean selection3, Boolean selection4, Boolean selection5, Boolean selection6) {
        player3Field.setVisible(selection3);
        player4Field.setVisible(selection4);
        player5Field.setVisible(selection5);
        player6Field.setVisible(selection6);

        token3SB.setVisible(selection3);
        token4SB.setVisible(selection4);
        token5SB.setVisible(selection5);
        token6SB.setVisible(selection6);

        token3Image.setVisible(selection3);
        token4Image.setVisible(selection4);
        token5Image.setVisible(selection5);
        token6Image.setVisible(selection6);
    }

    /**
     * getTokenDrawable() takes a Sprite object and returns the Drawable object associated with that sprite's texture
     * @param sprite the sprite who's texture is to be returned as a Drawable object
     * @return the required Drawable object
     */
    private Drawable getTokenDrawable(Sprite sprite) {
        return new TextureRegionDrawable(new TextureRegion(sprite.getTexture()));
    }

    /**
     * swapSprites() takes two Sprite objects and swaps their positions within the spriteList
     * @param s1pos the first Sprite object
     * @param s2str the second Sprite object
     */
    private void swapSprites(int s1pos, String s2str) {
        if (s2str.equals("white")) {
            Collections.swap(spriteList, s1pos, spriteList.indexOf(sprite1));
        } else if (s2str.equals("blue")) {
            Collections.swap(spriteList, s1pos, spriteList.indexOf(sprite2));
        } else if (s2str.equals("green")) {
            Collections.swap(spriteList, s1pos, spriteList.indexOf(sprite3));
        } else if (s2str.equals("yellow")) {
            Collections.swap(spriteList, s1pos, spriteList.indexOf(sprite4));
        } else if (s2str.equals("orange")) {
            Collections.swap(spriteList, s1pos, spriteList.indexOf(sprite5));
        } else if (s2str.equals("rainbow")) {
            Collections.swap(spriteList, s1pos, spriteList.indexOf(sprite6));
        }
    }

    /**
     * getSpriteTitle() takes a Sprite object and returns a String that represents the title of the Sprite
     * @param sprite the Sprite object who's title is to be returned
     * @return a String that represents the title of a Sprite
     */
    private String getSpriteTitle(Sprite sprite) {
        if (sprite == sprite1) {
            return "white";
        } else if (sprite == sprite2) {
            return "blue";
        } else if (sprite == sprite3) {
            return "green";
        } else if (sprite == sprite4) {
            return "yellow";
        } else if (sprite == sprite5) {
            return "orange";
        } else if (sprite == sprite6) {
            return "rainbow";
        }
        return "ERROR";
    }

    /**
     * updateSB() updates the selection boxs so to display the correct sprite title as the selected option
     */
    private void updateSB() {
        for (int i = 0; i < spriteList.size(); i++) {
            tokenSBList[i].setSelected(getSpriteTitle(spriteList.get(i)));
        }
    }

    /**
     * updaateTokenImageList() updates the token images so to display the correct image with each player field
     */
    private void updateTokenImageList() {
        for (int i = 0; i < spriteList.size(); i++) {
            tokenImageList[i].setDrawable(getTokenDrawable(spriteList.get(i)));
        }
    }

    /**
     * Called when GameSetUpScreen() should release all resources
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
     * Called when this GameSetUpScreen() is no longer the current screen for PropertyTycoon()
     */
    @Override
    public void hide() {}
}