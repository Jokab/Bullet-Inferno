package se.dat255.bulletinferno.model;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import se.dat255.bulletinferno.model.map.Segment;
import se.dat255.bulletinferno.model.map.SegmentManager;
import se.dat255.bulletinferno.model.map.SegmentManagerImpl;
import se.dat255.bulletinferno.model.physics.PhysicsWorldImpl;
import se.dat255.bulletinferno.util.Timer;
import se.dat255.bulletinferno.util.TimerImpl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

/**
 * Default implementation of Game, the central type in Bullet Inferno.
 * 
 * <p>
 * Game acts as a single point of entry for the outside environment, as well as central point of
 * lookup for the inside. It handles instance-based object creation and initialization (injection).
 */
public class GameImpl implements Game {

	private PhysicsWorld world = new PhysicsWorldImpl();

	private final List<Projectile> projectiles = new ArrayList<Projectile>();
	private final List<Enemy> enemies = new ArrayList<Enemy>();
	private final List<Obstacle> obstacles = new ArrayList<Obstacle>();
	private PlayerShip playerShip;
	private final Map<Class<? extends Projectile>, Pool<Projectile>> projectilePools;
	
	/** The default segment manager used to place segments in the viewport current (remove old). */
	private final SegmentManager segmentManager = new SegmentManagerImpl(this);
	
	/** List of all timers */
	private final List<Timer> timers;
	/** List of all queued timers to be added */
	private final List<Timer> timersAddQueue = new LinkedList<Timer>();
	private boolean isIteratingOverTimers = false;

	/** A list of Runnables that will be run at the next update. */
	private final List<Runnable> runLaters;

	public GameImpl(PhysicsWorld world) {
		this.world = world;
		projectilePools = new HashMap<Class<? extends Projectile>, Pool<Projectile>>();
		timers = new LinkedList<Timer>();
		runLaters = new LinkedList<Runnable>();
	}

	public GameImpl() {
		this(new PhysicsWorldImpl());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPlayerShip(PlayerShip ship) {
		this.playerShip = ship;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerShip getPlayerShip() {
		// TODO Auto-generated method stub
		return playerShip;
	}
	
	@Override
	public void bossDead(){
		Gdx.app.log("Game","Boss dead!");
		playerShip.restoreSpeed();
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
	public void addEnemy(Enemy enemy) {
		enemies.add(enemy);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeEnemy(Enemy enemy) {
		enemies.remove(enemy);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<? extends Segment> getSegments() {
		return segmentManager.getSegments();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRemovedSegmentCount() {
		return segmentManager.getRemovedSegmentCount();
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
		if (!projectilePools.containsKey(type)) {
			projectilePools.put(type, createNewPool(type));
		}

		// Get a projectile from the pool
		Projectile p = projectilePools.get(type).obtain();
		projectiles.add(p);
		return p;
	}

	/** {@inheritDoc} */
	@Override
	public void disposeProjectile(Projectile projectile) {
		projectiles.remove(projectile);
		if (projectilePools.containsKey(projectile.getClass())) {
			projectilePools.get(projectile.getClass()).free(projectile);
		}
	}

	private Pool<Projectile> createNewPool(
			final Class<? extends Projectile> type) {
		return new Pool<Projectile>() {
			@Override
			protected Projectile newObject() {
				try {
					// Create an instance of the specified type
					return type.getConstructor(Game.class).newInstance(
							GameImpl.this);
				} catch (InstantiationException e) {
					throw new RuntimeException(e);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				} catch (NoSuchMethodException e) {
					throw new RuntimeException(e);
				} catch (SecurityException e) {
					throw new RuntimeException(e);
				} catch (IllegalArgumentException e) {
					throw new RuntimeException(e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException(e);
				}
			}
		};
	}

	/** {@inheritDoc} */
	@Override
	public Timer getTimer() {
		Timer t = new TimerImpl();

		if (isIteratingOverTimers) {
			timersAddQueue.add(t);
		} else {
			timers.add(t);
		}

		return t;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(float deltaTime) {
		// Update timers, set iterator flag
		// to indicate that no one is allowed to modify list
		isIteratingOverTimers = true;
		for (Timer t : timers) {
			t.update(deltaTime);
		}
		// If timers are waiting to be added, add them
		if (!timersAddQueue.isEmpty()) {
			timers.addAll(timersAddQueue);
			timersAddQueue.clear();
		}
		isIteratingOverTimers = false;

		// Run all runLater Runnables from the previous tick.
		for (Runnable task : runLaters) {
			task.run();
		}
		runLaters.clear();

		world.update(deltaTime);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PhysicsWorld getPhysicsWorld() {
		return world;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		world.dispose();
	}

	@Override
	public void runLater(Runnable task) {
		runLaters.add(task);
	}	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setViewport(Vector2 viewportPosition, Vector2 viewportDimensions) {
		world.setViewport(viewportPosition, viewportDimensions);
		segmentManager.setViewport(viewportPosition, viewportDimensions);
	}

}