package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.propertytycoonmakers.make.PropertyTycoon;

public class OptionsScreen implements Screen {

    private PropertyTycoon game;
    private Texture optionsScreenTexture;
    private Skin optionsScreenSkin;
    private Stage stage;
    private Screen backScreen;

    public OptionsScreen(PropertyTycoon game, Screen backScreen) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        this.backScreen = backScreen;
        this.optionsScreenTexture = new Texture(Gdx.files.internal("mainMenuTexture.png"));
        this.optionsScreenTexture.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
        this.optionsScreenSkin = new Skin(Gdx.files.internal("skin/comic-ui.json"));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.clear();
        stage.addActor(table);

        Label musicVolumeLabel = new Label("Music Volume", optionsScreenSkin);
        Label musicOnOffLabel = new Label("Music On/Off", optionsScreenSkin);
        Label fxVolumeLabel = new Label("FX Volume", optionsScreenSkin);
        Label fxOnOffLabel = new Label("FX On/Off", optionsScreenSkin);

        final Slider musicVolumeSlider = new Slider(0f, 1f, 0.1f, false, optionsScreenSkin);
        musicVolumeSlider.setValue(game.getPreferences().getMusicVolume());
        musicVolumeSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                game.getPreferences().setMusicVolume(musicVolumeSlider.getValue());
                return false;
            }
        });

        final Slider fxVolumeSlider = new Slider(0f, 1f, 0.1f, false, optionsScreenSkin);
        fxVolumeSlider.setValue(game.getPreferences().getFxVolume());
        fxVolumeSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                game.getPreferences().setFxVolume(fxVolumeSlider.getValue());
                return false;
            }
        });

        final CheckBox musicOnOff = new CheckBox(null, optionsScreenSkin);
        musicOnOff.setChecked(game.getPreferences().isMusicEnabled());
        musicOnOff.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean isOn = musicOnOff.isChecked();
                game.getPreferences().setMusicEnabled(isOn);
                return false;
            }
        });

        final CheckBox fxOnOff = new CheckBox(null, optionsScreenSkin);
        fxOnOff.setChecked(game.getPreferences().isFxEnabled());
        fxOnOff.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean isOn = fxOnOff.isChecked();
                game.getPreferences().setFxEnabled(isOn);
                return false;
            }
        });

        final TextButton back = new TextButton("Back", optionsScreenSkin);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(backScreen);
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