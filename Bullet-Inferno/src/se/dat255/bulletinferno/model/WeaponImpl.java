package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;

public class WeaponImpl implements Weapon {
	private final Vector2 offset;
	private final float reloadingTime;
	private final Game world;
	private int projectileSpeed;
	private final Timer timer;

	/**
	 * Constructs a new weapon with the 0-vector
	 * 
	 * @param reloadingTime
	 * @param world
	 */
	public WeaponImpl(float reloadingTime, Game world) {
		this(reloadingTime, world, new Vector2());
	}

	/**
	 * Constructs a new weapon
	 * 
	 * @param reloadingTime
	 * @param world
	 * @param offset
	 */
	public WeaponImpl(float reloadingTime, Game world, Vector2 offset) {
		this.offset = offset;
		this.reloadingTime = reloadingTime;
		this.world = world;
		timer = world.getTimer();
		timer.setTime(reloadingTime);
		projectileSpeed = 3;
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fire(Vector2 origin) {
		if (isLoaded()) {
			// Get projectile and set properties accordingly
			Projectile projectile = getProjectile();
			projectile.init(origin.cpy().add(getOffset()), new Vector2(projectileSpeed, 0), 1);
			
			// Start count down
			timer.restart();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	protected Projectile getProjectile() {
		// Retrieve a projectile from the world
		return world.retrieveProjectile(ProjectileImpl.class);
	}
}
