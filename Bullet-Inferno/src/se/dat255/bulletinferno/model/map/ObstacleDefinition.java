package se.dat255.bulletinferno.model.map;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Obstacle;

/**
 * A definition of an obstacle type.
 */
public interface ObstacleDefinition {
	
	/**
	 * Create a new obstacle in the Game supplided.
	 * 
	 * @param game the Game instance to create the Obstacle in.
	 * @param position the world-coordinates the Obstacle will be placed at, in the physics world.
	 * @return a new Obstacle.
	 */
	public Obstacle createObstacle(Game game, Vector2 position);
	
}
