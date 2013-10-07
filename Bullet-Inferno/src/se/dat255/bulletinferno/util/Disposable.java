package se.dat255.bulletinferno.util;

/**
 * A class to represent an object that needs to be <strong>manually</strong> handled
 * on it's dispose. <strong>Failure to dispose resources could lead to memory leaks!</strong>
 * 
 */
public interface Disposable extends com.badlogic.gdx.utils.Disposable {
	/**
	 * Frees up the object's assets/heavy-weight memory resources.
	 * To be run when the object is no longer needed.
	 */
	@Override
	public void dispose();
}
