package se.dat255.bulletinferno.model;

import se.dat255.bulletinferno.model.weapon.ProjectileType;

import com.badlogic.gdx.math.Vector2;

public interface WeaponDescription extends ResourceIdentifier {

	/**
	 * Returns the time it takes to reload this weapon.
	 * 
	 * @return The time it takes to reload.
	 */
	float getReloadTime();

	/**
	 * The projectile type that this weapon will fire.
	 * 
	 * @return The projectile.
	 */
	ProjectileType getProjectileType();

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
	Weapon getPlayerWeaponForGame(Game game);

	/**
	 * Returns a new Weapon instance by using the data in the passed enum, which is tailored for use
	 * in an enemy.
	 * 
	 * @param game
	 * @return A new weapon instance.
	 */
	Weapon getEnemyWeaponForGame(Game game);

}
