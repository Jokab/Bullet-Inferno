package se.dat255.bulletinferno;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.units.enemy.DefaultEnemyImpl;
import se.dat255.bulletinferno.units.enemy.Enemy;
import se.dat255.bulletinferno.units.enemy.EnemyView;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

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
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private int numEnemies = 10;
	private List<Enemy> enemyList = new ArrayList<Enemy>();
	private List<EnemyView> enemyViews = new ArrayList<EnemyView>();

	@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera(w, h);
		camera.update();

		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);

		for (int i = 1; i < numEnemies+1; i++) {
			float yPos = -(h / numEnemies) * i;
			Enemy enemy = new DefaultEnemyImpl(new Vector2((w / 2), yPos), new Vector2(-100, 0), 100);
			enemyList.add(enemy);
			enemyViews.add(new EnemyView(enemy));
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		for (Enemy enemy : enemyList) {
			enemy.update(Gdx.graphics.getDeltaTime());
		}

		batch.begin();
		for (EnemyView enemyView : enemyViews) {
			enemyView.render(batch);
		}
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
