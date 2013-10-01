package se.dat255.bulletinferno.model.weapon;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Projectile;
import se.dat255.bulletinferno.model.Teamable;
import se.dat255.bulletinferno.model.Weapon;
import se.dat255.bulletinferno.util.Timer;

import com.badlogic.gdx.math.Vector2;

public class WeaponImpl implements Weapon {
	private final Timer timer;

	private final Game game;
	private float reloadingTime;
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
		return damage;
	}

	@Override
	public Vector2 getProjectileVelocity() {
		return projectileVelocity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fire(Vector2 position, Vector2 direction, Teamable source) {
		if (isLoaded()) {
			// Get projectile and set properties accordingly
			Projectile projectile = getProjectile();
			projectile.init(position.cpy().add(getOffset()), direction.scl(projectileVelocity),
					damage, source);

			// Start count down
			timer.restart();
		}
	}

	@Override
	public Timer getTimer() {
		return timer;
	}

	/**
	 * Gets the projectile to be fired.
	 * Purely for extension purposes. To be overridden when
	 * some kind of special property is needed for the projectile.
	 */
	protected Projectile getProjectile() {
		// Retrieve a projectile from the world
		return game.retrieveProjectile(projectile);
	}

	@Override
	public void setReloadingTime(float reloadingTime) {
		this.reloadingTime = reloadingTime;
		timer.setTime(reloadingTime);
		timer.start();
	}
}
