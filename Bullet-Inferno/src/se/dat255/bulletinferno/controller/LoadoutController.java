package se.dat255.bulletinferno.controller;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.util.ResourceIdentifier;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.ResourceManagerImpl.TextureType;
import se.dat255.bulletinferno.menu.PassiveButton;
import se.dat255.bulletinferno.menu.PassiveButtonsView;
import se.dat255.bulletinferno.menu.SpecialButton;
import se.dat255.bulletinferno.menu.SpecialButtonsView;
import se.dat255.bulletinferno.menu.WeaponButton;
import se.dat255.bulletinferno.menu.WeaponButtonsView;
import se.dat255.bulletinferno.model.loadout.PassiveAbilityDefinition;
import se.dat255.bulletinferno.model.loadout.SpecialAbilityDefinition;
import se.dat255.bulletinferno.model.weapon.WeaponDefinition;
import se.dat255.bulletinferno.model.weapon.WeaponDefinitionImpl;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.ResourceManagerImpl.TextureType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
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
	private PassiveButtonsView passiveButtonsView;

	private Label tableLabel;
	
	private Label primaryLabel;
	private Label heavyLabel;
	private Label specialLabel;
	private Label passiveLabel;

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
		weaponButtonsView = new WeaponButtonsView(stage, skin, table, tableLabel, resourceManager);
		specialButtonsView = new SpecialButtonsView(stage, skin, table, tableLabel, resourceManager);
		passiveButtonsView = new PassiveButtonsView(stage, skin, table, tableLabel, resourceManager);

		// Set up the start button and add its listener
		setupStartButton();

		setupSelectionButtons();
		
		// Initially populate a table with a kind of equipment
		weaponButtonsView.populateTable("standard");

		// BELOW IS FOR TESTING ONLY
		weaponButtonsView.populateTable("heavy");
		specialButtonsView.populateTable();
		passiveButtonsView.populateTable();
		
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

		// This is left here because the buttons will share style
		ButtonStyle weaponSelectionStyle = new ImageButtonStyle();
		weaponSelectionStyle.up = new TextureRegionDrawable(region);
		weaponSelectionStyle.over = skin.newDrawable(weaponSelectionStyle.up, Color.LIGHT_GRAY);

		// Standard weapon button
		Button standardButton = setupWeaponSelectionButton(weaponSelectionStyle, weaponButtonsView.getStandardWeapons(), "standard");
		
		// Heavy weapon button
		Button heavyButton = setupWeaponSelectionButton(weaponSelectionStyle, weaponButtonsView.getHeavyWeapons(), "heavy");

		// Special button
		Button specialButton = setupSpecialSelectionButton(weaponSelectionStyle);

		// Passive button
		Button passiveButton = setupPassiveSelectionButton(weaponSelectionStyle);

		stage.addActor(standardButton);
		stage.addActor(heavyButton);
		stage.addActor(specialButton);
		stage.addActor(passiveButton);
	}
	
	private Button setupWeaponSelectionButton(ButtonStyle weaponSelectionStyle, List<WeaponButton> list, String type) {
		Button weaponButton = new Button(weaponSelectionStyle);
		weaponButton.setSize(200, 120);
		WeaponButton selectionButton = new WeaponButton(weaponButton, null, resourceManager);
		weaponButton.addListener(weaponButtonsView.new SelectionClickedListener(selectionButton, list, type));
		if(type.equals("standard")) {
			weaponButton.setPosition(100, 100);
			weaponButtonsView.setStandardSelectionButton(selectionButton);
		} else if(type.equals("heavy")) {
			weaponButton.setPosition(400, 100);
			weaponButtonsView.setHeavySelectionButton(selectionButton);
		}
		return weaponButton;
	}

	private Button setupPassiveSelectionButton(ButtonStyle weaponSelectionStyle) {
		ButtonStyle passiveSelectionStyle = new ImageButtonStyle(weaponSelectionStyle);
		Button passiveButton = new Button(passiveSelectionStyle);
		passiveButton.setPosition(100, 450);
		passiveButton.setSize(200, 120);
		PassiveButton selectionPassiveButton = new PassiveButton(passiveButton, null,
				resourceManager);
		passiveButtonsView.setSelectionButton(selectionPassiveButton);
		selectionPassiveButton.getButton().addListener(
				passiveButtonsView.new SelectionClickedListener());
		return passiveButton;
	}

	private Button setupSpecialSelectionButton(ButtonStyle weaponSelectionStyle) {
		ButtonStyle specialSelectionStyle = new ImageButtonStyle(weaponSelectionStyle);
		Button specialButton = new Button(specialSelectionStyle);
		specialButton.setPosition(200, 300);
		specialButton.setSize(200, 120);
		SpecialButton selectionSpecialButton = new SpecialButton(specialButton, null,
				resourceManager);
		specialButtonsView.setSelectionButton(selectionSpecialButton);
		selectionSpecialButton.getButton().addListener(
				specialButtonsView.new SelectionClickedListener());
		return specialButton;
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
		startButton.addListener(new StartButtonClickedListener());
	}

	private void setupTable() {
		// Set up the table for the primary weapons
		this.table = new Table();

		// Add table to stage
//		table.debug();
		table.setPosition(1050, 450);
		table.setSize(100, 40);

		BitmapFont font = new BitmapFont();
		font = skin.getFont("default");
		font.scale(0.4f);
		LabelStyle labelStyle = new LabelStyle(font, Color.BLACK);
		tableLabel = new Label("Primary Weapon", labelStyle);

		tableLabel.setPosition(table.getX() - 45, table.getY() + 210);

		stage.addActor(table);
		stage.addActor(tableLabel);
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

	public void startGame(GameController gameScreen, WeaponDefinition[] weapons, SpecialAbilityDefinition special, PassiveAbilityDefinition passive) {
		gameScreen = new GameController(masterController, resourceManager);
		masterController.startGame(gameScreen, weapons, special, passive, true);
	}

	private void showErrorMessage(String equipmentMissing) {
		errorMessage.setText("You must select " + equipmentMissing + "!");
		errorMessage.setVisible(true);
	}

	public class StartButtonClickedListener extends ChangeListener {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			WeaponDefinition standardWeapon = weaponButtonsView.getStandardSelectionButton().getData();
			WeaponDefinition heavyWeapon = weaponButtonsView.getHeavySelectionButton().getData();
			SpecialAbilityDefinition special = specialButtonsView.getSelectionButton().getData();
			PassiveAbilityDefinition passive = passiveButtonsView.getSelectionButton().getData();
			if (standardWeapon == null) {
				showErrorMessage("primary weapon");
			} else if (heavyWeapon == null) {
				showErrorMessage("heavy weapon");
			} else if (special == null) {
				showErrorMessage("special ability");
			} else if (passive == null) {
				showErrorMessage("passive ability");
			} else {
				WeaponDefinition[] weapons = new WeaponDefinition[]{standardWeapon, heavyWeapon};
				startGame(gameController, weapons, special, passive);
			}
		}
	}
}
