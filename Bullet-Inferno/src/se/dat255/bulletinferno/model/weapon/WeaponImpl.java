package se.dat255.bulletinferno.model.weapon;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Projectile;
import se.dat255.bulletinferno.model.ProjectileImpl;
import se.dat255.bulletinferno.model.Teamable;
import se.dat255.bulletinferno.model.Timer;
import se.dat255.bulletinferno.model.Weapon;
import se.dat255.bulletinferno.model.WeaponDescription;

import com.badlogic.gdx.math.Vector2;

/**
 * This class uses a Builder pattern to construct itself.
 * To construct a new weapon, you use WeaponImpl.Builder(game, <projectile here>).<parameters go here>
 * <p>
 * For example: WeaponImpl.Builder(game, projectile).damage(10f).reloadingTime(1f).
 * This will create a Weapon with damage 10 and the offset 0-vector. The other parameters that
 * can be set are set to default values if not set through their setters.
 * <p>
 * See: http://bit.ly/LYocLC (Joshua Bloch article)
 * Also see {@link WeaponData#getWeaponForGame(Game)} for concrete usage.
 * 
 * @author Jakob Csörgei Gustavsson
 *
 */
public class WeaponImpl implements Weapon {
	private final Timer timer;
	
	private final Game game;
	private final float reloadingTime;
	private final Class<? extends Projectile> projectile;
	private final Vector2 offset;
	private final Vector2 projectileVelocity;
	private final float damage;
	
	private static final float DEFAULT_RELOADING_TIME = 0;
	private static final Vector2 DEFAULT_OFFSET = new Vector2();
	private static final Vector2 DEFAULT_PROJECTILE_VELOCITY = new Vector2(2,0);
	private static final int DEFAULT_DAMAGE = 1;

	/** Cannot be called. Use the Builder pattern (see class javadoc). **/
	private WeaponImpl(Builder builder) {
		game = builder.game;
		projectile = builder.projectile;
		reloadingTime = builder.reloadingTime;
		offset = builder.offset;
		projectileVelocity = builder.projectileVelocity;
		damage = builder.damage;
		
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
	public void fire(Vector2 position, Vector2 direction, Teamable source) {
		if (isLoaded()) {
			// Get projectile and set properties accordingly
			Projectile projectile = getProjectile();
			projectile.init(position.cpy().add(getOffset()), direction.scl(projectileVelocity), 
					DEFAULT_DAMAGE, source);
			
			// Start count down
			timer.restart();
		}
	}

	/**
	 * Gets the projectile to be fired.
	 * Purely for extension purposes. To be overridden when
	 * some kind of special property is needed for the projectile.
	 */
	protected Projectile getProjectile() {
		// Retrieve a projectile from the world
		return game.retrieveProjectile(projectile);
	}
	
	public static class Builder {
		// These are required parameters
		private final Game game;
		private final Class<? extends Projectile> projectile;
		
		// Not required, else set to default values
		private float reloadingTime = DEFAULT_RELOADING_TIME;
		private Vector2 offset = DEFAULT_OFFSET;
		private Vector2 projectileVelocity = DEFAULT_PROJECTILE_VELOCITY;
		private float damage = DEFAULT_DAMAGE;
		
		public Builder(Game game, Class<? extends Projectile> projectile) {
			this.game = game;
			this.projectile = projectile;
		}
		
		public Builder reloadingTime(float reloadTime) {reloadingTime = reloadTime; return this;}
		public Builder offset(Vector2 val) {offset = val; return this;}
		public Builder projectileVelocity(Vector2 val) {projectileVelocity = val; return this;}
		public Builder damage(float val) {damage = val; return this;}
		
		public WeaponImpl build() {
			return new WeaponImpl(this);
		}
	}
}
