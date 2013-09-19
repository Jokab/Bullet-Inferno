package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public interface Projectile extends Collidable, Poolable{
	/**
	 * Returns the damage of the Projectile.
	 * 
	 * @return damage The damage that this Projectile deals.
	 */
	public int getDamage();

	/**
	 * Sets the velocity of the projectile
	 * @param velocity
	 */
	public void setVelocity(Vector2 velocity);
	
	/**
	 * Sets the position of the projectile
	 * @param position
	 */
	public void setPosition(Vector2 position);
	
}