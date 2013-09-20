package se.dat255.bulletinferno.model.mock;

import se.dat255.bulletinferno.model.Collidable;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Projectile;

import com.badlogic.gdx.math.Vector2;

public class SimpleMockProjectile implements Projectile {
	public Game game;

	public SimpleMockProjectile() {
		this(null);
	}

	public SimpleMockProjectile(Game game) {
		this.game = game;
	}

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

	@Override
	public Vector2 getPosition() {
		return null;
	}

}
