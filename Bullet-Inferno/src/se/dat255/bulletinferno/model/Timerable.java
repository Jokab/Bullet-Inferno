package se.dat255.bulletinferno.model;

public interface Timerable {
	/**
	 * Called by a given timer on time out.
	 * 
	 * @param timeSinceLast the time in seconds since the last call. (Ideally the time interval.)
	 */
	public void onTimeout(Timer source, float timeSinceLast);
}
