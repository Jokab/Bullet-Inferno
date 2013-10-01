package se.dat255.bulletinferno.util;

/**
 * A class to represent an object that needs to be <strong>manually</strong> handled
 * on it's dispose. <strong>Failure to dispose resources could lead to memory leaks!</strong>
 * 
 */
public interface Disposable extends com.badlogic.gdx.utils.Disposable {
	@Override
	public void dispose();
}
