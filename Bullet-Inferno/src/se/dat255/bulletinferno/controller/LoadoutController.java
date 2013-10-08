package se.dat255.bulletinferno.controller;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.model.weapon.WeaponData;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.ResourceManagerImpl.TextureType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class LoadoutController extends SimpleController {

	/** Aspect to keep on the screen */
	private final static int VIRTUAL_WIDTH = 1280,
			VIRTUAL_HEIGHT = 720;

	private final Stage stage;
	private final Skin skin;

	private final ResourceManager resourceManager;
	
	private final MasterController masterController;

	private final List<Button> primaryWeapons = new ArrayList<Button>();

	/**
	 * Main controller used for the loadout screen
	 * 
	 * @param masterController
	 *        The master controller that creates this screen
	 * @param resourceManager
	 */
	public LoadoutController(final MasterController masterController, final ResourceManager resourceManager) {
		this.resourceManager = resourceManager;
		this.masterController = masterController;

		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		skin = new Skin();

		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));

		// Add default font as default
		skin.add("default", new BitmapFont());

		// Set up the start button and add its listener
		setupStartButton();

		// Set up the table for the primary weapons
		Table primaryWeaponTable = new Table();
		
		// Add table to stage
		primaryWeaponTable.debug();
		primaryWeaponTable.setColor(new Color(Color.BLACK));
		primaryWeaponTable.setPosition(1150,450);
		
		stage.addActor(primaryWeaponTable);
		// Set up and store buttons in list
		setupPrimaryWeaponButtons();
		
		for(Button button : primaryWeapons) {
			primaryWeaponTable.add(button).padBottom(20).row();
		}

//
//		weaponButton2.addListener(new ChangeListener() {
//			@Override
//			public void changed(ChangeEvent event, Actor actor) {
//				myGame.startGame(WeaponData.DISORDERER);
//			}
//		});
	}

	private void setupStartButton() {
		Texture startButtonTexture = TextureType.LOADOUT_START_BUTTON.getTexture();
		startButtonTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion startButtonRegion = new TextureRegion(startButtonTexture);
		
		ImageButtonStyle startButtonStyle = new ImageButtonStyle();
		startButtonStyle.up = new TextureRegionDrawable(startButtonRegion);
		startButtonStyle.over = skin.newDrawable(startButtonStyle.up, Color.LIGHT_GRAY);
		
		ImageButton startButton = new ImageButton(startButtonStyle);
		skin.add("startButton", startButton);
		
		Table startButtonTable = new Table();
		startButtonTable.setPosition(1150,40);
		startButtonTable.add(startButton).maxHeight(65).maxWidth(230);
		stage.addActor(startButtonTable);
		
		// Start button click listener
		startButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				masterController.startGame(WeaponData.MISSILE_LAUNCHER);
			}
		});
	}

	private void setupPrimaryWeaponButtons() {
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
