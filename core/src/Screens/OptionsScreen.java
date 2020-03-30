package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.propertytycoonmakers.make.PropertyTycoon;

public class OptionsScreen implements Screen {

    private final PropertyTycoon game;
    private Texture optionsScreenTexture;
    private Skin optionsScreenSkin;
    private Stage stage;

    public OptionsScreen(PropertyTycoon game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.optionsScreenTexture = new Texture(Gdx.files.internal("optionsScreenTexture.png"));
        this.optionsScreenSkin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"));
    }

    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        final Slider musicVolumeSlider = new Slider(0f, 1f, 0.1f, false, optionsScreenSkin);
        final Slider fxVolumeSlider = new Slider(0f, 1f, 0.1f, false, optionsScreenSkin);
        final CheckBox musicOnOff = new CheckBox(null, optionsScreenSkin);
        final CheckBox fxOnOff = new CheckBox(null, optionsScreenSkin);
        final TextButton back = new TextButton("Back", optionsScreenSkin);
        Label titleLabel = new Label("Preferences", optionsScreenSkin);
        Label musicVolumeLabel = new Label(null, optionsScreenSkin);
        Label fxVolumeLabel = new Label(null, optionsScreenSkin);
        Label musicOnOffLabel = new Label(null, optionsScreenSkin);
        Label fxOnOffLabel = new Label(null, optionsScreenSkin);

        table.add(titleLabel);
        table.row();
        table.add(musicVolumeLabel);
        table.add(musicVolumeSlider);
        table.row();
        table.add(musicOnOffLabel);
        table.add(musicOnOff);
        table.row();
        table.add(fxVolumeLabel);
        table.add(fxVolumeSlider);
        table.row();
        table.add(fxOnOffLabel);
        table.add(fxOnOff);
        table.add(back);

        musicVolumeSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                game.getPreferences().setMusicVolume(musicVolumeSlider.getValue());
                return false;
            }
        });

        fxVolumeSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                game.getPrefs().setMusicVolume(fxVolumeSlider.getValue());
                return false;
            }
        });

        musicOnOff.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean isOn = musicOnOff.isChecked();
                game.getPreferences.setMusicEnabled(isOn);
                return false;
            }
        });

        fxOnOff.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean isOn = fxOnOff.isChecked();
                game.getPreferences.setMusicEnabled(isOn);
                return false;
            }
        });

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(optionsScreenTexture, 0, 0);
        //game.font.getData().setScale(2);
        //game.font.draw(game.batch, "Property Tycoon", 100, 100);

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
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}

