package se.dat255.bulletinferno;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.controller.Touch;
import se.dat255.bulletinferno.model.Enemy;
import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.PlayerShipImpl;
import se.dat255.bulletinferno.view.EnemyView;
import se.dat255.bulletinferno.view.ShipView;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

/**
 * The main class that follows with LibGDX, shows a simple way of rendering an
 * image to the screen
 * 
 * For convenience of working several people on the same project, the rendering
 * part could be put in it's own class (Graphics.java), and logic could be put
 * in it's own class (GameLoop.java).
 * 
 * The entry is in the render() method below, and that method could call the
 * both classes mentioned above. In a game it's recommended to do the processing
 * in this order: 1) Check input from player and process it 2) Update game
 * logic; E.g. player position, collisions 3) Render the graphics 4) Repeat ->
 * (1)
 * 
 * All updates in the game loop should account for time passed since we never
 * know how time has passed. Time elapsed in seconds can be received by:
 * Gdx.graphics.getDeltaTime() All movement etc should be: x += speed *
 * deltaTime; All update methods should take in (float delta) which will be
 * passed from the calling code.
 * 
 * @author LibGDX
 * @author Marc Jamot
 * @version 1.0
 * @since 2013-09-12
 */
public class MyGame implements ApplicationListener {
	
	/** 
	 * Handles all the graphics with the game.<br>
	 * Also handles converting between <b>world</b> and 
	 * <b>local</b> positions.
	 */
	private final Graphics graphics = new Graphics();
	
	/**
	 * The touch input handler
	 */
	private InputProcessor processor;

	@Override
	public void create() {
		// TODO: Initialize the game
		graphics.create();
		
		// TODO: should probably not be created here
		// Set up the player ship, view and add it to gfx.
		PlayerShip ship = new PlayerShipImpl(0, 0);
		ShipView shipView = new ShipView(ship);
		graphics.addRenderable(shipView);
		
		// Set up input handler
		processor = new Touch(graphics, ship);
		Gdx.input.setInputProcessor(processor);
	}

	@Override
	public void dispose() {
		graphics.dispose();
	}

	/**
	 * Main game entry loop.
	 * Called every frame and should update
	 * logic etc.
	 */
	@Override
	public void render() {
		// Update game logics
		
		// Render the game
		graphics.render();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void resize(int width, int height) {
		graphics.resize(width, height);
	}
}
