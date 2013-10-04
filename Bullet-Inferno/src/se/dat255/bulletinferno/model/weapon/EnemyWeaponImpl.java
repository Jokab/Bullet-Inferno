package se.dat255.bulletinferno.model.weapon;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.ProjectileType;
import se.dat255.bulletinferno.model.Teamable;

import com.badlogic.gdx.math.Vector2;

public class EnemyWeaponImpl extends WeaponImpl {
	
	

	public EnemyWeaponImpl(WeaponDescription type, Game game, float reloadingTime, ProjectileType projectile,
			Vector2 offset, float projectileSpeed) {
		super(type, game, reloadingTime, projectile, offset, projectileSpeed);
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
	
	// Enemy weapon does not have an offset, yet.
	@Override
	public Vector2 getOffset() {
		return new Vector2();
		
	}
}
