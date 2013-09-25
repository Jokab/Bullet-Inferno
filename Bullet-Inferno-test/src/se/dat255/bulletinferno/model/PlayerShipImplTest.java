package se.dat255.bulletinferno.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import se.dat255.bulletinferno.model.mock.SimpleMockGame;
import se.dat255.bulletinferno.model.weapon.WeaponData;

import com.badlogic.gdx.math.Vector2;

public class PlayerShipImplTest {

	Game mockGame;

	@Before
	public void initialize() {
		mockGame = new SimpleMockGame();
	}

	@Test
	public void testSetGetPosition() {
		// Tests the set and get position methods

		Vector2 position = new Vector2(0, 0);
		PlayerShipImpl playerShip = new PlayerShipImpl(mockGame, position, 100, WeaponData.FAST.getPlayerWeaponForGame(mockGame));

		assertEquals("The position should be set in the constructor", position,
				playerShip.getPosition());

		Vector2 position2 = new Vector2(1, 1);
		playerShip.setPosition(position2);

		assertEquals("The setPosition should set the position", position2,
				playerShip.getPosition());
	}

	@Test
	public void testTakeDamage() {
		// Tests that taking damage reduces the health
		// but doesn't reduce the initial health value

		Vector2 position = new Vector2(0, 0);
		PlayerShipImpl playerShip = new PlayerShipImpl(mockGame, position, 100, WeaponData.FAST.getPlayerWeaponForGame(mockGame));

		int health = playerShip.getHealth();
		int initialHealth = playerShip.getInitialHealth();
		playerShip.takeDamage(1);

		assertTrue("After taking 1 damage, health should be 1 less.",
				(health - 1) == playerShip.getHealth());
		assertTrue("Taking damage should not affect the initial health",
				initialHealth == playerShip.getInitialHealth());
	}

	@Test
	public void testPositionNotReference() {
		// Make sure we are not using references to
		// the Vector2 position vector.

		// Test the constructor
		Vector2 position = new Vector2(0, 0);
		PlayerShipImpl playerShip = new PlayerShipImpl(mockGame, position, 100, WeaponData.FAST.getPlayerWeaponForGame(mockGame));
		position.x = 20;

		assertTrue(
				"Changing the vector outside the object should not affect the object",
				playerShip.getPosition().x != position.x);

		// Test the setPosition method.
		Vector2 position2 = new Vector2(0, 0);
		playerShip.setPosition(position2);
		position2.x = 20;

		assertTrue(
				"Changing the vector outside the object should not affect the object",
				playerShip.getPosition().x != position2.x);
	}

}
