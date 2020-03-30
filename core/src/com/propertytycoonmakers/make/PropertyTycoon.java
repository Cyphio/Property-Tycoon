package com.propertytycoonmakers.make;

import Screens.GameScreen;
import Screens.MainMenu;
import Screens.OptionsScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PropertyTycoon extends Game {

	public SpriteBatch batch;
	public BitmapFont font;
	//private LoadingScreen loadingScreen;
	private OptionsScreen optionsScreen;
	private MainMenu mainMenu;
	private GameScreen gameScreen;
	//private EndScreen endScreen;

	public final static int MENU = 0;
	public final static int OPTIONS = 1;
	public final static int GAME = 2;
	//public final static int ENDGAME = 3;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
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

	public void changeScreen(int screen){
		switch(screen){
			case MENU:
				if(mainMenu == null) mainMenu = new MainMenu();
				this.setScreen(mainMenu);
				break;
			case OPTIONS:
				if(optionsScreen == null) optionsScreen = new OptionsScreen();
				this.setScreen(optionsScreen);
				break;
			case GAME:
				if(mainMenu == null) mainMenu = new MainMenu();
				this.setScreen(mainMenu);
				break;
			/**case ENDGAME:
				if(endScreen == null) endScreen = new EndScreen();
				this.setScreen(endScreen);
				break;**/
		}
	}
}
