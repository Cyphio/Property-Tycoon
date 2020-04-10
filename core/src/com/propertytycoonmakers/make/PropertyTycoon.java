package com.propertytycoonmakers.make;

import Screens.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import main.GameController;

public class PropertyTycoon extends Game {


	public static SpriteBatch batch;
	public static BitmapFont font;
	public static GameController gameCon;

	private GameOptions options;
	private GameScreen gameScreen;

	public final static int GAME = 0;


	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		options = new GameOptions();
		gameCon = new GameController();
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
			case GAME:
				if(gameScreen == null) gameScreen = new GameScreen(this);
				this.setScreen(gameScreen);
				break;
		}
	}
}
