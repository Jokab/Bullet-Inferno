package se.dat255.bulletinferno.model.map;

import java.util.Random;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Segment;

import com.badlogic.gdx.math.Vector2;

/**
 * A factory for creating (randomized) Segments.
 * 
 */
public class SegmentFactory {
	private static Random random = new Random();

	/**
	 * Returns a randomly generated segment with specified
	 * amount of slices and position in the provided Game instance.
	 * 
	 * @param game
	 *        The game instance.
	 * @param position
	 *        The world-coordinates the Segment will be placed at in the physics world.
	 * @param sliceAmount
	 *        The number of slices that the Segment should be built of.
	 * @return A random Segment at <b>position</b> built of <b>sliceAmount</b> number of slices.
	 */
	public Segment generateRandomSegment(Game game, Vector2 position, int sliceAmount) {
		SegmentDefinitionImpl[] values = SegmentDefinitionImpl.values();
		return values[random.nextInt(values.length)].createSegment(game, position, sliceAmount);
	}

	/**
	 * Returns a randomly generated segment with randomly generated amount of slices
	 * between the specified interval
	 * 
	 * @param game
	 *        The game instance.
	 * @param position
	 *        The world-coordinates the Segment will be placed at in the physics world.
	 * @param minSliceLength
	 *        The highest number of Slices allowed.
	 * @param maxSliceLength
	 *        The lowest number of Slices allowed.
	 * @return A random Segment at <b>position</b> built of a random number between
	 *         <b>minSliceLength</b> and <b>maxSliceLength</b> number of slices.
	 */
	public Segment generateRandomSegment(Game game, Vector2 position, int minSliceLength,
			int maxSliceLength) {
		return generateRandomSegment(game, position,
				random.nextInt(maxSliceLength - minSliceLength + 1) + minSliceLength);
	}
}
