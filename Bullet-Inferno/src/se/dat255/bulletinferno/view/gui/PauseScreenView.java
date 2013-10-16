package se.dat255.bulletinferno.view.gui;

import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.ResourceManagerImpl.TextureType;
import se.dat255.bulletinferno.view.RenderableGUI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * When the game is paused, this will display a screen that tells the player that the game is
 * paused.
 */
public class PauseScreenView implements RenderableGUI {

	private final Vector2 position = new Vector2(-8.0f, -4.5f);
	private final Vector2 size = new Vector2(16.0f, 9.0f);
	private final TextureRegion texture;

	public PauseScreenView(ResourceManager resourceManager) {
		texture = resourceManager.getTexture(TextureType.PAUSE_SCREEN);
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(texture, position.x, position.y, size.x, size.y);
	}

	@Override
	public void dispose(ResourceManager resourceManager) {
	}

	@Override
	public GuiEvent pressed(float x, float y) {
		return GuiEvent.UNPAUSE;
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
