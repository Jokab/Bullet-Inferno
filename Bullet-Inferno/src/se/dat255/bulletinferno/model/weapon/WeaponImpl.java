package se.dat255.bulletinferno.model.weapon;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Projectile;
import se.dat255.bulletinferno.model.ProjectileImpl;
import se.dat255.bulletinferno.model.Timer;
import se.dat255.bulletinferno.model.Weapon;
import se.dat255.bulletinferno.model.WeaponDescription;

import com.badlogic.gdx.math.Vector2;

public class WeaponImpl implements Weapon {
	private final Vector2 offset;
	private final float reloadingTime;
	private final Game game;
	private final Vector2 projectileVelocity;
	private final Timer timer;
	private final Class<? extends Projectile> projectile;
	
	private final static Vector2 DEFAULT_PROJECTILE_VELOCITY = new Vector2(2,0);
	private final static Vector2 DEFAULT_OFFSET = new Vector2();

	/**
	 * Constructs a new weapon using data from a WeaponDescription.
	 * 
	 * @param game 
	 * @param weaponDescription Object that holds the data or "settings" for weapon.
	 */
	public WeaponImpl(Game game, WeaponDescription weaponDescription) {
		this(game, weaponDescription.getReloadTime(), weaponDescription.getProjectile(), weaponDescription.getOffset(), weaponDescription.getProjectileVelocity());
	}
	
	/**
	 * Constructs a new weapon with the 0-vector
	 * 
	 * @param world
	 * @param reloadingTime
	 */
	public WeaponImpl(Game game, float reloadingTime, Class<? extends Projectile> projectile) {
		this(game, reloadingTime, projectile, DEFAULT_OFFSET, DEFAULT_PROJECTILE_VELOCITY);
	}

	/**
	 * Constructs a new weapon
	 * 
	 * @param reloadingTime
	 * @param world
	 * @param offset
	 */
	public WeaponImpl(Game game, float reloadingTime, Class<? extends Projectile> projectile, Vector2 offset, Vector2 projectileVelocity) {
		this.game = game;
		this.reloadingTime = reloadingTime;
		this.projectile = projectile;
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fire(Vector2 origin) {
		if (isLoaded()) {
			// Get projectile and set properties accordingly
			Projectile projectile = getProjectile();
			projectile.init(origin.cpy().add(getOffset()), this.projectileVelocity, 1);
			
			// Start count down
			timer.restart();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	protected Projectile getProjectile() {
		// Retrieve a projectile from the world
		return game.retrieveProjectile(projectile);
	}
}
