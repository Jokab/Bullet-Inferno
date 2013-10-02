package se.dat255.bulletinferno.model.map;

import java.util.Random;

import se.dat255.bulletinferno.model.Game;

import com.badlogic.gdx.math.Vector2;

public class SegmentFactory {
	private static Random random = new Random();
	
	/**
	 * Returns a randomly generated segment with specified
	 * amount of slices and position
	 * @param game
	 * @param position
	 * @param sliceAmount
	 * @return random segment
	 */
	public Segment generateRandomSegment(Game game, Vector2 position, int sliceAmount) {
		SegmentDefinitionImpl[] values = SegmentDefinitionImpl.values();
		return values[random.nextInt(values.length)].createSegment(game, position, sliceAmount);
	}
	
	/**
	 * Returns a randomly generated segment with randomly generated amount of slices
	 * between the specified interval
	 * @param game
	 * @param position
	 * @param minSliceLength
	 * @param maxSliceLength
	 * @return
	 */
	public Segment generateRandomSegment(Game game, Vector2 position, int minSliceLength, 
			int maxSliceLength) {
		return generateRandomSegment(game, position, 
				random.nextInt(maxSliceLength - minSliceLength) + 1);
	}
}
