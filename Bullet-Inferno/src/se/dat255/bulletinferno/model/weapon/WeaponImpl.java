package se.dat255.bulletinferno.model.weapon;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Projectile;
import se.dat255.bulletinferno.model.ProjectileImpl;
import se.dat255.bulletinferno.model.Timer;
import se.dat255.bulletinferno.model.Weapon;
import se.dat255.bulletinferno.model.WeaponDescription;

import com.badlogic.gdx.math.Vector2;

public class WeaponImpl implements Weapon {
	private final Timer timer;
	
	private final Game game;
	private final float reloadingTime;
	private final Class<? extends Projectile> projectile;
	private final Vector2 offset;
	private final Vector2 projectileVelocity;
	private final float damage;
	
	public WeaponImpl(Game game, float reloadingTime, Class<? extends Projectile> projectile,
			Vector2 offset, Vector2 projectileVelocity, float damage) {
		this.game = game;
		this.reloadingTime = reloadingTime;
		this.projectile = projectile;
		this.offset = offset;
		this.projectileVelocity = projectileVelocity;
		this.damage = damage;
		
		timer = game.getTimer();
		timer.setTime(reloadingTime);
		timer.stop();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getReloadingTime() {
		return reloadingTime;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getReloadingTimeLeft() {
		return timer.getTimeLeft();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isLoaded() {
		return timer.isFinished();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector2 getOffset() {
		return offset;
	}
	
	@Override
	public float getDamage() {
		return this.damage;
	}
	
	@Override
	public Vector2 getProjectileVelocity() {
		return this.projectileVelocity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fire(Vector2 origin) {
		if (isLoaded()) {
			// Get projectile and set properties accordingly
			Projectile projectile = getProjectile();
			projectile.init(origin.cpy().add(getOffset()), this.projectileVelocity, this.damage);
			
			// Start count down
			timer.restart();
		}
	}
	
	public Timer getTimer() {
		return this.timer;
	}

	/**
	 * {@inheritDoc}
	 */
	protected Projectile getProjectile() {
		// Retrieve a projectile from the world
		return game.retrieveProjectile(projectile);
	}
}
