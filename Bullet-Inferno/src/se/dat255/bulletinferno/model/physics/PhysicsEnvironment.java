package se.dat255.bulletinferno.model.physics;

import se.dat255.bulletinferno.util.Disposable;
import se.dat255.bulletinferno.util.Timer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public interface PhysicsEnvironment extends Disposable {

	/**
	 * Creates a PhysicsBody from the body definition, placing it at the
	 * specified position. <strong>Do not place objects at the same place (e.g. origin)
	 * and move them later, as bad performance will follow.</strong>
	 * 
	 * <p>
	 * The body definitions position will be set to the position. Note that while the object is
	 * manipulated, its other properties can be re-used for performance reasons. The position is
	 * explicitly set here to make sure unexpected behavior will not follow from this issue.
	 * 
	 * @param definition
	 *        the body mold to use for creating the body.
	 * @param collidable
	 *        the element that will be called when collision detection occurs.
	 * @param position
	 *        the initial position of the body in world coordinates. See notice above!
	 */
	public PhysicsBody createBody(PhysicsBodyDefinition definition, Collidable collidable,
			Vector2 position);

	/**
	 * Removes the specified body from the world.
	 * <strong>Is only allowed to be called once on each body</strong>
	 * 
	 * @param body The body to be removed from the world.
	 */
	public void removeBody(PhysicsBody body);

	/**
	 * Updates the physics simulation (the simulation is time-step based). This
	 * should be called once every frame. (Callers should have to worry about
	 * using a fixed time step, it is required to be handled internally by the
	 * implementation, so that behavior is consistent between runs.)
	 * 
	 * @param deltaTime
	 *        the time in seconds since last call.
	 */
	public void update(float deltaTime);

	/**
	 * Set the viewport for the physics world (must be set for viewport intersection detection).
	 * 
	 * @param viewportPosition
	 *        the center-position i world coordinates for the viewport.
	 * @param viewportDimension
	 *        the dimensions of the viewport in world coordinates (width, height).
	 */
	public void setViewport(Vector2 viewportPosition, Vector2 viewportDimension);

	/**
	 * Attaches a movement pattern to the specified body. <em>NOTE</em> <strong>only one</strong>
	 * movement pattern can be attached to a body
	 * at a time. I.E. this removes any existing movement patterns attached to the specified body.
	 * 
	 * @param pattern
	 *        the attach
	 * @param body
	 *        to attach to
	 */
	public void attachMovementPattern(PhysicsMovementPattern pattern, PhysicsBody body);

	/**
	 * Detaches the movement pattern from the specified body.
	 * 
	 * @param body The body that should have its movement pattern removed.
	 */
	public void detachMovementPattern(PhysicsBody body);
	
	/**
	 * Returns the currently attached movement pattern for the specified body
	 * 
	 * @param body The body whose movement pattern should be checked
	 * @return The currently attached movement pattern
	 */
	public PhysicsMovementPattern getMovementPattern(PhysicsBody body);
	
	/**
	 * Returns a new timer
	 * 
	 * @return timer
	 */
	public Timer getTimer();
	
	/**
	 * Removes a timer from the list of timers.
	 * @param timer the timer instance to be removed
	 */
	public void removeTimer(Timer timer);

	/**
	 * Runs a task at the next update.
	 * 
	 * <p>
	 * This method could for example be used to schedule physics altering operation to after a
	 * physics world update has been completed.
	 * </p>
	 * 
	 * @param task
	 *        The Runnable that should be run.
	 */
	public void runLater(Runnable task);
	
	/**
	 * Returns the box2d world instance currently running 
	 * the game. <strong>To be handled with care</strong>
	 * @return world
	 */
	public World getWorld();
}