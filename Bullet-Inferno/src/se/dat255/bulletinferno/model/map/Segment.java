package se.dat255.bulletinferno.model.map;

import se.dat255.bulletinferno.util.Disposable;

/**
 * A segment is a holder of map slices, building up a part that
 *  looks alike in the game.
 */
public interface Segment extends Disposable {
	/** Gets all slices contained within this segment */
	public Slice[] getSlices();
	/** Gets the width of all the slices */
	public int getWidth();
	/** Gets the start X position of the Segment */
	public float getPosition();
	/** Sets the start X position of the Segment
	 *   and all the slices within */
	public void setPosition();
	/** Gets the entry height of the segment */
	public int getEntryHeight();
	/** Gets the exit height of the segment */
	public int getExitHeight();
}
