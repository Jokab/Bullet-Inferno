package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;

public class WeaponImpl implements Weapon {
	private final Vector2 offset;
	private final int reloadingTime;

	private int countdown;

	public WeaponImpl(int reloadingTime) {
		this(reloadingTime, new Vector2());
	}

	public WeaponImpl(int reloadingTime, Vector2 offset) {
		this.offset = offset;
		this.reloadingTime = reloadingTime;
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
			// TODO Add retrieved projectile to world
			getProjectile(origin.add(getOffset()));

			// Start count down
			countdown = getReloadingTime();
		}
	}

	@Override
	public Projectile getProjectile(Vector2 origin) {
		return new ProjectileImpl(origin, new Vector2(1,0));
	}

	// TODO Override gameobject interface
	public void update(int delta) {
		if (countdown > 0) {
			// Removed delta(time passed)
			countdown -= delta;
		}
	}
}
