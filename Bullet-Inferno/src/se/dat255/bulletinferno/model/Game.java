package se.dat255.bulletinferno.model;

import java.util.List;

public interface Game {
	public List<? extends Projectile> getProjectiles();

	public PlayerShip getPlayerShip();

	public List<? extends Obstacle> getObstacles();

	public List<? extends Enemy> getEnemies();
	
	/**
	 * Adds the specified projectile to the world
	 * @param projectile
	 */
	public void addProjectile(Projectile entity);
}
