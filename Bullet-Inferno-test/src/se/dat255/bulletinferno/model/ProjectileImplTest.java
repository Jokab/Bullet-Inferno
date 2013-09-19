package se.dat255.bulletinferno.model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ProjectileImplTest {

	@Test
	public void testGetDamage() {
		// Tests that the projectileImpl has a default damage >= 0.
		ProjectileImpl projectile = new ProjectileImpl(null, null);
		assertTrue(projectile.getDamage() >= 0);
	}

}
