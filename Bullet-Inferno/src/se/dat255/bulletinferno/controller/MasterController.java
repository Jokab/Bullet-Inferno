package se.dat255.bulletinferno.controller;

import se.dat255.bulletinferno.model.weapon.WeaponData;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.ResourceManagerImpl;

/**
 * The master controller is called every frame. It then calls appropriate classes
 * depending on what currently is active in the game. It handles all sub-controllers
 * and screens.
 */
public class MasterController extends com.badlogic.gdx.Game {
	/** Main controller for the load out screen */
	private LoadoutController loadoutScreen;
	/** Main controller for the game screen */
	private GameController gameScreen;

	private ResourceManager resourceManager;

	/** When the game is first started, we load the loadout screen */
	@Override
	public void create() {
		this.resourceManager = new ResourceManagerImpl();
		resourceManager.load();
		loadoutScreen = new LoadoutController(this, resourceManager);
		setScreen(loadoutScreen);
	}

	@Override
	public void dispose() {
		gameScreen.dispose();
		loadoutScreen.dispose();
	}

	/** Starts a new game and changes the screen to that game */
	public void startGame(WeaponData weaponData) {
		if (weaponData == null) {
			if (gameScreen == null) {
				throw new RuntimeException(
						"MyGame.startGame(null): Can't load weapon data since game screen is null.");
			}
			weaponData = gameScreen.getWeaponData();
		}
		if (gameScreen != null) {
			gameScreen.dispose();
		}
		gameScreen = new GameController(this, resourceManager);
		gameScreen.createNewGame(weaponData);
		setScreen(gameScreen);
	}

	public GameController getGameScreen() {
		return gameScreen;
	}

	public LoadoutController getLoadoutScreen() {
		return loadoutScreen;
	}
}
