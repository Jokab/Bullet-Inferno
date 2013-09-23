package se.dat255.bulletinferno;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class LoadoutScreen extends AbstractScreen {
	private final MyGame myGame;

	private Stage stage;
	private Skin skin;
	private SpriteBatch batch;

	public LoadoutScreen(final MyGame myGame) {
		this.myGame = myGame;

		batch = new SpriteBatch();

		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		Gdx.input.setInputProcessor(stage);

		// Set up skin
		skin = new Skin();

		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));

		// Add default font as default
		skin.add("default", new BitmapFont());

		// Set up style for labels
		TextButtonStyle btnStyle = new TextButtonStyle();
		btnStyle.font = skin.getFont("default");
		btnStyle.up = skin.newDrawable("white", Color.LIGHT_GRAY);
		btnStyle.over = skin.newDrawable("white", Color.DARK_GRAY);
		btnStyle.fontColor = Color.BLACK;
		skin.add("default", btnStyle);

		// Create buttons
		TextButton startButton = new TextButton("Start THE GAME ALREADY!", skin);
		TextButton exitButton = new TextButton("LOL YOU CANT CLICK ME. I NO WORK!", skin);

		// Create a table that fills the screen
		Table table = new Table();
		table.setFillParent(true);
		table.debug();
		
		// Add it to stage
		table.add(startButton).pad(20).row();
		table.add(exitButton);
		stage.addActor(table);
		
		
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
		stage.setViewport(width, height, false);
	}

	@Override
	public void dispose() {
		super.dispose();
		stage.dispose();
		skin.dispose();
	}
}
