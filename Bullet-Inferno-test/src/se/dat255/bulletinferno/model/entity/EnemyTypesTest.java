package se.dat255.bulletinferno.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.entity.EnemyDefinitionImpl;
import se.dat255.bulletinferno.model.entity.SimpleEnemy;
import se.dat255.bulletinferno.model.mock.EntityMockEnvironment;
import se.dat255.bulletinferno.model.mock.PhysicsWorldImplSpy;
import se.dat255.bulletinferno.model.mock.SimpleMockTimer;
import se.dat255.bulletinferno.model.mock.WeaponMockEnvironment;
import se.dat255.bulletinferno.test.Common;

public class EnemyTypesTest {

	@BeforeClass
	public static void beforeTests() {
		Common.loadEssentials();	
	}
	
	private PhysicsWorldImplSpy physics;
	private EntityMockEnvironment entities;
	private WeaponMockEnvironment weapons;

	@Before
	public void initialize() {
		physics = new PhysicsWorldImplSpy(new SimpleMockTimer());
		weapons = new WeaponMockEnvironment();
		entities = new EntityMockEnvironment(physics, weapons);
	}

	@Test
	public void testEnemyPosition() {
		Vector2 position = new Vector2(1, 1);
		Enemy enemy = EnemyDefinitionImpl.SPECIAL_ENEMY_SHIP.createEnemy(physics, entities, 
				weapons, position);

		assertTrue(
				"Position that was sent to the factory should be the same as the created enemy's position. ",
				position.equals(enemy.getPosition()));
	}
}
