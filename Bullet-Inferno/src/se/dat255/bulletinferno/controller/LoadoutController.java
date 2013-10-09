package se.dat255.bulletinferno.controller;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.util.ResourceIdentifier;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.ResourceManagerImpl.TextureType;
import se.dat255.bulletinferno.menu.SpecialButton;
import se.dat255.bulletinferno.menu.SpecialButtonsView;
import se.dat255.bulletinferno.menu.WeaponButton;
import se.dat255.bulletinferno.menu.WeaponButtonsView;
import se.dat255.bulletinferno.model.weapon.WeaponDefinition;
import se.dat255.bulletinferno.model.weapon.WeaponDefinitionImpl;

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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class LoadoutController extends SimpleController {

	/** Aspect to keep on the screen */
	private final static int VIRTUAL_WIDTH = 1280,
			VIRTUAL_HEIGHT = 720;

	private final Stage stage;
	private final Skin skin;

	private final MasterController masterController;
	private final GameController gameController;
	private final ResourceManager resourceManager;

	private Label errorMessage;
	private Table table;

	private WeaponButtonsView weaponButtonsView;
	private SpecialButtonsView specialButtonsView;

	/**
	 * Main controller used for the loadout screen
	 * 
	 * @param masterController
	 *        The master controller that creates this screen
	 * @param resourceManager
	 */
	public LoadoutController(final MasterController masterController,
			final ResourceManager resourceManager) {
		this.gameController = new GameController(masterController, resourceManager);

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
		setupTable();
		weaponButtonsView = new WeaponButtonsView(stage, skin, table, resourceManager);
		specialButtonsView = new SpecialButtonsView(stage, skin, table, resourceManager);
		
		// Set up the start button and add its listener
		setupStartButton();

		// Set up and store buttons in list
//		weaponButtonsView.setupPrimaryWeaponButtons();
		specialButtonsView.setupButtons();
		setupSelectionButtons();
		setupErrorMessage();

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

	private void setupSelectionButtons() {
		Texture texture = new Texture("data/frame.png");
		TextureRegion region = new TextureRegion(texture);

		// Weapon button
		ButtonStyle weaponSelectionStyle = new ImageButtonStyle();
		weaponSelectionStyle.up = new TextureRegionDrawable(region);
		weaponSelectionStyle.over = skin.newDrawable(weaponSelectionStyle.up, Color.LIGHT_GRAY);

		WeaponButton selectionWeaponButton = new WeaponButton(new Button(weaponSelectionStyle),
				null,
				resourceManager);
		selectionWeaponButton.getButton().setPosition(100, 100);
		selectionWeaponButton.getButton().setSize(200, 120);
		selectionWeaponButton.getButton().addListener(
				weaponButtonsView.new SelectionClickedListener());
		weaponButtonsView.setSelectionButton(selectionWeaponButton);

		// Special button
		ButtonStyle specialSelectionStyle = new ImageButtonStyle(weaponSelectionStyle);
		Button specialButton = new Button(specialSelectionStyle);
		specialButton.setPosition(200, 300);
		specialButton.setSize(200, 120);
		SpecialButton selectionSpecialButton = new SpecialButton(specialButton, null,
				resourceManager);
		specialButtonsView.setSelectionButton(selectionSpecialButton);
		selectionSpecialButton.getButton().addListener(
				specialButtonsView.new SelectionClickedListener());

		stage.addActor(selectionWeaponButton.getButton());
		stage.addActor(specialButton);
	}

	private void setupStartButton() {
		Texture startButtonTexture = resourceManager.getManagedTexture(
				TextureType.LOADOUT_START_BUTTON).getTexture();
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
				WeaponDefinition weapon = weaponButtonsView.getSelectionWeaponButton().getData();
				if (weapon == null) {
					showErrorMessage("primary weapon");
				} else {
					startGame(gameController,
							new WeaponDefinition[] { weaponButtonsView.getSelectionWeaponButton()
									.getData() });
				}
			}
		});
	}

	private void setupTable() {
		// Set up the table for the primary weapons
		this.table = new Table();

		// Add table to stage
		table.debug();
		table.setPosition(1100, 450);
		table.setSize(100, 40);

		BitmapFont font = new BitmapFont();
		font = skin.getFont("default");
		font.scale(0.4f);
		LabelStyle labelStyle = new LabelStyle(font, Color.BLACK);
		Label label = new Label("Primary Weapon", labelStyle);

		label.setPosition(table.getX() - 45, table.getY() + 210);

		stage.addActor(table);
		stage.addActor(label);
	}

	private void setupErrorMessage() {
		BitmapFont font = new BitmapFont();
		font = skin.getFont("default");
		font.scale(0.5f);
		LabelStyle labelStyle = new LabelStyle(font, Color.BLACK);
		errorMessage = new Label("", labelStyle);

		errorMessage.setPosition((VIRTUAL_WIDTH / 2) - 250, VIRTUAL_HEIGHT - 50);
		stage.addActor(errorMessage);
		errorMessage.setVisible(false);
	}

	public void startGame(GameController gameScreen, WeaponDefinition[] weapons) {
		gameScreen = new GameController(masterController, resourceManager);
		masterController.startGame(gameScreen, weapons, true);
	}

	private void showErrorMessage(String equipmentMissing) {
		errorMessage.setText("You must select " + equipmentMissing + "!");
		errorMessage.setVisible(true);
	}
}
