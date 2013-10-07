package se.dat255.bulletinferno.model.entity;

import java.util.List;

import se.dat255.bulletinferno.model.weapon.Projectile;


public interface EntityEnvironment {

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
	 * @param projectile
	 *        The projectile reference to dispose.
	 */
	public void disposeProjectile(Projectile projectile);
	
	/**
	 * Adds the specified enemy to the game
	 * 
	 * @param emeny
	 *        The enemy to be added to the game world.
	 */
	public void addEnemy(Enemy emeny);

	/**
	 * Returns a list of all enemies in the game
	 * 
	 * @return enemies
	 */
	public List<? extends Enemy> getEnemies();

	/**
	 * Returns the player's ship
	 * 
	 * @return The player's ship.
	 */
	public PlayerShip getPlayerShip();

	/**
	 * Dispose of the specified enemy from the game
	 * 
	 * @param enemy
	 *        The enemy to be disposed of.
	 */
	public void removeEnemy(Enemy enemy);

}
