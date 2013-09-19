package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;

public class WeaponImpl implements Weapon {
	private final Vector2 offset;
	private final int reloadingTime;
	private final Game world;
	
	private int countdown;

	/**
	 * Constructs a new weapon with the 0-vector
	 * @param reloadingTime
	 * @param world
	 */
	public WeaponImpl(int reloadingTime, Game world) {
		this(reloadingTime, world, new Vector2());
	}

	/**
	 * Constructs a new weapon
	 * @param reloadingTime
	 * @param world
	 * @param offset
	 */
	public WeaponImpl(int reloadingTime, Game world, Vector2 offset) {
		this.offset = offset;
		this.reloadingTime = reloadingTime;
		this.world = world;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getReloadingTime() {
		return reloadingTime;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getReloadingTimeLeft() {
		return countdown;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isLoaded() {
		return countdown <= 0;
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
			Projectile p = getProjectile();
			p.setPosition(origin.add(getOffset()));
			p.setVelocity(new Vector2(1,0));
			// Start count down
			countdown = getReloadingTime();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Projectile getProjectile() {
		// Retrieve a projectile from the world
		return world.retrieveProjectile(ProjectileImpl.class);
	}
}
