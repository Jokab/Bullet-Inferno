package se.dat255.bulletinferno.model.weapon;


import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;

import com.badlogic.gdx.math.Vector2;

/**
 * Enum for holding different Weapon types. The method {@link #getPlayerWeaponForGame(Game)}
 * (for players) or {@link #getEnemyWeaponForGame(Game)} (for enemies) are
 * used to retrieve a Weapon for the game.
 * 
 * @author Jakob Csorgei Gustavsson
 * 
 */
public enum WeaponDefinitionImpl implements WeaponDefinition {

	/**
	 * Order:
	 * reloadTime, projectile, offset, projectileVelocity
	 */
	
	DISORDERER(0.5f, ProjectileType.PLASMA, 5f, new Vector2(1f, 0.5f)),
	STANDARD(0.05f, ProjectileType.RED_PROJECTILE, 5f, new Vector2(1f, 0.5f)),
	FORCE_GUN(0.2f, ProjectileType.GREEN_PROJECTILE, 5f, new Vector2(1f, 0.5f)),
	MISSILE_LAUNCHER(0.5f, ProjectileType.MISSILE, 5f, new Vector2(1f, 0.5f)),
	SNIPER_RIFLE(3f, ProjectileType.HIGH_VELOCITY_PROJECTILE, 20f,
			new Vector2(2f, 0.8f)),
	BOSS_LAUNCHER(1f, ProjectileType.RED_PROJECTILE, 5, new Vector2(1f, 0.5f)),
	BOSS_GUN(0.5f, ProjectileType.GREEN_PROJECTILE, 15f, new Vector2(1f, 0.5f)), 
	//TODO : the weapon below is just copy pasted from above, should probably changed
	ENEMY_FORCE_GUN(0.5f, ProjectileType.GREEN_PROJECTILE, 15f, new Vector2(1f, 0.5f));

	private float reloadingTime;
	private final ProjectileType projectileType;
	private final float projectileSpeed;
	private final Vector2 dimensions;


WeaponDefinitionImpl(float reloadTime, ProjectileType projectileType,
			float projectileSpeed, Vector2 dimensions) {
		this.reloadingTime = reloadTime;
		this.projectileType = projectileType;
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
	public float getProjectileSpeed() {
		return this.projectileSpeed;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Weapon createWeapon(PhysicsEnvironment physics, WeaponEnvironment weapons) {
		if(this == FORCE_GUN || this ==  BOSS_LAUNCHER || this ==  BOSS_GUN || this == ENEMY_FORCE_GUN) {
			return new EnemyWeaponImpl(physics, weapons, this, reloadingTime, projectileType, projectileSpeed);
		} else {
			return new WeaponImpl(physics, weapons, this, reloadingTime, projectileType,
					projectileSpeed);
		}
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
