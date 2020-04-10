package com.propertytycoonmakers.make;

import Screens.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import main.GameController;

public class PropertyTycoon extends Game {


	public SpriteBatch batch;
	public BitmapFont font;
	public GameController gameCon;

	private GameOptions options;
	private OptionsScreen optionsScreen;
	private MainMenu mainMenu;
	private GameScreen gameScreen;
	private PauseScreen pauseScreen;
	private GameSetUpScreen setupScreen;

	public final static int MAINMENU = 0;
	public final static int OPTIONS = 1;
	public final static int PAUSE = 2;
	public final static int GAME = 3;
	public final static int SETUP = 4;


	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		options = new GameOptions();
		gameCon = new GameController();
		this.setScreen(new LoadingScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}

	public GameOptions getPreferences() {
		return options;
	}

	public void changeScreen(int screen){
		switch(screen){
			case MAINMENU:
				if(mainMenu == null) mainMenu = new MainMenu(this);
				this.setScreen(mainMenu);
				break;
			case OPTIONS:
				if(optionsScreen == null) optionsScreen = new OptionsScreen(this);
				this.setScreen(optionsScreen);
				break;
			case PAUSE:
				if(pauseScreen == null) pauseScreen = new PauseScreen(this);
				this.setScreen(pauseScreen);
				break;
			case GAME:
				if(gameScreen == null) gameScreen = new GameScreen(this);
				this.setScreen(gameScreen);
				break;
			case SETUP:
				if(setupScreen== null) setupScreen = new GameSetUpScreen(this);
				this.setScreen(setupScreen);
				break;
		}
	}


}
