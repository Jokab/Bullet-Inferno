package se.dat255.bulletinferno.model;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.map.Segment;
import se.dat255.bulletinferno.util.Disposable;
import se.dat255.bulletinferno.util.Timer;

public interface Game extends Disposable {
	/**
	 * Returns a list of all projectiles in the game
	 * 
	 * @return
	 */
	public List<? extends Projectile> getProjectiles();

	/**
	 * Retrieve a projectile
	 */
	public Projectile retrieveProjectile(Class<? extends Projectile> type);

	/**
	 * Dispose of the specified projectile
	 * 
	 * @param projectile
	 */
	public void disposeProjectile(Projectile projectile);

	/**
	 * Sets a reference to the player's ship, for use in update methods.
	 * 
	 * @return
	 */
	public void setPlayerShip(PlayerShip ship);

	/**
	 * Returns the player's ship
	 * 
	 * @return
	 */
	public PlayerShip getPlayerShip();

	/**
	 * Returns a list of all the obstacles.
	 * 
	 * @return A list of all the obstacles.
	 */
	public List<? extends Obstacle> getObstacles();

	/**
	 * Returns a list of all enemies in the game
	 * 
	 * @return enemies
	 */
	public List<? extends Enemy> getEnemies();

	/**
	 * Adds the specified enemy to the game
	 * 
	 * @param emeny
	 */
	public void addEnemy(Enemy emeny);

	/**
	 * Dispose of the specified enemy from the game
	 * 
	 * @param enemy
	 */
	public void removeEnemy(Enemy enemy);

	/**
	 * Returns a new timer
	 * 
	 * @return timer
	 */
	public Timer getTimer();

	/**
	 * Updates the game
	 * 
	 * @param delta
	 */
	public void update(float delta);

	/**
	 * @return the physics world (simulation object) used for the current
	 *         physics game models.
	 */
	public PhysicsWorld getPhysicsWorld();

	/**
	 * @return a list of all the active segments in the world.
	 */
	public List<? extends Segment> getSegments();

	/**
	 * @return the number of segments that has been removed from the segment list since the game
	 *         started. Removes only happen from the beginning of the segment list.
	 */
	public int getRemovedSegmentCount();
	
	/**
	 * Sets the viewport the Game happens in. Used as an optimization to not run the game in areas
	 * that is not within (wholly or partially), or near, the viewport.
	 * 
	 * @param viewportPosition
	 *        the center-position i world coordinates for the viewport.
	 * @param viewportDimensions
	 *        the dimensions of the viewport in world coordinates (width, height).
	 */
	public void setViewport(Vector2 viewportPosition, Vector2 viewportDimensions);

}