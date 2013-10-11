package se.dat255.bulletinferno.model.weapon;


/**
 * A WeaponLoadout is a collection of weapons that the player can carry in any given game. These are
 * set prior to the level starting.
 */
public interface WeaponLoadout {
	
	/**
	 * Returns the primary Weapon that is in this Loadout.
	 * 
	 * @return The primary Weapon.
	 */
	public Weapon getStandardWeapon();
	
	/**
	 * Returns the heavy Weapon that is in this Loadout.
	 * 
	 * @return The heavy Weapon.
	 */
	public Weapon getHeavyWeapon();
	
}
