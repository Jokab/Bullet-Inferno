package se.dat255.bulletinferno.model.enemy;

import com.badlogic.gdx.math.Vector2;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.Weapon;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.physics.PhysicsMovementPattern;
import se.dat255.bulletinferno.util.Timer;
import se.dat255.bulletinferno.util.Timerable;

public abstract class SimpleBoss extends SimpleEnemy {

	
	private final PlayerShip player;
	private final Game game;

	public SimpleBoss(Game game, EnemyType type, Vector2 position, Vector2 velocity,
			int initialHealth,
			Weapon[] weapons, int score, int credits,
			PhysicsBodyDefinition bodyDefinition, PhysicsMovementPattern pattern) {
		super(game, type, position, velocity, initialHealth, weapons, score, credits,
				bodyDefinition, pattern);

		this.game = game;
		
		this.player = game.getPlayerShip();
		
		
	}

	public abstract void onTimeout(Timer source, float timeSinceLast);

	

	@Override
	public void viewportIntersectionBegin() {
		super.viewportIntersectionBegin();
		player.halt();
	}


	@Override
	public void takeDamage(float damage) {
		
		super.takeDamage(damage);

		if (isDead()) {
			game.restorePlayerShipSpeed();
		}
	}

}
