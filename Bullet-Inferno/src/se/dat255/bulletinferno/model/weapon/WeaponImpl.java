package se.dat255.bulletinferno.model.weapon;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Teamable;
import se.dat255.bulletinferno.model.Weapon;
import se.dat255.bulletinferno.model.WeaponDescription;
import se.dat255.bulletinferno.util.Timer;

import com.badlogic.gdx.math.Vector2;

public class WeaponImpl implements Weapon {
	private final Timer timer;

	protected final Game game;
	private final ProjectileType projectileType;
	private final Vector2 offset;
	private final float projectileSpeed;
	private float reloadingTime;
	private WeaponDescription type;

	public WeaponImpl(WeaponDescription weaponData, Game game, float reloadingTime, ProjectileType projectileType,
			Vector2 offset, float projectileSpeed) {
		type = weaponData;
		this.game = game;
		this.reloadingTime = reloadingTime;
		this.projectileType = projectileType;
		this.offset = offset;
		this.projectileSpeed = projectileSpeed;

		timer = game.getTimer();
		timer.setTime(reloadingTime);
		timer.stop();
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector2 getOffset() {
		return offset;
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
			
			projectileType.releaseProjectile(game, position, getOffset(), direction.scl(projectileSpeed), source);
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
	public WeaponDescription getType() {
		return type;
	}

}
