package se.dat255.bulletinferno;

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
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class LoadoutScreen extends AbstractScreen {
	private final MyGame myGame;

	private final static int VIRTUAL_WIDTH = 1280;
	private final static int VIRTUAL_HEIGHT = 720;
	private final static int VIRTUAL_MID_WIDTH = VIRTUAL_WIDTH / 2;
	private final static int VIRTUAL_MID_HEIGHT = VIRTUAL_HEIGHT / 2;

	private Stage stage;
	private Skin skin;
	private SpriteBatch batch;

	private Texture startImgTexture;

	public LoadoutScreen(final MyGame myGame) {
		this.myGame = myGame;

		batch = new SpriteBatch();
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		Gdx.input.setInputProcessor(stage);

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
		ButtonStyle btnStyle = new ButtonStyle();

		btnStyle.up = new TextureRegionDrawable(startImage);
		btnStyle.over = skin.newDrawable(btnStyle.up, Color.LIGHT_GRAY);
		skin.add("default", btnStyle);

		// Create buttons
		Button startButton = new Button(skin);
		startButton.setSize(600, 150);
		startButton.setPosition(VIRTUAL_MID_WIDTH - startButton.getWidth() / 2, 20);

		// startButton.setSize(1280/2, 720/2);

		// Create a table that fills the screen

		// Add it to stage
		stage.addActor(startButton);

		// Start button click listener
		startButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				myGame.setScreen(myGame.getGameScreen());
			}
		});

	}

	@Override
	public void show() {

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
		super.dispose();
		startImgTexture.dispose();
		stage.dispose();
		skin.dispose();
	}
}
