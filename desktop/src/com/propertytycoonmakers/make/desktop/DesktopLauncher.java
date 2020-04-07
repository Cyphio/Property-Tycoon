package com.propertytycoonmakers.make.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.propertytycoonmakers.make.PropertyTycoon;

import java.awt.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		config.width = (int)dimension.getWidth();
		config.height = (int)dimension.getHeight();
		System.out.println(dimension.getHeight());
		new LwjglApplication(new PropertyTycoon(), config);
	}
}