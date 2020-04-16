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
import static com.propertytycoonmakers.make.PropertyTycoon.players;


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

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage.clear();
        stage.addActor(table);


        startGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                players = null;
                players = new Player[numPlayersBox.getSelected()];
                for (int i = 0; i < numPlayersBox.getSelected(); i++) {
                    players[i] = new Player(playerNames[i].getText(), spriteList.get(i));
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


    public void setUIVisibility(Boolean selection3, Boolean selection4, Boolean selection5, Boolean selection6) {

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

    private Drawable getTokenDrawable(Sprite sprite) {
        return new TextureRegionDrawable(new TextureRegion(sprite.getTexture()));
    }


    // maybe make switch statement?
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

    private void updateSB() {
        for (int i = 0; i < spriteList.size(); i++) {
            tokenSBList[i].setSelected(getSpriteTitle(spriteList.get(i)));
        }
    }

    public void setUIVisibility(){
        switch (numPlayersBox.getSelected()) {
            case 2:
                setUIVisibility(false, false, false, false);
                break;
            case 3:
                setUIVisibility(true, false, false, false);
                break;
            case 4:
                setUIVisibility(true, true, false, false);
                break;
            case 5:
                setUIVisibility(true, true, true, false);
                break;
            case 6:
                setUIVisibility(true, true, true, true);
                break;
        }



    }



    private void updateTokenImageList() {
        for (int i = 0; i < spriteList.size(); i++) {
            tokenImageList[i].setDrawable(getTokenDrawable(spriteList.get(i)));
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
}