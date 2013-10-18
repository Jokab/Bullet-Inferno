package se.dat255.bulletinferno.model.weapon;

import se.dat255.bulletinferno.model.physics.Collidable;
import se.dat255.bulletinferno.model.physics.PhysicsBody;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.physics.PhysicsViewportIntersectionListener;
import se.dat255.bulletinferno.model.team.Teamable;

import com.badlogic.gdx.math.Vector2;

/**
 * Implementation of a Projectile. 
 */
public class ProjectileImpl implements Projectile,
		PhysicsViewportIntersectionListener {

	private PhysicsBody body = null;

	private float damage;
	private Teamable source = null;
	private ProjectileDefinition projectileType;

	/** The PhysicsEnvironment instance injected at construction. */
	private final PhysicsEnvironment physics;

	/** The EntityEnvironment instance injected at construction. */
	private final WeaponEnvironment weapons;

	/**
	 * A task that when added to the Game's runLater will remove this projectile. Used to no modify
	 * the physics world during a simulation.
	 */
	private final Runnable removeSelf = new Runnable() {
		@Override
		public void run() {
			weapons.disposeProjectile(ProjectileImpl.this);
		}
	};

	/**
	 * Constructs a new ProjectileDefinitionImpl.
	 * 
	 * @param physicsEnvironment
	 * @param weaponEnvironment
	 */
	public ProjectileImpl(PhysicsEnvironment physicsEnvironment,
			WeaponEnvironment weaponEnvironment) {
		physics = physicsEnvironment;
		weapons = weaponEnvironment;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(ProjectileDefinition type, Vector2 origin, Vector2 velocity, float damage,
			Teamable source, PhysicsBodyDefinition bodyDefinition) {
		projectileType = type;
		this.damage = damage;
		this.source = source;
		body = physics.createBody(bodyDefinition, this, origin);

		// Check if there is a movement pattern to attach
		if (type.getMovementPattern() != null) {
			physics.attachMovementPattern(type.getMovementPattern().copy(), body);
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
				weapons.disposeProjectile(this);
			}
		}
	}

	private boolean shouldCollide(Collidable other) {
		return damage > 0 && !(other instanceof Projectile) && other != getSource()
				&& (!(other instanceof Teamable) || !getSource().isInMyTeam((Teamable) other));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reset() {
		physics.removeBody(body);
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
		// Check if the projectile has any damage left, i.e. if it has already
		// exploded (only happens in rare cases on the same frame as collided),
		// if so, explode and remove
		if (damage > 0) {
			physics.runLater(removeSelf);
			damage = 0;
		}
	}

	@Override
	public ProjectileDefinition getType() {
		return projectileType;
	}

	@Override
	public Vector2 getDimensions() {
		return body.getDimensions();
	}

}