package se.dat255.bulletinferno.model;

public interface Timerable {
	/**
	 * Called by a given timer on
	 * time out.
	 */
	public void onTimeout(Timer source);
}
