package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Simulates the world dynamics and handles collision detection.
 */
public class PhysicsWorldImpl implements PhysicsWorld {

	/** The discrete time step to take each update. */
	private static float TIME_STEP = 1 / 60f;

	/**
	 * The number of velocity iterations per update (performance vs. accuracy).
	 * See Box2D docs.
	 */
	private static int VELOCITY_ITERATIONS = 8;

	/**
	 * The number of position iterations per update (performance vs. accuracy).
	 * See Box2D docs.
	 */
	private static int POSITION_ITERATIONS = 3;

	/**
	 * Accumulates time that was not simulated the last update, since it didn't
	 * fit into TIME_STEP.
	 */
	private float timeStepAccumulator = 0f;

	/** Holds the Box2D world. */
	private final World world;

	/**
	 * Holds a shape factory instance. Creates Box2D shape objects in various
	 * ways.
	 */
	private final PhysicsShapeFactory shapeFactory = new PhysicsShapeFactoryImpl();
	
	private final PhysicsWorldCollisionQueue collisionQueue = new PhysicsWorldCollisionQueueImpl();
	
	/**
	 * Start the simulation.
	 */
	public PhysicsWorldImpl() {
		// Initialize world with 0 gravity and all objects non-sleeping. They
		// cannot sleep as we
		// move them manually (otherwise Box2D will make them sleep, possibly
		// with odd behavior).
		world = new World(new Vector2(0f, 0f), false);
		
		// Collision detect.
		world.setContactListener(collisionQueue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PhysicsBody createBody(PhysicsBodyDefinition definition, Collidable collidable,
			Vector2 position) {
		BodyDef bodyDef = definition.getBox2DBodyDefinition();

		bodyDef.position.set(position);
		Body body = world.createBody(bodyDef);
		body.createFixture(definition.getBox2DFixtureDefinition());
		body.setUserData(collidable);
		return new PhysicsBodyImpl(body);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeBody(PhysicsBody body) {
		world.destroyBody(body.getBox2DBody());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(float deltaTime) {
		timeStepAccumulator += deltaTime;

		// Take discrete steps of TIME_STEP, sometimes even multiple of them (to
		// keep up).
		for (; timeStepAccumulator > TIME_STEP; timeStepAccumulator -= TIME_STEP) {
			world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
		}

		// Resolve queued up collisions.
		// Collisions are queued to avoid the problem where the Box2D world cannot be modified
		// within the collision handler (not allowed by Box2D).
		for (PhysicsWorldCollisionQueue.Entry collision : collisionQueue) {
			Collidable collidableA = collision.getCollidableA();
			Collidable collidableB = collision.getCollidableB();

			collidableA.preCollided(collidableB);
			collidableB.preCollided(collidableA);
			collidableA.postCollided(collidableB);
			collidableB.postCollided(collidableA);
		}
		collisionQueue.removeAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PhysicsShapeFactory getShapeFactory() {
		return shapeFactory;
	}
}