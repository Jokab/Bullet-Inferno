package se.dat255.bulletinferno.model;


public interface Enemy{
	
	/**
	 * Returns the score that you get from destroying this enemy.
	 * 
	 * @return The score received.
	 */
	public int getScore();
	
	
	/**
	 * Returns the credits you get from destroying this enemy.
	 * 
	 * @return The credits received.
	 */
	public int getCredits();
}
