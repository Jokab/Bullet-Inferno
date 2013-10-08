package se.dat255.bulletinferno.model.map;

import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;

import com.badlogic.gdx.math.Vector2;

/**
 * A definition of an Obstacle type.
 */
public interface ObstacleDefinition {

	/**
	 * Creates a new obstacle in the supplied Game instance.
	 * 
	 * @param physics
	 *        the {@link PhysicsEnvironment} to run against.
	 * @param position
	 *        The world-coordinates the Obstacle will be placed at in the physics world.
	 * @return a new Obstacle.
	 */
	public Obstacle createObstacle(PhysicsEnvironment physics, Vector2 position);

}
