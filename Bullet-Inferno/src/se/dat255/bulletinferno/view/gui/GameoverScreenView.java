package se.dat255.bulletinferno.view.gui;

import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.TextureDefinitionImpl;
import se.dat255.bulletinferno.view.RenderableGUI;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Displays a game over screen and the score of the player
 */
public class GameoverScreenView implements RenderableGUI {

	private final Vector2 position = new Vector2(-8.05f, -4.55f);
	private final Vector2 size = new Vector2(16.1f, 9.1f);
	private final TextureRegion texture;
	public GameoverScreenView(ResourceManager resourceManager, int score) {
		texture = resourceManager.getTexture(TextureDefinitionImpl.GAMEOVER_SCREEN);
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
		// Restart
		if (-5.4f < x && x < -1.46f && -2.1f < y && y < -0.5f) {
			return GuiEvent.RESTARTGAME;
		}

		// Menu
		if (2.6f < x && x < 5.14f && -2.12f < y && y < -0.32) {
			return GuiEvent.STOPGAME;
		}

		return null;
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
