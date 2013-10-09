package se.dat255.bulletinferno.view.gui;

import se.dat255.bulletinferno.controller.GameController;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.ResourceManagerImpl.TextureType;
import se.dat255.bulletinferno.view.RenderableGUI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class PauseScreenView implements RenderableGUI {

	private final Vector2 position, size;
	private final Sprite sprite;
	private final GameController game;

	public PauseScreenView(GameController game, ResourceManager resourceManager) {
		Texture texture = TextureType.PAUSE_SCREEN.getTexture();
		sprite = new Sprite(texture);
		size = new Vector2(16.0f, 9.0f);
		sprite.setSize(size.x, size.y);
		position = new Vector2(0.0f, 0.0f);
		sprite.setPosition(position.x - 8.0f, position.y - 4.5f);
		this.game = game;
	}

	@Override
	public void render(SpriteBatch batch) {
		sprite.draw(batch);
	}

	@Override
	public void dispose() {
		sprite.getTexture().dispose();
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
