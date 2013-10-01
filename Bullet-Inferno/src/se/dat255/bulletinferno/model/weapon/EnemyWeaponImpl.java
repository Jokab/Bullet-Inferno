package se.dat255.bulletinferno.model.weapon;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Projectile;
import se.dat255.bulletinferno.model.Teamable;

import com.badlogic.gdx.math.Vector2;

public class EnemyWeaponImpl extends WeaponImpl {
	
	

	public EnemyWeaponImpl(Game game, float reloadingTime, ProjectileType projectile,
			Vector2 offset, Vector2 projectileVelocity, float damage) {
		super(game, reloadingTime, projectile, offset, projectileVelocity, damage);
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
	public void fire(Vector2 position, Teamable source) {

		if (isLoaded()) {

			getProjectileType().releasePorjectile(game, position, getOffset(),
					getProjectileVelocity(), source);
			// Start count down
			getTimer().restart();
		}

	}
}
