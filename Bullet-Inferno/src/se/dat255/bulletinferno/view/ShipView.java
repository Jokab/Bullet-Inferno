package se.dat255.bulletinferno.view;

import se.dat255.bulletinferno.model.PlayerShipImpl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ShipView {
	private Texture texture;
	private Sprite sprite;

	private PlayerShipImpl ship;

	public ShipView(final PlayerShipImpl ship) {		
		this.ship = ship;

		texture = new Texture(Gdx.files.internal("data/ship.png"));
		texture.setFilter(Texture.TextureFilter.Linear,
				Texture.TextureFilter.Linear);

		TextureRegion region = new TextureRegion(texture, 0, 0,
				texture.getWidth(), texture.getHeight());

		sprite = new Sprite(region);
		sprite.setSize(32f, 32f);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.setRotation(-90f);

	}

	public void render(SpriteBatch batch) {
		float x = ship.getX();
		float y = ship.getY() - sprite.getHeight() / 2;

		sprite.setPosition(x, y);
		sprite.draw(batch);
	}

	public void dispose() {
		texture.dispose();
	}
}
