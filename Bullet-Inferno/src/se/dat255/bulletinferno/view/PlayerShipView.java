package se.dat255.bulletinferno.view;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.ManagedTexture;
import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.ResourceManager;
import se.dat255.bulletinferno.model.ResourceManagerImpl;
import se.dat255.bulletinferno.model.ResourceManagerImpl.TextureType;
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
	private final Texture shipTexture;
	private final ManagedTexture mShipTexture;
	private final ManagedTexture mPrimaryWeapon;
	private final ManagedTexture mExplosion;
	private final Texture primaryWeaponTexture;
	private final Texture explosion;
	private Sprite primaryWeaponSprite;
	private Sprite shipSprite;
	private Sprite explosionSprite;
	private Timer timer;
	private ResourceManager resourceManager;

	private final PlayerShip ship;
	private final Weapon weapon;
	
	private Vector2 lastShipPosition = new Vector2();

	private static final float EXPLOSION_TIMEOUT = 1; // second

	public PlayerShipView(Game game, final PlayerShip ship, ResourceManager resourceManager) {
		this.ship = ship;
		this.resourceManager = resourceManager;
		this.weapon = ship.getLoadout().getPrimaryWeapon();

		this.timer = game.getTimer();
		this.timer.setTime(EXPLOSION_TIMEOUT);
		this.timer.registerListener(this);

		mShipTexture = resourceManager.getManagedTexture(ship);
		shipTexture = mShipTexture.getTexture();
		shipTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		mPrimaryWeapon = resourceManager.getManagedTexture(weapon.getType());
		primaryWeaponTexture = mPrimaryWeapon.getTexture();
		primaryWeaponTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		mExplosion = resourceManager.getManagedTexture(TextureType.PLAYER_EXPLOSION);
		explosion = mExplosion.getTexture();
		explosion.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		shipSprite = new Sprite(shipTexture);
		shipSprite.setSize(1f, 1f);
		shipSprite.setOrigin(shipSprite.getWidth() / 2, shipSprite.getHeight() / 2);

		explosionSprite = new Sprite(explosion);
		explosionSprite.setSize(1f, 1f);

		primaryWeaponSprite = new Sprite(primaryWeaponTexture);
		primaryWeaponSprite.setSize(1f, 0.5f);
	}

	@Override
	public void render(SpriteBatch batch) {
		// TODO: this is quite a messy program flow. you can ask me if it seems messed up (jakob)
		if (ship.isDead()) {
			shipSprite = null;
			drawExplosion(batch, lastShipPosition);
			removeWeapons();
		} else if (shipSprite != null) {
			lastShipPosition = ship.getPosition();
			float x = lastShipPosition.x;
			float y = lastShipPosition.y - shipSprite.getHeight() / 2;

			shipSprite.setPosition(x, y);
			shipSprite.draw(batch);

			if (primaryWeaponSprite != null) {
				primaryWeaponSprite.setPosition(x + weapon.getOffset().x, y + weapon.getOffset().y
						+ primaryWeaponSprite.getHeight() / 2);
				primaryWeaponSprite.draw(batch);
			}
		}
	}

	private void removeWeapons() {
		primaryWeaponSprite = null;
	}

	private void drawExplosion(SpriteBatch batch, Vector2 pos) {
		if (!timer.isFinished()) {
			timer.start();
		}
		if (explosionSprite != null) {
			explosionSprite.setSize(1.1f, 1.1f);
			explosionSprite.setPosition(pos.x, pos.y);
			explosionSprite.draw(batch);
		}
	}

	@Override
	public void dispose() {
		mExplosion.dispose(resourceManager);
		mPrimaryWeapon.dispose(resourceManager);
		mShipTexture.dispose(resourceManager);
	}

	@Override
	public void onTimeout(Timer source, float timeSinceLast) {
		explosionSprite = null;
		dispose();
	}
}
