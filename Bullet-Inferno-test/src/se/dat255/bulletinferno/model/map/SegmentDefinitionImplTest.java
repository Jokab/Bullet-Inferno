package se.dat255.bulletinferno.model.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.mock.SimpleMockGame;
import se.dat255.bulletinferno.test.Common;

import com.badlogic.gdx.math.Vector2;

public class SegmentDefinitionImplTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Common.loadEssentials();
	}

	private Game mockGame;
	private Vector2 pos;

	@Before
	public void setUp() throws Exception {
		mockGame = new SimpleMockGame();
		pos = new Vector2(10, 1);
	}

	@Test
	public void testHasDefinitions() {
		// Make sure we have pre-defined segmentDefinitions
		assertTrue("SegmentDefinitionImpl should define at least one SegmentDefinition",
				SegmentDefinitionImpl.values().length > 0);
	}

	@Test
	public void testCreateSegmentIsRandomized() {
		// Test each of the SegmentDefinitionImpl configurations provided in the
		// SegmentDefinitionImpl enum
		for (SegmentDefinitionImpl segmentDef : SegmentDefinitionImpl.values()) {
			// Get the suitable slices field via reflexion and dark magic
			List<? extends SliceDefinition> suitableSlices = getSuitableSlices(segmentDef);

			// Can not randomize a path of 1 or less slices.
			if (suitableSlices.size() <= 1) {
				continue;
			}

			// Can only be randomized if two or more slices share the same exit and entry height
			// (this is not true, but will have to do for the test)
			boolean canBeRandomized = false;
			for (SliceDefinition sliceDefA : suitableSlices) {
				for (SliceDefinition sliceDefB : suitableSlices) {
					if (sliceDefA != sliceDefB
							&& sliceDefA.getEntryHeight() == sliceDefB.getEntryHeight()
							&& sliceDefA.getExitHeight() == sliceDefB.getExitHeight()) {
						canBeRandomized = true;
						break;
					}
				}
			}

			if (!canBeRandomized) {
				continue;
			}

			// If the segments can be randomized, create a bunch of Segment instances and compare
			// the order of slices between them. Unless all of them match they should have been
			// randomized (or we have been very, very unlucky)
			boolean hasVariation = false;

			List<Segment> segments = new LinkedList<Segment>();
			for (int i = 0; i < 25; i++) {
				segments.add(segmentDef.createSegment(mockGame, pos, suitableSlices.size()));
			}

			List<? extends Slice> slicesSegA, slicesSegB;

			for (Segment segmentA : segments) {
				slicesSegA = segmentA.getSlices();
				for (Segment segmentB : segments) {
					slicesSegB = segmentB.getSlices();
					// Don't compare the same segments
					if (segmentA == segmentB) {
						continue;
					}

					// Go trough the slices and compare their identifiers.
					for (int i = 0; i < segmentA.getSlices().size(); i++) {
						if (slicesSegA.get(i).getIdentifier() != slicesSegB.get(i).getIdentifier()) {
							hasVariation = true;
							break;
						}
					}
				}
			}

			assertTrue("The Segment's order of Slices should be randomized.",
					hasVariation);
		}

	}

	@Test
	public void testCreateSegment() {
		// Test each of the SegmentDefinitionImpl configurations provided in the
		// SegmentDefinitionImpl enum
		for (SegmentDefinitionImpl segmentDef : SegmentDefinitionImpl.values()) {

			// Test a different number of slices. The Segment should either provide a
			// Segment of the length requested, or throw an IllegalArgumentException.
			for (int numSlices = 2; numSlices < 10; numSlices++) {
				Segment createdSegment;
				try {
					createdSegment = segmentDef.createSegment(mockGame, pos, numSlices);
				} catch (IllegalArgumentException e) {
					continue;
				}

				// Make sure the segment created is at the right position and has the
				// right number of slices.
				assertEquals("The created segment should be placed at the provied postition",
						createdSegment.getPosition(), pos);
				assertEquals("The created segment should have the correct number of slices",
						createdSegment.getSlices().size(), numSlices);

				// Make sure all slices fit together.
				Slice prevSlice = null;
				for (Slice slice : createdSegment.getSlices()) {
					if (prevSlice != null) {
						assertTrue(
								"Each slice should have an entry height that matches the slice before it's exit height",
								slice.getEntryHeight() == prevSlice.getExitHeight());
					}
					prevSlice = slice;
				}
			}
		}
	}

	/**
	 * Uses reflection to get the private list of suitableSlices from the SegmentDefinitionImpl
	 * 
	 * @param obj
	 *        The SegmentDefinitionImpl instance whose suitableSlices field should be returned.
	 * @return The suitableSlices field value of the provided SegmentDefinitionImpl instance.
	 */
	@SuppressWarnings("unchecked")
	private List<? extends SliceDefinition> getSuitableSlices(SegmentDefinitionImpl obj) {
		try {
			Field f = SegmentDefinitionImpl.class.getDeclaredField("suitableSlices");
			f.setAccessible(true);
			return (List<? extends SliceDefinition>) f.get(obj);
		} catch (Exception e) {
			// TODO: ehh, handle this.
			fail("Could not get the suitableSlices field via reflexion");
			return null;
		}
	}
}
