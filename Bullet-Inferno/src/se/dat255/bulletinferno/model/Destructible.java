package se.dat255.bulletinferno.model;

import se.dat255.bulletinferno.util.Disposable;

public interface Destructible extends Teamable, Disposable {
	/**
	 * Subtracts the object's health with the taken damage.
	 * 
	 * @param damage
	 *        The damage to be dealt.
	 */
	public void takeDamage(float damage);

	/**
	 * Returns the object's current health.
	 * 
	 * @return The current health.
	 */
	public int getHealth();

	/**
	 * Returns the object's initial health. That is, the health that the object
	 * started out with when it was created.
	 * 
	 * @return The initial health.
	 */
	public int getInitialHealth();
	
	/**
	 * Returns true if the enemy's health is below or equal to 0.
	 * 
	 * @return If the enemy is dead.
	 */
	public boolean isDead();
}
