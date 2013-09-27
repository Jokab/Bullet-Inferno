package se.dat255.bulletinferno.model.physics;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import se.dat255.bulletinferno.model.Collidable;
import se.dat255.bulletinferno.model.PhysicsWorldCollisionQueue;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Implementation of a collision queue with Box2D collision listener support.
 */
public class PhysicsWorldCollisionQueueImpl implements PhysicsWorldCollisionQueue {

	/**
	 * @see PhysicsWorldCollisionQueue.Entry
	 */
	public class Entry implements PhysicsWorldCollisionQueue.Entry {

		/** One of the objects that collided (no sorted ordering). */
		private final Collidable collidableA;

		/** One of the objects that collided (no sorted ordering). */
		private final Collidable collidableB;

		/**
		 * Constructs an entry for the queue. Do not use this outside PhysicsWorldCollisionQueueImpl
		 * (it is only here for testing to work).
		 * 
		 * @param collidableA
		 * @param collidableB
		 */
		public Entry(Collidable collidableA, Collidable collidableB) {
			this.collidableA = collidableA;
			this.collidableB = collidableB;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Collidable getCollidableA() {
			return collidableA;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Collidable getCollidableB() {
			return collidableB;
		}

	}

	/** The internal queue implementation for occured collisions. */
	private final Queue<PhysicsWorldCollisionQueue.Entry> queue =
			new LinkedList<PhysicsWorldCollisionQueue.Entry>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beginContact(Contact contact) {
		Collidable collidableA = (Collidable) contact.getFixtureA().getBody().getUserData();
		Collidable collidableB = (Collidable) contact.getFixtureB().getBody().getUserData();
		queue.add(new Entry(collidableA, collidableB));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void endContact(Contact contact) {
		// NOP
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// NOP
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// NOP
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<PhysicsWorldCollisionQueue.Entry> iterator() {
		return queue.iterator();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeAll() {
		queue.clear();
	}

}