package se.dat255.bulletinferno.controller;

import se.dat255.bulletinferno.model.weapon.WeaponData;

/**
 * The master controller is called every frame. It then calls appropriate classes
 * depending on what currently is active in the game. It handles all sub-controllers
 * and screens.
 */
public class MasterController extends com.badlogic.gdx.Game {
	/** Main controller for the load out screen */
	LoadoutController loadoutScreen;
	/** Main controller for the game screen */
	GameController gameScreen;

	/** When the game is first started, we load the load out screen */
	@Override
	public void create() {
		loadoutScreen = new LoadoutController(this);
		setScreen(loadoutScreen);
	}

	@Override
	public void dispose() {
		gameScreen.dispose();
		loadoutScreen.dispose();
	}
	
	/** Starts a new game and changes the screen to that game */
	public void startGame(WeaponData weaponData){
		if(weaponData == null){
			if(gameScreen == null) throw new RuntimeException(
					"MyGame.startGame(null): Can't load weapon data since game screen is null.");
			weaponData = gameScreen.getWeaponData();
		}
		if(gameScreen != null) gameScreen.dispose();
		gameScreen = new GameController(this);
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
