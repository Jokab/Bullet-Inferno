package se.dat255.bulletinferno.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.enemy.EnemyTypes;
import se.dat255.bulletinferno.model.enemy.SimpleEnemy;
import se.dat255.bulletinferno.model.mock.SimpleMockGame;

public class EnemyTypesTest {

	private SimpleMockGame mockGame;

	@Before
	public void initialize() {
		mockGame = new SimpleMockGame();
	}

	@Test
	public void testEnemyHealth() {
		SimpleEnemy enemy = EnemyTypes.DEFAULT_SHIP.getEnemyShip(mockGame, new Vector2());

		assertTrue(EnemyTypes.DEFAULT_SHIP.getInitialHealth() == enemy.getHealth());
	}

	@Test
	public void testEnemyPosition() {
		Vector2 position = new Vector2(1, 1);
		SimpleEnemy enemy = EnemyTypes.FAST_SHIP.getEnemyShip(mockGame, position);

		assertEquals(
				"Position that was sent to the factory should be the same as the created enemy's position. ",
				position, enemy.getPosition());
	}

	@Test
	public void testEnemy() {
		assertTrue("The fast ship should be faster than the slow ship.", EnemyTypes.FAST_SHIP
				.getVelocity().len() > EnemyTypes.SLOW_SHIP.getVelocity().len());
	}
}
