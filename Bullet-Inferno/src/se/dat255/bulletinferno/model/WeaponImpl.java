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

	@Override
	public int getReloadingTime() {
		return reloadingTime;
	}

	@Override
	public int getReloadingTimeLeft() {
		return countdown;
	}

	@Override
	public boolean isLoaded() {
		return countdown <= 0;
	}

	@Override
	public Vector2 getOffset() {
		return offset;
	}

	@Override
	public void fire(Vector2 origin) {
		if (isLoaded()) {
			//world.addProjectile(getProjectile(origin.add(getOffset())));

			// Start count down
			countdown = getReloadingTime();
		}
	}

	@Override
	public Projectile getProjectile(Vector2 origin) {
		return null;
		//return new ProjectileImpl(origin, new Vector2(1,0));
		// TODO Could be replace by Projectile.class and dynamically init
	}

	// TODO Override gameobject interface
	public void update(int delta) {
		if (countdown > 0) {
			// Removed delta(time passed)
			countdown -= delta;
		}
	}
}
