package se.dat255.bulletinferno.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import se.dat255.bulletinferno.model.entity.PlayerShip;
import se.dat255.bulletinferno.model.entity.PlayerShipImpl;
import se.dat255.bulletinferno.model.entity.PlayerShipImpl.ShipType;
import se.dat255.bulletinferno.model.loadout.Loadout;
import se.dat255.bulletinferno.model.loadout.LoadoutImpl;
import se.dat255.bulletinferno.model.mock.SimpleMockGame;
import se.dat255.bulletinferno.model.mock.SimpleMockProjectile;
import se.dat255.bulletinferno.model.physics.Collidable;
import se.dat255.bulletinferno.model.team.Teamable;
import se.dat255.bulletinferno.model.weapon.ProjectileType;
import se.dat255.bulletinferno.model.weapon.WeaponData;
import se.dat255.bulletinferno.model.weapon.WeaponDescription;
import se.dat255.bulletinferno.model.weapon.WeaponImpl;
import se.dat255.bulletinferno.test.Common;

import com.badlogic.gdx.math.Vector2;

public class PlayerShipImplTest {

	@BeforeClass
	public static void beforeTests() {
		Common.loadEssentials();	
	}
	
	Game mockGame;

	private class MockWeapon extends WeaponImpl {
		private boolean hasFired = false;
		
		public MockWeapon(WeaponDescription type, Game game, float reloadingTime, ProjectileType projectileType,
				Vector2 offset, float velocity) {
			super(type, game, reloadingTime, projectileType, offset, velocity);
		}
		
		@Override
		public void fire(Vector2 position, Vector2 direction, Teamable source) {
			hasFired = true;
		}
	}
	
	private class CollidedTestMockProjectile extends SimpleMockProjectile {
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
	public void testGetPosition() {
		// Tests the set and get position methods

		Vector2 position = new Vector2(8, 9);
		Loadout loadout = new LoadoutImpl(WeaponData.STANDARD.getPlayerWeaponForGame(mockGame), null, null, null);
		PlayerShipImpl playerShip = new PlayerShipImpl(mockGame, position, 100, loadout, ShipType.PLAYER_DEFAULT);

		assertTrue("The position should be set in the constructor", 
				position.equals(playerShip.getPosition()));
	}

	@Test
	public void testTakeDamage() {
		// Tests that taking damage reduces the health
		// but doesn't reduce the initial health value

		Vector2 position = new Vector2(0, 0);
		Loadout loadout = new LoadoutImpl(WeaponData.STANDARD.getPlayerWeaponForGame(mockGame), null, null, null);
		PlayerShipImpl playerShip = new PlayerShipImpl(mockGame, position, 100, 
				loadout, ShipType.PLAYER_DEFAULT);

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
		Loadout loadout = new LoadoutImpl(WeaponData.STANDARD.getPlayerWeaponForGame(mockGame), null, null, null);
		PlayerShipImpl playerShip = new PlayerShipImpl(mockGame, position, 100, 
				loadout, ShipType.PLAYER_DEFAULT);
		position.x = 20;

		assertTrue(
				"Changing the vector outside the object should not affect the object",
				playerShip.getPosition().x != position.x);

	}
	
	@Test
	public void testFireWeapon() {
		MockWeapon weapon = new MockWeapon(WeaponData.MISSILE_LAUNCHER, mockGame, 0, ProjectileType.RED_PROJECTILE, new Vector2(), 0);
		Loadout loadout = new LoadoutImpl(weapon, null, null, null);
		PlayerShipImpl playerShip = new PlayerShipImpl(mockGame, new Vector2(), 100, 
				loadout, ShipType.PLAYER_DEFAULT);
		
		playerShip.fireWeapon();
		assertTrue("Check that ship has fired it's weapon", weapon.hasFired);
	}
	
	@Test
	public void testIsInMyTeam() {
		Loadout loadout = new LoadoutImpl(WeaponData.STANDARD.getPlayerWeaponForGame(mockGame), null, null, null);
		PlayerShipImpl playerShip1 = new PlayerShipImpl(mockGame, new Vector2(), 100, 
				loadout, ShipType.PLAYER_DEFAULT);
		
		PlayerShip playerShip2 = new PlayerShipImpl(mockGame, new Vector2(), 100, 
				loadout, ShipType.PLAYER_DEFAULT);
		
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
		Loadout loadout = new LoadoutImpl(WeaponData.STANDARD.getPlayerWeaponForGame(mockGame), null, null, null);
		PlayerShipImpl playerShip = new PlayerShipImpl(mockGame, new Vector2(), 100, 
				loadout, ShipType.PLAYER_DEFAULT);
		
		// Create an entity that's not on the ships team
		NonTeamMember enemy = new NonTeamMember();
		
		// Create a projectile and add the enemy as the source
		CollidedTestMockProjectile projectile = new CollidedTestMockProjectile();
		projectile.setSource(enemy);
		
		int preCollisionHealth = playerShip.getHealth();
		
		// Simulate colliding with a projectile
		playerShip.preCollided(projectile);
		assertTrue("Should take specified damage from projectile", 
				playerShip.getHealth() == preCollisionHealth - projectile.getDamage());
		
		// Create another player, i.e. somebody who's in the same team
		PlayerShipImpl playerShip2 = new PlayerShipImpl(mockGame, new Vector2(), 100, 
				loadout, ShipType.PLAYER_DEFAULT);
		projectile = new CollidedTestMockProjectile();
		projectile.setSource(playerShip2);
		preCollisionHealth = playerShip.getHealth();
		
		playerShip.preCollided(playerShip2);
		assertTrue("Should take no damage from projectile, since it's hit by a team member", 
				playerShip.getHealth() == preCollisionHealth);
		
	}

}
