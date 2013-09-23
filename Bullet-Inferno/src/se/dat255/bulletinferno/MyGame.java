package se.dat255.bulletinferno;


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
public class MyGame extends com.badlogic.gdx.Game {

	LoadoutScreen loadoutScreen;
	GameScreen gameScreen;

	@Override
	public void create() {
		loadoutScreen = new LoadoutScreen(this);
		gameScreen = new GameScreen(this);
		
		setScreen(gameScreen);
	}

	@Override
	public void dispose() {
		gameScreen.dispose();
		loadoutScreen.dispose();
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	public LoadoutScreen getLoadoutScreen() {
		return loadoutScreen;
	}
}
