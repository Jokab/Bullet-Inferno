package se.dat255.bulletinferno.model.enemy;

import static org.junit.Assert.*;

import org.junit.Test;

import se.dat255.bulletinferno.model.Enemy;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Projectile;
import se.dat255.bulletinferno.model.Weapon;
import se.dat255.bulletinferno.model.mock.SimpleMockGame;
import se.dat255.bulletinferno.model.mock.SimpleMockProjectile;

import com.badlogic.gdx.math.Vector2;

public class EnemyImplTest {

	private class EnemyMockup extends SimpleEnemy {

		public EnemyMockup(Game game, Vector2 position, Vector2 velocity,
				int initialHealth, Weapon weapon, int score, int credits) {
			super(game, position, velocity, initialHealth, weapon, score, credits);
		}
		
	}
	
	private class EnemyGameMockup extends SimpleMockGame {
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
		SimpleEnemy enemy = new EnemyMockup(new SimpleMockGame(), new Vector2(), new Vector2(), 
				0, null, 99, 0);
		assertTrue("Enemy score should be = 99", enemy.getScore() == 99);
	}

	@Test
	public void testGetCredits() {
		// Set up a new enemy with credits of 65
		SimpleEnemy enemy = new EnemyMockup(new SimpleMockGame(), new Vector2(), new Vector2(), 
				0, null, 0, 65);
		assertTrue("Enemy credits should be = 65", enemy.getCredits() == 65);
	}

	@Test
	public void testPreCollided() {
		SimpleEnemy enemy = new EnemyMockup(new SimpleMockGame(), new Vector2(), new Vector2(), 
				100, null, 0, 0);
		Projectile projectile = new ColidedTestMockProjectile();
		
		int preCollisionHealth = enemy.getHealth();
		
		// Simulate colliding with a projectile
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
		SimpleEnemy enemy = new EnemyMockup(new SimpleMockGame(), new Vector2(), new Vector2(), 
				101, null, 0, 0);
		assertTrue("Enemy health should be = 101", enemy.getHealth() == 101);
		
		enemy.takeDamage(10);
		assertTrue("Enemy health should be = 91", enemy.getHealth() == 91);
	}

	@Test
	public void testTakeDamage() {		
		// Set up a new enemy with a health of 101
		EnemyGameMockup game = new EnemyGameMockup();
		SimpleEnemy enemy = new EnemyMockup(game, new Vector2(), new Vector2(), 
				101, null, 0, 0);
		
		// Take 61 damage
		enemy.takeDamage(61);
		assertTrue("Enemy health should be = 40", enemy.getHealth() == (101 - 61));
		
		// Take 40 more in damage, i.e. it should die
		// remove itself from the game and run dispose
		enemy.takeDamage(40);
		
		assertTrue("Enemy should be dead, i.e. health=0", enemy.getHealth() == 0);
		assertTrue(game.isEnemyRemoved());
		assertEquals(enemy, game.getRemovedEnemy());
		
		assertEquals("Enemy should do remove body once", 
				game.physicsWorld.removeBodyCalls.size(), 1);
		
		// Take some extra damage to check that it dosen't die again
		enemy.takeDamage(90);
		
		assertEquals("Enemy should only do 1 remove body call", 
				game.physicsWorld.removeBodyCalls.size(), 1);
		
	}

	@Test
	public void testDispose() {
		EnemyGameMockup game = new EnemyGameMockup();
		SimpleEnemy enemy = new EnemyMockup(game, new Vector2(), new Vector2(), 
				101, null, 0, 0);
		enemy.dispose();
		assertEquals("Enemy should do remove body once", 
				game.physicsWorld.removeBodyCalls.size(), 1);
	}

	@Test
	public void testGetInitialHealth() {
		// Set up a new enemy with a health of 101
		SimpleEnemy enemy = new EnemyMockup(new SimpleMockGame(), new Vector2(), new Vector2(), 
				98, null, 0, 0);
		assertTrue("Enemy initial health should be = 98", enemy.getInitialHealth() == 98);
		
		enemy.takeDamage(10);
		assertTrue("Enemy initial health should still be = 98, after taking more hits", 
				enemy.getInitialHealth() == 98);
	}

	@Test
	public void testGetPosition() {
		Vector2 position = new Vector2(2,3);
		SimpleEnemy enemy = new EnemyMockup(new SimpleMockGame(), position, new Vector2(), 
				98, null, 0, 0);
		
		assertTrue("Check that position is equal enemy's position",  
				position.equals(enemy.getPosition()));
		// Set position to something else, and check for alias problems
		position.x = 4;
		position.y = 5;
		assertFalse("Check for alias problems", position.equals(enemy.getPosition()));
		assertFalse("Check for alias problems", position == enemy.getPosition());
	}

}
