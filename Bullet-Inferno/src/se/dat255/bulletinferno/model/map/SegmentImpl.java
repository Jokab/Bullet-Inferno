package se.dat255.bulletinferno.model.map;

public class SegmentImpl implements Segment {
	
	/** The first Slice of the Segment */
	private final Slice entrySlice;
	/** The last Slice of the Segment */
	private final Slice exitSlice;
	/** The middle Slices */
	//private final Slice[] middleSlices;
	/** All slices this Segment handles */
	private final Slice[] allSlices; // TODO: Needed to keep separate?
	/** The position of this Segment */
	private final float positionX;
	
	/**
	 * Creates a new Segment with the given data to be used in game
	 * @param entrySlice The Slice to use as entry
	 * @param exitSlice The Slice to use as exit
	 * @param slices The slices between entry and exit
	 * @param positionX The position of the Segment
	 */
	public SegmentImpl(Slice entrySlice, Slice exitSlice, Slice[] slices, float positionX) {
		this.entrySlice = entrySlice;
		this.exitSlice = exitSlice;
		//this.middleSlices = slices;
		
		int length = slices.length;
		this.allSlices = new Slice[length + 2];
		allSlices[0] = entrySlice;
		allSlices[length + 1] = exitSlice;
		System.arraycopy(slices, 0, allSlices, 1, length);
		
		this.positionX = positionX;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	@Override
	public Slice[] getSlices() {
		return allSlices;
	}

	@Override
	public int getWidth() {
		return allSlices.length * 20;
	}

	@Override
	public float getPosition() {
		return positionX;
	}

	@Override
	public float getEntryHeight() {
		return entrySlice.getEntryHeight();
	}

	@Override
	public float getExitHeight() {
		return exitSlice.getExitHeight();
	}

}
