package se.dat255.bulletinferno.model.weapon;

import se.dat255.bulletinferno.model.entity.EnemyDefinition;

import com.badlogic.gdx.math.Vector2;

public class WeaponPlacementImpl implements WeaponPlacement {

	private final WeaponDefinition content;
	private final Vector2 offset;
	
	/**
	 * Constructs a new enemy placement entry
	 * @param definition enemy 
	 * @param x position
	 * @param y position
	 */
	public WeaponPlacementImpl(WeaponDefinition definition, float x, float y) {
		this.content = definition;
		this.offset = new Vector2(x,y);
	}
	
	public WeaponPlacementImpl() {
		content = WeaponDefinitionImpl.DISORDERER;
		offset = new Vector2(0, 0);
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
