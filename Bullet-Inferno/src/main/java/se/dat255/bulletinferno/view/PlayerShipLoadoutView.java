package se.dat255.bulletinferno.view;

import se.dat255.bulletinferno.model.entity.PlayerShip;
import se.dat255.bulletinferno.model.weapon.Weapon;
import se.dat255.bulletinferno.util.ResourceManager;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * A class that renders a player's weapons and other equipment.
 * 
 */
public class PlayerShipLoadoutView implements Renderable {

	private final Weapon standardWeapon;
	private final Weapon heavyWeapon;
	private final TextureRegion standardWeaponTexture;
	private final TextureRegion heavyWeaponTexture;
	private final Sprite standardWeaponSprite;
	private final Sprite heavyWeaponSprite;

	private final PlayerShip ship;

	private Vector2 lastShipPosition;

	public PlayerShipLoadoutView(final PlayerShip ship, ResourceManager resourceManager) {
		this.ship = ship;

		// Standard weapon
		standardWeapon = ship.getLoadout().getStandardWeapon();
		standardWeaponTexture = resourceManager.getTexture(standardWeapon.getType());
		standardWeaponSprite = new Sprite(standardWeaponTexture);
		standardWeaponSprite.setSize(standardWeapon.getDimensions().x,
				standardWeapon.getDimensions().y);

		// Heavy weapon
		heavyWeapon = ship.getLoadout().getHeavyWeapon();
		heavyWeaponTexture = resourceManager.getTexture(heavyWeapon.getType());
		heavyWeaponSprite = new Sprite(heavyWeaponTexture);
		heavyWeaponSprite.setSize(heavyWeapon.getDimensions().x, heavyWeapon.getDimensions().y);

	}

	@Override
	public void render(SpriteBatch batch, Camera viewport) {
		lastShipPosition = ship.getPosition();
		float x = lastShipPosition.x;
		float y = lastShipPosition.y;

		// Standard weapon
		standardWeaponSprite.setPosition(
				x + standardWeapon.getOffset().x - standardWeapon.getDimensions().y / 2,
				y + standardWeapon.getOffset().y - standardWeapon.getDimensions().y / 2);
		standardWeaponSprite.draw(batch);

		// Heavy weapon
		heavyWeaponSprite.setPosition(x + heavyWeapon.getOffset().x - heavyWeapon.getDimensions().y
				/ 2,
				y + heavyWeapon.getOffset().y - heavyWeapon.getDimensions().y / 2);
		heavyWeaponSprite.draw(batch);
	}

	@Override
	public void dispose() {
	}

}