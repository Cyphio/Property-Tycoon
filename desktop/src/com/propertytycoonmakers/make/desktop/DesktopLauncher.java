package com.propertytycoonmakers.make.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.propertytycoonmakers.make.PropertyTycoon;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Property Tycoon";
		config.width = 1920;
		config.height = 1080;
		//config.fullscreen = false;
		new LwjglApplication(new PropertyTycoon(), config);
	}
}