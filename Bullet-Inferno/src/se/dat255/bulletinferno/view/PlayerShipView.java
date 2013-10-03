package se.dat255.bulletinferno.view;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.ResourceManager;
import se.dat255.bulletinferno.util.Timer;
import se.dat255.bulletinferno.util.Timerable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class PlayerShipView implements Renderable, Timerable {
	private final Texture texture;
	private final Texture explosion;
	private Sprite sprite;
	private Timer timer;

	private final PlayerShip ship;

	public PlayerShipView(Game game, final PlayerShip ship, ResourceManager resourceManager) {
		this.ship = ship;
		this.timer = game.getTimer();
		timer.setTime(1);
		timer.registerListener(this);

		texture = resourceManager.getTexture(ship.getIdentifier());
		texture.setFilter(Texture.TextureFilter.Linear,
				Texture.TextureFilter.Linear);

		explosion = resourceManager.getTexture("PLAYER_EXPLOSION");
		explosion.setFilter(Texture.TextureFilter.Linear,
				Texture.TextureFilter.Linear);

		sprite = new Sprite(texture);
		sprite.setSize(1f, 1f);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
	}

	@Override
	public void render(SpriteBatch batch) {
		if (sprite != null) {
			if (ship.isDead()) {
				// TODO: draw cool explosion
				if (!timer.isFinished()) {
					timer.start();
				}

				sprite.setTexture(explosion);
				sprite.setSize(1.1f, 1.1f);
			}

			Vector2 pos = ship.getPosition();
			float x = pos.x;
			float y = pos.y - sprite.getHeight() / 2;
			sprite.setPosition(x, y);
			sprite.draw(batch);
		}
	}

	@Override
	public void dispose() {
		texture.dispose();
		explosion.dispose();
	}

	@Override
	public void onTimeout(Timer source, float timeSinceLast) {
		sprite = null;
		dispose();
	}
}
