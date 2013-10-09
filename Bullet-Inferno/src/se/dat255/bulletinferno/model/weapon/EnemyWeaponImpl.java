package se.dat255.bulletinferno.model.weapon;

import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.team.Teamable;

import com.badlogic.gdx.math.Vector2;

public class EnemyWeaponImpl extends WeaponImpl {
	
	/** The PhysicsEnvironment instance injected at construction. */
	private final PhysicsEnvironment physics;
	
	/** The WeaponEnvironment instance injected at construction. */
	private final WeaponEnvironment weapons;
	
	private Vector2 offset;

	public EnemyWeaponImpl(PhysicsEnvironment physics, WeaponEnvironment weapons,
			WeaponDefinition type, float reloadingTime, ProjectileType projectile, Vector2 offset,
			float projectileSpeed) {
		super(physics, weapons, type, reloadingTime, projectile, offset, projectileSpeed);
		this.physics = physics;
		this.weapons = weapons;
		this.offset = offset;
		
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

			getProjectileType().releaseProjectile(physics, weapons, position, getOffset(),
					direction.scl(getProjectileVelocity()), source);
			// Start count down
			getTimer().restart();
		}

	}
	

	@Override
	public Vector2 getOffset() {
		return offset;
		
	}
	
	public void setOffset(Vector2 offs){
		this.offset = offs;
	}
}
