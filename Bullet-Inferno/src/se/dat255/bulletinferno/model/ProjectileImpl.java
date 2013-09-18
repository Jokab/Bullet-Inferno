package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;

public class ProjectileImpl implements Projectile {
	private int damage;
	private final Game world;

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
		// TODO Call for removing bullet
	}
}
