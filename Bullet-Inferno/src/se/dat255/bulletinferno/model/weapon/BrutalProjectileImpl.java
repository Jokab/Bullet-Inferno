package se.dat255.bulletinferno.model.weapon;

import se.dat255.bulletinferno.model.physics.Collidable;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.team.Teamable;

/**
 * The brutal projectile type only collides with things from a Teamable (i.e. not Obstacles).
 */
public class BrutalProjectileImpl extends ProjectileImpl {

	/**
	 * @see ProjectileImpl#ProjectileImpl(PhysicsEnvironment, WeaponEnvironment)
	 */
	public BrutalProjectileImpl(PhysicsEnvironment physicsEnvironment,
			WeaponEnvironment weaponEnvironment) {
		super(physicsEnvironment, weaponEnvironment);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void preCollided(Collidable other) {
		if (other instanceof Teamable) {
			super.preCollided(other);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void postCollided(Collidable other) {
		if (other instanceof Teamable) {
			super.postCollided(other);
		}
	}

}