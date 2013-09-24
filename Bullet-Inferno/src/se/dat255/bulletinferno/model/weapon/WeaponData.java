package se.dat255.bulletinferno.model.weapon;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.Projectile;
import se.dat255.bulletinferno.model.ProjectileImpl;
import se.dat255.bulletinferno.model.WeaponDescription;

public enum WeaponData implements WeaponDescription {
	
	FAST(0f, ProjectileImpl.class, new Vector2(), new Vector2(5,0)),
	STANDARD(0.5f, ProjectileImpl.class, new Vector2(), new Vector2(3.5f,0)),
	SLOW(1f, ProjectileImpl.class, new Vector2(), new Vector2(2,0));
	
	private float reloadTime;
	private final Class<? extends Projectile> projectile;
	private final Vector2 offset;
	private final Vector2 projectileVelocity;
	
	WeaponData(float reloadTime, Class<? extends Projectile> projectile, Vector2 offset, Vector2 projectileVelocity) {
		this.reloadTime = reloadTime;
		this.projectile = projectile;
		this.offset = offset;
		this.projectileVelocity = projectileVelocity;
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector2 getProjectileVelocity() {
		return this.projectileVelocity;
	}
}
