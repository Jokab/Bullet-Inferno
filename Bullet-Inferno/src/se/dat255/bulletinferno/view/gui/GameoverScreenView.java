package se.dat255.bulletinferno.view.gui;

import se.dat255.bulletinferno.controller.GameController;
import se.dat255.bulletinferno.controller.MasterController;
import se.dat255.bulletinferno.util.ManagedTexture;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.ResourceManagerImpl.TextureType;
import se.dat255.bulletinferno.view.RenderableGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class GameoverScreenView implements RenderableGUI {

	private final Vector2 position = new Vector2(-8.0f, -4.5f);
	private final Vector2 size = new Vector2(16.0f, 9.0f);
	private final ManagedTexture managedTexture;
	private final Texture texture;
	private final MasterController game;

	public GameoverScreenView(MasterController game, ResourceManager resourceManager) {
		managedTexture = resourceManager.getManagedTexture(TextureType.GAMEOVER_SCREEN);
		texture = managedTexture.getTexture();
		this.game = game;
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(texture, position.x, position.y, size.x, size.y);
	}

	@Override
	public void dispose(ResourceManager resourceManager) {
		managedTexture.dispose(resourceManager);
	}

	@Override
	public void pressed(float x, float y) {
		// Restart
		if(-5.4f < x && x < -1.46f && -2.1f < y && y < -0.5f){
			game.startGame(null);
		}
		
		// Menu
		if(2.6f < x && x < 5.14f && -2.12f < y && y < -0.32){
			game.setScreen(game.getLoadoutScreen());
		}
	}

	@Override
	public Vector2 getPosition() {
		return position;
	}

	@Override
	public Vector2 getSize() {
		return size;
	}

}
