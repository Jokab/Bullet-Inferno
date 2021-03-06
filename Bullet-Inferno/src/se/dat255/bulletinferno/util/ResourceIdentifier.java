package se.dat255.bulletinferno.util;

/**
 * All classes that want to be associated with a resource must implement this interface
 * for the {@link ResourceManager} to find the appropriate mapping.
 * 
 */
public interface ResourceIdentifier {

	/**
	 * Returns the identifier that this {@link ResourceIdentifier} uses to retrieve
	 * its connected resource.
	 * 
	 * @return the identifier for this ResourceIdentifier.
	 */
	String getIdentifier();
}
