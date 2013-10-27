package se.dat255.bulletinferno.view.gui;

import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.view.RenderableGUI;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Displays the pause icon in-game that allows the player to pause the game.
 */
public class PauseIconView implements RenderableGUI {

	private final TextureRegion textureRegion;
	private final Vector2 position = new Vector2(6.6f, 3.1f);
	private final Vector2 size = new Vector2(1.2f, 1.2f);

	public PauseIconView(TextureRegion textureRegion) {
		this.textureRegion = textureRegion;
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(textureRegion, position.x, position.y, size.x, size.y);
	}

	/** Disposed by HudView */
	@Override
	@Deprecated
	public void dispose(ResourceManager resourceManager) {
	}

	@Override
	public GuiEvent pressed(float x, float y) {
		return GuiEvent.PAUSE;
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
