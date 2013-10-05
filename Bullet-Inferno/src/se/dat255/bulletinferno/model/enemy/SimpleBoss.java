package se.dat255.bulletinferno.model.enemy;

import com.badlogic.gdx.math.Vector2;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.Weapon;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.physics.PhysicsMovementPattern;
import se.dat255.bulletinferno.util.Timer;
import se.dat255.bulletinferno.util.Timerable;

public abstract class SimpleBoss extends SimpleEnemy implements Timerable {

	private Weapon[] weapons;
	private Timer[] timers;
	private PlayerShip player;
	private Game game;

	public SimpleBoss(Game game, EnemyType type, Vector2 position, Vector2 velocity,
			int initialHealth,
			Weapon[] weapons, int score, int credits,
			PhysicsBodyDefinition bodyDefinition, PhysicsMovementPattern pattern) {
		super(game, type, position, velocity, initialHealth, weapons, score, credits,
				bodyDefinition, pattern);

		this.game = game;
		this.weapons = weapons;
		this.timers = new Timer[weapons.length];
		this.player = game.getPlayerShip();
		for (int i = 0; i < weapons.length; i++) {
			timers[i] = weapons[i].getTimer();
			timers[i].registerListener(this);
		}
	}

	public abstract void onTimeout(Timer source, float timeSinceLast);

	public void fireSpread() {

		for (int i = (int) weapons.length / 2; i < weapons.length; i++) {
			weapons[i].fire(this.getPosition(), new Vector2(-1, 0), this);
		}

	}

	public void fireAimed() {
		for (int i = 0; i < (int) weapons.length / 2; i++) {

			weapons[i].fire(this.getPosition(), new Vector2(-1, 0), this);

		}

	}

	@Override
	public void viewportIntersectionBegin() {
		super.viewportIntersectionBegin();
		player.setXSpeed(0f);
	}


	@Override
	public void takeDamage(float damage) {
		super.takeDamage(damage);

		if (isDead()) {

			game.bossDead();

		}
	}

}
