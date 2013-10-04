package se.dat255.bulletinferno.model.enemy;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Ship;
import se.dat255.bulletinferno.model.Weapon;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.physics.PhysicsMovementPattern;
import se.dat255.bulletinferno.util.Timer;
import se.dat255.bulletinferno.util.Timerable;

import com.badlogic.gdx.math.Vector2;

public class AngryBoss extends BossAI implements Ship, Timerable {

	/**
	 * Constructs a new Angry Boss
	 * @param game The game instance
	 * @param type The enemy definition
	 * @param position
	 * @param velocity
	 * @param pattern The movement pattern
	 * @param initialHealth
	 * @param weapons
	 * @param score The score rewarded when boss is killed
	 * @param credits The credit rewarded when boss is killed
	 * @param offsets 
	 */
	public AngryBoss(Game game, EnemyType type, Vector2 position, Vector2 velocity,
			PhysicsMovementPattern pattern, int initialHealth, Weapon[] weapons, int score,
			int credits, Vector2[] offsets, PhysicsBodyDefinition bodyDefinition) {
		super(game, type, position, velocity, initialHealth, weapons, score, credits, offsets, 
				bodyDefinition, pattern);
	}

	/*
	 * This should be used instead of onTimeout, but for some reason we get a NullPointerException
	 */
	@Override
	public void fire() {
		if (isShooting()) {
			if (getHealth() > 0.75 * getInitialHealth()) {
				fireSpread();
			} else if (getHealth() <= 0.5 * getInitialHealth()
					&& getHealth() > 0.25 * getInitialHealth()) {
				fireAimed();
			} else {
				fireSpread();
				fireAimed();
			}
		}
	}

	@Override
	public void onTimeout(Timer source, float timeSinceLast) {
		if (isShooting()) {
			if (getHealth() > 0.75 * getInitialHealth()) {
				fireSpread();
			} else if (getHealth() <= 0.5 * getInitialHealth()
					&& getHealth() > 0.25 * getInitialHealth()) {
				fireAimed();
			} else {
				fireSpread();
				fireAimed();
			}
		}
	}

	@Override
	public Vector2 getDimensions() {
		return new Vector2(2, 2);
	}
}