package se.dat255.bulletinferno.model.map;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class SegmentImpl implements Segment {
	
	/** All slices this Segment handles */
	private final List<? extends Slice> allSlices;
	/** The position of this Segment */
	private final Vector2 position;
	
	/**
	 * Creates a new Segment with the given data to be used in game
	 * @param entrySlice The Slice to use as entry
	 * @param exitSlice The Slice to use as exit
	 * @param slices The slices between entry and exit
	 * @param positionX The position of the Segment
	 */
	public SegmentImpl(List<? extends Slice> slices, Vector2 position) {		
		this.allSlices = slices;		
		this.position = position;
		// TODO check if slices.size() > 0
	}

	@Override
	public void dispose() {
		for(Slice slice : allSlices) {
			slice.dispose();
		}
	}

	@Override
	public List<? extends Slice> getSlices() {
		return allSlices;
	}

	@Override
	public float getWidth() {
		float width = 0;
		for(Slice slice : allSlices) {
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
		return allSlices.get(0).getEntryHeight();
	}

	@Override
	public float getExitHeight() {
		return allSlices.get(allSlices.size()-1).getExitHeight();
	}

}
