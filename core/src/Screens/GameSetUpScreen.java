package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.propertytycoonmakers.make.PropertyTycoon;
import com.sun.org.apache.xpath.internal.operations.String;
import main.Player;

import static com.propertytycoonmakers.make.PropertyTycoon.players;

public class GameSetUpScreen implements Screen {

    private PropertyTycoon game;
    private Texture optionsScreenTexture;
    private Skin optionsScreenSkin;
    private Stage stage;
    private Viewport viewport;


    public GameSetUpScreen(PropertyTycoon game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        this.optionsScreenTexture = new Texture(Gdx.files.internal("mainMenuTexture.png"));
        this.optionsScreenSkin = new Skin(Gdx.files.internal("skin/comic-ui.json"));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Table table = new Table();
        table.setFillParent(true);
        stage.clear();
        stage.addActor(table);

// Texture and sprites for tokens
        Texture texture1 = new Texture(Gdx.files.internal("tokens/token1.png"));
        Sprite sprite1 = new Sprite(texture1);

        Texture texture2= new Texture(Gdx.files.internal("tokens/token2.png"));
        Sprite sprite2 = new Sprite(texture2);

        Texture texture3 = new Texture(Gdx.files.internal("tokens/token3.png"));
        Sprite sprite3= new Sprite(texture3);

        Texture texture4 = new Texture(Gdx.files.internal("tokens/token4.png"));
        Sprite sprite4 = new Sprite(texture4);

        Texture texture5= new Texture(Gdx.files.internal("tokens/token5.png"));
        Sprite sprite5 = new Sprite(texture5);

        Texture texture6= new Texture(Gdx.files.internal("tokens/token6.png"));
        Sprite sprite6 = new Sprite(texture6);

//        ImageButton tokenButton1 = new ImageButton("tokens/token6.png");
//        ImageButton tokenButton2 = new ImageButton((Drawable) texture2);
//        ImageButton tokenButton3 = new ImageButton((Drawable) texture3);
//        ImageButton tokenButton4 = new ImageButton((Drawable) texture4);
//        ImageButton tokenButton5 = new ImageButton((Drawable) texture5);
//        ImageButton tokenButton6 = new ImageButton((Drawable) texture6);

        final SelectBox<Integer> numPlayersBox = new SelectBox(optionsScreenSkin);
        numPlayersBox.setItems(new Integer[]{2, 3, 4, 5, 6});
        final Label numPlayers = new Label("Number of players:", optionsScreenSkin);

//select boxes for sprites in player set up (doesnt display images so might not use)
        final SelectBox<Sprite> tokenSelector1 = new SelectBox(optionsScreenSkin);
        tokenSelector1.setItems(new Sprite[]{sprite1,sprite2,sprite3,sprite4,sprite5,sprite6});
        final SelectBox<Sprite> tokenSelector2 = new SelectBox(optionsScreenSkin);
        tokenSelector2.setItems(new Sprite[]{sprite1,sprite2,sprite3,sprite4,sprite5,sprite6});
        final SelectBox<Sprite> tokenSelector3 = new SelectBox(optionsScreenSkin);
        tokenSelector3.setItems(new Sprite[]{sprite1,sprite2,sprite3,sprite4,sprite5,sprite6});
        final SelectBox<Sprite> tokenSelector4 = new SelectBox(optionsScreenSkin);
        tokenSelector4.setItems(new Sprite[]{sprite1,sprite2,sprite3,sprite4,sprite5,sprite6});
        final SelectBox<Sprite> tokenSelector5 = new SelectBox(optionsScreenSkin);
        tokenSelector5.setItems(new Sprite[]{sprite1,sprite2,sprite3,sprite4,sprite5,sprite6});
        final SelectBox<Sprite> tokenSelector6 = new SelectBox(optionsScreenSkin);
        tokenSelector6.setItems(new Sprite[]{sprite1,sprite2,sprite3,sprite4,sprite5,sprite6});

        final TextField player1Field = new TextField("Player 1", optionsScreenSkin);
        final TextField player2Field = new TextField("Player 2", optionsScreenSkin);
        final TextField player3Field = new TextField("Player 3", optionsScreenSkin);
        final TextField player4Field = new TextField("Player 4", optionsScreenSkin);
        final TextField player5Field = new TextField("Player 5", optionsScreenSkin);
        final TextField player6Field = new TextField("Player 6", optionsScreenSkin);

        player3Field.setVisible(false);
        player4Field.setVisible(false);
        player5Field.setVisible(false);
        player6Field.setVisible(false);


        final TextButton back = new TextButton("Back", optionsScreenSkin);

        numPlayersBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if (numPlayersBox.getSelected() == 2){

                    player3Field.setVisible(false);
                    player4Field.setVisible(false);
                    player5Field.setVisible(false);
                    player6Field.setVisible(false);


                }else if (numPlayersBox.getSelected() == 3){
                    player3Field.setVisible(true);
                    player4Field.setVisible(false);
                    player5Field.setVisible(false);
                    player6Field.setVisible(false);

                }else if (numPlayersBox.getSelected() == 4){
                    player3Field.setVisible(true);
                    player4Field.setVisible(true);
                    player5Field.setVisible(false);
                    player6Field.setVisible(false);

                }else if (numPlayersBox.getSelected() == 5){
                    player3Field.setVisible(true);
                    player4Field.setVisible(true);
                    player5Field.setVisible(true);
                    player6Field.setVisible(false);

                }else if (numPlayersBox.getSelected() == 6){
                    player3Field.setVisible(true);
                    player4Field.setVisible(true);
                    player5Field.setVisible(true);
                    player6Field.setVisible(true);

                }



            }
        });

        final TextField[] fields = new TextField[]{player1Field,player2Field,player3Field,player4Field,player5Field,player6Field};

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
            }
        });

        TextButton startGame = new TextButton("Start", optionsScreenSkin);

        TextButton test = new TextButton("test", optionsScreenSkin);


        test.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back.setVisible(false);
            }

        });


        startGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                players=null;
                players = new Player[numPlayersBox.getSelected()];

                for(int i = 0 ; i < numPlayersBox.getSelected(); i++){

// CHANGE THE TOKEN TYPE LATER WHEN MAKING A SELECTOR
                    players[i] = new Player(fields[i].getText(), null);
                }
                game.changeScreen(game.GAME);
            }
        });

        table.row().pad(10, 0, 0, 20);
        table.add(numPlayers).left();
        table.add(numPlayersBox);
        table.row().pad(10, 0, 0, 20);
        table.add(player1Field);
        table.row().pad(10, 0, 0, 20);
        table.add(player2Field);
        table.row().pad(10, 0, 0, 20);
        table.add(player3Field);
        table.row().pad(10, 0, 0, 20);
        table.add(player4Field);
        table.row().pad(10, 0, 0, 20);
        table.add(player5Field);
        table.row().pad(10, 0, 0, 20);
        table.add(player6Field);
        table.row().pad(50, 0, 0, 20);
        table.add(startGame);
        table.row().pad(10, 0, 0, 20);
        table.add(back);
        table.row().pad(10, 0, 0, 20);
        table.add(test);
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