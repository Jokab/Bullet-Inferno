package se.dat255.bulletinferno.model.weapon;

import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.physics.PhysicsMovementPattern;
import se.dat255.bulletinferno.model.team.Teamable;

import com.badlogic.gdx.math.Vector2;

public interface ProjectileDefinition {

	/**
	 * Releases (fires) a projectile at the provided position.
	 * 
	 * @param physics The PhysicsEnvironment.
	 * @param weapons The WeaponsEnvironment.
	 * @param position The position the projectile will be spawned at
	 * @param projectileVector The direction that this projectile will travel in.
	 * @param source The source that this projectile was fired from.
	 * @return The projectile that was released.
	 */
	public Projectile releaseProjectile(PhysicsEnvironment physics,
			WeaponEnvironment weapons,
			Vector2 position,
			Vector2 projectileVector, Teamable source);

	/**
	 * Sets the damage of the projectile.
	 * 
	 * @param damage The damage to be set.
	 */
	public void setDamage(float damage);

	/**
	 * Returns the projectile's movement pattern.
	 * 
	 * @return The projectile's movement pattern.
	 */
	public PhysicsMovementPattern getMovementPattern();

	/**
	 * Returns the projectile's damage.
	 * 
	 * @return The damage.
	 */
	public float getDamage();

}