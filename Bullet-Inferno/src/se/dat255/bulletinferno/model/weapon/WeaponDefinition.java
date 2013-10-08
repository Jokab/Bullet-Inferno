package se.dat255.bulletinferno.model.weapon;

import se.dat255.bulletinferno.model.entity.EntityEnvironment;
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
	 * Returns a new Weapon that this definition holds
	 * 
	 * 
	 * @return A new weapon instance.
	 */
	public Weapon createWeapon(PhysicsEnvironment physics, WeaponEnvironment weapons);

}
