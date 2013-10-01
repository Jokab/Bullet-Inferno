package se.dat255.bulletinferno.model.weapon;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Projectile;
import se.dat255.bulletinferno.model.ProjectileImpl;
import se.dat255.bulletinferno.model.Weapon;
import se.dat255.bulletinferno.model.WeaponDescription;

/**
 * Enum class for holding different Weapon types. The method {@link #getPlayerWeaponForGame(Game)}
 * (for players) or {@link #getEnemyWeaponForGame(Game)} (for enemies) are
 * used to retrieve a Weapon for the game.
 * 
 * @author Jakob Csorgei Gustavsson
 * 
 */
public enum WeaponData implements WeaponDescription {

	/**
	 * Order:
	 * reloadTime, projectile, offset, projectileVelocity, damage
	 */
	FAST(0.1f, ProjectileType.RIGHT_ACCELERATING_PROJECTILE, new Vector2(), 10f, 1f),
	STANDARD(0.5f, ProjectileType.DEFAULT_PROJECTILE, new Vector2(), 5f, 1f),
	SLOW(1f, ProjectileType.SINE_PROJECTILE, new Vector2(), 1f, 1f),

	// Enemy weapons below
	FASTENEMY(1f, ProjectileType.LEFT_ACCELERATING_PROJECTILE, new Vector2(), 10f, 1f);

	private float reloadingTime;
	private final ProjectileType projectileType;
	private final Vector2 offset;
	private final float velocity;
	private final float damage;

	WeaponData(float reloadTime, ProjectileType projectileType, Vector2 offset,
			float velocity, float damage) {
		reloadingTime = reloadTime;
		this.projectileType = projectileType;
		this.offset = offset;
		this.velocity = velocity;
		this.damage = damage;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getReloadTime() {
		return reloadingTime;
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
	public float getProjectileVelocity() {
		return velocity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getDamage() {
		return damage;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Weapon getPlayerWeaponForGame(Game game) {
		return new WeaponImpl(game, reloadingTime, projectileType, offset, velocity, damage);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Weapon getEnemyWeaponForGame(Game game) {
		return new EnemyWeaponImpl(game, reloadingTime, projectileType, offset, velocity,
				damage);
	}
	
	@Override
	public String getIdentifier() {
		return this.name();
	}

	@Override
	public ProjectileType getProjectileType() {
		return projectileType;
	}
}
