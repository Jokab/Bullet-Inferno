package se.dat255.bulletinferno;

import se.dat255.bulletinferno.controller.Touch;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.GameImpl;
import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.PlayerShipImpl;
import se.dat255.bulletinferno.model.enemy.DefaultEnemyShipImpl;
import se.dat255.bulletinferno.model.weapon.WeaponData;
import se.dat255.bulletinferno.view.EnemyView;
import se.dat255.bulletinferno.view.ProjectileView;
import se.dat255.bulletinferno.view.ShipView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class GameScreen extends AbstractScreen {

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

	private MyGame myGame;

	public GameScreen(MyGame myGame) {
		this.myGame = myGame;

		// TODO: Initialize the game
		graphics.create();

		// TODO: should probably not be created here
		// Set up the player ship, view and add it to gfx.

		game = new GameImpl();

		PlayerShip ship = new PlayerShipImpl(game, new Vector2(0, 0), 100, WeaponData.STANDARD);
		ShipView shipView = new ShipView(ship);
		graphics.addRenderable(shipView);

		// Set up input handler
		processor = new Touch(graphics, ship);

		// TODO: Debug test spawn enemy to draw in world coord
		setupHardcodedEnemies();

		// TODO: Debug test add bullet
		// ProjectileImpl projectile = new ProjectileImpl(null);
		// projectile.setPosition(new Vector2(5, 7));
		ProjectileView projectileView = new ProjectileView(game);
		graphics.addRenderable(projectileView);
	}

	@Override
	public void show() {
		super.show();
		Gdx.input.setInputProcessor(processor);
	}

	@Override
	public void hide() {
		super.hide();
		Gdx.input.setInputProcessor(null);
	}

	private void setupHardcodedEnemies() {
		Vector2 position = new Vector2(16 - 1, 9 / 3f * 1 - 2);
		Vector2 position2 = new Vector2(16 - 1, 9 / 3f * 2 - 2);
		Vector2 position3 = new Vector2(16 - 1, 9 / 3f * 3 - 2);

		Vector2 velocity = new Vector2(-3, 0);
		game.addEnemy(new DefaultEnemyShipImpl(game, position, velocity, 10));
		game.addEnemy(new DefaultEnemyShipImpl(game, position2, velocity, 10));
		game.addEnemy(new DefaultEnemyShipImpl(game, position3, velocity, 10));

		EnemyView enemyView = new EnemyView(game);

		graphics.addRenderable(enemyView);
	}

	/**
	 * Main game entry loop. Called every frame and should update logic etc.
	 */
	@Override
	public void render(float delta) {
		super.render(delta);

		// Render the game
		graphics.render();

		// Update models. This should be done after graphics rendering, so that
		// graphics commands
		// can be buffered up for being sent to the graphics pipeline.
		// Meanwhile, we run the models.
		game.update(delta);
	}

	@Override
	public void resize(int width, int height) {

	}

}
