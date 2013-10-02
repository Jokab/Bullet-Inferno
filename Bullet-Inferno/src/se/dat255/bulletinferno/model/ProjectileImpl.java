package se.dat255.bulletinferno.model;

import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinitionImpl;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;

public class ProjectileImpl implements Projectile, PhysicsViewportIntersectionListener {

	private static PhysicsBodyDefinition bodyDefinition = null;

	private PhysicsBody body = null;

	private float damage;
	private Teamable source = null;
	private final Game game;

	/**
	 * A task that when added to the Game's runLater will remove this projectile. Used to no modify
	 * the physics world during a simulation.
	 */
	private Runnable removeSelf = new Runnable() {
		@Override
		public void run() {
			game.disposeProjectile(ProjectileImpl.this);
		}
	};

	/**
	 * Constructs a new projectile
	 * 
	 * @param game
	 *        the game instance.
	 */
	public ProjectileImpl(Game game) {
		this.game = game;
		if (bodyDefinition == null) {
			Shape shape = game.getPhysicsWorld().getShapeFactory().getRectangularShape(0.1f, 0.1f);
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
	public void init(Vector2 origin, Vector2 velocity, float damage, Teamable source,
			PhysicsMovementPattern pmp) {
		this.damage = damage;
		this.source = source;
		body = game.getPhysicsWorld().createBody(bodyDefinition, this, origin);

		// Check if there is a movement pattern to attach
		if (pmp != null) {
			game.getPhysicsWorld().attachMovementPattern(pmp.copy(), body);
		}

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
		if (shouldCollide(other)) {
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

	private boolean shouldCollide(Collidable other) {
		return ((damage > 0 && !(other instanceof Projectile) && other != getSource())
				&& (!(other instanceof Teamable) || !getSource().isInMyTeam((Teamable) other)));
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
		game.runLater(removeSelf);
	}

}
