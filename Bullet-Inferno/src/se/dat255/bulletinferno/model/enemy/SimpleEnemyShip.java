package se.dat255.bulletinferno.model.enemy;

import se.dat255.bulletinferno.model.EnemyShip;
import se.dat255.bulletinferno.model.Game;

import com.badlogic.gdx.math.Vector2;

abstract class SimpleEnemyShip extends SimpleEnemy implements EnemyShip {

	public SimpleEnemyShip(Game game, Vector2 position, Vector2 velocity,
			int initialHealth) {
		super(game, position, velocity, initialHealth);
	}

}
