package se.dat255.bulletinferno;

import se.dat255.bulletinferno.controller.Touch;
import se.dat255.bulletinferno.model.Enemy;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.GameImpl;
import se.dat255.bulletinferno.model.Loadout;
import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.PlayerShipImpl;
import se.dat255.bulletinferno.model.PlayerShipImpl.ShipType;
import se.dat255.bulletinferno.model.ResourceManager;
import se.dat255.bulletinferno.model.ResourceManagerImpl;
import se.dat255.bulletinferno.model.enemy.EnemyType;
import se.dat255.bulletinferno.model.loadout.LoadoutImpl;
import se.dat255.bulletinferno.model.loadout.PassiveAbilityImpl;
import se.dat255.bulletinferno.model.loadout.PassiveReloadingTime;
import se.dat255.bulletinferno.model.loadout.SpecialAbilityImpl;
import se.dat255.bulletinferno.model.loadout.SpecialProjectileRain;
import se.dat255.bulletinferno.model.weapon.WeaponData;
import se.dat255.bulletinferno.view.BackgroundView;
import se.dat255.bulletinferno.view.EnemyView;
import se.dat255.bulletinferno.view.ProjectileView;
import se.dat255.bulletinferno.view.RenderableGUI;
import se.dat255.bulletinferno.view.PlayerShipView;
import se.dat255.bulletinferno.view.gui.GameoverScreenView;
import se.dat255.bulletinferno.view.gui.PauseIconView;
import se.dat255.bulletinferno.view.gui.PauseScreenView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;

public class GameScreen extends AbstractScreen {

	/**
	 * Handles all the graphics with the game.<br>
	 * Also handles converting between <b>world</b> and <b>local</b> positions.
	 */
	private Graphics graphics;

	/**
	 * The touch input handler
	 */
	private InputProcessor processor;

	/** The current session instance of the game model. */
	private Game game;

	/** If the game is paused; Should not update the game */
	private boolean gamePaused;
	
	/** If the player died; Should not update the game */
	private boolean gameOver;

	/** The views to use when going in or out pause */
	private RenderableGUI pauseScreenView, pauseIconView;

	/** The (center of the) current viewport position, in world coordinates */
	private Vector2 viewportPosition;

	/** The current viewport dimensions, in world coordinates. */
	private Vector2 viewportDimensions;
	
	/** Stores te weapon type for restarting the game */
	private WeaponData weaponData;

	private MyGame myGame;
	
	private PlayerShip ship;
	
	static BackgroundView bgView;
	
	private AssetManager assetManager = new AssetManager();
	private ResourceManager resourceManager = new ResourceManagerImpl(assetManager);

	public GameScreen(MyGame myGame) {
		this.myGame = myGame;
	}

	/**
	 * Creates or recreates a game "state". This method should be called before switching to the
	 * GameScreen.
	 * 
	 */
	public void createNewGame(WeaponData weaponType) {
		// Initiate instead of declaring statically above
		game = null;
		viewportPosition = new Vector2();
		viewportDimensions = new Vector2();
		this.weaponData = weaponType;
		
		// Original create new game code
		resourceManager.load();
		Gdx.app.log("GameScreen", "createNewGame, weaponType = " + weaponType);

		if (graphics != null) {
			graphics.dispose();
			graphics = null;
		}

		// TODO: Initialize the game
		graphics = new Graphics();
		graphics.create();

		// TODO: should probably not be created here
		// Set up the player ship, view and add it to gfx.

		game = new GameImpl();
		
		Loadout loadout = new LoadoutImpl(weaponType.getPlayerWeaponForGame(game), null, 
				new SpecialAbilityImpl(new SpecialProjectileRain(game)), 
				new PassiveAbilityImpl(new PassiveReloadingTime(0.5f)));
		ship = new PlayerShipImpl(game, new Vector2(0, 0), 1,
				loadout, ShipType.PLAYER_DEFAULT);
		game.setPlayerShip(ship);
		PlayerShipView shipView = new PlayerShipView(game, ship, resourceManager);
		graphics.setNewCameraPos(ship.getPosition().x+Graphics.GAME_WIDTH/2, Graphics.GAME_HEIGHT/2);
		graphics.addRenderable(shipView);
		
		bgView = new BackgroundView(game, resourceManager, ship);
		//graphics.addRenderable(bgView);

		// Set up input handler
		processor = new Touch(game, graphics, ship);

		// TODO: Move the gui setup to when the player enters a level
		setupGUI();

		setupHardcodedEnemies();

		// TODO: Debug test add bullet
		// ProjectileImpl projectile = new ProjectileImpl(null);
		// projectile.setPosition(new Vector2(5, 7));
		ProjectileView projectileView = new ProjectileView(game, resourceManager);
		graphics.addRenderable(projectileView);
	}

