package se.dat255.bulletinferno.model.map;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import se.dat255.bulletinferno.model.mock.PhysicsWorldImplSpy;
import se.dat255.bulletinferno.model.mock.SimpleMockGame;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.test.Common;

import com.badlogic.gdx.math.Vector2;

public class ObstacleDefinitionImplTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Common.loadEssentials();
	}

	private PhysicsWorldImplSpy physics;
	private Vector2 pos;

	@Before
	public void setUp() throws Exception {
		physics = new PhysicsWorldImplSpy();
		pos = new Vector2(10, 1);
	}

	@Test
	public void testCreateObstacle() {
		for (ObstacleDefinitionImpl obstacleDef : ObstacleDefinitionImpl.values()) {
			Obstacle obstacle = obstacleDef.createObstacle(physics, pos);
			assertNotNull("createObstacle should always return an obstacle instance", obstacle);

			assertTrue("createObstacle should add an obstacle to the physics world",
					physics.createBodyCalls.size() == 1);

			assertEquals("createObstacle should add the obstacle at the provided position",
					physics.createBodyCalls.get(0).position, pos);
		}
	}
}
