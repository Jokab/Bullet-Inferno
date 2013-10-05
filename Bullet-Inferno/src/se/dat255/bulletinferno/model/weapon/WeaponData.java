package se.dat255.bulletinferno.model.weapon;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.ProjectileType;
import se.dat255.bulletinferno.model.Weapon;

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
	DISORDERER(0.5f, ProjectileType.PLASMA, new Vector2(0,0.5f), 5f),
	STANDARD(0.05f, ProjectileType.RED_PROJECTILE, new Vector2(0,1), 14),
	FORCE_GUN(0.2f, ProjectileType.GREEN_PROJECTILE, new Vector2(0,1), 7),
	MISSILE_LAUNCHER(0.5f, ProjectileType.MISSILE, new Vector2(0,0.5f), 2f),

	BOSS_LAUNCHER(0.3f, ProjectileType.GREEN_PROJECTILE, new Vector2(0,0), 5),
	BOSS_GUN(0.15f, ProjectileType.GREEN_PROJECTILE, new Vector2(0,-1), 0.5f);

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
		return new WeaponImpl(this, game, reloadingTime, projectileType, offset, projectileSpeed);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Weapon getEnemyWeaponForGame(Game game) {
		return new EnemyWeaponImpl(this, game, reloadingTime, projectileType, offset, projectileSpeed);
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