	/** Initiates the pause components when the player starts a level */
	private void setupGUI() {
		pauseIconView = new PauseIconView(this);
		pauseScreenView = new PauseScreenView(this, resourceManager);
		graphics.addRenderableGUI(pauseIconView);
	}
	
	/** The player has died, the game is over */
	public void gameOver() {
		gameOver = true;
		RenderableGUI gameOver = new GameoverScreenView(myGame, resourceManager);
		graphics.addRenderableGUI(gameOver);
	}

	/** Pauses the game */
	public void pauseGame() {
		gamePaused = true;
		graphics.removeRenderableGUI(pauseIconView);
		graphics.addRenderableGUI(pauseScreenView);
	}

	/** Unpauses the game */
	public void unpauseGame() {
		gamePaused = false;
		graphics.removeRenderableGUI(pauseScreenView);
		graphics.addRenderableGUI(pauseIconView);
	}

	private void setupHardcodedEnemies() {
		Vector2 position = new Vector2(16 - 1, 9 / 3f * 1 - 2);
		Vector2 position2 = new Vector2(16 - 1, 9 / 3f * 2 - 2);
		Vector2 position3 = new Vector2(16 - 1, 9 / 3f * 3 - 2);
		
		Enemy enemy = EnemyType.DEFAULT_ENEMY_SHIP.getEnemyShip(game, position);
		Enemy enemy2 = EnemyType.SPECIAL_ENEMY_SHIP.getEnemyShip(game, position2);
		Enemy enemy3 = EnemyType.DEFAULT_ENEMY_SHIP.getEnemyShip(game, position3);

		game.addEnemy(enemy);
		game.addEnemy(enemy2);
		game.addEnemy(enemy3);

		EnemyView enemyView = new EnemyView(game, resourceManager);
		graphics.addRenderable(enemyView);
	}

	@Override
	public void show() {
		super.show();
		Gdx.input.setInputProcessor(processor);
	}

	@Override
	public void dispose() {
		graphics.dispose();

		if (game != null) {
			game.dispose();
		}
	}

	/**
	 * Main game entry loop. Called every frame and should update logic etc.
	 */
	@Override
	public void render(float delta) {
		graphics.setNewCameraPos(ship.getPosition().x-ship.getDimensions().x/2+Graphics.GAME_WIDTH/2, Graphics.GAME_HEIGHT/2);

		// Render the game
		graphics.render();
		
		if(!gameOver && game.getPlayerShip().isDead()){
			gameOver();
		}

		// Only pause logics, rendering of GUI could still be needed
		if (!gamePaused && !gameOver) {
			// Update models. This should be done after graphics rendering, so that
			// graphics commands
			// can be buffered up for being sent to the graphics pipeline.
			// Meanwhile, we run the models.

			// Calculate the new world coordinate position (in the middle) of the viewport.
			viewportPosition = new Vector2(0, 0);
			Graphics.screenToWorld(viewportPosition);
			viewportPosition.add(0.5f * viewportDimensions.x, 0);
			viewportPosition.sub(0, 0.5f * viewportDimensions.y);

			game.setViewport(viewportPosition, viewportDimensions);

			game.update(delta);
		}
	}

	@Override
	public void resize(int width, int height) {
		graphics.resize(width, height);

		// Get bottom left (at zero, world origo) and top right corners for further calculation...
		viewportPosition = new Vector2(0, height);
		Graphics.screenToWorld(viewportPosition);
		viewportDimensions = new Vector2(width, 0);
		Graphics.screenToWorld(viewportDimensions);

		// ...adjust dimensions to bottom left corner...
		viewportDimensions.sub(viewportPosition);

		// ...adjust position to being in the middle of the viewport...
		viewportPosition.add(0.5f * viewportDimensions.x, 0.5f * viewportDimensions.y);

		game.setViewport(viewportPosition, viewportDimensions);
	}
	
	public static BackgroundView getBgView(){
		return bgView;
	}
	
	/** Get method for weapon data set in create new game */
	public WeaponData getWeaponData(){
		return weaponData;
	}

}
