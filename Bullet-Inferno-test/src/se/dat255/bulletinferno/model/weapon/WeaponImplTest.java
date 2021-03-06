package se.dat255.bulletinferno.model.weapon;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import se.dat255.bulletinferno.model.mock.PhysicsWorldImplSpy;
import se.dat255.bulletinferno.model.mock.SimpleMockTimer;
import se.dat255.bulletinferno.model.mock.SimplePlayerShipMock;
import se.dat255.bulletinferno.model.mock.WeaponMockEnvironment;
import se.dat255.bulletinferno.test.Common;

import com.badlogic.gdx.math.Vector2;

public class WeaponImplTest {

	@BeforeClass
	public static void beforeTests() {
		Common.loadEssentials();	
	}
	
	PhysicsWorldImplSpy physics;
	WeaponImplMockTimer mockTimer;
	WeaponMockEnvironment weapons;
	

	@Before
	public void initialize() {
		mockTimer = new WeaponImplMockTimer();
		physics = new PhysicsWorldImplSpy(mockTimer);
		weapons = new WeaponMockEnvironment();
	}

	@Test
	public void testGetReloadingTime() {
		// Tests that the reloading time is set in the constructor

		WeaponImpl weapon = new WeaponImpl(physics, weapons,WeaponDefinitionImpl.MISSILE_LAUNCHER, 
				20, ProjectileDefinitionImpl.RED_PROJECTILE, 0);
		assertTrue("The reloadingTime should be set in the constructor",
				weapon.getReloadingTime() == 20);
	}

	@Test
	public void testGetReloadingTimeLeft() {
		// Tests that a newly created weapon is not reloading

		WeaponImpl weapon = new WeaponImpl(physics, weapons,WeaponDefinitionImpl.MISSILE_LAUNCHER, 
				20, ProjectileDefinitionImpl.RED_PROJECTILE, 0);
		assertTrue("A newly created weapon should not be reloading",
				weapon.getReloadingTimeLeft() <= 0);
	}

	@Test
	public void testIsLoaded() {
		// Tests that a newly created weapon is loaded
		// Essentially the same test as above

		WeaponImpl weapon = new WeaponImpl(physics, weapons,WeaponDefinitionImpl.MISSILE_LAUNCHER, 
				20, ProjectileDefinitionImpl.RED_PROJECTILE, 0);
		assertTrue("A newly created weapon should be loaded",
				weapon.isLoaded());
	}

	@Test
	public void testFire() {
		// Tests that the fire method adds a projectile to the world
		// at the provided origin position "+" the offset,
		// and that it triggers a cool-down on the weapon.

		WeaponImpl weapon = new WeaponImpl(physics, weapons, 
				WeaponDefinitionImpl.MISSILE_LAUNCHER, 20, ProjectileDefinitionImpl.RED_PROJECTILE, 1);
		Vector2 origin = new Vector2(1, 1);
		weapon.fire(origin, new Vector2(), new SimplePlayerShipMock());

		assertTrue("Firing a weapon should add a projectile to the game",
				weapons.retrievedProjectiles.size() == 1);

		assertTrue(
				"After firing a weapon, time left on reloading should be the reloading time",
				weapon.getReloadingTimeLeft() == weapon.getReloadingTime());
	}

	@Test
	public void testWeaponReloads() {
		// Tests that the weapon reloads

		WeaponImpl weapon = new WeaponImpl(physics, weapons,WeaponDefinitionImpl.MISSILE_LAUNCHER, 
				20, ProjectileDefinitionImpl.RED_PROJECTILE, 0);		
		assertTrue(weapon.isLoaded());
		weapon.fire(new Vector2(), new Vector2(), null);
		
		float preUpdateTime = weapon.getReloadingTimeLeft();

        mockTimer.timeLeft -= 1;

		assertTrue(preUpdateTime > weapon.getReloadingTimeLeft());
		assertTrue(!weapon.isLoaded());

	}
	
	class WeaponImplMockTimer extends SimpleMockTimer {	
	}
}
