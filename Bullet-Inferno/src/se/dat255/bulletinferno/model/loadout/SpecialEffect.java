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

}
