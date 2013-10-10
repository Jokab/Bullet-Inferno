package se.dat255.bulletinferno.view;

import se.dat255.bulletinferno.model.entity.PlayerShip;
import se.dat255.bulletinferno.model.weapon.Weapon;
import se.dat255.bulletinferno.util.ManagedTexture;
import se.dat255.bulletinferno.util.ResourceManager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * A class that renders weapons + equipment
 * 
 * @author Anton
 * 
 */
public class LoadoutView implements Renderable {

	private final ManagedTexture mStandardWeapon;
	private final ManagedTexture mHeavyWeapon;
	private final Texture standardWeaponTexture;
	private final Texture heavyWeaponTexture;
	private Sprite standardWeaponSprite;
	private Sprite heavyWeaponSprite;
	private final Weapon standardWeapon;
	private final Weapon heavyWeapon;

	private ResourceManager resourceManager;
	private final PlayerShip ship;

	public LoadoutView(final PlayerShip ship, ResourceManager resourceManager) {
		this.ship = ship;
		this.resourceManager = resourceManager;

		// Weapons
		this.standardWeapon = ship.getLoadout().getStandardWeapon();
		this.heavyWeapon = ship.getLoadout().getHeavyWeapon();

		// Weapons
		mStandardWeapon = resourceManager.getManagedTexture(standardWeapon.getType());
		standardWeaponTexture = mStandardWeapon.getTexture();
		standardWeaponTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		mHeavyWeapon = resourceManager.getManagedTexture(heavyWeapon.getType());
		heavyWeaponTexture = mHeavyWeapon.getTexture();
		heavyWeaponTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		// Weapons
		standardWeaponSprite = new Sprite(standardWeaponTexture);
		standardWeaponSprite.setSize(standardWeapon.getDimensions().x,
				standardWeapon.getDimensions().y);

		heavyWeaponSprite = new Sprite(heavyWeaponTexture);
		heavyWeaponSprite.setSize(heavyWeapon.getDimensions().x, heavyWeapon.getDimensions().y);

	}

	@Override
	public void render(SpriteBatch batch) {
		if (ship.isDead()) {
			standardWeaponSprite = null;
			heavyWeaponSprite = null;
			mStandardWeapon.dispose(resourceManager);
			mHeavyWeapon.dispose(resourceManager);
		} else {

			Vector2 lastShipPosition = ship.getPosition();
			float x = lastShipPosition.x - ship.getDimensions().x / 2;
			float y = lastShipPosition.y - ship.getDimensions().y / 2;

			// weapons, check if ship is dead
			if (standardWeaponSprite != null) {
				standardWeaponSprite.setPosition(x + standardWeapon.getOffset().x, y
						+ standardWeapon.getOffset().y
						+ standardWeaponSprite.getHeight());
				standardWeaponSprite.draw(batch);
			}

			if (heavyWeaponSprite != null) {
				heavyWeaponSprite.setPosition(x + heavyWeapon.getOffset().x,
						y + heavyWeapon.getOffset().y
								+ heavyWeaponSprite.getHeight());
				heavyWeaponSprite.draw(batch);
			}

		}
	}

	@Override
	public void dispose() {
		mStandardWeapon.dispose(resourceManager);
		mHeavyWeapon.dispose(resourceManager);
	}

}