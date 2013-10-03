package se.dat255.bulletinferno;

import java.awt.TextField;

import javax.xml.soap.Text;

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
	private final Texture weaponTexture1;
	private final Texture weaponTexture2;

	public LoadoutScreen(final MyGame myGame) {

		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		skin = new Skin();

		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));

		weaponTexture1 = new Texture(Gdx.files.internal("data/missileLauncher.png"));
		TextureRegion weaponTextureRegion1 = new TextureRegion(weaponTexture1);
		
		weaponTexture2 = new Texture(Gdx.files.internal("data/disorderer.png"));
		TextureRegion weaponTextureRegion2 = new TextureRegion(weaponTexture2);

		// Add default font as default
		skin.add("default", new BitmapFont());

		// Set up style for buttons
		ButtonStyle weaponButtonStyle1 = new ButtonStyle();
		weaponButtonStyle1.up = new TextureRegionDrawable(weaponTextureRegion1);
		weaponButtonStyle1.over = skin.newDrawable(weaponButtonStyle1.up, Color.LIGHT_GRAY);
		skin.add("weaponButton1", weaponButtonStyle1);
		
		ButtonStyle weaponButtonStyle2 = new ButtonStyle();
		weaponButtonStyle2.up = new TextureRegionDrawable(weaponTextureRegion2);
		weaponButtonStyle2.over = skin.newDrawable(weaponButtonStyle2.up, Color.LIGHT_GRAY);
		skin.add("weaponButton2", weaponButtonStyle2);

		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = skin.getFont("default");
		textButtonStyle.fontColor = Color.BLACK;
		skin.add("default", textButtonStyle);

		// Create buttons
		Button weaponButton1 = new Button(skin, "weaponButton1");
		weaponButton1.setSize(600, 150);
		// weaponButton1.setPosition(VIRTUAL_MID_WIDTH - weaponButton1.getWidth() / 2, 20);
		Button weaponButton2 = new Button(skin, "weaponButton2");
		weaponButton2.setSize(600, 150);

		// weaponButton1.setSize(1280/2, 720/2);

		// Create a table that fills the screen
		
		// Add it to stage
		Table t = new Table();
		t.setFillParent(true);
		
		t.add(weaponButton1).row();
		t.add(weaponButton2);

		stage.addActor(t);

		// Start button click listener
		weaponButton1.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GameScreen gameScreen = myGame.getGameScreen();
				gameScreen.createNewGame(WeaponData.MISSILE_LAUNCHER);
				myGame.setScreen(gameScreen);
			}
		});

		
		weaponButton2.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GameScreen gameScreen = myGame.getGameScreen();
				gameScreen.createNewGame(WeaponData.DISORDERER);
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
		weaponTexture1.dispose();
		stage.dispose();
		skin.dispose();
	}
}
