package se.dat255.bulletinferno.model;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.enemy.DefaultEnemyShipImpl;
import se.dat255.bulletinferno.model.enemy.EnemyImplTest;
import se.dat255.bulletinferno.model.enemy.SimpleEnemy;
import se.dat255.bulletinferno.model.mock.SimpleMockGame;
import se.dat255.bulletinferno.model.mock.PhysicsWorldImplSpy.CreateBodyCall;
import se.dat255.bulletinferno.model.mock.PhysicsWorldImplSpy.RemoveBodyCall;
import se.dat255.bulletinferno.model.weapon.WeaponData;

public class ProjectileImplTest {

	SimpleMockGame mockGame;

	@Before
	public void initialize() {
		mockGame = new SimpleMockGame();
	}

	@Test
	public void testGetDamage() {
		// Tests that the projectileImpl has a default damage > 0.
		ProjectileImpl projectile = new ProjectileImpl(mockGame);
		projectile.init(new Vector2(), new Vector2(), 30, null);

		assertTrue("The default damage of a projectile should be above 0",
				projectile.getDamage() > 0);

		projectile.reset();
	}

	@Test
	public void testCollidedOtherProjectileNoDamageChange() {
		Projectile projectileA = new ProjectileImpl(mockGame);
		projectileA.init(new Vector2(), new Vector2(), 30, null);
		float initialDamage = projectileA.getDamage();

		Projectile projectileB = new ProjectileImpl(mockGame);
		projectileB.init(new Vector2(), new Vector2(), 30, null);

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
		projectile.init(new Vector2(), new Vector2(), 30, new Teamable() {
			
			@Override
			public boolean isInMyTeam(Teamable teamMember) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		float initialDamage = projectile.getDamage();

		PlayerShip ship = new PlayerShipImpl(mockGame, new Vector2(), 10, 
				WeaponData.STANDARD.getPlayerWeaponForGame(mockGame));

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
		PlayerShip sourceShip = new PlayerShipImpl(mockGame, new Vector2(), 10, 
				WeaponData.STANDARD.getPlayerWeaponForGame(mockGame));
		
		// Set the ship as the source
		projectile.init(new Vector2(), new Vector2(), 30, sourceShip);
		
		float initialDamage = projectile.getDamage();
		projectile.postCollided(sourceShip);
		// Should not be affected on impact, i.e. damage should be unchanged
		assertTrue("A projectile should not be able to hit it's own source",
				projectile.getDamage() == initialDamage);
	}
	
	
	@Test
	public void testSameTeamSource() {
		Projectile projectile = new ProjectileImpl(mockGame);
		
		SimpleEnemy enemy1 = new DefaultEnemyShipImpl(mockGame, new Vector2(), new Vector2(), 10, 
				WeaponData.STANDARD.getPlayerWeaponForGame(mockGame), 0, 0);
		
		SimpleEnemy enemy2 = new DefaultEnemyShipImpl(mockGame, new Vector2(), new Vector2(), 20, 
				WeaponData.STANDARD.getPlayerWeaponForGame(mockGame), 0, 0);
		
		PlayerShip sourceShip = new PlayerShipImpl(mockGame, new Vector2(), 10, 
				WeaponData.STANDARD.getPlayerWeaponForGame(mockGame));
		
		// Set the ship as the source
		projectile.init(new Vector2(), new Vector2(), 30, enemy1);
		
		float initialDamage = projectile.getDamage();
		projectile.postCollided(enemy2);
		
		// Should not be affected on impact, i.e. damage should be unchanged
		assertTrue("A projectile should not be able to hit it's own team mate",
				projectile.getDamage() == initialDamage);
		
		// While it should be hit by a non member of team, i.e. damage = 0
		projectile.postCollided(sourceShip);
		assertTrue("A projectile should not be able to hit it's own team mate",
				projectile.getDamage() == 0);
	}
	
	@Test
	public void testPhysicsBodyAddedCollidedRemoved() {
		Projectile projectile = new ProjectileImpl(mockGame);
		projectile.init(new Vector2(), new Vector2(), 30, new Teamable() {
			
			@Override
			public boolean isInMyTeam(Teamable teamMember) {
				// TODO Auto-generated method stub
				return false;
			}
		});

		PlayerShip ship = new PlayerShipImpl(mockGame, new Vector2(), 10, 
				WeaponData.STANDARD.getPlayerWeaponForGame(mockGame));

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