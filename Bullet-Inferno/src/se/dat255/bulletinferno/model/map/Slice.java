package se.dat255.bulletinferno.model.map;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.util.Disposable;
import se.dat255.bulletinferno.util.ResourceIdentifier;

/**
 * An interface to define the properties of a slice (i.e. a part)
 * in a Segment.
 * 
 */
public interface Slice extends Disposable, ResourceIdentifier {
	/**
	 * Returns the height, measured in meters
	 * above sea-level, for the entry point of the slide.
	 * 
	 * @return The entry height of this Slice (meters).
	 */
	public float getEntryHeight();

	/**
	 * Returns the height, measured in meters
	 * above sea-level, for the exit point of the slide
	 * 
	 * @return The exit height of this Slice (meters).
	 */
	public float getExitHeight();

	/**
	 * Returns all Obstacles in this Slice.
	 * 
	 * @return a list of all Obstacles in the slice.
	 */
	public List<? extends Obstacle> getObstacles();

	/**
	 * Returns the width of this slice in meters.
	 * 
	 * @return width (meters)
	 */
	public float getWidth();

	/**
	 * Returns the position of the sprite in world coordinates
	 * 
	 * @return
	 */
	public Vector2 getPosition();
}
