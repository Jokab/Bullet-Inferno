package se.dat255.bulletinferno;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.controller.Touch;
import se.dat255.bulletinferno.units.ship.Ship;
import se.dat255.bulletinferno.units.ship.ShipView;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

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

	public static final int VIRTUAL_WIDTH = 480;
	public static final int VIRTUAL_HEIGHT = 320;

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Rectangle viewport;

	private List<ShipView> shipViews = new ArrayList<ShipView>();

	@Override
	public void create() {
		camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		batch = new SpriteBatch();

		Ship ship = new Ship(-(MyGame.VIRTUAL_WIDTH / 2), 0);
		shipViews.add(new ShipView(ship));

		InputProcessor inputProcessor = new Touch(camera, ship);
		Gdx.input.setInputProcessor(inputProcessor);
	}

	@Override
	public void dispose() {
		batch.dispose();
		for (ShipView shipView : shipViews) {
			shipView.dispose();
		}
	}

	@Override
	public void render() {
		// update camera
		camera.update();
		camera.apply(Gdx.gl10);

		// set viewport
		Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,
				(int) viewport.width, (int) viewport.height);

		// clear previous frame
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		for (ShipView shipView : shipViews) {
			shipView.render(batch);
		}

		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		float widthScale = (float) width / (float) VIRTUAL_WIDTH;
		float heightScale = (float) height / (float) VIRTUAL_HEIGHT;
		float w = (float) VIRTUAL_WIDTH * widthScale;
		float h = (float) VIRTUAL_HEIGHT * heightScale;
		viewport = new Rectangle(0, 0, w, h);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
