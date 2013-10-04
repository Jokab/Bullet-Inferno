package se.dat255.bulletinferno.model.map;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import se.dat255.bulletinferno.model.mock.map.SimpleObstacleDefinitionMock;
import se.dat255.bulletinferno.test.Common;

import com.badlogic.gdx.math.Vector2;

public class ObstaclePlacementImplTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Common.loadEssentials();
	}

	private Vector2 pos;
	private SimpleObstacleDefinitionMock mockObstacleDef;

	@Before
	public void setUp() throws Exception {
		mockObstacleDef = new SimpleObstacleDefinitionMock();
		pos = new Vector2(10, 1);
	}

	@Test
	public void testCreateObstaclePlacementImpl() {
		// Create an ObstaclePlacementImpl and make sure the properties are set.

		ObstaclePlacement obstaclePlacement = new ObstaclePlacementImpl(mockObstacleDef, pos);

		assertEquals("The obstacle definition should be the one provided",
				obstaclePlacement.getObstacleDefinition(), mockObstacleDef);
		assertEquals("The position should be the one provided", obstaclePlacement.getPosition(),
				pos);
	}
}
