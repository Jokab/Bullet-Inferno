package se.dat255.bulletinferno.model.enemy;

import se.dat255.bulletinferno.model.Collidable;
import se.dat255.bulletinferno.model.Enemy;

import com.badlogic.gdx.math.Vector2;

abstract class EnemyImpl implements Enemy, Collidable {

	private int health;
	private final int initialHealth;
	private int score;
	private int credits;

	public EnemyImpl(Vector2 position, Vector2 velocity, int initialHealth) {
		this.initialHealth = initialHealth;
	}

	@Override
	public int getScore() {
		return this.score;
	}

	@Override
	public int getCredits() {
		return this.credits;
	}

	@Override
	public boolean intersects(Vector2 point) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void collided(Collidable with) {
		// TODO Auto-generated method stub

	}
	
}
