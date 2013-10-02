package se.dat255.bulletinferno.model.weapon;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Teamable;

import com.badlogic.gdx.math.Vector2;

public class EnemyWeaponImpl extends WeaponImpl {
	
	

	public EnemyWeaponImpl(Game game, float reloadingTime, ProjectileType projectile,
			Vector2 offset, float velocity) {
		super(game, reloadingTime, projectile, offset, velocity);
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

			getProjectileType().releaseProjectile(game, position, getOffset(),
					direction.scl(getProjectileVelocity()), source);
			// Start count down
			getTimer().restart();
		}

	}
}
