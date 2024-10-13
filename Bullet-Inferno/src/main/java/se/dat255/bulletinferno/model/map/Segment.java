package se.dat255.bulletinferno.model.map;

import java.util.List;

import se.dat255.bulletinferno.util.Disposable;

import com.badlogic.gdx.math.Vector2;

/**
 * A Segment is a holder of map slices, building up a part that
 * looks alike in the game.
 */
public interface Segment extends Disposable {
	/**
	 * Gets all slices contained within this Segment.
	 * 
	 * @return The slices contained in this Segment.
	 */
	public List<? extends Slice> getSlices();

	/**
	 * Gets the width of the Segment, which is equal to the combined width of all slices within the
	 * Segment.
	 * 
	 * @return The width of this Segment.
	 */
	public float getWidth();

	/**
	 * Gets the starting position of the Segment.
	 * 
	 * @return The starting position of the Segment.
	 */
	public Vector2 getPosition();

	/**
	 * Gets the entry height (i.e. the height of the leftmost Slice) of the Segment.
	 * 
	 * @return The entry height of the Segment.
	 */
	public float getEntryHeight();

	/**
	 * Gets the exit height (i.e. the height of the rightmost Slice) of the Segment.
	 * 
	 * @return The exit height of the Segment.
	 */
	public float getExitHeight();
}
