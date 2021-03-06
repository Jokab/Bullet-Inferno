package se.dat255.bulletinferno.model.entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import se.dat255.bulletinferno.model.mock.EntityMockEnvironment;
import se.dat255.bulletinferno.model.mock.PhysicsWorldImplSpy;
import se.dat255.bulletinferno.model.mock.SimpleMockScoreListener;
import se.dat255.bulletinferno.model.mock.SimpleMockTimer;
import se.dat255.bulletinferno.model.mock.WeaponMockEnvironment;
import se.dat255.bulletinferno.test.Common;
import se.dat255.bulletinferno.util.Listener;

import com.badlogic.gdx.math.Vector2;

public class EnemyDefinitionTest {

	@BeforeClass
	public static void beforeTests() {
		Common.loadEssentials();	
	}
	
	private PhysicsWorldImplSpy physics;
	private EntityMockEnvironment entities;
	private WeaponMockEnvironment weapons;
	private Listener<Integer> scoreListener;

	@Before
	public void initialize() {
		physics = new PhysicsWorldImplSpy(new SimpleMockTimer());
		weapons = new WeaponMockEnvironment();
		entities = new EntityMockEnvironment(physics, weapons);
		scoreListener = new SimpleMockScoreListener();
	}

	@Test
	public void testEnemyPosition() {
		Vector2 position = new Vector2(1, 1);
		Enemy enemy = EnemyDefinitionImpl.SQUIB.createEnemy(physics, entities, 
				weapons, position, scoreListener);

		assertTrue(
				"Position that was sent to the factory should be the same as the created enemy's position. ",
				position.equals(enemy.getPosition()));
		
		assertFalse("Check for alias problems", position == enemy.getPosition());
	}
}
