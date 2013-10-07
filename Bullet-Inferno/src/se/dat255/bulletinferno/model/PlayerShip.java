package se.dat255.bulletinferno.model;

import se.dat255.bulletinferno.util.ResourceIdentifier;


public interface PlayerShip extends Ship, ResourceIdentifier {

	/**
	 * Delegates to the PlayerShip's weapon to fire a projectile.
	 * 
	 */
	public void fireWeapon();

	// TODO: javadoc this in some good way


	/**
	 * Makes the PlayerShip move the specified distance on the y-axis
	 * 
	 * @param dy distance in y
	 */
	public void moveY(float dy);
	
	/**
	 * Makes the PlayerShip move the specified distance on the y-axis, with
	 * a specified scale. For example scale = 0.5 only moves the ship half the
	 * distance specified, and scale = 2 twice as long
	 * @param dy distance in y
	 * @param scale
	 */
	public void moveY(float dy, float scale);

	/**
	 * Returns the Weapon that is currently in the PlayerShip's Loadout.
	 * 
	 * @return The Weapon.
	 */
	public Weapon getWeapon();

	/**
	 * Returns the Loadout that the PlayerShip is currently equipped with.
	 * 
	 * @return The Loadout
	 */
	public Loadout getLoadout();

	/**
	 * Attaches a PassiveAbility to the PlayerShip. This ability is constantly active, and in
	 * essence
	 * it modifies some attribute (field) of the PlayerShip, or an attribute of one of its fields.
	 * 
	 * @param passiveAbility
	 *        The PassiveAbility to be set.
	 */
	public void attachPassive(PassiveAbility passiveAbility);

	/**
	 * Sets the modifier for taking damage to the specified value.
	 * 
	 * @param takeDamageModifier
	 *        The modifier value.
	 */
	void setTakeDamageModifier(float takeDamageModifier);

	/**
	 * Sets the velocity to 0
	 * 
	 * @param speed
	 */
	public void halt();
	
	
	/**
	 * Restores speed to what it was before
	 * Used mainly for starting movement after defeating a boss
	 */
	public void restoreSpeed();
}
