package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;

public interface Weapon {
	/**
	 * Returns the <strong>constant</strong> reloading time in milliseconds
	 * 
	 * @return time in milliseconds
	 */
	public int getReloadingTime();

	/**
	 * Returns the current time in milliseconds until the weapon is ready to
	 * fire again
	 * 
	 * @return time in milliseconds
	 */
	public int getReloadingTimeLeft();

	/**
	 * Returns whether the weapon is loaded/ready to fire
	 * 
	 * @return loaded
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

	/**
	 * Returns the projectile to be fired
	 * 
	 * @param origin
	 * @return projectile
	 */
	public Projectile getProjectile();
}
