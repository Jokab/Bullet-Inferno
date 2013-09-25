package se.dat255.bulletinferno.model.enemy;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Ship;
import se.dat255.bulletinferno.model.Timer;
import se.dat255.bulletinferno.model.Timerable;
import se.dat255.bulletinferno.model.Weapon;

import com.badlogic.gdx.math.Vector2;

public class DefaultEnemyShipImpl extends SimpleEnemy implements Ship, Timerable {

	public DefaultEnemyShipImpl(Game game, Vector2 position, Vector2 velocity,
			int initialHealth, Weapon weapon, int score, int credits) {
		super(game, position, velocity, initialHealth, weapon, score, credits);
			

		Timer timer = weapon.getTimer();
		timer.registerListener(this);
	}

	@Override
	public void onTimeout(Timer source) {
		weapon.fire(new Vector2(getPosition().x, getPosition().y), weapon.getProjectileVelocity().cpy().nor(), this);
	}
}
