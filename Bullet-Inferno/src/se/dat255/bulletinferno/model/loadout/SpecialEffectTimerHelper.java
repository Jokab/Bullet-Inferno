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
}