package se.dat255.bulletinferno.model.weapon;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.PhysicsMovementPattern;
import se.dat255.bulletinferno.model.Projectile;
import se.dat255.bulletinferno.model.Teamable;
import se.dat255.bulletinferno.model.Weapon;
import se.dat255.bulletinferno.util.Timer;

import com.badlogic.gdx.math.Vector2;

public class WeaponImpl implements Weapon {
	private final Timer timer;

	protected final Game game;
	private final float reloadingTime;
	private final ProjectileType projectileType;
	private final Vector2 offset;
	private final Vector2 projectileVelocity;	

	public WeaponImpl(Game game, float reloadingTime, ProjectileType projectileType,
			Vector2 offset, Vector2 projectileVelocity, float damage) {
		this.game = game;
		this.reloadingTime = reloadingTime;
		this.projectileType = projectileType;
		this.offset = offset;
		this.projectileVelocity = projectileVelocity;

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
	public Vector2 getProjectileVelocity() {
		return projectileVelocity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fire(Vector2 position, Teamable source) {
		if (isLoaded()) {
			
			projectileType.releasePorjectile(game, position, getOffset(), projectileVelocity, source);
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

}
