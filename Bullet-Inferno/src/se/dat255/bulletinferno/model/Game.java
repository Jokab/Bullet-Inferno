package se.dat255.bulletinferno.model;

import java.util.List;

public interface Game {
	/**
	 * Returns a list of all Collidable objects.
	 * 
	 * @return A list with Collidable objects.
	 */
	public List<? extends Collidable> getCollidables();

	/**
	 * Returns a list of all the Projectiles.
	 * 
	 * @return A list of all the Projectiles.
	 */
	public List<? extends Projectile> getProjectiles();

	/**
	 * Returns the PlayerShip that is currently in play.
	 * 
	 * @return The PlayerShip currently in play.
	 */
	public PlayerShip getPlayerShip();

	/**
	 * Returns a list of all the obstacles.
	 * 
	 * @return A list of all the obstacles.
	 */
	public List<? extends Obstacle> getObstacles();

	/**
	 * Returns a list of all the Enemies.
	 * 
	 * @return A list of all the Enemies.
	 */
	public List<? extends Enemy> getEnemies();
}
