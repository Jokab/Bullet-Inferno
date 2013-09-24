package se.dat255.bulletinferno.model;

/**
 * Collision detection event callback for a body in the physics world that can collide.
 * 
 * <p>preCollided/postCollided is called on two objects A, B (you cannot rely on the order) -
 * possibly multiple times - if an object might have collided. "Might have collided" means that if
 * some object in its collision handler removes a body from the world (which is OK to do), that body
 * might still get several collision event callback calls. Also, there is no way to stop this
 * process. The advice is that implementations keep track of this internally.
 */
public interface Collidable {
	
	/**
	 * Called before postCollided for each collision pair (can be called multiple times). Please see
	 * the documentation for {@link Collidable} for caveats about this process!
	 * 
	 * <p>Useful for getting information from another body (e.g. decrease health if other not dead).
	 * 
	 * @param other the object that a collision (might) have occured with.
	 */
	public void preCollided(Collidable other);
	
	/**
	 * Called after preCollided for each collision pair (can be called multiple times). Please see
	 * the documentation for {@link Collidable} for caveats about this process!
	 * 
	 * <p>Useful for setting properties on this object (e.g. set dead flag).
	 * 
	 * @param other the object that a collision (might) have occured with.
	 */
	public void postCollided(Collidable other);
	
}