package se.dat255.bulletinferno.model.weapon;

import java.awt.Dimension;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.entity.EntityEnvironment;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;

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
	DISORDERER(0.5f, ProjectileType.PLASMA, new Vector2(1.4f, 1.2f), 5f, new Vector2(1f, 0.5f)),
	STANDARD(0.05f, ProjectileType.RED_PROJECTILE, new Vector2(0, 1), 14, new Vector2(1f, 0.5f)),
	FORCE_GUN(0.2f, ProjectileType.GREEN_PROJECTILE, new Vector2(0, 1), 7, new Vector2(1f, 0.5f)),
	MISSILE_LAUNCHER(0.5f, ProjectileType.MISSILE, new Vector2(1.4f, 1.2f), 5f, new Vector2(1f, 0.5f)),
	SNIPER_RIFLE(3f, ProjectileType.HIGH_VELOCITY_PROJECTILE, new Vector2(0.8f, -0.7f), 20f,
			new Vector2(2f, 0.8f)),
	BOSS_LAUNCHER(1f, ProjectileType.RED_PROJECTILE, new Vector2(0, 0), 5, new Vector2(1f, 0.5f)),
	BOSS_GUN(0.5f, ProjectileType.GREEN_PROJECTILE, new Vector2(0, 0), 15f, new Vector2(1f, 0.5f));

	private float reloadingTime;
	private final ProjectileType projectileType;
	private final Vector2 offset;
	private final float projectileSpeed;
	private final Vector2 dimensions;

	WeaponData(float reloadTime, ProjectileType projectileType, Vector2 offset,
			float projectileSpeed, Vector2 dimensions) {
		this.reloadingTime = reloadTime;
		this.projectileType = projectileType;
		this.offset = offset;
		this.projectileSpeed = projectileSpeed;
		this.dimensions = dimensions;
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
	public Weapon getPlayerWeaponForGame(PhysicsEnvironment physics, WeaponEnvironment weapons) {
		return new WeaponImpl(physics, weapons, this, reloadingTime, projectileType, offset,
				projectileSpeed);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Weapon getEnemyWeaponForGame(PhysicsEnvironment physics, WeaponEnvironment weapons) {
		return new EnemyWeaponImpl(physics, weapons, this, reloadingTime, projectileType, offset,
				projectileSpeed);
	}
	
	@Override
	public String getIdentifier() {
		return this.name();
	}

	@Override
	public ProjectileType getProjectileType() {
		return this.projectileType;
	}

	@Override
	public Vector2 getDimensions() {
		return dimensions;
	}
}
