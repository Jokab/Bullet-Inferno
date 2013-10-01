package se.dat255.bulletinferno.model;

import java.util.List;

import se.dat255.bulletinferno.util.Disposable;
import se.dat255.bulletinferno.util.Timer;
import se.dat255.bulletinferno.view.MockSegment;

public interface Game extends Disposable {
	/**
	 * Returns a list of all projectiles in the game
	 * 
	 * @return a list of projectiles currently in the game.
	 */
	public List<? extends Projectile> getProjectiles();

	/**
	 * Retrieve a projectile
	 */
	public Projectile retrieveProjectile(Class<? extends Projectile> type);

	/**
	 * Dispose of the specified projectile
	 * 
	 * @param projectile The projectile reference to dispose.
	 */
	public void disposeProjectile(Projectile projectile);

	/**
	 * Sets a reference to the player's ship, for use in update methods.
	 *
	 */
	public void setPlayerShip(PlayerShip ship);

	/**
	 * Returns the player's ship
	 * 
	 * @return The player's ship.
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
	 * @param emeny The enemy to be added to the game world.
	 */
	public void addEnemy(Enemy emeny);

	/**
	 * Dispose of the specified enemy from the game
	 * 
	 * @param enemy The enemy to be disposed of.
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
	 * @param delta Time elapsed in seconds since the last update.
	 */
	public void update(float delta);

	/**
	 * @return the physics world (simulation object) used for the current
	 *         physics game models.
	 */
	public PhysicsWorld getPhysicsWorld();
	
	
	
	public void addSegment(MockSegment seg);
	
	public void removeSegment(MockSegment seg);
	
	public List<MockSegment> getSegments();
	

}


