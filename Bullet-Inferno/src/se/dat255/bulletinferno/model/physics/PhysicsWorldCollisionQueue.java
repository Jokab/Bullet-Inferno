package se.dat255.bulletinferno.model.physics;

import com.badlogic.gdx.physics.box2d.ContactListener;

/**
 * A collision queue with Box2D collision listener support.
 */
public interface PhysicsWorldCollisionQueue extends
		Iterable<PhysicsWorldCollisionQueue.Entry>, ContactListener {

	/**
	 * The queue entry types (tuple-like).
	 */
	public interface Entry {

		/**
		 * Returns one of the objects that collided. There is no ordering, so knowing if a given
		 * element will turn up as object A or object B is not possible.
		 * 
		 * @return one of the objects that collided.
		 */
		public Collidable getCollidableA();

		/**
		 * Returns one of the objects that collided. There is no ordering, so knowing if a given
		 * element will turn up as object A or object B is not possible.
		 * 
		 * @return one of the objects that collided.
		 */
		public Collidable getCollidableB();

	}

	/**
	 * Removes all entries currently in the queue.
	 */
	public void removeAll();

}