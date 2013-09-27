package se.dat255.bulletinferno.model.map;

import java.util.List;

import se.dat255.bulletinferno.model.PhysicsBody;
import se.dat255.bulletinferno.util.Disposable;

/**
 * An interface to define the properties of a slice
 * in a Segment. 
 *
 */
public interface Slice extends Disposable {
	/**
	 * Returns the height, measured in meters
	 * above sea-level, for the entry point of the slide
	 * @return height in meters
	 */
	public int getEntryHeight();
	
	/**
	 * Returns the height, measured in meters
	 * above sea-level, for the exit point of the slide
	 * @return height in meters
	 */
	public int getExitHeight();
	
	/**
	 * Returns the physics bodies contained
	 * in this slice. I.e. all the physical space
	 * of this slice
	 * @return physics bodies
	 */
	public List<PhysicsBody> getPhysicsBodyies();
	
}
