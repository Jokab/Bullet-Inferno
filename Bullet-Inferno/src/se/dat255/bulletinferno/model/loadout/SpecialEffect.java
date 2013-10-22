package se.dat255.bulletinferno.model.loadout;

import se.dat255.bulletinferno.model.entity.PlayerShip;

/**
 * SpecialEffect is the effect carried by the SpecialAbility.
 * 
 */
public interface SpecialEffect {

	/**
	 * Carries out the effect, i.e. what the ability should do.
	 * 
	 * @param playerShip
	 *        The current player ship.
	 */
	public void activate(PlayerShip playerShip);

	/**
	 * The Special Effect is ready if it has reloaded and has charges.
	 * 
	 * @return
	 */
	public boolean isReady();

	/**
	 * Returns the amount of charges that are left.
	 * 
	 * @return The amount of charges.
	 */
	public int getCharges();

	/**
	 * Returns the percentage of loading done between 0 and 1, where 1 means the ability is ready.
	 * 
	 * @return percentage loaded, between 0 and 1.
	 */
	float getReadyPercentage();
}
