package se.dat255.bulletinferno.model.map;

import java.util.List;

import se.dat255.bulletinferno.model.Obstacle;
import se.dat255.bulletinferno.model.ResourceIdentifier;
import se.dat255.bulletinferno.util.Disposable;

/**
 * An interface to define the properties of a slice
 * in a Segment. 
 *
 */
public interface Slice extends Disposable, ResourceIdentifier {
	/**
	 * Returns the height, measured in meters
	 * above sea-level, for the entry point of the slide
	 * @return height in meters
	 */
	public float getEntryHeight();
	
	/**
	 * Returns the height, measured in meters
	 * above sea-level, for the exit point of the slide
	 * @return height in meters
	 */
	public float getExitHeight();
	
	/**
	 * @return a list of all Obstacles in the slice.
	 */
	public List<? extends Obstacle> getObstacles();
	
	/**
	 * Returns the width of the slice in meters
	 * @return width (m)
	 */
	public float getWidth();	
}
