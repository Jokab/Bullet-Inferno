package se.dat255.bulletinferno.view.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import se.dat255.bulletinferno.model.Renderable;

public class PauseIconView implements Renderable {
	
	private final Sprite sprite;
	
	public PauseIconView() {
		Texture texture = new Texture(Gdx.files.internal("images/gui/icon_pause.png"));
		sprite = new Sprite(texture);
		sprite.setOrigin(0, 0);
		sprite.setSize(1.0f, 1.0f);
		sprite.setPosition(-3.0f, -3.0f);
	}

	@Override
	public void render(SpriteBatch batch) {
		sprite.draw(batch);
	}

	@Override
	public void dispose() {
		sprite.getTexture().dispose();
	}

}
