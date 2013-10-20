package se.dat255.bulletinferno.model.loadout;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.entity.Enemy;
import se.dat255.bulletinferno.model.entity.EnemyDefinitionImpl;
import se.dat255.bulletinferno.model.entity.PlayerShip;
import se.dat255.bulletinferno.model.entity.SimpleEnemy;
import se.dat255.bulletinferno.model.mock.EntityMockEnvironment;
import se.dat255.bulletinferno.model.mock.PhysicsWorldImplSpy;
import se.dat255.bulletinferno.model.mock.ScoreMockListener;
import se.dat255.bulletinferno.model.mock.SimpleMockScoreListener;
import se.dat255.bulletinferno.model.mock.SimpleMockTimer;
import se.dat255.bulletinferno.model.mock.SimplePhysicsMovementPatternMock;
import se.dat255.bulletinferno.model.mock.SimplePlayerShipMock;
import se.dat255.bulletinferno.model.mock.WeaponMockEnvironment;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinitionImpl;
import se.dat255.bulletinferno.model.weapon.Weapon;
import se.dat255.bulletinferno.test.Common;
import se.dat255.bulletinferno.util.PhysicsShapeFactory;
import se.dat255.bulletinferno.util.Timer;
import se.dat255.bulletinferno.util.Timerable;

public class SpecialAbilityTest {
	
	private class EnemyMockup extends SimpleEnemy {
		public EnemyMockup(EnemyDefinitionImpl type, Vector2 position, Vector2 velocity, 
				int initialHealth, Weapon[] weapon, Vector2[] weaponPositionModifier, int score, int credits) {
			super(physics, entities, type, position, velocity,
					initialHealth, weapon, score,
					credits, 
					new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(1, 1)), 
					new SimplePhysicsMovementPatternMock(),
					new SimpleMockScoreListener());
		}
	}

	@BeforeClass
	public static void beforeTests() {
		Common.loadEssentials();
	}

	EntityMockEnvironment entities;
	PhysicsWorldImplSpy physics;
	WeaponMockEnvironment weapons;
	private SpecialEffect damageAll;
	private SimpleMockTimer timer;

	@Before
	public void initialize() {
		this.timer = new SpecialAbilityMockTimer();
		this.physics = new PhysicsWorldImplSpy(timer);
		this.weapons = new WeaponMockEnvironment();
		this.entities = new EntityMockEnvironment(physics, weapons);
	}

	@Test
	public void testDamageAll() {
		SpecialEffect damageAll = new SpecialDamageAll(entities, physics, 5);
		SimplePlayerShipMock playerShip = new SimplePlayerShipMock();
		playerShip.position = new Vector2(0, 0);
		playerShip.dimensions = new Vector2(0, 0);

		SimpleEnemy enemy1 = (SimpleEnemy) EnemyDefinitionImpl.KATZE.createEnemy(
				physics, entities, entities.weapons,
				new Vector2(1, 1), new ScoreMockListener());
		SimpleEnemy enemy2 = (SimpleEnemy) EnemyDefinitionImpl.KATZE.createEnemy(
				physics, entities, entities.weapons,
				new Vector2(1, 1), new ScoreMockListener());
		entities.addEnemy(enemy1);
		entities.addEnemy(enemy2);
		
		List<Float> enemyHealth = new ArrayList<Float>();
		for (Enemy enemy : entities.getEnemies()) {
			enemyHealth.add(((SimpleEnemy) enemy).getHealth());
		}
		damageAll.activate(playerShip);

		boolean passed = true;
		for (int i = 0; i < enemyHealth.size(); i++) {
			float expectedHealth = enemyHealth.get(i) - 5;
			float currentHealth = ((SimpleEnemy)entities.getEnemies().get(i)).getHealth();
			passed = expectedHealth == currentHealth ? true : false;
		}
		assertTrue("The health should be five lower after activation.", passed);
	}
	
	@Test
	public void testReloadTimer() {
		float testReloadTime = 5;
		timer.initialValue = testReloadTime;
		
		SimplePlayerShipMock playerShip = new SimplePlayerShipMock();
		playerShip.position = new Vector2(0, 0);
		playerShip.dimensions = new Vector2(0, 0);
		
		damageAll = new SpecialDamageAll(entities, physics, testReloadTime);
		assertTrue("Ability should be ready on creation.", damageAll.isReady());
		damageAll.activate(playerShip);
		assertFalse("Ability should not be ready immediately after activation", damageAll.isReady());
	
		timer.callAllListeners(0);
		
		assertTrue("Ability should be ready after time runs out.", damageAll.isReady());
	}
	
	@Test
	public void testCharges() {
		SimplePlayerShipMock playerShip = new SimplePlayerShipMock();
		playerShip.position = new Vector2(0, 0);
		playerShip.dimensions = new Vector2(0, 0);
		
		damageAll = new SpecialDamageAll(entities, physics, 5);
		assertTrue("Ability should have the default amount of charges (3) on creation.", damageAll.getCharges() == 3);
		damageAll.activate(playerShip);
		assertTrue("Ability should have one less charge after activation.", damageAll.getCharges() == 2);
		
		timer.callAllListeners(0);
		
		assertTrue("Ability should have the default amount of charges (3) when timer runs out", damageAll.getCharges() == 3);
	}

	class SpecialAbilityMockTimer extends SimpleMockTimer {
	}
}
