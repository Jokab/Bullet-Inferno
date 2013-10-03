package se.dat255.bulletinferno.view;

import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.ResourceManager;
import se.dat255.bulletinferno.model.Weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class PlayerShipView implements Renderable {
	private final Texture texture;
	private final Sprite sprite;
	private Texture primaryWeaponTexture;
	private Sprite primaryWeaponSprite;

	private final PlayerShip ship;
	private final Weapon weapon;

	public PlayerShipView(final PlayerShip ship, ResourceManager resourceManager) {
		this.ship = ship;
		this.weapon = ship.getLoadout().getPrimaryWeapon();
		
		texture = resourceManager.getTexture(ship.getIdentifier());
		texture.setFilter(Texture.TextureFilter.Linear,
				Texture.TextureFilter.Linear);
		
		primaryWeaponTexture = resourceManager.getTexture(weapon.getType().getIdentifier());
		
//
//		TextureRegion region = new TextureRegion(texture, 0, 0,
//				texture.getWidth(), texture.getHeight());

		sprite = new Sprite(texture);
		sprite.setSize(1f, 1f);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		
		primaryWeaponSprite = new Sprite(primaryWeaponTexture);
		primaryWeaponSprite.setSize(1f, 0.5f);

	}

	@Override
	public void render(SpriteBatch batch) {
		Vector2 pos = ship.getPosition();
		float x = pos.x;
		float y = pos.y - sprite.getHeight() / 2;

		sprite.setPosition(x, y);
		sprite.draw(batch);
		
		primaryWeaponSprite.setPosition(x+weapon.getOffset().x, y+weapon.getOffset().y+primaryWeaponSprite.getHeight()/2);
		primaryWeaponSprite.draw(batch);
	}

	@Override
	public void dispose() {
		texture.dispose();
		primaryWeaponTexture.dispose();
	}
}
