package se.dat255.bulletinferno.model.map;

import java.util.Random;

public enum SegmentFactory {
	WATER, EARTH, TUNNEL;
	
	public Segment generateRandomSegment() {
		SegmentFactory[] values = SegmentFactory.values();
		Random random = new Random();
		return values[random.nextInt(values.length)].generateSegment();
	}
	
	public Segment generateSegment() {
		return null;
	}
}
