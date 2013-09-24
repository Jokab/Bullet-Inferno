package se.dat255.bulletinferno.model.enemy;

import se.dat255.bulletinferno.model.EnemyShip;
import se.dat255.bulletinferno.model.Game;

import com.badlogic.gdx.math.Vector2;

abstract class EnemyShipImpl extends EnemyImpl implements EnemyShip {

	public EnemyShipImpl(Game game, Vector2 position, Vector2 velocity,
			int initialHealth, int score, int credits) {
		super(game, position, velocity, initialHealth, score, credits);
	}

}
