package se.dat255.bulletinferno.controller;

import java.util.LinkedList;
import java.util.List;

import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.view.menu.LoadingScreenView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * A screen that handles loading and allocation of all assets that are required in the game.
 * This is to prevent the game from slowing down due to resource loading which could take time
 * depending on the speed of the secondary memory.
 */
public class LoadingScreenController extends SimpleController {

	/**
	 * A listener for when the loading the loading screen is doing is finished. This includes
	 * waiting for the user to touch the screen if the loading screen is set to wait for click.
	 */
	public interface FinishedLoadingEventListener {
		/**
		 * Called when the loading the loading screen was shown for is complete. If the loading
		 * screen is set to require a click, this event is called after that happens.
		 */
		public void onLoaded();
	}

	/** The InputListener used to capture touches on the entire screen */
	private final InputListener stageClickInputListener = new InputListener() {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			if (LoadingScreenController.this.loadingFinished) {
				finishedLoading();
			}
			return LoadingScreenController.this.loadingFinished;
		}
	};

	/** Aspect to keep on the screen */
	private final static int VIRTUAL_WIDTH = 1280, VIRTUAL_HEIGHT = 720;

	/** The resource manager this loading screen is used to display progress of */
	private final ResourceManager resourceManager;

	/** The event listeners for when loading finishes. */
	List<FinishedLoadingEventListener> finishListeners = new LinkedList<LoadingScreenController.FinishedLoadingEventListener>();

	/** Flag if we require a user input to do the switch after loading is finished */
	private boolean clickToSwitch = true;

	/** Flag indicating whether the loading of the asset manager has completed */
	private boolean loadingFinished = false;

	/** The scene2d stage that takes care of gui handling for us */
	private Stage stage;

	/** The loading screen view that holds all the gui elements */
	private LoadingScreenView loadingScreenView;

	/**
	 * Initiates the loading screen and its view. Also starts the loading of the resourceManager.
	 * 
	 * <p>
	 * Note: This screen will load textures without using the resource manager.
	 * </p>
	 */
	public LoadingScreenController(ResourceManager resourceManager,
			MasterController masterController) {

		this.resourceManager = resourceManager;
		resourceManager.startLoad(false);

		// Set up the GUI elements
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		loadingScreenView = new LoadingScreenView();
		loadingScreenView.setFillParent(true);
		stage.addActor(loadingScreenView);

		// Add a click listener to the whole stage to capture "click to continue" touches
		stage.addListener(stageClickInputListener);

	}

	@Override
	public void show() {
		super.show();

		Gdx.input.setInputProcessor(stage);
		loadingFinished = false;
	}

	private void finishedLoading() {
		for (FinishedLoadingEventListener listener : finishListeners) {
			listener.onLoaded();
		}
		// We will clear the list here for now, as currently there is no
		// case where something wants to listen for all loads
		finishListeners.clear();
	}

	@Override
	public void render(float delta) {
		if (!resourceManager.loadAsync()) {
			// Still loading
			loadingScreenView.setLoadProgress(resourceManager.getLoadProgress());
		} else {
			// Done loading
			loadingFinished = true;
			if (!clickToSwitch) {
				finishedLoading();
			} else {
				loadingScreenView.loadingFinished();
			}
		}

		// Clear the screen every frame
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, false);
	}

	@Override
	public void dispose() {
		loadingScreenView.dispose();
		stage.dispose();
	}

	public void addFinishedLoadingEventListener(FinishedLoadingEventListener finishListener) {
		this.finishListeners.add(finishListener);
	}

	public void setClickToSwitch(boolean clickToSwitch) {
		this.clickToSwitch = clickToSwitch;
	}

}
