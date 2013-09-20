package se.dat255.bulletinferno.model.enemy;

import se.dat255.bulletinferno.model.EnemyShip;
import se.dat255.bulletinferno.model.Game;

import com.badlogic.gdx.math.Vector2;

public class DefaultEnemyShipImpl extends EnemyShipImpl implements EnemyShip {

	public DefaultEnemyShipImpl(Game game, Vector2 position, Vector2 velocity,
			int initialHealth) {
		super(game, position, velocity, initialHealth);
		// TODO Auto-generated constructor stub
	}
}
