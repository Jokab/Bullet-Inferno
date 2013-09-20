package se.dat255.bulletinferno.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import se.dat255.bulletinferno.model.mock.SimpleMockGame;

import com.badlogic.gdx.math.Vector2;

public class ProjectileImplTest {

	Game mockGame;

	@Before
	public void initialize() {
		mockGame = new SimpleMockGame();
	}

	@Test
	public void testGetDamage() {
		// Tests that the projectileImpl has a default damage >= 0.
		ProjectileImpl projectile = new ProjectileImpl(mockGame);
		assertTrue("The default damage of a projectile should be above 0",
				projectile.getDamage() >= 0);
	}

	@Test
	public void testSetGetPosition() {
		// Tests the set and get position methods

		Vector2 position = new Vector2(0, 0);
		ProjectileImpl projectile = new ProjectileImpl(mockGame);
		projectile.setPosition(position);

		assertEquals("The setPosition should set the position", position,
				projectile.getPosition());
	}

	@Test
	public void testPositionNotReference() {
		// Make sure we are not using references to
		// the Vector2 position vector.

		// Test the setPosition method.
		Vector2 position = new Vector2(0, 0);
		ProjectileImpl projectile = new ProjectileImpl(mockGame);
		projectile.setPosition(position);
		position.x = 20;

		assertTrue(
				"Changing the vector outside the object should not affect the object",
				projectile.getPosition().x != position.x);
	}

}
