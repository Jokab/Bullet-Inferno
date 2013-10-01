package se.dat255.bulletinferno.model;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import se.dat255.bulletinferno.model.mock.SimpleMockGame;
import se.dat255.bulletinferno.model.weapon.EnemyWeaponImpl;
import se.dat255.bulletinferno.model.weapon.WeaponData;
import se.dat255.bulletinferno.model.weapon.WeaponImpl;
import se.dat255.bulletinferno.test.Common;

public class WeaponDataTest {

	@BeforeClass
	public static void beforeTests() {
		Common.loadEssentials();	
	}
	
	private SimpleMockGame mockGame;

	@Before
	public void initialize() {
		mockGame = new SimpleMockGame();
	}

	@Test
	public void testFastWeaponDamage() {
		Weapon weapon = WeaponData.FAST.getPlayerWeaponForGame(mockGame);

		assertTrue(
				"The damage of the fast weapon should be the same as the weapon's damage created from the FAST enum.",
				WeaponData.FAST.getDamage() == weapon.getDamage());
	}

	@Test
	public void testSlowWeaponReloadingTime() {
		Weapon weapon = WeaponData.SLOW.getPlayerWeaponForGame(mockGame);

		assertTrue(
				"The reloading time of the slow weapon should be the same as the weapon's reloading time created from the SLOW enum.",
				WeaponData.SLOW.getReloadTime() == weapon.getReloadingTime());
	}

	@Test
	public void testIsFastEnemyWeapon() {
		Weapon weapon = WeaponData.FASTENEMY.getEnemyWeaponForGame(mockGame);

		assertTrue(
				"When you retrieve an enemy weapon, it should be an enemy weapon.",
				weapon.getClass() == EnemyWeaponImpl.class);
	}
	
	@Test
	public void testIsFastPlayerWeapon() {
		Weapon weapon = WeaponData.FASTENEMY.getPlayerWeaponForGame(mockGame);

		assertTrue(
				"When you retrieve a player weapon, it should be player weapon.",
				weapon.getClass() == WeaponImpl.class);
	}
}
