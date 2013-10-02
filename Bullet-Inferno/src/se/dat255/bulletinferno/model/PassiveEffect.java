package se.dat255.bulletinferno.model;

/**
 * A class that is used to specify the behavior of a PassiveAbility, which is constantly active
 * during a given game.
 * 
 */
public interface PassiveEffect {

	/**
	 * Applies the effect to the PlayerShip. See concrete implementations for examples.
	 * 
	 * @param playerShip
	 *        The PlayerShip.
	 */
	public void applyEffect(PlayerShip playerShip);

	/**
	 * Removes (reverts) the effect from the PlayerShip.
	 * 
	 * @param playerShip
	 *        The PlayerShip.
	 */
	public void removeEffect(PlayerShip playerShip);

}
