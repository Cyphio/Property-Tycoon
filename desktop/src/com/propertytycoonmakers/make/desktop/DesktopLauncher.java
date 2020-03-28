package com.propertytycoonmakers.make.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import Screens.MainMenu;
import com.propertytycoonmakers.make.PropertyTycoon;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Property Tycoon";
		config.width = 1280;
		config.height = 720;
		new LwjglApplication(new PropertyTycoon(), config);
	}
}