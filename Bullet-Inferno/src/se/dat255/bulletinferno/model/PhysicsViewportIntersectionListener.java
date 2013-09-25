package se.dat255.bulletinferno.model;

/**
 * A Collidable can also be a PhysicsViewportIntersectionListener, which means it is notified when
 * when is appears and is moved outside the viewport. Collidables just have to implement this
 * interface in order to get these events by the physics system. As long as the collidable is added
 * to the physics world, it will continue to get these updates. This is a bit magic and might seem
 * confusing, but it has to be this way with the current physics backend.
 */
public interface PhysicsViewportIntersectionListener extends Collidable {
	/**
	 * Called when the object appears inside the viewport. Never not modify the physics world inside
	 * this method! The recommended approach is to schedule such a task for later execution outside
	 * the physics stepping (outside this method).
	 * 
	 * <p>
	 * This method may be called multiple times without viewportIntersectionEnd() being called. This
	 * should be interpreted as if the intersection still is ongoing.
	 */
	public void viewportIntersectionBegin();

	/**
	 * Called when the object is outside the viewport. Never not modify the physics world inside
	 * this method! The recommended approach is to schedule such a task for later execution outside
	 * the physics stepping (outside this method).
	 * 
	 * <p>
	 * This method will only be called once, and that after a call (or sequence of calls) to
	 * viewportIntersectionBegin().
	 */
	public void viewportIntersectionEnd();
}