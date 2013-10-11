package se.dat255.bulletinferno.model.entity;

import java.util.List;


public interface EntityEnvironment {
	
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