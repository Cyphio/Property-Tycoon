package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.propertytycoonmakers.make.PropertyTycoon;
import com.sun.org.apache.xpath.internal.operations.String;

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
        stage.addActor(table);


        final SelectBox<Integer> numPlayersBox = new SelectBox(optionsScreenSkin);

        numPlayersBox.setItems(new Integer[]{2, 3, 4, 5, 6});


        Label numPlayers = new Label("Number of players:", optionsScreenSkin);


        final TextField player1Field = new TextField("Player 1 name", optionsScreenSkin);
        final TextField player2Field = new TextField("Player 2 name", optionsScreenSkin);
        final TextField player3Field = new TextField("Player 3 name", optionsScreenSkin);
        final TextField player4Field = new TextField("Player 4 name", optionsScreenSkin);
        final TextField player5Field = new TextField("Player 5 name", optionsScreenSkin);
        final TextField player6Field = new TextField("Player 6 name", optionsScreenSkin);

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





        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(game.MAINMENU);
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