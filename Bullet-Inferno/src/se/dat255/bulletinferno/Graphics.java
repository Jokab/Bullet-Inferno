package se.dat255.bulletinferno;

import java.util.HashSet;

import se.dat255.bulletinferno.view.Renderable;
import se.dat255.bulletinferno.view.RenderableGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Graphics {

	/** 2D world camera */
	private static OrthographicCamera worldCamera;
	/** 2D GUI camera */
	private static OrthographicCamera guiCamera;
	/** Handles efficient drawing of several images */
	private SpriteBatch worldBatch, guiBatch;

	/** The size, in meters, of the visible area. */
	public static final float GAME_WIDTH = 16f, GAME_HEIGHT = 9f;
	/** Inverted size, multiplication is faster than division */
	public static final float GAME_WIDTH_INVERTED = 1 / GAME_WIDTH,
			GAME_HEIGHT_INVERTED = 1 / GAME_HEIGHT;

	/** List of all objects that are to be rendered in the world */
	private final HashSet<Renderable> renderables = new HashSet<Renderable>();
	/** List of all objects that are to be rendered as GUI elements */
	private final HashSet<RenderableGUI> guiRenderables = new HashSet<RenderableGUI>();

	/**
	 * Initializes all the required assets
	 */
	public void create() {
		Gdx.app.log("Graphics", "create()");
		
		worldCamera = new OrthographicCamera();
		worldBatch = new SpriteBatch();
		
		guiCamera = new OrthographicCamera(16, 9);
		guiBatch = new SpriteBatch();
		guiBatch.setProjectionMatrix(guiCamera.combined);
		
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	/**
	 * Sets the new size of the view field when the screen changes size
	 */
	public void resize(float w, float h) {
		Gdx.app.log("Graphics", "resize(" + w + ", " + h + ")");
		float width = w / h * GAME_HEIGHT;
		Gdx.app.log("Graphics", "camera.setToOrtho(false, " + width + ", "
				+ GAME_HEIGHT + ")");
		worldCamera.setToOrtho(false, width, GAME_HEIGHT);
//		guiCamera.setToOrtho(false, w, h);
//		guiCamera.update();
//		guiBatch.setProjectionMatrix(guiCamera.combined);
	}

	/**
	 * Releases the assets when called
	 */
	public void dispose() {
		Gdx.app.log("Graphics", "dispose()");
		worldBatch.dispose();
	}

	/**
	 * Positions the camera correctly and renders all the graphics of the game
	 */
	public void render() {
		// Update the camera position
		// TODO: camera.setPosition(...)
		worldCamera.update();
		worldBatch.setProjectionMatrix(worldCamera.combined);

		// Clear the screen every frame
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// TODO: Render world without blending

		// Render units that have alpha
		worldBatch.begin();
		for (Renderable renderable : renderables) {
			renderable.render(worldBatch);
		}
		worldBatch.end();

		// TODO: Render GUI
		guiBatch.begin();
		for (RenderableGUI renderable : guiRenderables) {
			renderable.render(guiBatch);
		}
		guiBatch.end();
	}

	/** Adds an object to be rendered in the world. Uses hashcode to separate */
	public void addRenderable(Renderable renderable) {
		Gdx.app.log("Graphics", "addRenderable(" + renderable.toString() + ")");
		renderables.add(renderable);
	}

	/** Removes an object from being rendered in the world */
	public void removeRenderable(Renderable renderable) {
		Gdx.app.log("Graphics", "removeRenderable(" + renderable.toString()
				+ ")");
		renderables.remove(renderable);
	}

	/** Adds an object to be rendered in the GUI. Uses hashcode to separate */
	public void addRenderableGUI(RenderableGUI renderable) {
		Gdx.app.log("Graphics", "addRenderableGUI(" + renderable.toString() + ")");
		guiRenderables.add(renderable);
	}

	/** Removes an object from being rendered in the GUI */
	public void removeRenderableGUI(RenderableGUI renderable) {
		Gdx.app.log("Graphics", "removeRenderableGUI(" + renderable.toString()
				+ ")");
		guiRenderables.remove(renderable);
	}
	
	/**
	 * Checks if a GUI element was activated, also calling that
	 *  element.
	 * @param x The X position of the GUI
	 * @param y The Y position of the GUI
	 * @return If a GUI element was activated
	 */
	public boolean guiInput(float x, float y){
		Gdx.app.log("guiInput", x + ", " + y);
		for(RenderableGUI gui : guiRenderables){
			Vector2 position = gui.getPosition();
			Vector2 size = gui.getSize();
			if(x > position.x && y > position.y && x < position.x + size.x && y < position.y + size.y){
				gui.pressed();
				return true;
			}
		}
		return false;
	}

	/** Temporary local vector to prevent re-allocation every call */
	private static final Vector3 vector = new Vector3();

	/** Changes the given vector from screen to world position */
	public static void screenToWorld(Vector2 position) {
		vector.set(position.x, position.y, 0);
		worldCamera.unproject(vector);
		position.set(vector.x, vector.y);
	}

	/** Changed the given vector from world to screen position */
	public static void worldToScreen(Vector2 position) {
		Gdx.app.log("Graphics", "worldToScreen(" + position + ")");
		vector.set(position.x, position.y, 0);
		worldCamera.project(vector);
		Gdx.app.log("Graphics", "result: " + vector);
		position.set(vector.x, vector.y);
	}
}
