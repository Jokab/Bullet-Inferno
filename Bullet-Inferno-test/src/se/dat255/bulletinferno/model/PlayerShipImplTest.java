package se.dat255.bulletinferno.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import se.dat255.bulletinferno.model.mock.SimpleMockGame;
import se.dat255.bulletinferno.model.mock.SimpleMockProjectile;
import se.dat255.bulletinferno.model.weapon.WeaponData;
import se.dat255.bulletinferno.model.weapon.WeaponImpl;

import com.badlogic.gdx.math.Vector2;

public class PlayerShipImplTest {

	Game mockGame;

	private class MockWeapon extends WeaponImpl {
		private boolean hasFired = false;
		
		public MockWeapon(Game game, float reloadingTime, Class<? extends Projectile> projectile,
				Vector2 offset, Vector2 projectileVelocity, float damage) {
			super(game, reloadingTime, projectile, offset, projectileVelocity, damage);
		}
		
		@Override
		public void fire(Vector2 position, Vector2 direction, Teamable source) {
			hasFired = true;
		}
	}
	
	private class ColidedTestMockProjectile extends SimpleMockProjectile {
		@Override
		public float getDamage() {
			return 19;
		}
	}
	
	// A class that is definitely not a team member of the ship
	private class NonTeamMember implements Teamable, Collidable {
		@Override
		public void preCollided(Collidable other) {}
		@Override
		public void postCollided(Collidable other) {}

		@Override
		public boolean isInMyTeam(Teamable teamMember) {
			return false;
		}
	}
	
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
		PlayerShipImpl playerShip = new PlayerShipImpl(mockGame, position, 100, 
				WeaponData.FAST.getPlayerWeaponForGame(mockGame));

		int health = playerShip.getHealth();
		int initialHealth = playerShip.getInitialHealth();
		
		assertTrue("Check that given health parameter in construct is equal to" + 
				" get health", health == 100);
		
		assertTrue("Check that get health is equal to initial health" + 
				" get health", health == initialHealth);
		
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
		PlayerShipImpl playerShip = new PlayerShipImpl(mockGame, position, 100, 
				WeaponData.FAST.getPlayerWeaponForGame(mockGame));
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
	
	@Test
	public void testFireWeapon() {
		MockWeapon weapon = new MockWeapon(mockGame, 0, null, new Vector2(), new Vector2(), 0);
		PlayerShipImpl playerShip = new PlayerShipImpl(mockGame, new Vector2(), 100, 
				weapon);
		
		playerShip.fireWeapon();
		assertTrue("Check that ship has fired it's weapon", weapon.hasFired);
	}
	
	@Test
	public void testIsInMyTeam() {
		PlayerShipImpl playerShip1 = new PlayerShipImpl(mockGame, new Vector2(), 100, 
				WeaponData.FAST.getPlayerWeaponForGame(mockGame));
		
		PlayerShip playerShip2 = new PlayerShipImpl(mockGame, new Vector2(), 100, 
				WeaponData.FAST.getPlayerWeaponForGame(mockGame));
		
		NonTeamMember enemy = new NonTeamMember();
		
		assertTrue("Check so that two player ships is in the same team", 
				playerShip1.isInMyTeam(playerShip2));
		
		assertFalse("Check so that a me and a pleyer is not in the same team",
				playerShip2.isInMyTeam(enemy));
	}
	
	@Test
	public void testPostCollided() {
		assertTrue("NOP", true);
	}
	
	@Test
	public void testPreCollided() {
		PlayerShipImpl playerShip = new PlayerShipImpl(mockGame, new Vector2(), 100, 
				WeaponData.FAST.getPlayerWeaponForGame(mockGame));
		
		// Create an entity that's not on the ships team
		NonTeamMember enemy = new NonTeamMember();
		
		// Create a projectile and add the enemy as the source
		ColidedTestMockProjectile projectile = new ColidedTestMockProjectile();
		projectile.setSource(enemy);
		
		int preCollisionHealth = playerShip.getHealth();
		
		// Simulate colliding with a projectile
		playerShip.preCollided(projectile);
		assertTrue("Should take specified damage from projectile", 
				playerShip.getHealth() == preCollisionHealth - projectile.getDamage());
		
		// Create another player, i.e. somebody who's in the same team
		PlayerShipImpl playerShip2 = new PlayerShipImpl(mockGame, new Vector2(), 100, 
				WeaponData.FAST.getPlayerWeaponForGame(mockGame));
		projectile = new ColidedTestMockProjectile();
		projectile.setSource(playerShip2);
		preCollisionHealth = playerShip.getHealth();
		
		playerShip.preCollided(playerShip2);
		assertTrue("Should take no damage from projectile, since it's hit by a team member", 
				playerShip.getHealth() == preCollisionHealth);
		
	}

}
