package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;

public class ProjectileImpl implements Projectile {
	private int damage;
	private final Game world;
	private final Vector2 velocity = new Vector2();
	private final Vector2 position = new Vector2();
	
	/**
	 * Constructs a new projectile
	 * @param world
	 */
	public ProjectileImpl(Game world) {
		this.world = world;
	}
	
	/**
	 * Initializes the projectile
	 * @param origin position
	 * @param velocity
	 * @param damage
	 */
	public void init(Vector2 origin, Vector2 velocity, int damage) {
		this.damage = damage;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getDamage() {
		return damage;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void collided(Collidable entity) {
		// Code for special behavior here
		
		world.disposeProjectile(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reset() {
		// TODO Reset projectile
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setVelocity(Vector2 velocity) {
		velocity.set(velocity);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPosition(Vector2 position) {
		this.position.set(position);
	}
}
