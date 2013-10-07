package se.dat255.bulletinferno.model.weapon;

import se.dat255.bulletinferno.model.entity.EntityEnvironment;
import se.dat255.bulletinferno.model.entity.Teamable;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;

import com.badlogic.gdx.math.Vector2;

public class EnemyWeaponImpl extends WeaponImpl {
	
	/** The PhysicsEnvironment instance injected at construction. */
	private final PhysicsEnvironment physics;
	
	/** The EntityEnvironment instance injected at construction. */
	private final EntityEnvironment entities;

	public EnemyWeaponImpl(PhysicsEnvironment physics, EntityEnvironment entities,
			WeaponDescription type, float reloadingTime, ProjectileType projectile, Vector2 offset,
			float projectileSpeed) {
		super(physics, entities, type, reloadingTime, projectile, offset, projectileSpeed);
		this.physics = physics;
		this.entities = entities;
		
		if (getReloadingTime() == 0) {
			throw new RuntimeException("Enemy reloading speed must not be 0.");
		}
		getTimer().setContinuous(true);
		getTimer().start();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fire(Vector2 position, Vector2 direction, Teamable source) {

		if (isLoaded()) {

			getProjectileType().releaseProjectile(physics, entities, position, getOffset(),
					direction.scl(getProjectileVelocity()), source);
			// Start count down
			getTimer().restart();
		}

	}
	
	// Enemy weapon does not have an offset, yet.
	@Override
	public Vector2 getOffset() {
		return new Vector2();
		
	}
}
