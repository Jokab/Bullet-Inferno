package se.dat255.bulletinferno.model.weapon;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.Projectile;
import se.dat255.bulletinferno.model.ProjectileImpl;

public enum WeaponData implements WeaponDescription {
	
	FAST(0f, ProjectileImpl.class, new Vector2()),
	STANDARD(1f, ProjectileImpl.class, new Vector2());
	
	private float reloadTime;
	private final Class<? extends Projectile> projectile;
	private final Vector2 offset;
	
	WeaponData(float reloadTime, Class<? extends Projectile> projectile, Vector2 offset) {
		this.reloadTime = reloadTime;
		this.projectile = projectile;
		this.offset = offset;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getReloadTime() {
		return this.reloadTime;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<? extends Projectile> getProjectile() {
		return this.projectile;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector2 getOffset() {
		return this.offset;
	}
}
