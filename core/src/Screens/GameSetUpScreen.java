package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.propertytycoonmakers.make.PropertyTycoon;
import com.sun.org.apache.xpath.internal.operations.String;
import com.sun.scenario.Settings;
import main.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static com.propertytycoonmakers.make.PropertyTycoon.players;

public class GameSetUpScreen implements Screen {

    private PropertyTycoon game;
    private Texture optionsScreenTexture;
    private Skin optionsScreenSkin;
    private Stage stage;
    private Viewport viewport;

    private Sprite sprite1;
    private Sprite sprite2;
    private Sprite sprite3;
    private Sprite sprite4;
    private Sprite sprite5;
    private Sprite sprite6;

    private ArrayList<Sprite> spriteList;

    public GameSetUpScreen(PropertyTycoon game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        this.optionsScreenTexture = new Texture(Gdx.files.internal("mainMenuTexture.png"));
        this.optionsScreenSkin = new Skin(Gdx.files.internal("skin/comic-ui.json"));

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
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Table table = new Table();
        table.setFillParent(true);
        stage.clear();
        stage.addActor(table);

        final SelectBox<Integer> numPlayersBox = new SelectBox(optionsScreenSkin);
        numPlayersBox.setItems(new Integer[]{2, 3, 4, 5, 6});
        final Label numPlayers = new Label("Number of players:", optionsScreenSkin);

        final TextField player1Field = new TextField("Player 1", optionsScreenSkin);
        final TextField player2Field = new TextField("Player 2", optionsScreenSkin);
        final TextField player3Field = new TextField("Player 3", optionsScreenSkin);
        final TextField player4Field = new TextField("Player 4", optionsScreenSkin);
        final TextField player5Field = new TextField("Player 5", optionsScreenSkin);
        final TextField player6Field = new TextField("Player 6", optionsScreenSkin);

        final ImageButton token1Button = new ImageButton(getTokenDrawable(spriteList.get(0)));
        final ImageButton token2Button = new ImageButton(getTokenDrawable(spriteList.get(1)));
        final ImageButton token3Button = new ImageButton(getTokenDrawable(spriteList.get(2)));
        final ImageButton token4Button = new ImageButton(getTokenDrawable(spriteList.get(3)));
        final ImageButton token5Button = new ImageButton(getTokenDrawable(spriteList.get(4)));
        final ImageButton token6Button = new ImageButton(getTokenDrawable(spriteList.get(5)));

        player3Field.setVisible(false);
        token3Button.setVisible(false);
        player4Field.setVisible(false);
        token4Button.setVisible(false);
        player5Field.setVisible(false);
        token5Button.setVisible(false);
        player6Field.setVisible(false);
        token6Button.setVisible(false);

        numPlayersBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (numPlayersBox.getSelected() == 2) {
                    player3Field.setVisible(false);
                    player4Field.setVisible(false);
                    player5Field.setVisible(false);
                    player6Field.setVisible(false);
                    token3Button.setVisible(false);
                    token4Button.setVisible(false);
                    token5Button.setVisible(false);
                    token6Button.setVisible(false);
                }
                else if (numPlayersBox.getSelected() == 3) {
                    player3Field.setVisible(true);
                    player4Field.setVisible(false);
                    player5Field.setVisible(false);
                    player6Field.setVisible(false);
                    token3Button.setVisible(true);
                    token4Button.setVisible(false);
                    token5Button.setVisible(false);
                    token6Button.setVisible(false);
                }
                else if (numPlayersBox.getSelected() == 4) {
                    player3Field.setVisible(true);
                    player4Field.setVisible(true);
                    player5Field.setVisible(false);
                    player6Field.setVisible(false);
                    token3Button.setVisible(true);
                    token4Button.setVisible(true);
                    token5Button.setVisible(false);
                    token6Button.setVisible(false);
                }
                else if (numPlayersBox.getSelected() == 5) {
                    player3Field.setVisible(true);
                    player4Field.setVisible(true);
                    player5Field.setVisible(true);
                    player6Field.setVisible(false);
                    token3Button.setVisible(true);
                    token4Button.setVisible(true);
                    token5Button.setVisible(true);
                    token6Button.setVisible(false);
                }
                else if (numPlayersBox.getSelected() == 6) {
                    player3Field.setVisible(true);
                    player4Field.setVisible(true);
                    player5Field.setVisible(true);
                    player6Field.setVisible(true);
                    token3Button.setVisible(true);
                    token4Button.setVisible(true);
                    token5Button.setVisible(true);
                    token6Button.setVisible(true);
                }
            }
        });

        token1Button.addListener(new ChangeListener() {
            int swapPos1 = 1;
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                swapSprites(0, swapPos1);
                swapPos1 = (swapPos1 + 1) % spriteList.size();
                token1Button.getStyle().imageUp = getTokenDrawable(spriteList.get(0));
                token2Button.getStyle().imageUp = getTokenDrawable(spriteList.get(1));
                token3Button.getStyle().imageUp = getTokenDrawable(spriteList.get(2));
                token4Button.getStyle().imageUp = getTokenDrawable(spriteList.get(3));
                token5Button.getStyle().imageUp = getTokenDrawable(spriteList.get(4));
                token6Button.getStyle().imageUp = getTokenDrawable(spriteList.get(5));
            }
        });

        token2Button.addListener(new ChangeListener() {
            int swapPos1 = 2;
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                swapSprites(1, swapPos1);
                swapPos1 = (swapPos1 + 1) % spriteList.size();
                token1Button.getStyle().imageUp = getTokenDrawable(spriteList.get(0));
                token2Button.getStyle().imageUp = getTokenDrawable(spriteList.get(1));
                token3Button.getStyle().imageUp = getTokenDrawable(spriteList.get(2));
                token4Button.getStyle().imageUp = getTokenDrawable(spriteList.get(3));
                token5Button.getStyle().imageUp = getTokenDrawable(spriteList.get(4));
                token6Button.getStyle().imageUp = getTokenDrawable(spriteList.get(5));
            }
        });

        token3Button.addListener(new ChangeListener() {
            int swapPos1 = 3;
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                swapSprites(2, swapPos1);
                swapPos1 = (swapPos1 + 1) % spriteList.size();
                token1Button.getStyle().imageUp = getTokenDrawable(spriteList.get(0));
                token2Button.getStyle().imageUp = getTokenDrawable(spriteList.get(1));
                token3Button.getStyle().imageUp = getTokenDrawable(spriteList.get(2));
                token4Button.getStyle().imageUp = getTokenDrawable(spriteList.get(3));
                token5Button.getStyle().imageUp = getTokenDrawable(spriteList.get(4));
                token6Button.getStyle().imageUp = getTokenDrawable(spriteList.get(5));
            }
        });

        token4Button.addListener(new ChangeListener() {
            int swapPos1 = 4;
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                swapSprites(3, swapPos1);
                swapPos1 = (swapPos1 + 1) % spriteList.size();
                token1Button.getStyle().imageUp = getTokenDrawable(spriteList.get(0));
                token2Button.getStyle().imageUp = getTokenDrawable(spriteList.get(1));
                token3Button.getStyle().imageUp = getTokenDrawable(spriteList.get(2));
                token4Button.getStyle().imageUp = getTokenDrawable(spriteList.get(3));
                token5Button.getStyle().imageUp = getTokenDrawable(spriteList.get(4));
                token6Button.getStyle().imageUp = getTokenDrawable(spriteList.get(5));
            }
        });

        token5Button.addListener(new ChangeListener() {
            int swapPos1 = 5;
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                swapSprites(4, swapPos1);
                swapPos1 = (swapPos1 + 1) % spriteList.size();
                token1Button.getStyle().imageUp = getTokenDrawable(spriteList.get(0));
                token2Button.getStyle().imageUp = getTokenDrawable(spriteList.get(1));
                token3Button.getStyle().imageUp = getTokenDrawable(spriteList.get(2));
                token4Button.getStyle().imageUp = getTokenDrawable(spriteList.get(3));
                token5Button.getStyle().imageUp = getTokenDrawable(spriteList.get(4));
                token6Button.getStyle().imageUp = getTokenDrawable(spriteList.get(5));
            }
        });

        token6Button.addListener(new ChangeListener() {
            int swapPos1 = 0;
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                swapSprites(5, swapPos1);
                swapPos1 = (swapPos1 + 1) % spriteList.size();
                token1Button.getStyle().imageUp = getTokenDrawable(spriteList.get(0));
                token2Button.getStyle().imageUp = getTokenDrawable(spriteList.get(1));
                token3Button.getStyle().imageUp = getTokenDrawable(spriteList.get(2));
                token4Button.getStyle().imageUp = getTokenDrawable(spriteList.get(3));
                token5Button.getStyle().imageUp = getTokenDrawable(spriteList.get(4));
                token6Button.getStyle().imageUp = getTokenDrawable(spriteList.get(5));
            }
        });

        TextButton startGame = new TextButton("Start", optionsScreenSkin);
        final TextField[] playerNames = new TextField[]{player1Field,player2Field,player3Field,player4Field,player5Field,player6Field};
        startGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                players=null;
                players = new Player[numPlayersBox.getSelected()];
                for(int i = 0 ; i < numPlayersBox.getSelected(); i++){
                    players[i] = new Player(playerNames[i].getText(), spriteList.get(i));
                }
                game.changeScreen(game.GAME);
            }
        });

        final TextButton back = new TextButton("Back", optionsScreenSkin);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
            }
        });

        table.row().pad(10, 0, 0, 20);
        table.add(numPlayers).left();
        table.add(numPlayersBox);
        table.row().pad(10, 0, 0, 20);
        table.add(player1Field);
        table.add(token1Button);
        table.row().pad(10, 0, 0, 20);
        table.add(player2Field);
        table.add(token2Button);
        table.row().pad(10, 0, 0, 20);
        table.add(player3Field);
        table.add(token3Button);
        table.row().pad(10, 0, 0, 20);
        table.add(player4Field);
        table.add(token4Button);
        table.row().pad(10, 0, 0, 20);
        table.add(player5Field);
        table.add(token5Button);
        table.row().pad(10, 0, 0, 20);
        table.add(player6Field);
        table.add(token6Button);
        table.row().pad(10, 0, 0, 20);
        table.add(startGame).colspan(2);
        table.row().pad(10, 0, 0, 20);
        table.add(back).colspan(2);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        game.batch.begin();

        game.batch.draw(optionsScreenTexture, 0, 0);
        game.font.getData().setScale(2);
        game.font.draw(game.batch, "Property Tycoon Options", 100, 100);
        game.batch.draw(optionsScreenTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        game.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    public Drawable getTokenDrawable(Sprite sprite) {
        return new TextureRegionDrawable(new TextureRegion(sprite.getTexture()));
    }

    public void swapSprites(int s1pos, int s2pos) {
        Collections.swap(spriteList, s1pos, s2pos);
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