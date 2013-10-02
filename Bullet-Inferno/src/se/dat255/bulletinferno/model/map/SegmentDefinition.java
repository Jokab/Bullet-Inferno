package se.dat255.bulletinferno.model.map;

import se.dat255.bulletinferno.model.Game;

import com.badlogic.gdx.math.Vector2;

public interface SegmentDefinition {
	/**
	 * Returns an instance of a segment defined by this definition
	 * @param game
	 * @param position
	 * @param sliceAmount amount of slices the segment should consist of 
	 * @return segment
	 */
	public Segment createSegment(Game game, Vector2 position, int sliceAmount);
}
