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
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
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

	private final List<WeaponButton> primaryWeapons = new ArrayList<WeaponButton>();

	private WeaponButton selectionWeaponButton;
	private ButtonStyle selectionButtonStyle;

	/**
	 * Main controller used for the loadout screen
	 * 
	 * @param masterController
	 *        The master controller that creates this screen
	 * @param resourceManager
	 */
	public LoadoutController(final MasterController masterController,
			final ResourceManager resourceManager) {
		this.resourceManager = resourceManager;
		this.masterController = masterController;

		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		skin = new Skin();

		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.GRAY);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));

		// Add default font as default
		skin.add("default", new BitmapFont());

		// Set up the start button and add its listener
		setupStartButton();

		// Set up and store buttons in list
		setupPrimaryWeaponButtons();

		setupSelectionButtons();

	}

	private void setupSelectionButtons() {
		Texture texture = new Texture("data/frame.png");
		TextureRegion region = new TextureRegion(texture);

		selectionButtonStyle = new ImageButtonStyle();
		selectionButtonStyle.up = new TextureRegionDrawable(region);
		selectionButtonStyle.over = skin.newDrawable(selectionButtonStyle.up, Color.LIGHT_GRAY);

		selectionWeaponButton = new WeaponButton(new Button(selectionButtonStyle), null);

		selectionWeaponButton.getButton().setPosition(100, 100);
		selectionWeaponButton.getButton().setSize(200, 120);

		selectionWeaponButton.getButton().addListener(new SelectionClickedListener());

		stage.addActor(selectionWeaponButton.getButton());

	}

	private void setupStartButton() {
		Texture startButtonTexture = TextureType.LOADOUT_START_BUTTON.getTexture();
		startButtonTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion startButtonRegion = new TextureRegion(startButtonTexture);

		ImageButtonStyle startButtonStyle = new ImageButtonStyle();
		startButtonStyle.up = new TextureRegionDrawable(startButtonRegion);
		startButtonStyle.over = skin.newDrawable(startButtonStyle.up, Color.LIGHT_GRAY);

		ImageButton startButton = new ImageButton(startButtonStyle);

		startButton.setPosition(1040, 20);
		startButton.setSize(230, 65);
		stage.addActor(startButton);

		// Start button click listener
		startButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				masterController.startGame(WeaponData.MISSILE_LAUNCHER);
			}
		});
	}

	private void setupPrimaryWeaponButtons() {
		for (int i = 0; i < 5; i++) {
			// TODO: the line below needs changing to take into account all weapons
			WeaponData weaponData = WeaponData.DISORDERER;
			Texture texture = resourceManager.getManagedTexture(weaponData).getTexture();
			TextureRegion region = new TextureRegion(texture);
			ButtonStyle buttonStyle = new ButtonStyle();
			buttonStyle.up = new TextureRegionDrawable(region);
			// buttonStyle.over = skin.newDrawable(buttonStyle.up, Color.LIGHT_GRAY);

			WeaponButton weaponButton = new WeaponButton(new Button(buttonStyle), weaponData);
			primaryWeapons.add(weaponButton);

			weaponButton.getButton().addListener(new ClickedListener());

		}

		// Set up the table to add these buttons to
		setupPrimaryWeaponsTable();
	}

	private void setupPrimaryWeaponsTable() {
		// Set up the table for the primary weapons
		Table primaryWeaponTable = new Table();

		// Add table to stage
		primaryWeaponTable.debug();
		primaryWeaponTable.setColor(new Color(Color.BLACK));
		primaryWeaponTable.setOrigin(1100, 450);
		primaryWeaponTable.setPosition(1100, 450);
		primaryWeaponTable.setSize(100, 40);

		BitmapFont font = new BitmapFont();
		font = skin.getFont("default");
		font.scale(0.7f);
		LabelStyle labelStyle = new LabelStyle(font, Color.BLACK);
		Label label = new Label("Primary Weapon", labelStyle);

		stage.addActor(primaryWeaponTable);

		label.setPosition(primaryWeaponTable.getX() - 45, primaryWeaponTable.getY() + 200);
		stage.addActor(label);

		for (WeaponButton button : primaryWeapons) {
			primaryWeaponTable.add(button.getButton()).padBottom(20).height(50).width(100).row();
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

	private class SelectionClickedListener extends ChangeListener {

		@Override
		public void changed(ChangeEvent event, Actor actor) {
			Button button = selectionWeaponButton.getButton();
			if (button == ((Button) actor)) {
				selectionButtonStyle.up = new TextureRegionDrawable(new TextureRegion(
						TextureType.DISORDERER.getTexture()));
				selectionWeaponButton.setWeaponData(null);
			}
		}

	}

	private class ClickedListener extends ChangeListener {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			WeaponButton selected = null;
			for (WeaponButton wButton : primaryWeapons) {
				Button button = wButton.getButton();
				if (button == ((Button) actor)) {
					selected = wButton;
					if (!wButton.isSelected()) {
						wButton.toggleSelected(skin);
						setWeaponSelectionToChosenWeapon(wButton);
					} else {
						wButton.toggleSelected(skin);
						setWeaponSelectionToNothing();
					}
				}
				// TODO: add break here since we don't want to keep looping after we found the
				// matching weapon
			}
			
			deselectOtherButtons(selected);
		}

		private void deselectOtherButtons(WeaponButton selected) {
			for(WeaponButton wButton : primaryWeapons) {
				if(wButton != selected && wButton.isSelected()) {
					System.out.println(wButton.isSelected());
					wButton.toggleSelected(skin);
				}
			}
		}

		private void setWeaponSelectionToChosenWeapon(WeaponButton wButton) {
			selectionWeaponButton.setWeaponData(wButton.getWeaponData());
			Texture texture = resourceManager.getManagedTexture(selectionWeaponButton.getWeaponData()).getTexture();
			selectionButtonStyle.up = new TextureRegionDrawable(new TextureRegion(texture));
			selectionWeaponButton.getButton().setStyle(selectionButtonStyle);
		}

		private void setWeaponSelectionToNothing() {
			selectionButtonStyle.up = new TextureRegionDrawable(new TextureRegion(
					new Texture("data/frame.png")));
			selectionWeaponButton.getButton().setStyle(selectionButtonStyle);
			selectionWeaponButton.setWeaponData(null);
		}
	}
}
