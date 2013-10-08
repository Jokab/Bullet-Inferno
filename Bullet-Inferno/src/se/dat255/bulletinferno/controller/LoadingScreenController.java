package se.dat255.bulletinferno.controller;

import se.dat255.bulletinferno.util.ResourceManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class LoadingScreenController extends SimpleController {
	public enum FinishToScreen {
		GameScreen, LoadoutScreen;
	}

	/** Aspect to keep on the screen */
	private final static int VIRTUAL_WIDTH = 1280,
			VIRTUAL_HEIGHT = 720;

	// GUI
	private Stage stage;
	private Skin skin;
	private Texture screenBgTexture;
	private Image screenBg;
	private Label clickToStart;

	private boolean shouldListenForClick = false;
	//

	/** The master screen controller */
	private final MasterController masterController;

	/** The resource manager this loading screen is used to display progress of */
	private final ResourceManager resourceManager;

	/** The screen to switch to when loading is finished */
	private FinishToScreen onFinishedScreen = FinishToScreen.LoadoutScreen;

	/** Flag if we require a user input to do the switch after loading is finished */
	private boolean clickToSwitch = true;

	public LoadingScreenController(ResourceManager resourceManager, MasterController masterController) {
		this.resourceManager = resourceManager;
		resourceManager.startLoad(false);
		
		this.masterController = masterController;

		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		skin = new Skin();

		setupScreenElements();
	}

	private void setupScreenElements() {
		screenBgTexture = new Texture("data/loadingScreenBg.png");
		screenBg = new Image(screenBgTexture);
		screenBg.setFillParent(true);
		stage.addActor(screenBg);

		BitmapFont f = new BitmapFont();
		f.scale(3);
		LabelStyle ls = new LabelStyle(f, Color.BLACK);
		clickToStart = new Label("Loading... 0%", ls);
		clickToStart.setPosition(1280 / 2 - clickToStart.getWidth() / 2,
				clickToStart.getHeight() + 10);
		stage.addActor(clickToStart);

		stage.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (shouldListenForClick) {
					switchToFinishedScreen();
					return true;
				} else {
					return false;
				}
			}

		});
	}

	@Override
	public void show() {
		super.show();
		Gdx.input.setInputProcessor(stage);
		
		shouldListenForClick = false;
	}

	private void switchToFinishedScreen() {
		switch (onFinishedScreen) {
		case GameScreen:
			masterController.setScreen(masterController.getGameScreen());
			break;
		case LoadoutScreen:
			masterController.setScreen(masterController.getLoadoutScreen());
			break;
		}
	}

	@Override
	public void render(float delta) {
		if (!resourceManager.loadAsync()) {
			int percLoaded = (int) Math.floor(resourceManager.getLoadProgress() * 100);
			clickToStart.setText("Loading... " + percLoaded + "%");
		} else {
			if (clickToSwitch){
				if(!shouldListenForClick) {
					shouldListenForClick = true;
					clickToStart.setText("Touch to Start!");
					clickToStart.validate();
					clickToStart.setPosition(1280 / 2 - clickToStart.getWidth() / 2,
							clickToStart.getHeight() + 10);
				}
			} else {
				switchToFinishedScreen();
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
		stage.dispose();
		skin.dispose();
	}

	public void setOnFinishedScreen(FinishToScreen onFinishedScreen) {
		this.onFinishedScreen = onFinishedScreen;
	}

	public void setClickToSwitch(boolean clickToSwitch) {
		this.clickToSwitch = clickToSwitch;
	}

}
