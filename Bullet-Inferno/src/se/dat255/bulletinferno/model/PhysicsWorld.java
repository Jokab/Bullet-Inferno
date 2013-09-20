package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;

public interface PhysicsWorld {

	/**
	 * Creates a PhysicsBody from the body definition, placing it at the
	 * specified position. Do not place objects at the same place (e.g. origin)
	 * and move them later, as bad performance will follow.
	 * 
	 * <p>
	 * The body definitions position will be set to the position. Note that
	 * while the object is manipulated, its other properties can be re-used for
	 * performance reasons. The position is explicitly set here to make sure
	 * unexpected behavior will not follow from this issue.
	 */
	public PhysicsBody createBody(PhysicsBodyDefinition definition,
			Vector2 position);

	/**
	 * Updates the physics simulation (the simulation is time-step based). This
	 * should be called once every frame. (Callers should have to worry about
	 * using a fixed time step, it is required to be handled internally by the
	 * implementation, so that behavior is consistent between runs.)
	 * 
	 * @param deltaTime
	 *            the time in seconds since last call.
	 */
	public void update(float deltaTime);

	/**
	 * Get a physics shape factory that can help in Box2D Shape creation.
	 * 
	 * @return a shape factory.
	 */
	PhysicsShapeFactory getShapeFactory();

}