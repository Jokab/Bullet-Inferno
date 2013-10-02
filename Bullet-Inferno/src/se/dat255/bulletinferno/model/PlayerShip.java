package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;

public interface PlayerShip extends Ship, ResourceIdentifier {

	/**
	 * Delegates to the PlayerShip's weapon to fire a projectile.
	 * 
	 */
	public void fireWeapon();

	// TODO: javadoc this in some good way
	public void update(float deltaTime);

	/**
	 * Sets the PlayerShip's position to the provided position.
	 * 
	 * @param position
	 *        The position to be set.
	 */
	void setPosition(Vector2 position);

	/**
	 * Makes the PlayerShip move to the specified position.
	 * 
	 * @param yPos
	 *        The position that the ship should move to.
	 */
	public void moveTo(float yPos);

	/**
	 * Stops the PlayerShip's movement.
	 */
	public void stopMovement();

	/**
	 * Returns the position that the PlayerShip is currently moving to.
	 * 
	 * @return The position.
	 */
	public float getMovePos();

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
}
