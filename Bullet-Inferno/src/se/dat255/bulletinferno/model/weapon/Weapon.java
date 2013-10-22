package se.dat255.bulletinferno.model.weapon;

import se.dat255.bulletinferno.model.team.Teamable;
import se.dat255.bulletinferno.util.Timer;

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
	 * Fires the weapon from the given position with the weapon's given
	 * offset, in the given direction. The source is for determine which
	 * team the fired projectile belongs to.
	 * 
	 * @param position
	 *        The initial position where the projectile should be added.
	 * @param direction
	 *        The direction of the projectile.
	 * @param source
	 *        The Teamable that should be used by the projectile.
	 */
	public void fire(Vector2 position, Vector2 direction, Teamable source);

	/**
	 * Returns the projectile velocity for this weapon.
	 * 
	 * @return the projectile velocity.
	 */
	float getProjectileVelocity();

	/**
	 * Returns the type of projectile that this weapon fires.
	 * 
	 * @return The type of projectile.
	 */
	ProjectileDefinition getProjectileType();

	/**
	 * Returns the timer that the weapon uses.
	 * 
	 * @return The timer.
	 */
	public Timer getTimer();

	/**
	 * Sets the reloading time.
	 * 
	 * @param reloadingTime
	 *        The reloading time to be set.
	 */
	public void setReloadingTime(float reloadingTime);

	/**
	 * Returns this weapon's type.
	 * 
	 * @return The weapon's type.
	 */
	public WeaponDefinition getType();

	/**
	 * Returns this weapon's dimensions (for the hitbox).
	 * 
	 * @return The weapon's dimensions.
	 */
	public Vector2 getDimensions();

	/**
	 * Returns this weapon's offset (for placing it in the world relative to the player ship).
	 * 
	 * @return The weapon's offset.
	 */
	public Vector2 getOffset();

	/**
	 * Sets this weapon's offset (for placing it in the world relative to the player ship).
	 * 
	 * @param offset
	 *        Sets the weapon's offset.
	 */
	public void setOffset(Vector2 offset);

}
