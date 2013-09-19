package se.dat255.bulletinferno.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.utils.Pool;

/**
 * Default implementation of Game, the central type in Bullet Inferno.
 * 
 * <p>Game acts as a single point of entry for the outside environment, as well as central point of
 * lookup for the inside. It handles instance-based object creation and initialization (injection).
 */
public class GameImpl implements Game {

	private final List<Projectile> projectiles = new ArrayList<Projectile>();
	private final List<Enemy> enemies = new ArrayList<Enemy>();
	private final List<Obstacle> obstacles = new ArrayList<Obstacle>();

	private final Map<Class<? extends Projectile>, Pool<Projectile>> projectilePools;
	
	public GameImpl() {
		projectilePools = new HashMap<Class<?extends Projectile>, Pool<Projectile>>();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerShip getPlayerShip() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<? extends Obstacle> getObstacles() {
		return obstacles;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<? extends Enemy> getEnemies() {
		return enemies;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<? extends Projectile> getProjectiles() {
		return projectiles;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Projectile retrieveProjectile(Class<? extends Projectile> type) {
		// If pool for specified type of projectile doesn't exist
		// create a new pool and but it in the map 
		if(!projectilePools.containsKey(type)) {
			projectilePools.put(type, createNewPool(type));
		}
		
		// Get a projectile from the pool
		Projectile p = projectilePools.get(type).obtain();
		projectiles.add(p);
		return p;
	}
	
	/**
	 * {@inheritDoc}
	 * @param projectile
	 */
	@Override
	public void disposeProjectile(Projectile projectile) {
		if(projectilePools.containsKey(projectile.getClass())) {
			projectilePools.get(projectile.getClass()).free(projectile);
		}
	}
	
	private Pool<Projectile> createNewPool(final Class<? extends Projectile> type) {
		return new Pool<Projectile>() {
	        @Override
	        protected Projectile newObject() {
	                try {
	                	// Create an instance of the specified type
						return type.newInstance();
					} catch (InstantiationException e) {
						throw new RuntimeException(e);
					} catch (IllegalAccessException e) {
						throw new RuntimeException(e);
					}
	        }
	    };
	}
}