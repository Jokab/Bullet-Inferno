package se.dat255.bulletinferno.model.enemy;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Ship;
import se.dat255.bulletinferno.model.Weapon;

import com.badlogic.gdx.math.Vector2;

public class DefaultEnemyShipImpl extends SimpleEnemy implements Ship {

	public DefaultEnemyShipImpl(Game game, Vector2 position, Vector2 velocity,
			int initialHealth, Weapon weapon) {
		super(game, position, velocity, initialHealth, weapon);

	}
}
