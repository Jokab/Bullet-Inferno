package se.dat255.bulletinferno.model.entity;

import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.physics.PhysicsMovementPattern;
import se.dat255.bulletinferno.model.weapon.Weapon;
import se.dat255.bulletinferno.util.Listener;
import se.dat255.bulletinferno.util.Timer;

import com.badlogic.gdx.math.Vector2;

/**
 * Default implementation of a boss
 */
public class DefaultBossImpl extends SimpleBoss {

	/**
	 * Constructs a new Angry Boss
	 * 
	 * @param physics
	 *        The game instance
	 * @param type
	 *        The enemy definition
	 * @param position
	 * @param velocity
	 * @param pattern
	 *        The movement pattern
	 * @param initialHealth
	 * @param weapons
	 * @param score
	 *        The score rewarded when boss is killed
	 * @param credits
	 *        The credit rewarded when boss is killed
	 */
	public DefaultBossImpl(PhysicsEnvironment physics, EntityEnvironment entities, 
			EnemyDefinitionImpl type, Vector2 position, Vector2 velocity, 
			PhysicsMovementPattern pattern, float initialHealth, Weapon[] weapons, int score, 
			int credits, PhysicsBodyDefinition bodyDefinition, Listener<Integer> scoreListener) {
		super(physics, entities,type, position, velocity, initialHealth, weapons, score, credits,
				bodyDefinition, pattern, scoreListener);
		entities.getPlayerShip();
		super.getWeaponTimers();

	}
	
	public DefaultBossImpl(PhysicsEnvironment physics, EntityEnvironment entities, 
			EnemyDefinitionImpl type, Vector2 position, Vector2 velocity, float initialHealth, 
			Weapon[] weapons, int score, int credits, PhysicsBodyDefinition bodyDefinition,
			Listener<Integer> scoreListener) {
		super(physics, entities,type, position, velocity, initialHealth, weapons, score, credits,
				bodyDefinition, scoreListener);

		entities.getPlayerShip();
		super.getWeaponTimers();

	}

	@Override
	public void onTimeout(Timer source, float timeSinceLast) {
		if (getHealth() >= getInitialHealth() * 0.75f) {
			fireSpread(source);
		} else if(getHealth() < getInitialHealth() * 0.25) {
			fireAim(source);
			fireWide(source);
		} else { 
			fireSpreadAim(source);
		}
	}

	@Override
	public void takeDamage(float damage) {
		float preHealth = getHealth();
		super.takeDamage(damage);
		
		// If and only if the health has just passed below a given %, change pattern
		if(preHealth >= (getInitialHealth() * 0.75f) && getHealth() < getInitialHealth()*0.75f) {
			changeToFollowingMovement();
		} else if(preHealth >= (getInitialHealth() * 0.25f) && 
				getHealth() < getInitialHealth()*0.25f) {
			changeToEvadingMovement();
		}
	}
	
	@Override
	public void viewportIntersectionBegin() {
		super.viewportIntersectionBegin();
	}
}