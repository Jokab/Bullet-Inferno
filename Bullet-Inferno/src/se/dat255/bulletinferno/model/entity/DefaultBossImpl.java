package se.dat255.bulletinferno.model.entity;

import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.physics.PhysicsMovementPattern;
import se.dat255.bulletinferno.model.weapon.Weapon;
import se.dat255.bulletinferno.util.Timer;

import com.badlogic.gdx.math.Vector2;

public class DefaultBossImpl extends SimpleBoss implements Ship{

	private Weapon[] weapons;
	private Timer[] timers;
	private final PlayerShip player;

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
	 * @param offsets
	 */
	public DefaultBossImpl(PhysicsEnvironment physics, EntityEnvironment entities, EnemyType type, 
			Vector2 position, Vector2 velocity, PhysicsMovementPattern pattern, int initialHealth, 
			Weapon[] weapons, int score, int credits, PhysicsBodyDefinition bodyDefinition) {
		super(physics, entities,type, position, velocity, initialHealth, weapons, score, credits,
				bodyDefinition, pattern);

		this.player = entities.getPlayerShip();
		this.weapons = weapons;
		this.timers = super.getWeaponTimers();

	}

	@Override
	public void onTimeout(Timer source, float timeSinceLast) {
		
		if (getHealth() >= getInitialHealth() * 0.75f) {

			fireSpread(source);

		} else if(getHealth() < getInitialHealth() * 0.25) {
			
			fireAimSpread(source);
		} else { 
			
			fireAim(source);
		}
		
	}

	@Override
	public void viewportIntersectionBegin() {
		super.viewportIntersectionBegin();
	}
}