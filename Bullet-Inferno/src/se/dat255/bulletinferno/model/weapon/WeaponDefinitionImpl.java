package se.dat255.bulletinferno.model.weapon;

import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;

import com.badlogic.gdx.math.Vector2;

/**
 * Enum for holding different weapon types. Weapons are retrieved with createWeapon().
 * 
 * @author Jakob Csorgei Gustavsson
 * 
 */
public enum WeaponDefinitionImpl implements WeaponDefinition {

	/**
	 * Order:
	 * reloadTime, projectile, offset, projectileVelocity
	 */

	STANDARD_MACHINE_GUN(0.17f, ProjectileDefinitionImpl.VELOCITY_BULLET, 16f, new Vector2(0.8f, 0.4f)),
	STANDARD_MINI_GUN(0.07f, ProjectileDefinitionImpl.ROUND_BULLET, 12f, new Vector2(0.8f, 0.6f)),
	STANDARD_PLASMA_GUN(0.25f, ProjectileDefinitionImpl.PLASMA, 10f, new Vector2(0.8f, 0.5f)),

	HEAVY_LASER_CANNON(0.5f, ProjectileDefinitionImpl.LASER, 20f, new Vector2(1.2f, 0.65f)),
	HEAVY_EGG_CANNON(2f, ProjectileDefinitionImpl.EGG, 35f, new Vector2(1.2f, 0.65f)),

	KATZE_GUN(0.5f, ProjectileDefinitionImpl.VELOCITY_BULLET, 10f, new Vector2(0f, 0f)),
	LASER_GUN(0.3f, ProjectileDefinitionImpl.LASER, 10f, new Vector2(0f, 0f)),
	DISORDERER(0.5f, ProjectileDefinitionImpl.PLASMA, 10f, new Vector2(1f, 0.5f)),
	// STANDARD(0.05f, ProjectileType.RED_PROJECTILE, 14, new Vector2(1f,0.5f)),
	FORCE_GUN(0.2f, ProjectileDefinitionImpl.GREEN_PROJECTILE, 7, new Vector2(1f, 0.5f)),
	MISSILE_LAUNCHER(2f, ProjectileDefinitionImpl.MISSILE, 10f, new Vector2(1f, 0.5f)),

	// Different weapons for bosses.
	BOSS_SPR(1f, ProjectileDefinitionImpl.RED_PROJECTILE, 5, new Vector2(1f, 0.5f)),
	BOSS_AIM(0.5f, ProjectileDefinitionImpl.GREEN_PROJECTILE, 7f, new Vector2(1f, 0.5f));

	private float reloadingTime;
	private final ProjectileDefinition projectileType;
	private final float projectileSpeed;
	private final Vector2 dimensions;

	WeaponDefinitionImpl(float reloadTime, ProjectileDefinition projectileType,
			float projectileSpeed, Vector2 dimensions) {
		reloadingTime = reloadTime;
		this.projectileType = projectileType;
		this.projectileSpeed = projectileSpeed;
		this.dimensions = dimensions;
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
	public float getProjectileSpeed() {
		return projectileSpeed;
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
	public ProjectileDefinition getProjectileType() {
		return projectileType;
	}

	@Override
	public Vector2 getDimensions() {
		return dimensions;
	}
}
