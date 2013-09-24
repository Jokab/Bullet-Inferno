package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;

public interface WeaponDescription {
	
	/**
	 * Returns the time it takes to reload this weapon.
	 * 
	 * @return The time it takes to reload.
	 */
	float getReloadTime();
	
	/**
	 * The projectile that this weapon will fire.
	 * 
	 * @return The projectile.
	 */
	Class<? extends Projectile> getProjectile();
	
	/**
	 * Returns the offset, in relation to the ship, that this weapon will fire its bullets from.
	 * 
	 * @return The offset.
	 */
	Vector2 getOffset();

	/**
	 * Returns the velocity of the projectile that is fired.
	 * 
	 * @return The projectile's velocity.
	 */
	Vector2 getProjectileVelocity();
	
}