package se.dat255.bulletinferno.model.weapon;

import se.dat255.bulletinferno.model.entity.EnemyDefinition;

import com.badlogic.gdx.math.Vector2;

public interface WeaponPlacement {
	
	/**
	 * Returns the Weapon definition
	 * @return enemy type
	 */
	public WeaponDefinition getContent();
	
	/**
	 * Returns the offset 
	 * @return position
	 */
	public Vector2 getOffset();

}
