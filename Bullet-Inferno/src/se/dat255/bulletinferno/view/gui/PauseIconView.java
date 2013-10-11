package se.dat255.bulletinferno.view.gui;

import se.dat255.bulletinferno.controller.GameController;
import se.dat255.bulletinferno.view.RenderableGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class PauseIconView implements RenderableGUI {

	private final Vector2 position, size;
	private final Sprite sprite;
	private final GameController game;

	public PauseIconView(GameController game) {
		Texture texture = new Texture(Gdx.files.internal("images/gui/icon_pause.png"));
		sprite = new Sprite(texture);
		size = new Vector2(1.0f, 1.0f);
		sprite.setSize(size.x, size.y);
		position = new Vector2(15.0f, 8.0f);
		sprite.setPosition(position.x - 8.0f, position.y - 4.5f);
		this.game = game;
	}

	@Override
	public void render(SpriteBatch batch, Camera viewport) {
		sprite.draw(batch);
	}

	@Override
	public void dispose() {
		sprite.getTexture().dispose();
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
