package se.dat255.bulletinferno.model.map;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import se.dat255.bulletinferno.model.mock.SimpleMockGame;
import se.dat255.bulletinferno.test.Common;

import com.badlogic.gdx.math.Vector2;

public class ObstacleDefinitionImplTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Common.loadEssentials();
	}

	private SimpleMockGame mockGame;
	private Vector2 pos;

	@Before
	public void setUp() throws Exception {
		mockGame = new SimpleMockGame();
		pos = new Vector2(10, 1);
	}

	@Test
	public void testCreateObstacle() {
		for (ObstacleDefinitionImpl obstacleDef : ObstacleDefinitionImpl.values()) {
			Obstacle obstacle = obstacleDef.createObstacle(mockGame, pos);
			assertNotNull("createObstacle should always return an obstacle instance", obstacle);

			assertTrue("createObstacle should add an obstacle to the physics world",
					mockGame.physicsWorld.createBodyCalls.size() == 1);

			assertEquals("createObstacle should add the obstacle at the provided position",
					mockGame.physicsWorld.createBodyCalls.get(0).position, pos);
		}
	}
}
