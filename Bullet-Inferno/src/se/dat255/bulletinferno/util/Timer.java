package se.dat255.bulletinferno.util;

public interface Timer {
	/**
	 * Returns the time left until finish
	 * 
	 * @return time left
	 */
	public float getTimeLeft();

	/**
	 * Returns if the timer is finished
	 * 
	 * @return is finished
	 */
	public boolean isFinished();

	/**
	 * Returns the initial value of the timer
	 * 
	 * @return initial value
	 */
	public float getInitialValue();

	/**
	 * Sets the time to count down and <strong>stops</strong> the timer
	 * 
	 * @param time The time to set the timer for.
	 */
	public void setTime(float time);

	/**
	 * Starts the timer
	 */
	public void start();

	/**
	 * Stops the timer, i.e. <strong>sets the time left to 0</strong>
	 */
	public void stop();

	/**
	 * Pauses the timer
	 */
	public void pause();

	/**
	 * <strong>Stops</strong> the timer and resets it to it's initial value
	 */
	public void reset();

	/**
	 * Resets the timer and <strong>starts</strong> it.
	 */
	public void restart();

	/**
	 * Returns whether the timer is continuous
	 * 
	 * @return is continuous
	 */
	public boolean isContinuous();

	/**
	 * Set if the timer should be continuous (loop). Affect goes in place on
	 * timeout
	 * 
	 * @param continuous If the timer should be continuous.
	 */
	public void setContinuous(boolean continuous);

	/**
	 * Registers specified listener to the timer, if it isn't already registered
	 * 
	 * @param listener The listener that should be registered.
	 */
	public void registerListener(Timerable listener);

	/**
	 * unregisters the specified listener from the timer
	 * 
	 * @param listener The listener that should be unregistered.
	 */
	public void unregisterListener(Timerable listener);

	/**
	 * Updates the time
	 * 
	 * @param delta
	 *        time passed since last call in seconds
	 */
	public void update(float delta);
}
