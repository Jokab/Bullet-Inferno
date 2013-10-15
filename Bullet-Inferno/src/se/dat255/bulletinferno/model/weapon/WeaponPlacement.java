package se.dat255.bulletinferno.model.weapon;

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
