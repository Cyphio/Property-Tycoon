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

public class PauseScreen implements Screen {

    private PropertyTycoon game;
    private Texture pauseScreenTexture;
    private Skin pauseScreenSkin;
    private Stage stage;

    public PauseScreen(PropertyTycoon game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        this.pauseScreenTexture = new Texture(Gdx.files.internal("mainMenuTexture.png"));
        this.pauseScreenSkin = new Skin(Gdx.files.internal("skin/comic-ui.json"));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.clear();
        stage.addActor(table);

        Label musicVolumeLabel = new Label("Music Volume", pauseScreenSkin);
        Label musicOnOffLabel = new Label("Music On/Off", pauseScreenSkin);
        Label fxVolumeLabel = new Label("FX Volume", pauseScreenSkin);
        Label fxOnOffLabel = new Label("FX On/Off", pauseScreenSkin);

        final Slider musicVolumeSlider = new Slider(0f, 1f, 0.1f, false, pauseScreenSkin);
        musicVolumeSlider.setValue(game.getPreferences().getMusicVolume());
        musicVolumeSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                game.getPreferences().setMusicVolume(musicVolumeSlider.getValue());
                return false;
            }
        });

        final Slider fxVolumeSlider = new Slider(0f, 1f, 0.1f, false,pauseScreenSkin);
        fxVolumeSlider.setValue(game.getPreferences().getFxVolume());
        fxVolumeSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                game.getPreferences().setFxVolume(fxVolumeSlider.getValue());
                return false;
            }
        });

        final CheckBox musicOnOff = new CheckBox(null, pauseScreenSkin);
        musicOnOff.setChecked(game.getPreferences().isMusicEnabled());
        musicOnOff.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean isOn = musicOnOff.isChecked();
                game.getPreferences().setMusicEnabled(isOn);
                return false;
            }
        });

        final CheckBox fxOnOff = new CheckBox(null, pauseScreenSkin);
        fxOnOff.setChecked(game.getPreferences().isFxEnabled());
        fxOnOff.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean isOn = fxOnOff.isChecked();
                game.getPreferences().setFxEnabled(isOn);
                return false;
            }
        });

        final TextButton resume = new TextButton("Resume", pauseScreenSkin);
        resume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(game.GAME);
            }
        });

        final TextButton backToMainMenu = new TextButton("Back To Main Menu", pauseScreenSkin);
        backToMainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(game.MAINMENU);
            }
        });

        table.row().pad(10, 0, 0, 20);
        table.add(musicVolumeLabel).left();
        table.add(musicVolumeSlider);
        table.row().pad(10, 0, 0, 20);
        table.add(musicOnOffLabel).left();
        table.add(musicOnOff);
        table.row().pad(10, 0, 0, 20);
        table.add(fxVolumeLabel).left();
        table.add(fxVolumeSlider);
        table.row().pad(10, 0, 0, 20);
        table.add(fxOnOffLabel).left();
        table.add(fxOnOff);
        table.row().pad(10, 0, 0, 20);
        table.add(resume).colspan(2);
        table.row().pad(10, 0, 0, 20);
        table.add(backToMainMenu).colspan(2);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(pauseScreenTexture, 0, 0);
        game.font.getData().setScale(2);
        game.font.draw(game.batch, "Property Tycoon Paused", 100, 100);

        game.batch.end();

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
