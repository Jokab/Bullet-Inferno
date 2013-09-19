package se.dat255.bulletinferno.model;

public interface Timer {
	/**
	 * Returns the time left until finish
	 * @return time left
	 */
	public float getTimeLeft();
	
	/**
	 * Returns if the timer is finished
	 * @return is finished
	 */
	public boolean isFinished();
	
	/**
	 * Returns the initial value of the timer
	 * @return initial value
	 */
	public float getInitialValue();
	
	/**
	 * Sets the time to count down and 
	 * <strong>stops</strong> the timer
	 * @param time
	 */
	public void setTime(float time);
	
	/**
	 * Starts the timer
	 */
	public void start();
	
	/**
	 * Stops the timer, i.e. <strong>sets
	 * the time left to 0</strong>
	 */
	public void stop();
	
	/**
	 * Pauses the timer
	 */
	public void pause();
	
	/**
	 * Resets the timer to it's initial value
	 */
	public void reset();
	
	/**
	 * Registers specified listener to the timer
	 * @param listener
	 */
	public void registerListener(Timerable listener);
	
	/**
	 * Deregisters specified listener to the timer
	 * @param listener
	 */
	public void deregisterListeneR(Timerable listener);
}
