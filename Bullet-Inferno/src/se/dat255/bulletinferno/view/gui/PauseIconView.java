package se.dat255.bulletinferno.view.gui;

import se.dat255.bulletinferno.controller.GameController;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.view.RenderableGUI;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class PauseIconView implements RenderableGUI {

	private final TextureRegion textureRegion;
	private final GameController game;
	private final Vector2 position = new Vector2(6.5f, 3f);
	private final Vector2 size = new Vector2(1.5f, 1.5f);

	public PauseIconView(GameController game, TextureRegion textureRegion) {
		this.textureRegion = textureRegion;
		this.game = game;
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(textureRegion, position.x, position.y, size.x, size.y);
	}

	/** Disposed by HudView */
	@Override @Deprecated
	public void dispose(ResourceManager resourceManager) {
	}

	@Override
	public void pressed(float x, float y) {
		game.pauseGame();
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
