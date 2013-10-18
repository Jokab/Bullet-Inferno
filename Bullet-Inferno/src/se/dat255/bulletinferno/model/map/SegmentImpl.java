package se.dat255.bulletinferno.model.map;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

/**
 * An implementation of the Segment interface. A Segment is one more many "map slices" that fit
 * together both by size and in-game look. Use an appropriate SegmentDefinition to get help creating
 * the Segment.
 * 
 * @see SegmentDefinition
 */
public class SegmentImpl implements Segment {

	/** All slices contained within this Segment */
	private final List<? extends Slice> slices;

	/** The position of this Segment */
	private final Vector2 position;

	/**
	 * Creates a new Segment with the given data to be used in game
	 * 
	 * @param slices
	 *        A list of slices that this segment should be made of. Must be at least 1.
	 * @param position
	 *        The position of the Segment.
	 */
	public SegmentImpl(List<? extends Slice> slices, Vector2 position) {
		// TODO: For debugging purposes. Should probably throw exception or do nothing instead.
		assert slices.size() > 0 : "Number of slices must be > 0";

		this.slices = slices;
		this.position = position.cpy();
	}

	@Override
	public void dispose() {
		for (Slice slice : slices) {
			slice.dispose();
		}
	}

	@Override
	public List<? extends Slice> getSlices() {
		return slices;
	}

	@Override
	public float getWidth() {
		float width = 0;
		for (Slice slice : slices) {
			width += slice.getWidth();
		}
		return width;
	}

	@Override
	public Vector2 getPosition() {
		return position;
	}

	@Override
	public float getEntryHeight() {
		return slices.get(0).getEntryHeight();
	}

	@Override
	public float getExitHeight() {
		return slices.get(slices.size() - 1).getExitHeight();
	}

}
