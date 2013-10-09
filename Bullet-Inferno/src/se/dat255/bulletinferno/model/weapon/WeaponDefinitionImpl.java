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
	
	DISORDERER(0.5f, ProjectileType.PLASMA, new Vector2(0,0.5f), 5f, new Vector2(1f,0.5f)),
	STANDARD(0.05f, ProjectileType.RED_PROJECTILE, new Vector2(0,1), 14, new Vector2(1f,0.5f)),
	FORCE_GUN(0.2f, ProjectileType.GREEN_PROJECTILE, new Vector2(0,1), 7, new Vector2(1f,0.5f)),
	MISSILE_LAUNCHER(0.5f, ProjectileType.MISSILE, new Vector2(0,0.5f), 10f, new Vector2(1f,0.5f)),
	
	// Different weapons for standard enemies
	ENEMY_DISORDERER(0.5f, ProjectileType.PLASMA, new Vector2(), 5f, new Vector2(1f,0.5f)),
	ENEMY_STANDARD(0.05f, ProjectileType.RED_PROJECTILE, new Vector2(), 14, new Vector2(1f,0.5f)),
	ENEMY_FORCE_GUN(0.2f, ProjectileType.GREEN_PROJECTILE, new Vector2(), 7, new Vector2(1f,0.5f)),
	ENEMY_MISSILE_LAUNCHER(0.5f, ProjectileType.MISSILE, new Vector2(), 10f, new Vector2(1f,0.5f)),
	
	// Different weapons for bosses. 
	BOSS_SPR(1f, ProjectileType.RED_PROJECTILE, new Vector2(0,0), 5, new Vector2(1f,0.5f)),
	BOSS_SPR2(1f, ProjectileType.RED_PROJECTILE, new Vector2(0,1), 5, new Vector2(1f,0.5f)),
	BOSS_SPR3(1f, ProjectileType.RED_PROJECTILE, new Vector2(0,-1), 5, new Vector2(1f,0.5f)),
	
	BOSS_AIM(0.5f, ProjectileType.GREEN_PROJECTILE, new Vector2(0,0), 7f, new Vector2(1f,0.5f)),
	BOSS_AIM2(0.5f, ProjectileType.GREEN_PROJECTILE, new Vector2(0,1), 7f, new Vector2(1f,0.5f)),
	BOSS_AIM3(0.5f, ProjectileType.GREEN_PROJECTILE, new Vector2(0,-1), 7f, new Vector2(1f,0.5f));

	private float reloadingTime;
	private final ProjectileType projectileType;
	private final Vector2 offset;
	private final float projectileSpeed;
	private final Vector2 dimensions;


WeaponDefinitionImpl(float reloadTime, ProjectileType projectileType, Vector2 offset,
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
	public Weapon createWeapon(PhysicsEnvironment physics, WeaponEnvironment weapons) {
		String s = this.getIdentifier().substring(0,5);
		if(s.equals("ENEMY") || s.equals("BOSS_")){
			return new EnemyWeaponImpl(physics, weapons, this, reloadingTime, projectileType, 
					offset, projectileSpeed);
		} else {
			return new WeaponImpl(physics, weapons, this, reloadingTime, projectileType, offset,
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
