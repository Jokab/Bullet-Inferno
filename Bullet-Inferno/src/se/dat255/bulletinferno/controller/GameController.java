package se.dat255.bulletinferno.controller;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.GameImpl;
import se.dat255.bulletinferno.model.Loadout;
import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.PlayerShipImpl;
import se.dat255.bulletinferno.model.PlayerShipImpl.ShipType;
import se.dat255.bulletinferno.model.ResourceManager;
import se.dat255.bulletinferno.model.ResourceManagerImpl;
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

/**
 * 
 */
public class GameController extends SimpleController {

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
	
	/** If the player died; Should not update the game */
	private boolean gameOver;

	/** The views to use when going in or out pause */
	private RenderableGUI pauseScreenView, pauseIconView;

	/** The (center of the) current viewport position, in world coordinates */
	private Vector2 viewportPosition;

	/** The current viewport dimensions, in world coordinates. */
	private Vector2 viewportDimensions;
	
	/** Stores the weapon type for restarting the game */
	private WeaponData weaponData;

	/** Reference to the master controller */
	private MasterController myGame;
	
	/** Reference to the background view */
	static BackgroundView bgView;
	
	private AssetManager assetManager = new AssetManager();
	private ResourceManager resourceManager = new ResourceManagerImpl(assetManager);

	/**
	 * Default controller to set required references
	 * @param myGame The master controller that creates this controller
	 */
	public GameController(MasterController myGame) {
		this.myGame = myGame;
	}

	/**
	 * Creates or recreates a game "state". This method should be called before switching to the
	 * GameScreen.
	 */
	public void createNewGame(WeaponData weaponType) {
		// Initiate instead of declaring statically above
		game = null;
		viewportPosition = new Vector2();
		viewportDimensions = new Vector2();
		this.weaponData = weaponType;
		
		// Original create new game code
		resourceManager.load();

		if (graphics != null) {
			graphics.dispose();
			graphics = null;
		}

		graphics = new Graphics();
		graphics.create();

		game = new GameImpl();
		
		// Create a new loadout for the ship and create the ship
		Loadout loadout = new LoadoutImpl(weaponType.getPlayerWeaponForGame(game), 
									null, 
									new SpecialAbilityImpl(new SpecialProjectileRain(game)), 
									new PassiveAbilityImpl(new PassiveReloadingTime(0.5f))
								);
		PlayerShip ship = new PlayerShipImpl(game, new Vector2(0, 0), 1000000,
								loadout, ShipType.PLAYER_DEFAULT);
		game.setPlayerShip(ship);
		
		PlayerShipView shipView = new PlayerShipView(game, ship, resourceManager);
		graphics.setNewCameraPos(ship.getPosition().x+Graphics.GAME_WIDTH/2, 
				Graphics.GAME_HEIGHT/2);
		graphics.addRenderable(shipView);
		
		
		
		bgView = new BackgroundView(game, resourceManager, ship);
		//graphics.addRenderable(bgView);

		// Set up input handler
		processor = new GameTouchController(graphics, ship);

		setupGUI();

		EnemyView enemyView = new EnemyView(game, resourceManager);
		graphics.addRenderable(enemyView);

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
	
	/** Pauses the game when the application loses focus */
	@Override
	public void pause() {
		super.pause();
		pauseGame();
	}

	/** Pauses the game */
	public void pauseGame() {
		graphics.removeRenderableGUI(pauseIconView);
		graphics.addRenderableGUI(pauseScreenView);
	}
	
	/** Do nothing when application resumes, let the user resume the game. */
	@Override
	public void resume() {}

	/** Unpauses the game */
	public void unpauseGame() {
		graphics.removeRenderableGUI(pauseScreenView);
		graphics.addRenderableGUI(pauseIconView);
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
		graphics.setNewCameraPos(game.getPlayerShip().getPosition().x - 
									game.getPlayerShip().getDimensions().x/2 + 
									Graphics.GAME_WIDTH/2, 
								Graphics.GAME_HEIGHT/2);

		// Render the game
		graphics.render();
		
		if(!gameOver && game.getPlayerShip().isDead()){
			gameOver();
		}

		// Only pause logics, rendering of GUI could still be needed
		if (!isPaused && !gameOver) {
			// Update models. This should be done after graphics rendering, so that
			// graphics commands
			// can be buffered up for being sent to the graphics pipeline.
			// Meanwhile, we run the models.

			// Calculate the new world coordinate position (in the middle) of the viewport.
			viewportPosition = new Vector2(0, 0);
			graphics.screenToWorld(viewportPosition);
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
		graphics.screenToWorld(viewportPosition);
		viewportDimensions = new Vector2(width, 0);
		graphics.screenToWorld(viewportDimensions);

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
