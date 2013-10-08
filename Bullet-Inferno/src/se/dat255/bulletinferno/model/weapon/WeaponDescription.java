package se.dat255.bulletinferno.model.weapon;

import se.dat255.bulletinferno.model.entity.EntityEnvironment;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.util.ResourceIdentifier;

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
	float getProjectileSpeed();

	/**
	 * Returns a new Weapon instance by using the data in the passed enum.
	 * 
	 * @deprecated
	 * @return A new weapon instance.
	 */
	Weapon getPlayerWeaponForGame(PhysicsEnvironment physics, WeaponEnvironment weapons);

	/**
	 * Returns a new Weapon instance by using the data in the passed enum, which is tailored for use
	 * in an enemy.
	 * 
	 * @deprecated
	 * @return A new weapon instance.
	 */
	Weapon getEnemyWeaponForGame(PhysicsEnvironment physics, WeaponEnvironment weapons);

	/**
	 * Returns the dimensions of the weapon
	 * @return The dimensions as a vector
	 */
	Vector2 getDimensions();
	
	
}
