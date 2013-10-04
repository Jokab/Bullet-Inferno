package se.dat255.bulletinferno.model;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.map.Slice;
import se.dat255.bulletinferno.util.Disposable;

/**
 * A Segment is a holder of map slices, building up a part that
 * looks alike in the game.
 */
public interface Segment extends Disposable {
	/**
	 * Gets all slices contained within this segment.
	 * 
	 * @return The slices contained in this Segment.
	 */
	public List<? extends Slice> getSlices();

	/**
	 * Gets the width of the segment, which is equal to the combined width of all slices within the
	 * Segment.
	 * 
	 * @return The width of this segment.
	 */
	public float getWidth();

	/**
	 * Gets the starting position of the Segment.
	 * 
	 * @return The starting position of the Segment.
	 */
	public Vector2 getPosition();

	/**
	 * Gets the entry height (i.e. the height of the leftmost Slice) of the segment.
	 * 
	 * @return The entry height of the segment.
	 */
	public float getEntryHeight();

	/** 
	 * Gets the exit height (i.e. the height of the rightmost Slice) of the segment.
	 * 
	 * @return The exit height of the segment.
	 */
	public float getExitHeight();
}
