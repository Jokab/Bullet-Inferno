package se.dat255.bulletinferno.model;

import java.util.List;

public interface Game {
	public List<? extends Collidable> getCollidables();

	public List<? extends VelocityEntity> getVelocityEntities();

	public List<? extends AccelerationEntity> getAccelerationEntities();

	public List<? extends Projectile> getProjectiles();

	public PlayerShip getPlayerShip();

	public List<? extends Obstacle> getObstacles();

	public List<? extends Enemy> getEnemies();
}
