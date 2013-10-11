package se.dat255.bulletinferno.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import se.dat255.bulletinferno.model.mock.EntityMockEnvironment;
import se.dat255.bulletinferno.model.mock.PhysicsWorldImplSpy;
import se.dat255.bulletinferno.model.mock.SimpleMockProjectile;
import se.dat255.bulletinferno.model.mock.SimpleMockScoreListener;
import se.dat255.bulletinferno.model.mock.SimplePhysicsMovementPatternMock;
import se.dat255.bulletinferno.model.physics.Collidable;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinitionImpl;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.team.Teamable;
import se.dat255.bulletinferno.model.weapon.Weapon;
import se.dat255.bulletinferno.test.Common;
import se.dat255.bulletinferno.util.PhysicsShapeFactory;

import com.badlogic.gdx.math.Vector2;

public class EnemyImplTest {

	@BeforeClass
	public static void beforeTests() {
		Common.loadEssentials();
	}

	private class EnemyMockup extends SimpleEnemy {
		public EnemyMockup(EnemyDefinitionImpl type, Vector2 position, Vector2 velocity, 
				int initialHealth, Weapon[] weapon, Vector2[] weaponPositionModifier, int score, int credits) {
			super(new PhysicsWorldImplSpy(), new EntityMockEnvironment(), type, position, velocity,
					initialHealth, weapon, score,
					credits, 
					new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(1, 1)), 
					new SimplePhysicsMovementPatternMock(),
					new SimpleMockScoreListener());
		}
		public EnemyMockup(PhysicsEnvironment physics, EntityEnvironment entities, 
				EnemyDefinitionImpl type, Vector2 position, Vector2 velocity, 
				int initialHealth, Weapon[] weapon, int score, int credits) {
			super(physics, entities, type, position, velocity,
					initialHealth, weapon, score,
					credits, 
					new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(1, 1)), 
					new SimplePhysicsMovementPatternMock(),
					new SimpleMockScoreListener());
		}

	}


	// A class that is definitely not a team member of the enemy
	private class NonTeamMember implements Teamable, Collidable {
		@Override
		public void preCollided(Collidable other) {
		}

		@Override
		public void postCollided(Collidable other) {
		}

		@Override
		public boolean isInMyTeam(Teamable teamMember) {
			return false;
		}
	}

	private class EemyEntityEnvironmentMockup extends EntityMockEnvironment {
		private Enemy enemy = null;

		@Override
		public void removeEnemy(Enemy enemy) {
			this.enemy = enemy;
		}

		public boolean isEnemyRemoved() {
			return enemy != null;
		}

		public Enemy getRemovedEnemy() {
			return enemy;
		}
	}

	private class ColidedTestMockProjectile extends SimpleMockProjectile {
		@Override
		public float getDamage() {
			return 19;
		}
	}

	@Test
	public void testGetScore() {
		// Set up a new enemy with score 99

		SimpleEnemy enemy = new EnemyMockup(EnemyDefinitionImpl.DEFAULT_ENEMY_SHIP, new Vector2(), 
				new Vector2(), 0, new Weapon[] {}, new Vector2[] {}, 99, 0);
		assertTrue("Enemy score should be = 99", enemy.getScore() == 99);
	}

	@Test
	public void testGetCredits() {
		// Set up a new enemy with credits of 65

		SimpleEnemy enemy = new EnemyMockup(EnemyDefinitionImpl.DEFAULT_ENEMY_SHIP, new Vector2(), 
				new Vector2(), 0, new Weapon[] {}, new Vector2[] {}, 0, 65);
		assertTrue("Enemy credits should be = 65", enemy.getCredits() == 65);
	}

	@Test
	public void testPreCollided() {

		SimpleEnemy enemy = new EnemyMockup(EnemyDefinitionImpl.DEFAULT_ENEMY_SHIP, new Vector2(),
				new Vector2(), 100, new Weapon[] {}, new Vector2[] {}, 0, 0);
		SimpleEnemy enemy2 = new EnemyMockup(EnemyDefinitionImpl.DEFAULT_ENEMY_SHIP, new Vector2(), 
				new Vector2(), 100, new Weapon[] {}, new Vector2[] {}, 0, 0);
		SimpleMockProjectile projectile = new ColidedTestMockProjectile();

		// Set another enemy as source and check that friendly fire is't enabled
		projectile.setSource(enemy2);
		int preCollisionHealth = enemy.getHealth();

		// Simulate colliding with a projectile
		enemy.preCollided(projectile);
		assertTrue("Should not take damage from projectile, since it's hit by it's own" +
				" team mate",
				enemy.getHealth() == preCollisionHealth);

		// Reset and go again with a non team member
		NonTeamMember nonTeam = new NonTeamMember();
		projectile = new ColidedTestMockProjectile();
		projectile.setSource(nonTeam);
		preCollisionHealth = enemy.getHealth();
		
		// Wake enemy by saying it's in the viewport
		enemy.viewportIntersectionBegin();
		
		enemy.preCollided(projectile);
		assertTrue("Should take specified damage from projectile",
				enemy.getHealth() == preCollisionHealth - projectile.getDamage());
		
	}

	@Test
	public void testPostCollided() {
		assertTrue("Should do nothing", true);
	}

	@Test
	public void testGetHealth() {
		// Set up a new enemy with a health of 101

		SimpleEnemy enemy = new EnemyMockup(EnemyDefinitionImpl.DEFAULT_ENEMY_SHIP, new Vector2(), 
				new Vector2(), 101, new Weapon[] {}, new Vector2[] {}, 0, 0);
		assertTrue("Enemy health should be = 101", enemy.getHealth() == 101);

		enemy.takeDamage(10);
		assertTrue("Enemy health should be = 91", enemy.getHealth() == 91);
	}

	@Test
	public void testTakeDamage() {
		// Set up a new enemy with a health of 101
		PhysicsWorldImplSpy physics = new PhysicsWorldImplSpy();
		EemyEntityEnvironmentMockup entities = new EemyEntityEnvironmentMockup();
		SimpleEnemy enemy = new EnemyMockup(physics, entities, EnemyDefinitionImpl.DEFAULT_ENEMY_SHIP, 
				new Vector2(),new Vector2(), 101, new Weapon[] {}, 0, 0);

		// Take 61 damage
		enemy.takeDamage(61);
		assertTrue("Enemy health should be = 40", enemy.getHealth() == (101 - 61));

		// Take 40 more in damage, i.e. it should die
		// remove itself from the game and run dispose
		enemy.takeDamage(40);

		// Fake a next frame tick, as to make runLater run.
		physics.update(1);

		assertTrue("Enemy should be dead, i.e. health=0", enemy.getHealth() == 0);
		assertTrue("The enemy should be removed from the game when its health reaches 0",
				entities.isEnemyRemoved());
		assertEquals("The enemy removed should be the enemy that died", enemy,
				entities.getRemovedEnemy());

		assertEquals("Enemy should do remove body once",
				physics.removeBodyCalls.size(), 1);

		// Take some extra damage to check that it dosen't die again
		enemy.takeDamage(90);

		assertEquals("Enemy should only do 1 remove body call",
				physics.removeBodyCalls.size(), 1);

	}

	@Test
	public void testDispose() {
		PhysicsWorldImplSpy physics = new PhysicsWorldImplSpy();
		EemyEntityEnvironmentMockup entities = new EemyEntityEnvironmentMockup();
		SimpleEnemy enemy = new EnemyMockup(physics, entities, 
				EnemyDefinitionImpl.DEFAULT_ENEMY_SHIP, new Vector2(), new Vector2(), 101, new Weapon[] {}, 0, 0);
		enemy.dispose();
		assertEquals("Enemy should do remove body once",
				physics.removeBodyCalls.size(), 1);
	}

	@Test
	public void testGetInitialHealth() {
		// Set up a new enemy with a health of 101

		SimpleEnemy enemy = new EnemyMockup(EnemyDefinitionImpl.DEFAULT_ENEMY_SHIP, new Vector2(),
				new Vector2(), 98, new Weapon[] {}, new Vector2[] {}, 0, 0);
		assertTrue("Enemy initial health should be = 98", enemy.getInitialHealth() == 98);

		enemy.takeDamage(10);
		assertTrue("Enemy initial health should still be = 98, after taking more hits",
				enemy.getInitialHealth() == 98);
	}

	@Test
	public void testGetPosition() {

		Vector2 position = new Vector2(2, 3);
		SimpleEnemy enemy = new EnemyMockup(EnemyDefinitionImpl.DEFAULT_ENEMY_SHIP, position,
				new Vector2(), 98, new Weapon[] {}, new Vector2[] {}, 0, 0);

		assertTrue("Check that position is equal enemy's position",
				position.equals(enemy.getPosition()));
		// Set position to something else, and check for alias problems
		position.x = 4;
		position.y = 5;
		assertFalse("Check for alias problems", position.equals(enemy.getPosition()));
		assertFalse("Check for alias problems", position == enemy.getPosition());
	}

	@Test
	public void testIsInMyTeam() {

		SimpleEnemy enemy1 = new EnemyMockup(EnemyDefinitionImpl.DEFAULT_ENEMY_SHIP, new Vector2(),
				new Vector2(), 98, new Weapon[] {}, new Vector2[] {}, 0, 0);

		Enemy enemy2 = new EnemyMockup(EnemyDefinitionImpl.DEFAULT_ENEMY_SHIP, new Vector2(),
				new Vector2(), 98, new Weapon[] {}, new Vector2[] {}, 0, 0);

		class AnotherEnemy extends SimpleEnemy {
			public AnotherEnemy(EnemyDefinitionImpl type, Vector2 position, Vector2 velocity,
					int initialHealth,
					Weapon[] weapon,Vector2[] weaponPositionModifier ,int score, int credits) {
				super(new PhysicsWorldImplSpy(), new EntityMockEnvironment(), type, position, 
						velocity, initialHealth, weapon, score, credits, 
						new PhysicsBodyDefinitionImpl(
								PhysicsShapeFactory.getRectangularShape(1, 1)), 
						new SimplePhysicsMovementPatternMock(),
						new SimpleMockScoreListener());
			}
		}

		NonTeamMember nonTeamMember = new NonTeamMember();

		Enemy otherEnemy = new AnotherEnemy(EnemyDefinitionImpl.DEFAULT_ENEMY_SHIP,
				new Vector2(), new Vector2(),
				98, new Weapon[] {}, new Vector2[] {}, 0, 0);

		assertTrue("Check if two enemies of the same class is in the same team",
				enemy1.isInMyTeam(enemy2));

		assertTrue("Check if two enemies of a different class is in the same team",
				otherEnemy.isInMyTeam(enemy1));

		assertFalse("Check that en enemy and a non team member isn't the same team",
				enemy2.isInMyTeam(nonTeamMember));
	}

}
