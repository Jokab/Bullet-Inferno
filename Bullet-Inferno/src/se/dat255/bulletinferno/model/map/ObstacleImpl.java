package se.dat255.bulletinferno.model.map;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.Collidable;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Obstacle;
import se.dat255.bulletinferno.model.PhysicsBody;
import se.dat255.bulletinferno.model.PhysicsBodyDefinition;

/**
 * A standard implementation of an Obstacle, built by a pre-existing physics body. Use an
 * appropriate ObstacleDefinition to get help creating the Obstacle in an easy way.
 * 
 * @see ObstacleDefinition
 */
public class ObstacleImpl implements Obstacle {

	private final PhysicsBody body;

	/**
	 * Construct a new ObstacleImpl with a body definition and position. The Obstacle will be added
	 * to the physics world simulation.
	 * 
	 * @param game the game in which the obstacle will operate.
	 * @param bodyDefinition a body definition the obstacle will use for its body.
	 * @param position the world-position of the obstacle.
	 */
	public ObstacleImpl(Game game, PhysicsBodyDefinition bodyDefinition, Vector2 position) {
		body = game.getPhysicsWorld().createBody(bodyDefinition, this, position);
	}

	@Override
	public void preCollided(Collidable other) {
		// NOP
	}

	@Override
	public void postCollided(Collidable other) {
		// NOP
	}

}