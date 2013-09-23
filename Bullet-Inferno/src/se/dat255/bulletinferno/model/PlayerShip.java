package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;

public interface PlayerShip extends Ship {

	/**
	 * Delegates to the PlayerShip's weapon to fire a projectile.
	 * 
	 */
	public void fireWeapon();

	/**
	 * Sets the PlayerShip's position to the provided position.
	 * 
	 * @param position The position to be set.
	 */
	void setPosition(Vector2 position);
}
