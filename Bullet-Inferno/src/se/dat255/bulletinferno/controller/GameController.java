package se.dat255.bulletinferno.controller;

import se.dat255.bulletinferno.model.ModelEnvironment;
import se.dat255.bulletinferno.model.ModelEnvironmentImpl;
import se.dat255.bulletinferno.model.entity.PlayerShip;
import se.dat255.bulletinferno.model.weapon.WeaponData;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.ResourceManagerImpl;
import se.dat255.bulletinferno.view.BackgroundView;
import se.dat255.bulletinferno.view.EnemyView;
import se.dat255.bulletinferno.view.PlayerShipView;
import se.dat255.bulletinferno.view.ProjectileView;
import se.dat255.bulletinferno.view.RenderableGUI;
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
	private ModelEnvironment models;
	
	/** If the player died; Should not update the game */
	private boolean gameOver;

	/** The views to use when going in or out pause */
	private RenderableGUI pauseScreenView, pauseIconView;

	/** The (center of the) current viewport position, in world coordinates */
	private Vector2 viewportPosition;

	/** The current viewport dimensions, in world coordinates. */
	private Vector2 viewportDimensions;
	
	/** Stores the weapons type for restarting the game */
	private WeaponData weaponDataStandard;
	private WeaponData weaponDataHeavy;

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
	public void createNewGame(WeaponData[] weaponData) {
		// Initiate instead of declaring statically above
		viewportPosition = new Vector2();
		viewportDimensions = new Vector2();
		this.weaponDataStandard = weaponData[0];
		this.weaponDataHeavy = weaponData[1];
		
		// Original create new game code
		resourceManager.load();

		if (graphics != null) {
			graphics.dispose();
			graphics = null;
		}

		graphics = new Graphics();
		graphics.create();
		
		if(models != null) {
			models.dispose();
		}
		models = new ModelEnvironmentImpl(weaponData);
		
		PlayerShip ship = models.getPlayerShip();
		PlayerShipView shipView = new PlayerShipView(ship, resourceManager);
		graphics.setNewCameraPos(ship.getPosition().x+Graphics.GAME_WIDTH/2, 
				Graphics.GAME_HEIGHT/2);
		graphics.addRenderable(shipView);
		
		
		
		bgView = new BackgroundView(models, resourceManager, ship);
		//graphics.addRenderable(bgView);

		// Set up input handler
		processor = new GameTouchController(graphics, ship);

		setupGUI();

		EnemyView enemyView = new EnemyView(models, resourceManager);
		graphics.addRenderable(enemyView);

		ProjectileView projectileView = new ProjectileView(models, resourceManager);
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

		// Remove pause screen and pause icon from screen
		graphics.removeRenderableGUI(pauseIconView);
		graphics.removeRenderableGUI(pauseScreenView);

		RenderableGUI gameOver = new GameoverScreenView(myGame, resourceManager);
		graphics.addRenderableGUI(gameOver);
	}

	/**
	 * Pauses the game when the application loses focus. If game isn't over, show pause gui. If it
	 * is over, only keep track of pause state.
	 */
	@Override
	public void pause() {
		// Don't show pause gui, only track pause state if game is over
		if (gameOver) {
			super.pause();
		} else {
			pauseGame();
		}
	}

	/** Pauses the game */
	public void pauseGame() {
		super.pause();
		graphics.removeRenderableGUI(pauseIconView);
		graphics.addRenderableGUI(pauseScreenView);
	}

	/**
	 * Do nothing when application resumes, let the user resume the game. Unless the current game is
	 * over, in which case keep track of the pause state of the app.
	 */
	@Override
	public void resume() {
		if (gameOver) {
			super.resume();
		}
	}

	/** Unpauses the game */
	public void unpauseGame() {
		super.resume();
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

		if (models != null) {
			models.dispose();
		}
	}

	/**
	 * Main game entry loop. Called every frame and should update logic etc.
	 */
	@Override
	public void render(float delta) {
		graphics.setNewCameraPos(models.getPlayerShip().getPosition().x - 
									models.getPlayerShip().getDimensions().x/2 + 
									Graphics.GAME_WIDTH/2, 
								Graphics.GAME_HEIGHT/2);

		// Render the game
		graphics.render();
		
		if(!gameOver && models.getPlayerShip().isDead()){
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

			models.setViewport(viewportPosition, viewportDimensions);

			models.update(delta);
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

		models.setViewport(viewportPosition, viewportDimensions);
	}
	
	public static BackgroundView getBgView(){
		return bgView;
	}
	
	/** Get method for weapon data set in create new game */
	public WeaponData[] getWeaponData(){
		return new WeaponData[] {weaponDataStandard, weaponDataHeavy};
	}

}
