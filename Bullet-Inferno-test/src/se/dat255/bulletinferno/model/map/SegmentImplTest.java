package se.dat255.bulletinferno.model.map;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import se.dat255.bulletinferno.model.mock.map.SimpleSliceMock;
import se.dat255.bulletinferno.test.Common;

import com.badlogic.gdx.math.Vector2;

public class SegmentImplTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Common.loadEssentials();
	}

	private Vector2 pos;

	@Before
	public void setUp() throws Exception {
		pos = new Vector2(10, 1);
	}

	@Test
	public void testCreateSegmentImpl() {
		List<SimpleSliceMock> slices = new ArrayList<SimpleSliceMock>();
		slices.add(new SimpleSliceMock(1, 1, 1));
		slices.add(new SimpleSliceMock(2, 2, 2));

		SegmentImpl segmentImpl = new SegmentImpl(slices, pos);

		assertTrue("The segment's entry height should be the same as the first slice",
				segmentImpl.getEntryHeight() == 1);

		assertTrue("The segment's exit height should be the same as the last slice",
				segmentImpl.getExitHeight() == 2);

		assertTrue("The segment's width should be the combined width of the slices",
				segmentImpl.getWidth() == 3);

		assertEquals("The segment should be at the postition provided", pos,
				segmentImpl.getPosition());

		assertEquals("The Segment should have the slices provided",
				slices, segmentImpl.getSlices());
	}

}
