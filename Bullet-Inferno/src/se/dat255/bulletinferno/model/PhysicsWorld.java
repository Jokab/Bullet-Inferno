package se.dat255.bulletinferno.model;

import se.dat255.bulletinferno.util.Disposable;

import com.badlogic.gdx.math.Vector2;

public interface PhysicsWorld extends Disposable {

	/**
	 * Creates a PhysicsBody from the body definition, placing it at the
	 * specified position. <strong>Do not place objects at the same place (e.g. origin)
	 * and move them later, as bad performance will follow.</strong>
	 * 
	 * <p>
	 * The body definitions position will be set to the position. Note that
	 * while the object is manipulated, its other properties can be re-used for
	 * performance reasons. The position is explicitly set here to make sure
	 * unexpected behavior will not follow from this issue.
	 * 
	 * @param definition the body mold to use for creating the body.
	 * @param collidable the element that will be called when collision detection occurs.
	 * @param position the initial position of the body in world coordinates. See notice above!
	 */
	public PhysicsBody createBody(PhysicsBodyDefinition definition, Collidable collidable,
			Vector2 position);

	/**
	 * Removes the specified body from the world.
	 * <strong>Is only allowed to be called once on each body</strong>
	 * @param body
	 */
	public void removeBody(PhysicsBody body);
	
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

	/**
	 * Set the viewport for the physics world (must be set for viewport intersection detection).
	 * 
	 * @param viewportPosition
	 *        the center-position i world coordinates for the viewport.
	 * @param viewportDimension
	 *        the dimensions of the viewport in world coordinates (width, height).
	 */
	public void setViewport(Vector2 viewportPosition, Vector2 viewportDimension);

}