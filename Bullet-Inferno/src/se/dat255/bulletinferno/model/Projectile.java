package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public interface Projectile extends Collidable, Poolable {
	/**
	 * Returns the damage of the Projectile.
	 * 
	 * @return damage The damage that this Projectile deals.
	 */
	public float getDamage();

	/**
	 * Initializes the projectile. Call upon acquiring from the Pool.
	 * 
	 * @param position the initial position.
	 * @param velocity the initial velocity.
	 * @param damage the projectile damage coefficient.
	 */
	public void init(Vector2 position, Vector2 velocity, float damage);
	
	/**
	 * Sets the velocity of the projectile
	 * 
	 * @param velocity
	 */
	public void setVelocity(Vector2 velocity);

	/**
	 * Gets the position of the projectile
	 */
	public Vector2 getPosition();

}