package se.dat255.bulletinferno.view.gui;

import se.dat255.bulletinferno.GameScreen;
import se.dat255.bulletinferno.view.RenderableGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class PauseIconView implements RenderableGUI {

	private final Vector2 position, size;
	private final Sprite sprite;
	private final GameScreen game;

	public PauseIconView(GameScreen game) {
		Texture texture = new Texture(Gdx.files.internal("images/gui/icon_pause.png"));
		sprite = new Sprite(texture);
		size = new Vector2(1.0f, 1.0f);
		sprite.setSize(size.x, size.y);
		position = new Vector2(15.0f, 8.0f);
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
	public void pressed() {
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
