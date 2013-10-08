package se.dat255.bulletinferno.controller;

import se.dat255.bulletinferno.controller.LoadingScreenController.FinishToScreen;
import se.dat255.bulletinferno.model.weapon.WeaponData;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.ResourceManagerImpl;

import com.badlogic.gdx.Screen;

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

	/** The controller for the loading screen */
	private LoadingScreenController loadingScreen;

	private ResourceManager resourceManager;

	@Override
	public void create() {
		this.resourceManager = new ResourceManagerImpl();

		this.loadingScreen = new LoadingScreenController(resourceManager, this);
		loadingScreen.setClickToSwitch(true);
		loadingScreen.setOnFinishedScreen(FinishToScreen.LoadoutScreen);
		setScreen(loadingScreen);
	}

	@Override
	public void dispose() {
		if (gameScreen != null) {
			gameScreen.dispose();
		}
		if (loadingScreen != null) {
			loadingScreen.dispose();
		}
		if (loadoutScreen != null) {
			loadoutScreen.dispose();
		}
		if(resourceManager != null) {
			resourceManager.dispose();
		}
		
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

	@Override
	public void setScreen(Screen screen) {
		super.setScreen(screen);
	}
	
	public GameController getGameScreen() {
		return gameScreen;
	}

	public LoadoutController getLoadoutScreen() {
		if (loadoutScreen == null) {
			loadoutScreen = new LoadoutController(this, resourceManager);
		}
		return loadoutScreen;
	}

	@Override
	public void resume() {
		Screen currentScreen = super.getScreen();

		if (currentScreen != loadingScreen) {
			loadingScreen.setClickToSwitch(false);

			if (currentScreen == loadoutScreen) {
				loadingScreen.setOnFinishedScreen(FinishToScreen.LoadoutScreen);
			} else if (currentScreen == gameScreen) {
				loadingScreen.setOnFinishedScreen(FinishToScreen.GameScreen);
			}

			setScreen(loadingScreen);
		}
	}
}
