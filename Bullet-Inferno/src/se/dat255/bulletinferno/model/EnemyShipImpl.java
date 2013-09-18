package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;

public class EnemyShipImpl extends SimpleVelocityEntity implements EnemyShip {

	private int health;
	private final int initialHealth;
	private int score;
	private int credits;

	public EnemyShipImpl(Vector2 position, Vector2 velocity, int initialHealth) {
		super(position, velocity);
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

	@Override
	public void takeDamage(int damage) {
		this.health -= damage;
	}

	@Override
	public int getHealth() {
		return this.health;
	}

	@Override
	public int getInitialHealth() {
		return this.initialHealth;
	}
}
