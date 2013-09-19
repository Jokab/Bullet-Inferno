package se.dat255.bulletinferno.model.mock;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.Collidable;
import se.dat255.bulletinferno.model.Projectile;

public class SimpleMockProjectile implements Projectile {

	@Override
	public void collided(Collidable other) {
	}

	@Override
	public void reset() {
	}

	@Override
	public int getDamage() {
		return 0;
	}

	@Override
	public void setVelocity(Vector2 velocity) {
	}

	@Override
	public void setPosition(Vector2 position) {
	}

}
