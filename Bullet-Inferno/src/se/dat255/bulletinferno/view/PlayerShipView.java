package se.dat255.bulletinferno.view;

import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.ResourceManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class PlayerShipView implements Renderable {
	private final Texture texture;
	private final Sprite sprite;

	private final PlayerShip ship;

	public PlayerShipView(final PlayerShip ship, ResourceManager resourceManager) {
		this.ship = ship;

		texture = resourceManager.getTexture(ship.getIdentifier());
		texture.setFilter(Texture.TextureFilter.Linear,
				Texture.TextureFilter.Linear);
		//
		// TextureRegion region = new TextureRegion(texture, 0, 0,
		// texture.getWidth(), texture.getHeight());

		sprite = new Sprite(texture);
		sprite.setSize(1f, 1f);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);

	}

	@Override
	public void render(SpriteBatch batch) {
		if (!ship.isDead()) {
			Vector2 pos = ship.getPosition();
			float x = pos.x;
			float y = pos.y - sprite.getHeight() / 2;

			sprite.setPosition(x, y);
			sprite.draw(batch);
		} else {
			// TODO: draw cool explosion
		}
	}

	@Override
	public void dispose() {
		texture.dispose();
	}
}
