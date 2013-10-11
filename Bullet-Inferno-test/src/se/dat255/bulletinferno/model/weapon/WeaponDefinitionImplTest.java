package se.dat255.bulletinferno.model.weapon;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.map.ObstacleDefinitionImpl;
import se.dat255.bulletinferno.model.mock.PhysicsWorldImplSpy;
import se.dat255.bulletinferno.model.mock.SimpleMockTimer;
import se.dat255.bulletinferno.model.mock.WeaponMockEnvironment;
import se.dat255.bulletinferno.test.Common;

public class WeaponDefinitionImplTest {

	@BeforeClass
	public static void beforeTests() {
		Common.loadEssentials();	
	}
	
	private PhysicsWorldImplSpy physics;
	private WeaponEnvironment weapons;
	
	@Before
	public void initialize() {
		physics = new PhysicsWorldImplSpy(new SimpleMockTimer());
		weapons = new WeaponMockEnvironment();
	}

	@Test
	public void testReloadingTime() {
		for (WeaponDefinitionImpl weaponDefinition : WeaponDefinitionImpl.values()) {
			Weapon weapon = weaponDefinition.createWeapon(physics, weapons, new Vector2());

			assertTrue(
					"The reloading time of the slow weapon should be the same as the weapon's " +
					"reloading time created from the SLOW enum." + "FAILING: " + weaponDefinition,
					weaponDefinition.getReloadTime() == weapon.getReloadingTime());
		}
	}

	@Test
	public void testAutomaticWeapon() {
		Weapon weapon = WeaponDefinitionImpl.FORCE_GUN.createWeapon(physics, weapons, new Vector2());

		assertTrue(
				"When you retrieve a force gun weapon, it should be an automatic weapon.",
				weapon.getClass() == AutomaticWeaponImpl.class);
	}
	
	@Test
	public void testCooldownWeapon() {
		// Get an automatic weapon
		Weapon weapon = WeaponDefinitionImpl.MISSILE_LAUNCHER.createWeapon(physics, weapons,
				new Vector2());

		assertTrue(
				"When you retrieve a missile lanucher weapon, it should be cooldown weapon.",
				weapon.getClass() == CooldownWeaponImpl.class);
	}
}
