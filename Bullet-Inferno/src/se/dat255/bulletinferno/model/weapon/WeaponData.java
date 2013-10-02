package se.dat255.bulletinferno.model.weapon;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Projectile;
import se.dat255.bulletinferno.model.ProjectileImpl;
import se.dat255.bulletinferno.model.Weapon;
import se.dat255.bulletinferno.model.WeaponDescription;

/**
 * Enum for holding different Weapon types. The method {@link #getPlayerWeaponForGame(Game)}
 * (for players) or {@link #getEnemyWeaponForGame(Game)} (for enemies) are
 * used to retrieve a Weapon for the game.
 * 
 * @author Jakob Csorgei Gustavsson
 * 
 */
public enum WeaponData implements WeaponDescription {

	/**
	 * Order:
	 * reloadTime, projectile, offset, projectileVelocity
	 */
	FAST(0.05f, ProjectileType.SINE_PROJECTILE, new Vector2(), 4),
	SLOW(1f, ProjectileType.SINE_PROJECTILE, new Vector2(), 1);

	private float reloadingTime;
	private final ProjectileType projectileType;
	private final Vector2 offset;
	private final float projectileSpeed;

	WeaponData(float reloadTime, ProjectileType projectileType, Vector2 offset,
			float projectileSpeed) {
		this.reloadingTime = reloadTime;
		this.projectileType = projectileType;
		this.offset = offset;
		this.projectileSpeed = projectileSpeed;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getReloadTime() {
		return this.reloadingTime;
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
	public float getProjectileSpeed() {
		return this.projectileSpeed;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Weapon getPlayerWeaponForGame(Game game) {
		return new WeaponImpl(game, reloadingTime, projectileType, offset, projectileSpeed);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Weapon getEnemyWeaponForGame(Game game) {
		return new EnemyWeaponImpl(game, reloadingTime, projectileType, offset, projectileSpeed);
	}
	
	@Override
	public String getIdentifier() {
		return this.name();
	}

	@Override
	public ProjectileType getProjectileType() {
		return this.projectileType;
	}
}
