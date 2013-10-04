package se.dat255.bulletinferno.view;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.ResourceManager;
import se.dat255.bulletinferno.model.Weapon;
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
	private Texture primaryWeaponTexture;
	private Sprite primaryWeaponSprite;
	private final Texture explosion;
	private Sprite sprite;
	private Timer timer;

	private final PlayerShip ship;
	private final Weapon weapon;

	public PlayerShipView(Game game, final PlayerShip ship, ResourceManager resourceManager) {
		this.ship = ship;
		this.weapon = ship.getLoadout().getPrimaryWeapon();

		texture = resourceManager.getTexture(ship.getIdentifier());
		texture.setFilter(Texture.TextureFilter.Linear,
				Texture.TextureFilter.Linear);

		primaryWeaponTexture = resourceManager.getTexture(weapon.getType().getIdentifier());

		this.timer = game.getTimer();
		timer.setTime(1);
		timer.registerListener(this);

		explosion = resourceManager.getTexture("PLAYER_EXPLOSION");
		explosion.setFilter(Texture.TextureFilter.Linear,
				Texture.TextureFilter.Linear);

		sprite = new Sprite(texture);
		sprite.setSize(ship.getDimensions().x, ship.getDimensions().y);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);

		primaryWeaponSprite = new Sprite(primaryWeaponTexture);
		primaryWeaponSprite.setSize(1f, 0.5f);
	}

	@Override
	public void render(SpriteBatch batch) {
		
		if (sprite != null) {
			if (ship.isDead()) {
				drawExplosion();
				removeWeapons();
			}
			Vector2 pos = ship.getPosition();
			float x = pos.x - ship.getDimensions().x/2;
			float y = pos.y - ship.getDimensions().y/2;
			
			sprite.setPosition(x, y);
			sprite.draw(batch);
			
			if(primaryWeaponSprite != null) {
				primaryWeaponSprite.setPosition(x + weapon.getOffset().x, y + weapon.getOffset().y
						+ primaryWeaponSprite.getHeight() / 2);
				primaryWeaponSprite.draw(batch);
			}
		}
		

	}

	private void removeWeapons() {
		primaryWeaponSprite = null;
	}

	private void drawExplosion() {
		if (!timer.isFinished()) {
			timer.start();
		}
		sprite.setTexture(explosion);
		sprite.setSize(1.1f, 1.1f);
	}

	@Override
	public void dispose() {
		texture.dispose();
		primaryWeaponTexture.dispose();
		explosion.dispose();
	}

	@Override
	public void onTimeout(Timer source, float timeSinceLast) {
		sprite = null;
		dispose();
	}
}
