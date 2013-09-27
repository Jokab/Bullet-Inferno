package se.dat255.bulletinferno.model.physics;

import se.dat255.bulletinferno.model.PhysicsBody;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Standard implementation of a simulated PhysicsBody.
 */
public class PhysicsBodyImpl implements PhysicsBody {

	/** The Box2D body wrapped by this object. */
	private final Body body;

	/**
	 * Constructs a new PhysicsBody from the Box2D body supplied.
	 * 
	 * @param definition
	 *        a PhysicsBodyDefiniton to inherit properties from.
	 */
	public PhysicsBodyImpl(Body body) {
		this.body = body;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector2 getPosition() {
		return body.getPosition();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setVelocity(Vector2 velocity) {
		body.setLinearVelocity(velocity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector2 getVelocity() {
		return body.getLinearVelocity();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Body getBox2DBody() {
		return body;
	}

}