package se.dat255.bulletinferno.model.weapon;

import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.team.Teamable;

import com.badlogic.gdx.math.Vector2;

public class AutomaticWeaponImpl extends WeaponImpl {

	/** The PhysicsEnvironment instance injected at construction. */
	private final PhysicsEnvironment physics;

	/** The WeaponEnvironment instance injected at construction. */
	private final WeaponEnvironment weapons;

	public AutomaticWeaponImpl(PhysicsEnvironment physics, WeaponEnvironment weapons,
			WeaponDefinition type, float reloadingTime, ProjectileType projectile,
			float projectileSpeed, Vector2 offset) {
		super(physics, weapons, type, reloadingTime, projectile, projectileSpeed, offset);
		this.physics = physics;
		this.weapons = weapons;

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

			getProjectileType().releaseProjectile(physics, weapons,
					position.add(getOffset().cpy().add(new Vector2(getDimensions().x / 2, 0))),
					direction.scl(getProjectileVelocity()), source);
			// Start count down
			getTimer().restart();
		}

	}
}
