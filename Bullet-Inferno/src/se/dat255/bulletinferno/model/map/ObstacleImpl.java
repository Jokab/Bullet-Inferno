package se.dat255.bulletinferno.model.map;

import se.dat255.bulletinferno.model.physics.Collidable;
import se.dat255.bulletinferno.model.physics.PhysicsBody;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;

import com.badlogic.gdx.math.Vector2;

/**
 * A standard implementation of an Obstacle, built by a pre-existing physics body. Use an
 * appropriate ObstacleDefinition to get help creating the Obstacle in an easy way.
 * 
 * @see ObstacleDefinition
 */
public class ObstacleImpl implements Obstacle {

	/** The physics body used by this Obstacle. */
	private PhysicsBody body;
	
	/** The Physics instance this Obstacle belongs to. */
	private final PhysicsEnvironment physics;

	/**
	 * Construct a new ObstacleImpl with a body definition and position. The Obstacle will be added
	 * to the physics world simulation.
	 * 
	 * @param physics the game in which the Obstacle will operate.
	 * @param bodyDefinition a body definition the Obstacle will use for its body.
	 * @param position The world-coordinates the Obstacle will be placed at in the physics world.
	 */
	public ObstacleImpl(PhysicsEnvironment physics, PhysicsBodyDefinition bodyDefinition, Vector2 position) {
		this.physics = physics;
		body = physics.createBody(bodyDefinition, this, position);
	}

	@Override
	public void preCollided(Collidable other) {
		// NOP
	}

	@Override
	public void postCollided(Collidable other) {
		// NOP
	}

	@Override
	public void dispose() {
		physics.removeBody(body);
		body = null;
	}

}