package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;

public interface Weapon {
	/**
	 * Returns the <strong>constant</strong> reloading time in milliseconds
	 * 
	 * @return time in seconds
	 */
	public float getReloadingTime();

	/**
	 * Returns the current time in milliseconds until the weapon is ready to
	 * fire again
	 * 
	 * @return time in milliseconds
	 */
	public float getReloadingTimeLeft();

	/**
	 * Returns whether the weapon is loaded/ready to fire
	 * 
	 * @return loaded // TODO: Debug test add bullet ProjectileImpl projectile =
	 *         new ProjectileImpl(null); projectile.setPosition(new Vector2(5,
	 *         7));
	 */
	public boolean isLoaded();

	/**
	 * Returns the weapon's offset position
	 * 
	 * @return offset
	 */
	public Vector2 getOffset();

	/**
	 * Fires the weapon from the specified origin with the weapon's given
	 * offset.
	 * 
	 * @param origin
	 */
	public void fire(Vector2 origin);
}
