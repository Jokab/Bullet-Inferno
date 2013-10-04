package se.dat255.bulletinferno.model;


/**
 * A Loadout is a collection of equipment that the player can carry in any given game. These are
 * set prior to the level starting.
 *
 */
public interface Loadout {
	
	/**
	 * Returns the primary Weapon that is in this Loadout.
	 * 
	 * @return The primary Weapon.
	 */
	public Weapon getPrimaryWeapon();
	
	/**
	 * Returns the heavy Weapon that is in this Loadout.
	 * 
	 * @return The heavy Weapon.
	 */
	public Weapon getHeavyWeapon();
	
	
	/**
	 * Returns the SpecialAbility that is in this Loadout.
	 * 
	 * @return The SpecialAbility.
	 */
	public SpecialAbility getSpecialAbility();

	/**
	 * Returns the PassiveAbility that is in this Loadout.
	 * 
	 * @return The PassiveAbility.
	 */
	public PassiveAbility getPassiveAbility();
}
