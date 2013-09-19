package se.dat255.bulletinferno;

import java.util.HashSet;

import se.dat255.bulletinferno.model.Renderable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Graphics {

	/** 2D camera */
	private static OrthographicCamera camera;
	/** Handles efficient drawing of several images */
	private SpriteBatch batch;
	
	/** The size, in meters, of the visible area. */
	public static final float GAME_WIDTH = 16f, GAME_HEIGHT = 9f;
	/** Inverted size, multiplication is faster than division */
	public static final float GAME_WIDTH_INVERTED  = 1 / GAME_WIDTH,
			                  GAME_HEIGHT_INVERTED = 1 / GAME_HEIGHT;
	
	/** List of all objects that are to be rendered */
	private final HashSet<Renderable> renderables = new HashSet<Renderable>();
	
	/**
	 * Initializes all the required assets
	 */
	public void create(){
		Gdx.app.log("Graphics", "create()");
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	/**
	 * Sets the new size of the view field
	 * when the screen changes size
	 * @param w
	 * @param h
	 */
	public void resize(float w, float h){
		Gdx.app.log("Graphics", "resize("+w+", "+h+")");
		float width = (w / h) * GAME_HEIGHT;
		Gdx.app.log("Graphics", "camera.setToOrtho(false, "+width+", "+GAME_HEIGHT+")");
		camera.setToOrtho(false, width, GAME_HEIGHT);
	}
	
	/**
	 * Releases the assets when called
	 */
	public void dispose(){
		Gdx.app.log("Graphics", "dispose()");
		batch.dispose();
	}
	
	/**
	 * Positions the camera correctly and
	 *  renders all the graphics of the game
	 */
	public void render() {
		// Update the camera position
		// TODO: camera.setPosition(...)
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		// Clear the screen every frame
		Gdx.gl.glClearColor(0,0,0,0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// TODO: Render GUI without alpha
//		batch.disableBlending();
//		batch.begin();
//		batch.end();
//		batch.enableBlending();

		// Render units that have alpha
		batch.begin();
		for(Renderable renderable : renderables){
			renderable.render(batch);
		}
		batch.end();
	}
	
	/** Adds an object to be rendered. Uses hashcode to separate */
	public void addRenderable(Renderable renderable){
		Gdx.app.log("Graphics", "addRenderable("+renderable.toString()+")");
		renderables.add(renderable);
	}
	
	/** Removes an object from being rendered */
	public void removeRenderable(Renderable renderable){
		Gdx.app.log("Graphics", "removeRenderable("+renderable.toString()+")");
		renderables.remove(renderable);
	}
	
	/** Temporary local vector to prevent re-allocation every call */
	private static final Vector3 vector = new Vector3();
	
	/** Changes the given vector from screen to world position */
	public static void screenToWorld(Vector2 position){
		Gdx.app.log("Graphics", "screenToWorld("+position+")");
		vector.set(position.x, position.y, 0);
		camera.unproject(vector);
		Gdx.app.log("Graphics", "result: "+vector);
		position.set(vector.x, vector.y);
	}
	
	/** Changed the given vector from world to screen position */
	public static void worldToScreen(Vector2 position){
		Gdx.app.log("Graphics", "worldToScreen("+position+")");
		vector.set(position.x, position.y, 0);
		camera.project(vector);
		Gdx.app.log("Graphics", "result: "+vector);
		position.set(vector.x, vector.y);
	}
}
