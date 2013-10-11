package se.dat255.bulletinferno.view;

import se.dat255.bulletinferno.model.entity.PlayerShip;
import se.dat255.bulletinferno.model.weapon.Weapon;
import se.dat255.bulletinferno.util.ResourceManager;

import com.badlogic.gdx.graphics.Camera;
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

	private final Texture standardWeaponTexture;
	private final Texture heavyWeaponTexture;
	private Sprite standardWeaponSprite;
	private Sprite heavyWeaponSprite;
	private final Weapon standardWeapon;
	private final Weapon heavyWeapon;

	private ResourceManager resourceManager;
	private final PlayerShip ship;
	
	private Vector2 lastShipPosition;

	public LoadoutView(final PlayerShip ship, ResourceManager resourceManager) {
		this.ship = ship;
		this.resourceManager = resourceManager;

		// Weapons
		this.standardWeapon = ship.getLoadout().getStandardWeapon();
		this.heavyWeapon = ship.getLoadout().getHeavyWeapon();

		// Weapons
		standardWeaponTexture = resourceManager.getTexture(standardWeapon.getType());
		standardWeaponTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		heavyWeaponTexture = resourceManager.getTexture(heavyWeapon.getType());
		heavyWeaponTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		// Weapons
		standardWeaponSprite = new Sprite(standardWeaponTexture);
		standardWeaponSprite.setSize(standardWeapon.getDimensions().x,
				standardWeapon.getDimensions().y);

		heavyWeaponSprite = new Sprite(heavyWeaponTexture);
		heavyWeaponSprite.setSize(heavyWeapon.getDimensions().x, heavyWeapon.getDimensions().y);

	}

	@Override
	public void render(SpriteBatch batch, Camera viewport) {
		if (!ship.isDead()) {
			lastShipPosition = ship.getPosition();
			float x = lastShipPosition.x - ship.getDimensions().x / 2;
			float y = lastShipPosition.y - ship.getDimensions().y / 2;

			standardWeaponSprite.setPosition(x + standardWeapon.getOffset().x, y
					+ standardWeapon.getOffset().y
					+ standardWeaponSprite.getHeight());
			standardWeaponSprite.draw(batch);

			heavyWeaponSprite.setPosition(x + heavyWeapon.getOffset().x,
					y + heavyWeapon.getOffset().y
							+ heavyWeaponSprite.getHeight());
			heavyWeaponSprite.draw(batch);

		}
	}

	@Override
	public void dispose() {
	}

}