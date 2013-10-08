package se.dat255.bulletinferno.model.weapon;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.entity.PlayerShip;
import se.dat255.bulletinferno.model.entity.PlayerShipImpl;
import se.dat255.bulletinferno.model.entity.PlayerShipImpl.ShipType;
import se.dat255.bulletinferno.model.mock.SimpleMockGame;
import se.dat255.bulletinferno.model.mock.PhysicsWorldImplSpy.CreateBodyCall;
import se.dat255.bulletinferno.model.mock.PhysicsWorldImplSpy.RemoveBodyCall;
import se.dat255.bulletinferno.model.physics.Collidable;
import se.dat255.bulletinferno.model.physics.PhysicsBody;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinitionImpl;
import se.dat255.bulletinferno.model.team.Teamable;
import se.dat255.bulletinferno.model.weapon.WeaponLoadoutImpl;
import se.dat255.bulletinferno.model.weapon.Projectile;
import se.dat255.bulletinferno.model.weapon.ProjectileImpl;
import se.dat255.bulletinferno.model.weapon.ProjectileType;
import se.dat255.bulletinferno.model.weapon.WeaponLoadout;
import se.dat255.bulletinferno.model.weapon.WeaponDefinitionImpl;
import se.dat255.bulletinferno.test.Common;
import se.dat255.bulletinferno.util.PhysicsShapeFactory;

public class ProjectileImplTest {

	@BeforeClass
	public static void beforeTests() {
		Common.loadEssentials();	
	}
	
	SimpleMockGame mockGame;

	@Before
	public void initialize() {
		mockGame = new SimpleMockGame();
	}

	@Test
	public void testGetDamage() {
		// Tests that the projectileImpl has a default damage > 0.
		ProjectileImpl projectile = new ProjectileImpl(mockGame);
		projectile.init(ProjectileType.RED_PROJECTILE, new Vector2(), new Vector2(), 30, null, 
				new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(0.25f,0.25f)));

		assertTrue("The default damage of a projectile should be above 0",
				projectile.getDamage() > 0);

