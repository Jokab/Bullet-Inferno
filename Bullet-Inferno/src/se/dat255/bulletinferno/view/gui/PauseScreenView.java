package se.dat255.bulletinferno.view.gui;

import se.dat255.bulletinferno.controller.GameController;
import se.dat255.bulletinferno.util.ManagedTexture;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.ResourceManagerImpl.TextureType;
import se.dat255.bulletinferno.view.RenderableGUI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class PauseScreenView implements RenderableGUI {

	private final Vector2 position = new Vector2(-8.0f, -4.5f);
	private final Vector2 size = new Vector2(16.0f, 9.0f);
	private final ManagedTexture managedTexture;
	private final Texture texture;
	private final GameController game;

	public PauseScreenView(GameController game, ResourceManager resourceManager) {
		managedTexture = resourceManager.getManagedTexture(TextureType.PAUSE_SCREEN);
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
		game.unpauseGame();
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
