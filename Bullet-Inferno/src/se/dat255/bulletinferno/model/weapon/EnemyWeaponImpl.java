package se.dat255.bulletinferno.model.weapon;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Projectile;
import se.dat255.bulletinferno.model.Teamable;
import se.dat255.bulletinferno.model.Timer;
import se.dat255.bulletinferno.model.Timerable;

import com.badlogic.gdx.math.Vector2;

public class EnemyWeaponImpl extends WeaponImpl {

	public EnemyWeaponImpl(Game game, float reloadingTime, Class<? extends Projectile> projectile,
			Vector2 offset, Vector2 projectileVelocity, float damage) {
		super(game, reloadingTime, projectile, offset, projectileVelocity, damage);
		getTimer().setContinuous(true);
		getTimer().start();
		System.out.println(getTimer().getTimeLeft());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fire(Vector2 origin, Vector2 velocity, Teamable source) {
		if (isLoaded()) {
			// Get projectile and set properties accordingly
			Projectile projectile = getProjectile();
			projectile.init(origin.cpy().add(getOffset()), getProjectileVelocity(), getDamage(), source);
			
			// Start count down
			getTimer().restart();
		}
	}
}
