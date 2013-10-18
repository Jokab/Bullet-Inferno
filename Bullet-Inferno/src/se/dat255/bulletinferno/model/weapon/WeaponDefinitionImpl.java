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
	
	STANDARD_MACHINE_GUN(0.17f, ProjectileType.VELOCITY_BULLET, 16f, new Vector2(0.8f, 0.4f)),
	STANDARD_MINI_GUN(0.07f, ProjectileType.ROUND_BULLET, 12f, new Vector2(0.8f, 0.6f)),
	STANDARD_PLASMA_GUN(0.25f, ProjectileType.PLASMA, 10f, new Vector2(0.8f,0.5f)),
	
	HEAVY_LASER_CANNON(0.5f, ProjectileType.LASER, 20f, new Vector2(1.2f,0.65f)),
	HEAVY_EGG_CANNON(2f, ProjectileType.EGG, 35f, new Vector2(1.2f,0.65f)),
	
	KATZE_GUN(0.2f, ProjectileType.VELOCITY_BULLET, 10f, new Vector2(0f, 0f)),
	LASER_GUN(0.5f, ProjectileType.LASER, 10f, new Vector2(0f,0f)),
	DISORDERER(0.5f, ProjectileType.PLASMA, 10f, new Vector2(1f,0.5f)),
	//STANDARD(0.05f, ProjectileType.RED_PROJECTILE, 14, new Vector2(1f,0.5f)),
	FORCE_GUN(0.2f, ProjectileType.GREEN_PROJECTILE, 7, new Vector2(1f,0.5f)),
	MISSILE_LAUNCHER(2f, ProjectileType.MISSILE, 10f, new Vector2(1f,0.5f)),
	
	// Different weapons for bosses. 
	BOSS_SPR(1f, ProjectileType.RED_PROJECTILE, 5, new Vector2(1f,0.5f)),
	BOSS_AIM(0.5f, ProjectileType.GREEN_PROJECTILE, 7f, new Vector2(1f,0.5f));

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
	public Weapon createWeapon(PhysicsEnvironment physics, WeaponEnvironment weapons, 
			Vector2 offset) {
		if (this == HEAVY_LASER_CANNON || this == HEAVY_EGG_CANNON) {
			return new CooldownWeaponImpl(physics, weapons, this, reloadingTime, projectileType, 
					projectileSpeed, offset);

		} else {
			return new AutomaticWeaponImpl(physics, weapons, this, reloadingTime, projectileType,
					projectileSpeed, offset);
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