		projectile.reset();
	}

	@Test
	public void testCollidedOtherProjectileNoDamageChange() {
		Projectile projectileA = new ProjectileImpl(mockGame);
		projectileA.init(ProjectileType.RED_PROJECTILE,new Vector2(), new Vector2(), 30, null,
				new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(0.25f,0.25f)));
		float initialDamage = projectileA.getDamage();

		Projectile projectileB = new ProjectileImpl(mockGame);
		projectileB.init(ProjectileType.RED_PROJECTILE,new Vector2(), new Vector2(), 30, null,
				new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(0.25f,0.25f)));

		projectileA.preCollided(projectileB);
		assertTrue("A projectile should not take damage upon hit by another projectile (pre)",
				projectileA.getDamage() == initialDamage);

		projectileA.postCollided(projectileB);
		assertTrue("A projectile should not take damage upon hit by another projectile (post)",
				projectileA.getDamage() == initialDamage);

		projectileA.reset();
		projectileB.reset();
	}

	@Test
	public void testCollidedDamageDecreasePostCollision() {
		Projectile projectile = new ProjectileImpl(mockGame);
		projectile.init(ProjectileType.RED_PROJECTILE,new Vector2(), new Vector2(), 30, new Teamable() {
			
			@Override
			public boolean isInMyTeam(Teamable teamMember) {
				return false;
			}
		}, new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(0.25f,0.25f)));
		float initialDamage = projectile.getDamage();

		Loadout loadout = new LoadoutImpl(WeaponDefinitionImpl.STANDARD.getPlayerWeaponForGame(mockGame), null, null, null);
		PlayerShip ship = new PlayerShipImpl(mockGame, new Vector2(), 10, 
				loadout, ShipType.PLAYER_DEFAULT);

		// If your change fails this test: think again! The order of collision pairs is not defined!
		projectile.preCollided(ship);
		assertTrue("A projectile should not change damage in preCollided",
				projectile.getDamage() == initialDamage);

		projectile.postCollided(ship);
		assertTrue("A projectile should get damage 0 when hitting a ship in postCollided",
				projectile.getDamage() == 0);

		// Called when hit: projectile.reset();
	}

	@Test
	public void testCollidedWithSource() {
		Projectile projectile = new ProjectileImpl(mockGame);
		Loadout loadout = new LoadoutImpl(WeaponDefinitionImpl.STANDARD.createWeapon(mockGame), null, null, null);
		PlayerShip sourceShip = new PlayerShipImpl(mockGame, new Vector2(), 10, loadout, ShipType.PLAYER_DEFAULT);
		
		// Set the ship as the source
		projectile.init(ProjectileType.RED_PROJECTILE,new Vector2(), new Vector2(), 30, sourceShip,
				new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(0.25f,0.25f)));
		
		float initialDamage = projectile.getDamage();
		projectile.postCollided(sourceShip);
		// Should not be affected on impact, i.e. damage should be unchanged
		assertTrue("A projectile should not be able to hit it's own source",
				projectile.getDamage() == initialDamage);
	}
	
	private class TeamA implements Teamable, Collidable {
		@Override
		public void preCollided(Collidable other) {}
		@Override
		public void postCollided(Collidable other) {}

		@Override
		public boolean isInMyTeam(Teamable teamMember) {
			return teamMember instanceof TeamA;
		}
	}
	private class TeamB implements Teamable, Collidable {
		@Override
		public void preCollided(Collidable other) {}
		@Override
		public void postCollided(Collidable other) {}

		@Override
		public boolean isInMyTeam(Teamable teamMember) {
			return teamMember instanceof TeamB;
		}
	}
	@Test
	public void testSameTeamSource() {
		Projectile projectile = new ProjectileImpl(mockGame);
		
		TeamA teamA1 = new TeamA();
		TeamA teamA2 = new TeamA();
		TeamB teamB = new TeamB();
		// Set team A as the source
		projectile.init(ProjectileType.RED_PROJECTILE, new Vector2(), new Vector2(), 30, teamA1,
				new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(0.25f,0.25f)));
		
		float initialDamage = projectile.getDamage();
		projectile.postCollided(teamA2);
		// Should not be affected on impact, i.e. damage should be unchanged
		assertTrue("A projectile should not be able to hit it's source's own team mate",
				projectile.getDamage() == initialDamage);
		
		
		// While it should be hit by a non member of team, i.e. damage = 0
		projectile.postCollided(teamB);
		assertTrue("A projectile should be able to hit a non team mate of it's source",
				projectile.getDamage() == 0);
	}
	
	@Test
	public void testPhysicsBodyAddedCollidedRemoved() {
		Projectile projectile = new ProjectileImpl(mockGame);
		projectile.init(ProjectileType.RED_PROJECTILE, new Vector2(), new Vector2(), 30, new Teamable() {
			
			@Override
			public boolean isInMyTeam(Teamable teamMember) {
				return false;
			}
		}, new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(0.25f,0.25f)));
		Loadout loadout = new LoadoutImpl(WeaponDefinitionImpl.STANDARD.getPlayerWeaponForGame(mockGame), null, null, null);
		PlayerShip ship = new PlayerShipImpl(mockGame, new Vector2(), 10, 
				loadout, ShipType.PLAYER_DEFAULT);

		PhysicsBody body = null;
		for (CreateBodyCall call : mockGame.physicsWorld.createBodyCalls) {
			if (call.collidable == projectile) {
				body = call.returnValue;
				break;
			}
		}
		assertTrue("Adds a physics body to the physics world upon construction", body != null);

		boolean projectilePresentPreCollision = false;
		for (RemoveBodyCall call : mockGame.physicsWorld.removeBodyCalls) {
			if (call.body == body) {
				projectilePresentPreCollision = true;
				break;
			}
		}
		assertTrue("Does not remove the body from the physics world immediately",
				!projectilePresentPreCollision);

		projectile.preCollided(ship);
		projectile.postCollided(ship);
		boolean projectileRemovedPostCollision = false;
		for (RemoveBodyCall call : mockGame.physicsWorld.removeBodyCalls) {
			if (call.body == body) {
				projectileRemovedPostCollision = true;
				break;
			}
		}
		assertTrue("Does remove the physics body of a projectile from the world upon ship hits",
				projectileRemovedPostCollision);

		// Called when hit: projectile.reset();
	}

}