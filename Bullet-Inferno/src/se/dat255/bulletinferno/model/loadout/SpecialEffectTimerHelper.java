package se.dat255.bulletinferno.model.loadout;

/**
 * Helper class for holding timers pertaining to special effects reloading and
 * "ammo" (charges). {@link #startReloadTimer()} should be called when the
 * effect is activated. {@link #isReady()} should be called to check if
 * it is allowed to activate.
 */
public interface SpecialEffectTimerHelper {

	/**
	 * Starts the reload timer, which is set in the constructor.
	 * 
	 */
	public void startReloadTimer();

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

	/**
	 * Reduces the number of charges by one.
	 */
	void useCharge();
}