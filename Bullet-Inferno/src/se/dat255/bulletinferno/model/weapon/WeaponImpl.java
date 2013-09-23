package se.dat255.bulletinferno.model.weapon;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Projectile;
import se.dat255.bulletinferno.model.ProjectileImpl;
import se.dat255.bulletinferno.model.Timer;
import se.dat255.bulletinferno.model.Weapon;

import com.badlogic.gdx.math.Vector2;

public class WeaponImpl implements Weapon {
	private final Vector2 offset;
	private final float reloadingTime;
	private final Game game;
	private final int weaponSpeed;
	private final Timer timer;
	private final Class<? extends Projectile> projectile;

	/**
	 * Constructs a new weapon using data from a WeaponDescription.
	 * 
	 * @param game 
	 * @param weaponDescription Object that holds the data or "settings" for weapon.
	 */
	public WeaponImpl(Game game, WeaponDescription weaponDescription) {
		this(game, weaponDescription.getReloadTime(), weaponDescription.getProjectile(), weaponDescription.getOffset());
	}
	
	/**
	 * Constructs a new weapon with the 0-vector
	 * 
	 * @param world
	 * @param reloadingTime
	 */
	public WeaponImpl(Game game, float reloadingTime, Class<? extends Projectile> projectile) {
		this(game, reloadingTime, projectile, new Vector2());
	}

	/**
	 * Constructs a new weapon
	 * 
	 * @param reloadingTime
	 * @param world
	 * @param offset
	 */
	public WeaponImpl(Game game, float reloadingTime, Class<? extends Projectile> projectile, Vector2 offset) {
		this.offset = offset;
		this.reloadingTime = reloadingTime;
		this.projectile = projectile;
		this.game = game;
		timer = game.getTimer();
		timer.setTime(reloadingTime);
		weaponSpeed = 3;
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
		System.out.println("IS LOADED: " + timer.getTimeLeft());
		if (isLoaded()) {
			// Get projectile and set properties accordingly
			Projectile p = getProjectile();
			p.setPosition(origin.add(getOffset()));
			p.setVelocity(new Vector2(weaponSpeed, 0));
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
