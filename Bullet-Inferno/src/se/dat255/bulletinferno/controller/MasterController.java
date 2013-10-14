package se.dat255.bulletinferno.controller;

import se.dat255.bulletinferno.controller.LoadingScreenController.FinishedLoadingEventListener;
import se.dat255.bulletinferno.model.weapon.WeaponDefinition;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.ResourceManagerImpl;

import com.badlogic.gdx.Screen;

import se.dat255.bulletinferno.model.loadout.PassiveAbilityDefinition;
import se.dat255.bulletinferno.model.loadout.SpecialAbilityDefinition;

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

	private FinishedLoadingEventListener switchToGameOnLoaded = new FinishedLoadingEventListener() {
		@Override
		public void onLoaded() {
			setScreen(getGameScreen());
		}
	};

	private FinishedLoadingEventListener switchToLoadoutOnLoaded = new FinishedLoadingEventListener() {
		@Override
		public void onLoaded() {
			setScreen(getLoadoutScreen());
		}
	};

	@Override
	public void create() {
		this.resourceManager = new ResourceManagerImpl();

		this.loadingScreen = new LoadingScreenController(resourceManager, this);
		loadingScreen.addFinishedLoadingEventListener(switchToLoadoutOnLoaded);
		loadingScreen.setClickToSwitch(true);
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
		if (resourceManager != null) {
			resourceManager.dispose();
		}

	}

	/**
	 * Starts a new game and changes the screen to that game
	 * 
	 * @param passive
	 * @param special
	 */
	public void startGame(GameController gameScreen, WeaponDefinition[] weaponData,
			SpecialAbilityDefinition special, PassiveAbilityDefinition passive, boolean fromLoadout) {

		if (weaponData == null) {
			weaponData = gameScreen.getWeaponData();
		}

		if (!fromLoadout) {
			if (gameScreen != null) {
				gameScreen.dispose();
			}
			gameScreen = new GameController(this, resourceManager);
		}

		gameScreen.createNewGame(weaponData, special, passive);
		this.gameScreen = gameScreen;

		setScreen(gameScreen);
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
				loadingScreen.addFinishedLoadingEventListener(switchToLoadoutOnLoaded);
			} else if (currentScreen == gameScreen) {
				loadingScreen.addFinishedLoadingEventListener(switchToGameOnLoaded);
			}

			setScreen(loadingScreen);
		}
	}
}
