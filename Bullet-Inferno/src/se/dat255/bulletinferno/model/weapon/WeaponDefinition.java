package se.dat255.bulletinferno.model.weapon;

import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.util.ResourceIdentifier;

import com.badlogic.gdx.math.Vector2;

public interface WeaponDefinition extends ResourceIdentifier {

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
	ProjectileDefinition getProjectileType();

	/**
	 * Returns the velocity of the projectile that is fired.
	 * 
	 * @return The projectile's velocity.
	 */
	float getProjectileSpeed();

	/**
	 * Returns a new Weapon that this definition holds
	 * 
	 * @param physics
	 * @param weapons
	 * @param offset
	 * @return A new weapon instance.
	 */
	public Weapon createWeapon(PhysicsEnvironment physics, WeaponEnvironment weapons,
			Vector2 offset);

	/**
	 * Returns the dimensions of the weapon
	 * 
	 * @return The dimensions as a vector
	 */
	Vector2 getDimensions();

}
