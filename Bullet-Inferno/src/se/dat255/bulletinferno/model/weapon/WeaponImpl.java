package se.dat255.bulletinferno.model.weapon;

import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.team.Teamable;
import se.dat255.bulletinferno.util.Timer;

import com.badlogic.gdx.math.Vector2;

public class WeaponImpl implements Weapon {
	private final Timer timer;

	private final PhysicsEnvironment physics;
	private final WeaponEnvironment weapons;
	private final ProjectileType projectileType;
	private final float projectileSpeed;
	private float reloadingTime;
	private WeaponDefinition type;
	private Vector2 offset;

	public WeaponImpl(PhysicsEnvironment physics, WeaponEnvironment weapons, 
			WeaponDefinition weaponData, float reloadingTime, ProjectileType projectileType, 
			float projectileSpeed, Vector2 offset) {
		type = weaponData;
		this.physics = physics;
		this.weapons = weapons;
		this.reloadingTime = reloadingTime;
		this.projectileType = projectileType;
		this.projectileSpeed = projectileSpeed;
		this.offset = offset;
		timer = physics.getTimer();
		timer.setTime(reloadingTime);
		timer.stop();
	}
	
	/**
	 * Constructs a new weapon with no offset
	 * @param physics
	 * @param weapons
	 * @param weaponData
	 * @param reloadingTime
	 * @param projectileType
	 * @param projectileSpeed
	 */
	public WeaponImpl(PhysicsEnvironment physics, WeaponEnvironment weapons, 
			WeaponDefinition weaponData, float reloadingTime, ProjectileType projectileType, 
			float projectileSpeed) {
		this(physics, weapons, weaponData, reloadingTime, projectileType, projectileSpeed,
				new Vector2());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getReloadingTime() {
		return reloadingTime;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getReloadingTimeLeft() {
		return timer.getTimeLeft();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isLoaded() {
		return timer.isFinished();
	}

	@Override
	public float getProjectileVelocity() {
		return projectileSpeed;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fire(Vector2 position, Vector2 direction, Teamable source) {
		if (isLoaded()) {
			
			projectileType.releaseProjectile(physics, weapons, position.add(getOffset()),
					direction.scl(projectileSpeed), source);
			// Start count down
			timer.restart();
		}
	}

	@Override
	public Timer getTimer() {
		return timer;
	}

	@Override
	public ProjectileType getProjectileType() {
		return projectileType;
	}

	@Override
	public void setReloadingTime(float reloadingTime) {
		this.reloadingTime = reloadingTime;
		timer.setTime(reloadingTime);
		timer.start();
	}

	@Override
	public WeaponDefinition getType() {
		return type;
	}

	@Override
	public Vector2 getDimensions() {
		return type.getDimensions();
	}

	@Override
	public Vector2 getOffset() {
		return offset;
	}

	@Override
	public void setOffset(Vector2 offset) {
		this.offset = offset;

	}
}
