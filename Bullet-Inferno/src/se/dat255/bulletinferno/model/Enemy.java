package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;

public interface Enemy {
	
	/**
	 * Returns the enemy's velocity.
	 * 
	 * @return The velocity.
	 */
	Vector2 getVelocity();
	
	/**
	 * Sets the enemy's velocity.
	 * 
	 * @param velocity The velocity to be set.
	 */
	void setVelocity(Vector2 velocity);
	
	/**
	 * Returns the enemy's position.
	 * 
	 * @return The position.
	 */
	Vector2 getPosition();
	
	/**
	 * Sets the enemy's position.
	 * 
	 * @param position The position to be set.
	 */
	void setPosition(Vector2 position);
	
	/**
	 * Returns the enemy's hit points.
	 * 
	 * @return The hit points.
	 */
	int getHitPoints();
	
	/**
	 * Sets the enemy's hit points.
	 * 
	 * @param hitpoints The hit points to be set.
	 */
	void setHitPoints(int hitpoints);

	/**
	 * {@inheritDoc}
	 * 
	 */
	// TODO: I expect this to be added in a superclass/superinterface.
	void update(float deltaTime);
}
