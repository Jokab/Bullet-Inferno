package se.dat255.bulletinferno.model.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Segment;

import com.badlogic.gdx.math.Vector2;

/**
 * Configurations of simple SegmentDefinitions.
 * 
 * @see SegmentDefinition
 */
public enum SegmentDefinitionImpl implements SegmentDefinition {
	/**
	 * A water segment.
	 */
	// Add when the view can handle this.
	//WATER(SliceDefinitionImpl.WATER, SliceDefinitionImpl.WATER, Arrays
	//		.asList(SliceDefinitionImpl.WATER)),

	/**
	 * A mountain segment.
	 */
	MOUNTAIN(SliceDefinitionImpl.MOUNTAIN_1, SliceDefinitionImpl.MOUNTAIN_8, Arrays.asList(
			SliceDefinitionImpl.MOUNTAIN_2,
			SliceDefinitionImpl.MOUNTAIN_3,
			SliceDefinitionImpl.MOUNTAIN_4,
			SliceDefinitionImpl.MOUNTAIN_5,
			SliceDefinitionImpl.MOUNTAIN_6,
			SliceDefinitionImpl.MOUNTAIN_7));

	/**
	 * The entry (i.e. the first) slice of the segment.
	 */
	private final SliceDefinition entrySlice;

	/**
	 * The exit (i.e. the last) slice of the segment.
	 */
	private final SliceDefinition exitSlice;

	/**
	 * A list of slices that can be used to fill space between the entry and the exit slice.
	 */
	protected final List<? extends SliceDefinition> suitableSlices;

	/**
	 * 
	 * @param entry
	 *        The entry (i.e. the first) slice of the segment.
	 * @param exit
	 *        The exit (i.e. the last) slice of the segment.
	 * @param suitableSlices
	 *        A list of slices that can be used to fill space between the entry and the exit slice.
	 */
	SegmentDefinitionImpl(SliceDefinition entry, SliceDefinition exit,
			List<? extends SliceDefinition> suitableSlices) {
		this.entrySlice = entry;
		this.exitSlice = exit;
		this.suitableSlices = suitableSlices;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Segment createSegment(Game game, Vector2 position, int sliceAmount) {
		List<SliceDefinition> sliceDefinitonsPath = getSlices(sliceAmount);
		List<Slice> slices = new ArrayList<Slice>(sliceDefinitonsPath.size());
		Vector2 slicePosition = position.cpy();

		Slice slice;
		for (SliceDefinition sliceType : sliceDefinitonsPath) {
			slice = sliceType.createSlice(game, slicePosition.cpy());
			slices.add(slice);
			slicePosition.add(slice.getWidth(), 0);
		}
		
		return new SegmentImpl(slices, position.cpy());
	}

	/**
	 * Returns a list of size <i>amount</i> of slices that fit together.
	 * 
	 * @param amount
	 *        The number of slices that should be returned.
	 * @return a list of <i>amount</i> number of slices
	 */
	private List<SliceDefinition> getSlices(int amount) {
		// The number of slices we have to generate, entry and exit is already set.
		int numMiddleSlices = amount - 2;
		
		// The final path of slices that will be returned
		List<SliceDefinition> path = new ArrayList<SliceDefinition>(amount);

		// Add the entry slice as the first entry of the path.
		path.add(entrySlice);

		// A list of "dead ends" that will not be tried again
		List<List<SliceDefinition>> exceptions = new ArrayList<List<SliceDefinition>>(
				amount);
		for (int i = 0; i < amount; i++) {
			exceptions.add(new LinkedList<SliceDefinition>());
		}
		// The current step of the path we are currently trying
		SliceDefinition current = entrySlice;

		for (int sliceNum = 0; sliceNum < numMiddleSlices; sliceNum++) {
			// Shuffle around the slices to randomize path
			Collections.shuffle(suitableSlices);
			boolean foundAMatch = false;

			// Go through all the suitable slices to find a matching one
			for (SliceDefinition slice : suitableSlices) {
				// We want a matching slice that is not in the exceptions list
				if (slice.getEntryHeight() != current.getExitHeight()
						|| exceptions.get(sliceNum).contains(slice)) {
					continue;
				}

				// If the current slice is the second to the last exit slice
				if (sliceNum == (numMiddleSlices - 1)) {
					// make sure it also matches the exit slice
					if (slice.getExitHeight() == exitSlice.getEntryHeight()) {
						path.add(slice);
						current = slice;
						foundAMatch = true;
						break;
					}
				} else {
					path.add(slice);
					current = slice;
					foundAMatch = true;
					break;
				}
			}

			if (!foundAMatch) {
				// If we fail on first iteration, that means there is no path.
				if (sliceNum == 0) {
					throw new IllegalArgumentException("Couldn't find a way from specified " +
							"entry slice to end slice " + sliceNum);
				}

				// Remove current slice from path
				if (path.size() > sliceNum) {
					path.remove(sliceNum);
				}

				// Erase exception list on this stage because this path leads nowhere
				// and won't be tried again
				exceptions.get(sliceNum).clear();
				
				// Add the current slice as an exception to the past one
				exceptions.get(sliceNum - 1).add(current);

				// Set the past one to be the current to find another slice mate
				current = path.get(sliceNum - 1);
				sliceNum -= 2;
			}
		}
		path.add(exitSlice);
		return path;
	}
}
