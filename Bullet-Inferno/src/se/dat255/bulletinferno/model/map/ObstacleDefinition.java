package se.dat255.bulletinferno.model.map;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Obstacle;

/**
 * A definition of an Obstacle type.
 */
public interface ObstacleDefinition {

	/**
	 * Creates a new obstacle in the supplied Game instance.
	 * 
	 * @param game
	 *        The Game instance to create the Obstacle in.
	 * @param position
	 *        The world-coordinates the Obstacle will be placed at in the physics world.
	 * @return a new Obstacle.
	 */
	public Obstacle createObstacle(Game game, Vector2 position);

}
