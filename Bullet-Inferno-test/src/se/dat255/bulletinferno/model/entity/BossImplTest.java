package se.dat255.bulletinferno.model.entity;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import se.dat255.bulletinferno.model.mock.EntityMockEnvironment;
import se.dat255.bulletinferno.model.mock.PhysicsWorldImplSpy;
import se.dat255.bulletinferno.model.mock.SimpleMockScoreListener;
import se.dat255.bulletinferno.model.mock.SimplePhysicsMovementPatternMock;
import se.dat255.bulletinferno.model.physics.DisorderedBossMovementPattern;
import se.dat255.bulletinferno.model.physics.EvadingMovementPattern;
import se.dat255.bulletinferno.model.physics.FollowingMovementPattern;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinitionImpl;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.weapon.Weapon;
import se.dat255.bulletinferno.test.Common;
import se.dat255.bulletinferno.util.PhysicsShapeFactory;
import se.dat255.bulletinferno.util.Timer;

import com.badlogic.gdx.math.Vector2;

public class BossImplTest {

	@BeforeClass
	public static void beforeTests() {
		Common.loadEssentials();
	}
	
	private class BossMockup extends SimpleBoss {
		public BossMockup(EnemyDefinitionImpl type, Vector2 position, Vector2 velocity, 
				int initialHealth, Weapon[] weapon, Vector2[] weaponPositionModifier, int score, int credits) {
			super(new PhysicsWorldImplSpy(), new EntityMockEnvironment(), type, position, velocity,
					initialHealth, weapon, weaponPositionModifier, score,
					credits, 
					new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(1, 1)), 
					new SimplePhysicsMovementPatternMock(),
					new SimpleMockScoreListener());
		}
		public BossMockup(PhysicsEnvironment physics, EntityEnvironment entities, 
				EnemyDefinitionImpl type, Vector2 position, Vector2 velocity, 
				int initialHealth, Weapon[] weapon, Vector2[] weaponPositionModifier, int score, int credits) {
			super(physics, entities, type, position, velocity,
					initialHealth, weapon, weaponPositionModifier, score,
					credits, 
					new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(1, 1)), 
					new SimplePhysicsMovementPatternMock(),
					new SimpleMockScoreListener());
		}
		@Override
		public void onTimeout(Timer source, float timeSinceLast) {
		
		}

	}
	
	@Test
	public void testChangeMovementPattern(){
		SimpleBoss boss = new BossMockup(EnemyDefinitionImpl.HARD_BOSS_SHIP, new Vector2(), 
				new Vector2(0,1f), 0, new Weapon[] {}, new Vector2[] {}, 99, 0);
		boss.changeToDisorderedMovement();
		assertTrue("Movement pattern should be disordered", boss.getMovementPattern() instanceof DisorderedBossMovementPattern);
		boss.prepareMovementChange();
		assertTrue("Boss should have speed 0", boss.getVelocity().y == 0);
		assertTrue("Boss should not have a movement pattern", boss.getMovementPattern() == null);		
		boss.changeToFollowingMovement();
		assertTrue("Movement pattern should be following", boss.getMovementPattern() instanceof FollowingMovementPattern);
		boss.changeToEvadingMovement();
		assertTrue("Movement pattern should be Evading", boss.getMovementPattern() instanceof EvadingMovementPattern);

	}
}
