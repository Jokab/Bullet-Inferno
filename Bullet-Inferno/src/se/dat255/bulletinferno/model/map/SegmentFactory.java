package se.dat255.bulletinferno.model.map;

import java.util.Random;

public enum SegmentFactory {
	WATER(SliceType.WATER, SliceType.WATER, new SliceType[] {SliceType.WATER}), 
	MOUNTAIN(SliceType.MOUNTAIN_1, SliceType.MOUNTAIN_8, 
			new SliceType[] {SliceType.MOUNTAIN_8,
				SliceType.MOUNTAIN_2,
				SliceType.MOUNTAIN_3,
				SliceType.MOUNTAIN_4,
				SliceType.MOUNTAIN_5,
				SliceType.MOUNTAIN_6,
				SliceType.MOUNTAIN_7});	
	
	private final SliceType entrySlice;
	private final SliceType exitSlice;
	private final SliceType[] suitableSlices;
	
	SegmentFactory(SliceType entry, SliceType exit, SliceType[] suitableSlices) {
		this.entrySlice = entry;
		this.exitSlice = exit;
		this.suitableSlices = suitableSlices;
	}
	
	public Segment generateRandomSegment() {
		SegmentFactory[] values = SegmentFactory.values();
		Random random = new Random();
		return values[random.nextInt(values.length)].generateSegment();
	}
	
	public Segment generateSegment() {
		return null;
	}
}
