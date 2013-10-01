package se.dat255.bulletinferno.model;

import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinitionImpl;
import se.dat255.bulletinferno.util.PhysicsShapeFactory;
import se.dat255.bulletinferno.util.Timer;
import se.dat255.bulletinferno.util.Timerable;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;

public class ProjectileImpl implements Projectile, PhysicsViewportIntersectionListener {

	private static PhysicsBodyDefinition bodyDefinition = null;

	private PhysicsBody body = null;

	private float damage;
	private Teamable source = null;
	private final Game game;

	/** An instance of a timer to help "running things later", i.e. on the next timestep. */
	private final Timer runLater;

	/**
	 * Schedule an instance of this class to a timer and it will remove this projectile when the
	 * timer calls it. This class takes care of unregistering itself on the timer when run.
	 */
	public class RemoveThisProjectileTimerable implements Timerable {
		@Override
		public void onTimeout(Timer source, float timeSinceLast) {
			game.disposeProjectile(ProjectileImpl.this);

			source.unregisterListener(this);
		}
	}

	/**
	 * Constructs a new projectile
	 * 
	 * @param world
	 */
	public ProjectileImpl(Game game) {
		this.game = game;
		runLater = game.getTimer();
		if (bodyDefinition == null) {
			Shape shape = PhysicsShapeFactory.getRectangularShape(0.1f, 0.1f);
			bodyDefinition = new PhysicsBodyDefinitionImpl(shape, false);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(Vector2 origin, Vector2 velocity, float damage, Teamable source) {
		this.damage = damage;
		this.source = source;
		body = game.getPhysicsWorld().createBody(bodyDefinition, this, origin);

		this.setVelocity(velocity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getDamage() {
		return damage;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void preCollided(Collidable other) {
		// NOP
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void postCollided(Collidable other) {
		// Decrease the damage after hits, since we must let the other object that collided with us
		// decide if they want to take our current damage (etc.) before we zero it.
		if (damage > 0 && !(other instanceof Projectile) && other != getSource()) {
			if (!(other instanceof Teamable) || !getSource().isInMyTeam((Teamable) other)) {
				damage = 0;

				// Note: Do not move this out of here - this must be called only once, and that is
				// when
				// damage reaches 0. (Calling it twice will give you hard to debug segfaults.)
				if (damage <= 0) {
					// We won't need this projectile anymore, since it is useless and can't hurt
					// anyone.
					game.disposeProjectile(this);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reset() {
		game.getPhysicsWorld().removeBody(body);
		body = null;
		source = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setVelocity(Vector2 velocity) {
		body.setVelocity(velocity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector2 getPosition() {
		return body.getPosition();
	}

	@Override
	public Teamable getSource() {
		return source;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void viewportIntersectionBegin() {
		// NOP
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void viewportIntersectionEnd() {
		// Run later as we are not allowed to alter the world here.
		runLater.registerListener(new RemoveThisProjectileTimerable());
		runLater.start();
	}

}
