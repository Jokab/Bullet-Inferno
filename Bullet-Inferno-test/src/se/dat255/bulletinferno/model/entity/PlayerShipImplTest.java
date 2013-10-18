package se.dat255.bulletinferno.model.entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import se.dat255.bulletinferno.model.entity.PlayerShipImpl.ShipType;
import se.dat255.bulletinferno.model.mock.EntityMockEnvironment;
import se.dat255.bulletinferno.model.mock.HealthMockListener;
import se.dat255.bulletinferno.model.mock.PhysicsWorldImplSpy;
import se.dat255.bulletinferno.model.mock.SimpleMockProjectile;
import se.dat255.bulletinferno.model.mock.SimpleMockTimer;
import se.dat255.bulletinferno.model.mock.WeaponMockEnvironment;
import se.dat255.bulletinferno.model.physics.Collidable;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.team.Teamable;
import se.dat255.bulletinferno.model.weapon.ProjectileDefinition;
import se.dat255.bulletinferno.model.weapon.ProjectileDefinitionImpl;
import se.dat255.bulletinferno.model.weapon.WeaponDefinition;
import se.dat255.bulletinferno.model.weapon.WeaponDefinitionImpl;
import se.dat255.bulletinferno.model.weapon.WeaponImpl;
import se.dat255.bulletinferno.model.weapon.WeaponLoadout;
import se.dat255.bulletinferno.model.weapon.WeaponLoadoutImpl;
import se.dat255.bulletinferno.test.Common;

import com.badlogic.gdx.math.Vector2;

public class PlayerShipImplTest {

	@BeforeClass
	public static void beforeTests() {
		Common.loadEssentials();	
	}
	
	PhysicsEnvironment physics;
	WeaponMockEnvironment weapons;
	EntityEnvironment entityEnvironment;
	PlayerShipImpl playerShip;
	WeaponLoadout loadout;
	Vector2 startPosition;
	
	
	private class MockWeapon extends WeaponImpl {
		private boolean hasFired = false;
		
		public MockWeapon(WeaponDefinition type, float reloadingTime, ProjectileDefinition projectileType, float velocity) {
			super(physics, weapons, type, reloadingTime, projectileType, velocity);
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
		physics = new PhysicsWorldImplSpy(new SimpleMockTimer());
		weapons = new WeaponMockEnvironment();
		
		startPosition = new Vector2(8, 9);
		
		loadout = new WeaponLoadoutImpl(
				WeaponDefinitionImpl.STANDARD_MACHINE_GUN.createWeapon(physics, weapons, new Vector2()), 
				WeaponDefinitionImpl.MISSILE_LAUNCHER.createWeapon(physics, weapons, new Vector2()));
		
		playerShip = new PlayerShipImpl(physics, new EntityMockEnvironment(), 
				startPosition, loadout, ShipType.PLAYER_DEFAULT, new HealthMockListener());
		
	}

	@Test
	public void testGetPosition() {
		// Tests the set and get position methods

		assertTrue("The position should be set in the constructor", 
				startPosition.equals(playerShip.getPosition()));
	}

	@Test
	public void testTakeDamage() {
		// Tests that taking damage reduces the health
		// but doesn't reduce the initial health value


		float health = playerShip.getHealth();
		float initialHealth = playerShip.getInitialHealth();
		
		assertTrue("Check that given health parameter in construct is equal to" + 
				" get health", health == 1);
		
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
		startPosition.x = 20;

		assertTrue(
				"Changing the vector outside the object should not affect the object",
				playerShip.getPosition().x != startPosition.x);

	}
	
	@Test
	public void testFireWeapon() {
		MockWeapon weapon = new MockWeapon(WeaponDefinitionImpl.MISSILE_LAUNCHER, 0, 
				ProjectileDefinitionImpl.RED_PROJECTILE, 0);
		WeaponLoadout loadout = new WeaponLoadoutImpl(weapon, weapon);
		playerShip = new PlayerShipImpl(physics, new EntityMockEnvironment(), 
				new Vector2(), loadout, ShipType.PLAYER_DEFAULT, new HealthMockListener());
		
		playerShip.fireWeapon();
		assertTrue("Check that ship has fired it's weapon", weapon.hasFired);
	}
	
	@Test
	public void testIsInMyTeam() {
		PlayerShipImpl playerShip1 = new PlayerShipImpl(physics, new EntityMockEnvironment(), 
				new Vector2(), loadout, ShipType.PLAYER_DEFAULT, new HealthMockListener());
		
		PlayerShip playerShip2 = new PlayerShipImpl(physics, new EntityMockEnvironment(), 
				new Vector2(), loadout, ShipType.PLAYER_DEFAULT, new HealthMockListener());
		
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
		// Create an entity that's not on the ships team
		NonTeamMember enemy = new NonTeamMember();
		
		// Create a projectile and add the enemy as the source
		CollidedTestMockProjectile projectile = new CollidedTestMockProjectile();
		projectile.setSource(enemy);
		
		float preCollisionHealth = playerShip.getHealth();
		
		// Simulate colliding with a projectile
		playerShip.preCollided(projectile);
		assertTrue("Should take specified damage from projectile", 
				playerShip.getHealth() == preCollisionHealth - projectile.getDamage());
		
		// Create another player, i.e. somebody who's in the same team
		PlayerShipImpl playerShip2 = new PlayerShipImpl(physics, new EntityMockEnvironment(), 
				new Vector2(), loadout, ShipType.PLAYER_DEFAULT, new HealthMockListener());
		projectile = new CollidedTestMockProjectile();
		projectile.setSource(playerShip2);
		preCollisionHealth = playerShip.getHealth();
		
		playerShip.preCollided(playerShip2);
		assertTrue("Should take no damage from projectile, since it's hit by a team member", 
				playerShip.getHealth() == preCollisionHealth);
		
	}

}
