package se.dat255.bulletinferno.model.weapon;

import com.badlogic.gdx.math.Vector2;

public class WeaponPlacementImpl implements WeaponPlacement {

	private final WeaponDefinition content;
	private final Vector2 offset;

	/**
	 * Constructs a new enemy placement entry
	 * 
	 * @param definition
	 *        enemy
	 * @param x
	 *        position
	 * @param y
	 *        position
	 */
	public WeaponPlacementImpl(WeaponDefinition definition, float x, float y) {
		content = definition;
		offset = new Vector2(x, y);
	}

	@Override
	public WeaponDefinition getContent() {
		return content;
	}

	@Override
	public Vector2 getOffset() {
		return offset;
	}
}