package com.propertytycoonmakers.make.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.propertytycoonmakers.make.PropertyTycoon;

import java.awt.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Property Tycoon";
		Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		config.width = (int)dimension.getWidth();
		config.height = (int)dimension.getHeight();
		config.resizable = false;
		new LwjglApplication(new PropertyTycoon(), config);
	}
}