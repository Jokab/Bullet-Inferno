package se.dat255.bulletinferno.model;

import se.dat255.bulletinferno.model.weapon.WeaponData;
import se.dat255.bulletinferno.model.weapon.WeaponImpl;

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

	/**
	 * Returns the amount of damage this weapon's bullets will deal.
	 * 
	 * @return The damage.
	 */
	float getDamage();

	/**
	 * Returns a new Weapon instance by using the data in the passed enum.
	 * 
	 * @param game
	 * @return A new weapon instance.
	 */
	Weapon getWeaponForGame(Game game);

}
