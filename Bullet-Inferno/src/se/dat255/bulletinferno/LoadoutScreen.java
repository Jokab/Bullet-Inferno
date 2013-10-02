package se.dat255.bulletinferno;

import se.dat255.bulletinferno.model.weapon.WeaponData;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class LoadoutScreen extends AbstractScreen {

	private final static int VIRTUAL_WIDTH = 1280;
	private final static int VIRTUAL_HEIGHT = 720;
	private final static int VIRTUAL_MID_WIDTH = VIRTUAL_WIDTH / 2;
	private final static int VIRTUAL_MID_HEIGHT = VIRTUAL_HEIGHT / 2;

	private final Stage stage;
	private final Skin skin;

	private final Texture startImgTexture;

	public LoadoutScreen(final MyGame myGame) {

		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		skin = new Skin();

		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));

		startImgTexture = new Texture(Gdx.files.internal("data/startBtn.png"));
		TextureRegion startImage = new TextureRegion(startImgTexture);

		// Add default font as default
		skin.add("default", new BitmapFont());

		// Set up style for buttons
		ButtonStyle startBtnStyle = new ButtonStyle();
		startBtnStyle.up = new TextureRegionDrawable(startImage);
		startBtnStyle.over = skin.newDrawable(startBtnStyle.up, Color.LIGHT_GRAY);
		skin.add("startButton", startBtnStyle);

		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = skin.getFont("default");
		textButtonStyle.fontColor = Color.BLACK;
		skin.add("default", textButtonStyle);

		// Create buttons
		Button startButton = new Button(skin, "startButton");
		startButton.setSize(600, 150);
		// startButton.setPosition(VIRTUAL_MID_WIDTH - startButton.getWidth() / 2, 20);
		TextButton btn2 = new TextButton("Slower", skin);
		btn2.setSize(600, 150);

		// startButton.setSize(1280/2, 720/2);

		// Create a table that fills the screen

		// Add it to stage
		Table t = new Table();
		t.setFillParent(true);

		t.add(startButton).row();
		t.add(btn2);

		stage.addActor(t);

		// Start button click listener
		startButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GameScreen gameScreen = myGame.getGameScreen();
				gameScreen.createNewGame(WeaponData.FAST);
				myGame.setScreen(gameScreen);
			}
		});

		btn2.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GameScreen gameScreen = myGame.getGameScreen();
				gameScreen.createNewGame(WeaponData.SLOW);
				myGame.setScreen(gameScreen);
			}
		});

	}

	@Override
	public void show() {
		Gdx.app.debug("LoadoutScreen", "Screen shown");
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, false);
	}

	@Override
	public void dispose() {
		Gdx.app.debug("LoadoutScreen", "Screen Disposed");
		startImgTexture.dispose();
		stage.dispose();
		skin.dispose();
	}
}
