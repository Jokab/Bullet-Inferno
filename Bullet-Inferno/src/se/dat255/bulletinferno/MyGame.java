package se.dat255.bulletinferno;

import se.dat255.bulletinferno.controller.Touch;
import se.dat255.bulletinferno.model.Enemy;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.GameImpl;
import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.PlayerShipImpl;
import se.dat255.bulletinferno.model.ProjectileImpl;
import se.dat255.bulletinferno.model.enemy.DefaultEnemyShipImpl;
import se.dat255.bulletinferno.view.EnemyView;
import se.dat255.bulletinferno.view.ProjectileView;
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
	 * Also handles converting between <b>world</b> and <b>local</b> positions.
	 */
	private final Graphics graphics = new Graphics();

	/**
	 * The touch input handler
	 */
	private InputProcessor processor;

	/** The current session instance of the game model. */
	private Game game = null;
	
	@Override
	public void create() {
		// TODO: Initialize the game
		graphics.create();

		// TODO: should probably not be created here
		// Set up the player ship, view and add it to gfx.
		
		this.game = new GameImpl();
		
		PlayerShip ship = new PlayerShipImpl(new Vector2(0, 0), game, 100);
		ShipView shipView = new ShipView(ship);
		graphics.addRenderable(shipView);

		// Set up input handler
		processor = new Touch(graphics, ship);
		Gdx.input.setInputProcessor(processor);

		// TODO: Debug test spawn enemy to draw in world coord
		setupHardcodedEnemies();
	
		// TODO: Debug test add bullet
		//ProjectileImpl projectile = new ProjectileImpl(null);
		//projectile.setPosition(new Vector2(5, 7));
		ProjectileView projectileView = new ProjectileView(game);
		graphics.addRenderable(projectileView);
	}

	private void setupHardcodedEnemies() {
		Vector2 position = new Vector2(16 - 1, (9 / 3f) * 1 - 2);
		Vector2 position2 = new Vector2(16 - 1, (9 / 3f) * 2 - 2);
		Vector2 position3 = new Vector2(16 - 1, (9 / 3f) * 3 - 2);

		Vector2 velocity = new Vector2(0, 2);
		Enemy enemy = new DefaultEnemyShipImpl(position, velocity, 100);
		Enemy enemy2 = new DefaultEnemyShipImpl(position2, velocity, 100);
		Enemy enemy3 = new DefaultEnemyShipImpl(position3, velocity, 100);

		EnemyView eV = new EnemyView(enemy);
		EnemyView eV2 = new EnemyView(enemy2);
		EnemyView eV3 = new EnemyView(enemy3);

		graphics.addRenderable(eV);
		graphics.addRenderable(eV2);
		graphics.addRenderable(eV3);

	}

	@Override
	public void dispose() {
		graphics.dispose();
	}

	/**
	 * Main game entry loop. Called every frame and should update logic etc.
	 */
	@Override
	public void render() {
	    // The time since the last frame in seconds.
	    float deltaTime = Gdx.graphics.getDeltaTime();
	    
		// Render the game
		graphics.render();
		
		// Update models. This should be done after graphics rendering, so that graphics commands
		// can be buffered up for being sent to the graphics pipeline. Meanwhile, we run the models.
		game.update(deltaTime);
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
