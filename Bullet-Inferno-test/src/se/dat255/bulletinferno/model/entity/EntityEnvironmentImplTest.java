package se.dat255.bulletinferno.model.entity;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import se.dat255.bulletinferno.model.mock.EntityMockEnvironment;
import se.dat255.bulletinferno.model.mock.HealthMockListener;
import se.dat255.bulletinferno.model.mock.PhysicsWorldImplSpy;
import se.dat255.bulletinferno.model.mock.SimpleMockScoreListener;
import se.dat255.bulletinferno.model.mock.SimpleMockTimer;
import se.dat255.bulletinferno.model.mock.SimplePhysicsMovementPatternMock;
import se.dat255.bulletinferno.model.mock.WeaponMockEnvironment;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinitionImpl;
import se.dat255.bulletinferno.model.weapon.Weapon;
import se.dat255.bulletinferno.model.weapon.WeaponDefinitionImpl;
import se.dat255.bulletinferno.model.weapon.WeaponLoadout;
import se.dat255.bulletinferno.model.weapon.WeaponLoadoutImpl;
import se.dat255.bulletinferno.test.Common;
import se.dat255.bulletinferno.util.PhysicsShapeFactory;

import com.badlogic.gdx.math.Vector2;

public class EntityEnvironmentImplTest {

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
	
	PhysicsWorldImplSpy physics;
	WeaponMockEnvironment weapons;
	EntityMockEnvironment entities;

	@Before
	public void initialize() {
		physics = new PhysicsWorldImplSpy(new SimpleMockTimer());
		weapons = new WeaponMockEnvironment();
		entities = new EntityMockEnvironment(physics, weapons);
	}
	
	@Test
	public void testAddEnemy() {
		WeaponLoadout loadout = new WeaponLoadoutImpl(
				WeaponDefinitionImpl.STANDARD_MACHINE_GUN.createWeapon(physics, weapons, new Vector2()), 
				WeaponDefinitionImpl.MISSILE_LAUNCHER.createWeapon(physics, weapons, new Vector2()));
		Enemy enemy = new EnemyMockup(EnemyDefinitionImpl.KATZE, new Vector2(), 
				new Vector2(), 0, new Weapon[] {}, new Vector2[] {}, 0, 65);
		EntityEnvironment entities = new EntityEnvironmentImpl(physics, 
				weapons, loadout, new HealthMockListener());
		entities.addEnemy(enemy);
		
		assertTrue("Check so that the enemy gets added", entities.getEnemies().contains(enemy));
		assertTrue("Check so that the enemy only get added ocnce", 
				entities.getEnemies().size() == 1);
		
		Enemy enemy2 = new EnemyMockup(EnemyDefinitionImpl.KATZE, new Vector2(), 
				new Vector2(), 0, 
				new Weapon[] {WeaponDefinitionImpl.FORCE_GUN.createWeapon(physics, weapons,
						new Vector2())}, 
				new Vector2[] {new Vector2()}, 0, 65);
		// Add another one
		entities.addEnemy(enemy2);
		assertTrue("Check so that the first enemy is still in the list when another one gets added", 
				entities.getEnemies().contains(enemy) && entities.getEnemies().contains(enemy2));
	}

	@Test
	public void testRemoveEnemy() {
		WeaponLoadout loadout = new WeaponLoadoutImpl(
				WeaponDefinitionImpl.STANDARD_MACHINE_GUN.createWeapon(physics, weapons, new Vector2()), 
				WeaponDefinitionImpl.MISSILE_LAUNCHER.createWeapon(physics, weapons, new Vector2()));
		Enemy enemy = new EnemyMockup(EnemyDefinitionImpl.KATZE, new Vector2(), 
				new Vector2(), 0, new Weapon[] {}, new Vector2[] {}, 0, 65);
		Enemy enemy2 = new EnemyMockup(EnemyDefinitionImpl.KATZE, new Vector2(), 
				new Vector2(), 0, new Weapon[] {}, new Vector2[] {}, 0, 65);
		
		EntityEnvironment entities = new EntityEnvironmentImpl(physics, 
				weapons, loadout, new HealthMockListener());
		entities.addEnemy(enemy);
		entities.addEnemy(enemy2);
		
		entities.removeEnemy(enemy);
		
		assertTrue("Check that enemy gets removed", !entities.getEnemies().contains(enemy));
		assertTrue("Check that all enemies dosen't get removed", 
				entities.getEnemies().contains(enemy2));
		
	}

}
