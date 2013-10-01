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
	FAST(0f, ProjectileImpl.class, new Vector2(), new Vector2(5, 0), 1f),
	STANDARD(0.5f, ProjectileImpl.class, new Vector2(), new Vector2(3.5f, 0), 1f),
	SLOW(1f, ProjectileImpl.class, new Vector2(), new Vector2(2, 0), 1f),

	FASTENEMY(1f, ProjectileImpl.class, new Vector2(), new Vector2(-5, 0), 1f);

	private float reloadingTime;
	private final Class<? extends Projectile> projectile;
	private final Vector2 offset;
	private final Vector2 projectileVelocity;
	private final float damage;

	WeaponData(float reloadTime, Class<? extends Projectile> projectile, Vector2 offset,
			Vector2 projectileVelocity, float damage) {
		reloadingTime = reloadTime;
		this.projectile = projectile;
		this.offset = offset;
		this.projectileVelocity = projectileVelocity;
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
	public Class<? extends Projectile> getProjectile() {
		return projectile;
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
	public Vector2 getProjectileVelocity() {
		return projectileVelocity;
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
		return new WeaponImpl(game, reloadingTime, projectile, offset, projectileVelocity, damage);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Weapon getEnemyWeaponForGame(Game game) {
		return new EnemyWeaponImpl(game, reloadingTime, projectile, offset, projectileVelocity,
				damage);
	}
	
	@Override
	public String getIdentifier() {
		return this.name();
	}
}
