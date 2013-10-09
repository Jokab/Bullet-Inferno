package se.dat255.bulletinferno.model.weapon;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import se.dat255.bulletinferno.model.mock.PhysicsWorldImplSpy;
import se.dat255.bulletinferno.model.mock.SimpleMockProjectile;
import se.dat255.bulletinferno.test.Common;

public class WeaponEnvironmentImplTest {

	@BeforeClass
	public static void beforeTests() {
		Common.loadEssentials();
	}
	
	@Test
	public void testRetrieveProjectile() {
		// Tests that retrieving a projectile returns the correct
		// type of projectile and that the projectile is added
		// to the current projectiles.

		WeaponEnvironment weapons = new WeaponEnvironmentImpl(new PhysicsWorldImplSpy());
		assertTrue("The list of projectiles of a new game should be empty",
				weapons.getProjectiles().isEmpty());

		Projectile projectile = weapons
				.retrieveProjectile(SimpleMockProjectile.class);
		assertTrue(
				"retrieveProjectile should return a Projectile of the wanted class-type",
				projectile.getClass() == SimpleMockProjectile.class);

		assertTrue("The projectile should be added to the list of projectiles",
				weapons.getProjectiles().contains(projectile));

	}

	@Test
	public void testDisposeProjectile() {
		WeaponEnvironment weapons = new WeaponEnvironmentImpl(new PhysicsWorldImplSpy());
		Projectile projectile = weapons
				.retrieveProjectile(SimpleMockProjectile.class);
		assertTrue("The projectile should be added to the list of projectiles",
				weapons.getProjectiles().contains(projectile));

		weapons.disposeProjectile(projectile);
		assertTrue("The list of projectiles of a new game should be empty",
				weapons.getProjectiles().isEmpty());
	}
}
