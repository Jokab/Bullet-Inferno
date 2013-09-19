package se.dat255.bulletinferno.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import se.dat255.bulletinferno.model.mock.SimpleMockGame;

import com.badlogic.gdx.math.Vector2;

public class WeaponImplTest {
	
	SimpleMockGame mockGame;
	
	@Before
	public void initialize() {
		 mockGame = new SimpleMockGame();
	}
	
	@Test
	public void testGetReloadingTime() {
		// Tests that the reloading time is set in the constructor

		WeaponImpl weapon = new WeaponImpl(20, mockGame);
		assertEquals(weapon.getReloadingTime(), 20);
	}

	@Test
	public void testGetReloadingTimeLeft() {
		// Tests that a newly created weapon is not reloading

		WeaponImpl weapon = new WeaponImpl(20, mockGame);
		assertTrue(weapon.getReloadingTimeLeft() <= 0);
	}

	@Test
	public void testIsLoaded() {
		// Tests that a newly created weapon is loaded
		// Essentially the same test as above

		WeaponImpl weapon = new WeaponImpl(20, mockGame);
		assertTrue(weapon.isLoaded());
	}

	@Test
	public void testGetOffset() {
		// Tests that the offset is always set, and if
		// provided in the constructor is set to that value

		WeaponImpl weapon = new WeaponImpl(20, mockGame);
		assertNotNull(weapon.getOffset());

		Vector2 offset = new Vector2();
		WeaponImpl weapon1 = new WeaponImpl(20, mockGame, offset);
		assertEquals(weapon1.getOffset(), offset);
	}

	@Test
	public void testFire() {
		// Tests that the fire method adds a projectile to the world
		// at the provided origin position "+" the offset,
		// and that it triggers a cool-down on the weapon.
		
		WeaponImpl weapon = new WeaponImpl(20, mockGame, new Vector2(1, 1));
		
		Vector2 origin = new Vector2(1, 1);
		weapon.fire(origin);

		// Make sure fire adds a single projectile to the Game
		assertTrue(mockGame.numProjectilesSpawned == 1);

		// After firing, time left should be the reloading time
		assertTrue(weapon.getReloadingTimeLeft() == weapon.getReloadingTime());
	}


	@Test
	public void testUpdate() {
		fail("NYI");
		// Tests that the update function updates the
		// cool-down of the weapon

		WeaponImpl weapon = new WeaponImpl(20, mockGame, new Vector2(1, 1));
		weapon.fire(new Vector2());

		int preUpdateTime = weapon.getReloadingTimeLeft();
		assertTrue(preUpdateTime > weapon.getReloadingTimeLeft());

	}
}
