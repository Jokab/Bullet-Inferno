package se.dat255.bulletinferno.view;

import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.Renderable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ShipView implements Renderable {
	private final Texture texture;
	private final Sprite sprite;

	private final PlayerShip ship;

	public ShipView(final PlayerShip ship) {
		this.ship = ship;

		texture = new Texture(Gdx.files.internal("data/ship.png"));
		texture.setFilter(Texture.TextureFilter.Linear,
				Texture.TextureFilter.Linear);

		TextureRegion region = new TextureRegion(texture, 0, 0,
				texture.getWidth(), texture.getHeight());

		sprite = new Sprite(region);
		sprite.setSize(1f, 1f);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.setRotation(-90f);

	}

	@Override
	public void render(SpriteBatch batch) {
		float x = ship.getX();
		float y = ship.getY() - sprite.getHeight() / 2;

		sprite.setPosition(x, y);
		sprite.draw(batch);
	}

	@Override
	public void dispose() {
		texture.dispose();
	}
}
