package se.dat255.bulletinferno;

import se.dat255.bulletinferno.controller.MasterController;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Main entry for the desktop deployment, making debugging fast and easy
 * A tip: Try different screen sizes below to check if the graphics looks
 *  as intended on different sizes.
 * @author Marc Jamot
 * @version 1.0
 * @since 2013-09-12
 */
public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Bullet-Inferno";
		cfg.useGL20 = true;
		cfg.width = 800;
		cfg.height = 450;
		
		new LwjglApplication(new MasterController(), cfg);
	}
}
