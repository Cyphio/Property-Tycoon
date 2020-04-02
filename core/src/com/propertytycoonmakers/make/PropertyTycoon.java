package com.propertytycoonmakers.make;

import Screens.GameScreen;
import Screens.MainMenu;
import Screens.OptionsScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PropertyTycoon extends Game {

	public SpriteBatch batch;
	public BitmapFont font;

	private GameOptions options;

	private OptionsScreen optionsScreen;
	private MainMenu mainMenu;
	private GameScreen gameScreen;
	public final static int MAINMENU = 0;
	public final static int OPTIONSxMAINMENU = 1;
	public final static int OPTIONSxGAME = 2;
	public final static int GAME = 3;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		options = new GameOptions();
		this.setScreen(new MainMenu(this));
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
			case OPTIONSxMAINMENU:
				if(optionsScreen == null) optionsScreen = new OptionsScreen(this, mainMenu);
				this.setScreen(optionsScreen);
				break;
			case OPTIONSxGAME:
				if(optionsScreen == null) optionsScreen = new OptionsScreen(this, gameScreen);
				this.setScreen(optionsScreen);
				break;
			case GAME:
				if(gameScreen == null) gameScreen = new GameScreen(this);
				this.setScreen(gameScreen);
				break;
		}
	}
}
