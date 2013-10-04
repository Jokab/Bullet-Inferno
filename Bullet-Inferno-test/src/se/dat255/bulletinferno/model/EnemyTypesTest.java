package se.dat255.bulletinferno.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.enemy.EnemyType;
import se.dat255.bulletinferno.model.enemy.SimpleEnemy;
import se.dat255.bulletinferno.model.mock.SimpleMockGame;
import se.dat255.bulletinferno.test.Common;

public class EnemyTypesTest {

	@BeforeClass
	public static void beforeTests() {
		Common.loadEssentials();	
	}
	
	private SimpleMockGame mockGame;

	@Before
	public void initialize() {
		mockGame = new SimpleMockGame();
	}

	@Test
	public void testEnemyHealth() {
		SimpleEnemy enemy = EnemyType.DEFAULT_ENEMY_SHIP.getEnemyShip(mockGame, new Vector2(), false);

		assertTrue(EnemyType.DEFAULT_ENEMY_SHIP.getInitialHealth() == enemy.getHealth());
	}

	@Test
	public void testEnemyPosition() {
		Vector2 position = new Vector2(1, 1);
		SimpleEnemy enemy = EnemyType.SPECIAL_ENEMY_SHIP.getEnemyShip(mockGame, position, false);

		assertEquals(
				"Position that was sent to the factory should be the same as the created enemy's position. ",
				position, enemy.getPosition());
	}
}
