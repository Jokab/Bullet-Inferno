package se.dat255.bulletinferno.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import se.dat255.bulletinferno.model.mock.SimpleMockGame;
import se.dat255.bulletinferno.model.mock.SimpleMockTimer;
import se.dat255.bulletinferno.model.weapon.ProjectileType;
import se.dat255.bulletinferno.model.weapon.WeaponImpl;
import se.dat255.bulletinferno.test.Common;

import com.badlogic.gdx.math.Vector2;

public class WeaponImplTest {

	@BeforeClass
	public static void beforeTests() {
		Common.loadEssentials();	
	}
	
	SimpleMockGame mockGame;
	WeaponImplMockTimer mockTimer;
	

	@Before
	public void initialize() {
		mockTimer = new WeaponImplMockTimer();
		mockGame = new SimpleMockGame(mockTimer);
	}

	@Test
	public void testGetReloadingTime() {
		// Tests that the reloading time is set in the constructor

		WeaponImpl weapon = new WeaponImpl(mockGame, 20, ProjectileType.DEFAULT_PROJECTILE, new Vector2(), 0, 1f);
		assertTrue("The reloadingTime should be set in the constructor",
				weapon.getReloadingTime() == 20);
	}

	@Test
	public void testGetReloadingTimeLeft() {
		// Tests that a newly created weapon is not reloading

		WeaponImpl weapon = new WeaponImpl(mockGame, 20, ProjectileType.DEFAULT_PROJECTILE, new Vector2(), 0, 1f);
		assertTrue("A newly created weapon should not be reloading",
				weapon.getReloadingTimeLeft() <= 0);
	}

	@Test
	public void testIsLoaded() {
		// Tests that a newly created weapon is loaded
		// Essentially the same test as above

		WeaponImpl weapon = new WeaponImpl(mockGame, 20, ProjectileType.DEFAULT_PROJECTILE, new Vector2(), 0, 1f);
		assertTrue("A newly created weapon should be loaded",
				weapon.isLoaded());
	}

	@Test
	public void testGetOffset() {
		// Tests that the offset is always set, and if
		// provided in the constructor is set to that value

		WeaponImpl weapon = new WeaponImpl(mockGame, 20, ProjectileType.DEFAULT_PROJECTILE, new Vector2(), 0, 1f);
		assertNotNull("The offset should always exist", weapon.getOffset());

		Vector2 offset = new Vector2();
		WeaponImpl weapon1 = new WeaponImpl(mockGame, 20, ProjectileType.DEFAULT_PROJECTILE, offset, 0, 1f);
		assertEquals("The offset should be set in the constructor",
				weapon1.getOffset(), offset);
	}

	@Test
	public void testFire() {
		// Tests that the fire method adds a projectile to the world
		// at the provided origin position "+" the offset,
		// and that it triggers a cool-down on the weapon.

		WeaponImpl weapon = new WeaponImpl(mockGame, 20, ProjectileType.DEFAULT_PROJECTILE, new Vector2(10,0), 1, 1f);

		Vector2 origin = new Vector2(1, 1);
		weapon.fire(origin, new Vector2(), null);

		assertTrue("Firing a weapon should add a projectile to the game",
				mockGame.numProjectilesSpawned == 1);

		assertTrue(
				"After firing a weapon, time left on reloading should be the reloading time",
				weapon.getReloadingTimeLeft() == weapon.getReloadingTime());
	}

	@Test
	public void testWeaponReloads() {
		// Tests that the weapon reloads

		WeaponImpl weapon = new WeaponImpl(mockGame, 20, ProjectileType.DEFAULT_PROJECTILE, new Vector2(1,1), 0, 1f);
		weapon.fire(new Vector2(), new Vector2(), null);

		float preUpdateTime = weapon.getReloadingTimeLeft();

        mockTimer.timeLeft -= 1;

		assertTrue(preUpdateTime > weapon.getReloadingTimeLeft());

	}
	
	class WeaponImplMockTimer extends SimpleMockTimer {	
	}
}
