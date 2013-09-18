package se.dat255.bulletinferno.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

public class WeaponImplTest {

	@Test
	public void testGetReloadingTime() {
		// Tests that the reloading time is set in the constructor

		WeaponImpl weapon = new WeaponImpl(20);
		assertEquals(weapon.getReloadingTime(), 20);
	}

	@Test
	public void testGetReloadingTimeLeft() {
		// Tests that a newly created weapon is not reloading

		WeaponImpl weapon = new WeaponImpl(20);
		assertTrue(weapon.getReloadingTimeLeft() <= 0);
	}

	@Test
	public void testIsLoaded() {
		// Tests that a newly created weapon is loaded
		// Essentially the same test as above

		WeaponImpl weapon = new WeaponImpl(20);
		assertTrue(weapon.isLoaded());
	}

	@Test
	public void testGetOffset() {
		// Tests that the offset is always set, and if
		// provided in the constructor is set to that value

		WeaponImpl weapon = new WeaponImpl(20);
		assertNotNull(weapon.getOffset());

		Vector2 offset = new Vector2();
		WeaponImpl weapon1 = new WeaponImpl(20, offset);
		assertEquals(weapon1.getOffset(), offset);
	}

	@Test
	public void testFire() {
		// Tests that the fire method adds a projectile to the world
		// at the provided origin position "+" the offset,
		// and that it triggers a cool-down on the weapon.

		WeaponImpl weapon = new WeaponImpl(20, new Vector2(1, 1));
		Vector2 origin = new Vector2(1, 1);
		weapon.fire(origin);

		// TODO: Make sure fire adds a projectile to the world

		// After firing, time left should be the reloading time
		assertTrue(weapon.getReloadingTimeLeft() == weapon.getReloadingTime());
	}

	@Test
	public void testGetProjectile() {
		// Tests that a projectile is returned from the
		// getProjectile method

		WeaponImpl weapon = new WeaponImpl(20, new Vector2(1, 1));
		Projectile projectile = weapon.getProjectile(new Vector2(0, 0));
		assertNotNull(projectile);
	}

	@Test
	public void testUpdate() {
		// Tests that the update function updates the
		// cool-down of the weapon

		WeaponImpl weapon = new WeaponImpl(20, new Vector2(1, 1));
		weapon.fire(new Vector2());

		int preUpdateTime = weapon.getReloadingTimeLeft();
		weapon.update(1);
		assertTrue(preUpdateTime > weapon.getReloadingTimeLeft());

	}
}
