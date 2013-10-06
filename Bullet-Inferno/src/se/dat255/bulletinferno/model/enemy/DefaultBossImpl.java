package se.dat255.bulletinferno.model.enemy;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.Ship;
import se.dat255.bulletinferno.model.Weapon;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.physics.PhysicsMovementPattern;
import se.dat255.bulletinferno.util.Timer;
import se.dat255.bulletinferno.util.Timerable;

import com.badlogic.gdx.math.Vector2;

public class DefaultBossImpl extends SimpleBoss implements Ship, Timerable {

	private Weapon[] weapons;
	private Timer[] timers;
	private final PlayerShip player;

	/**
	 * Constructs a new Angry Boss
	 * 
	 * @param game
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
	public DefaultBossImpl(Game game, EnemyType type, Vector2 position, Vector2 velocity,
			PhysicsMovementPattern pattern, int initialHealth, Weapon[] weapons, int score,
			int credits, PhysicsBodyDefinition bodyDefinition) {
		super(game, type, position, velocity, initialHealth, weapons, score, credits,
				bodyDefinition, pattern);

		this.player = game.getPlayerShip();
		this.weapons = weapons;
		this.timers = new Timer[weapons.length];
		for (int i = 0; i < weapons.length; i++) {
			timers[i] = weapons[i].getTimer();
			timers[i].registerListener(this);
			timers[i].stop();
		}

	}

	@Override
	public void onTimeout(Timer source, float timeSinceLast) {
		fire(source);
	}

	// Determines how many weapon to fire and in what direction
	public void fire(Timer source) {

		if (getHealth() > getInitialHealth() / 2) {

			for (int i = 0; i < weapons.length/2; i++) {

				if (source == timers[i]) {
					weapons[i].fire(this.getPosition(), new Vector2(-1, 0), this);
				}
			}

		} else {

			for (int i = 0; i < weapons.length; i++) {

				if (source == timers[i]) {
					weapons[i].fire(this.getPosition(), new Vector2(player.getPosition().x
							- getPosition().x, player.getPosition().y - getPosition().y).nor(), this);
				}
			}

		}

	}

	@Override
	public Vector2 getDimensions() {
		return new Vector2(1, 1);
	}
}