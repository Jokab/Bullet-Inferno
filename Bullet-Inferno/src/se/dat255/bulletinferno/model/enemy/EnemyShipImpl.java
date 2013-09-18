package se.dat255.bulletinferno.model.enemy;

import se.dat255.bulletinferno.model.EnemyShip;

import com.badlogic.gdx.math.Vector2;

abstract class EnemyShipImpl extends EnemyImpl implements EnemyShip {

	public EnemyShipImpl(Vector2 position, Vector2 velocity, int initialHealth) {
		super(position, velocity, initialHealth);
	}
}
