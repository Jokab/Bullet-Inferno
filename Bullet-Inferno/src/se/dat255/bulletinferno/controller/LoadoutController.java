package se.dat255.bulletinferno.controller;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.model.weapon.WeaponData;
import se.dat255.bulletinferno.util.ResourceManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class LoadoutController extends SimpleController {

	/** Aspect to keep on the screen */
	private final static int VIRTUAL_WIDTH = 1280,
			VIRTUAL_HEIGHT = 720;

	private final Stage stage;
	private final Skin skin;

	private final ResourceManager resourceManager;

	private final List<Button> primaryWeapons = new ArrayList<Button>();

	/**
	 * Main controller used for the loadout screen
	 * 
	 * @param myGame
	 *        The master controller that creates this screen
	 * @param resourceManager
	 */
	public LoadoutController(final MasterController myGame, final ResourceManager resourceManager) {
		this.resourceManager = resourceManager;

		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		skin = new Skin();

		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));

		// Add default font as default
		skin.add("default", new BitmapFont());

		// TextButtonStyle textButtonStyle = new TextButtonStyle();
		// textButtonStyle.font = skin.getFont("default");
		// textButtonStyle.fontColor = Color.BLACK;
		// skin.add("default", textButtonStyle);


		// Create a table that fills the screen
		Table table = new Table();
		table.debug();
		table.setFillParent(true);
		table.setColor(new Color(Color.BLACK));
		table.setPosition(100, 100);
		
		// Set up and store buttons in list
		setupButtons();
		
		for(Button button : primaryWeapons) {
			table.add(button).padBottom(20).row();
		}

		// Add table to stage
		stage.addActor(table);

		// Start button click listener
//		weaponButton1.addListener(new ChangeListener() {
//			@Override
//			public void changed(ChangeEvent event, Actor actor) {
//				myGame.startGame(WeaponData.MISSILE_LAUNCHER);
//			}
//		});
//
//		weaponButton2.addListener(new ChangeListener() {
//			@Override
//			public void changed(ChangeEvent event, Actor actor) {
//				myGame.startGame(WeaponData.DISORDERER);
//			}
//		});
	}

	private void setupButtons() {
		for (int i = 0; i < 10; i++) {
			// TODO: the line below needs changing to take into account all weapons
			Texture texture = resourceManager.getManagedTexture(WeaponData.values()[0]).getTexture();
			TextureRegion region = new TextureRegion(texture);
			ButtonStyle buttonStyle = new ButtonStyle();
			buttonStyle.up = new TextureRegionDrawable(region);
			buttonStyle.over = skin.newDrawable(buttonStyle.up, Color.LIGHT_GRAY);
			
			primaryWeapons.add(new Button(buttonStyle));
		}
	}

	@Override
	public void show() {
		Gdx.app.debug("LoadoutScreen", "Screen shown");
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		// Clear the screen every frame
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Table.drawDebug(stage);

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
		stage.dispose();
		skin.dispose();
	}
}
