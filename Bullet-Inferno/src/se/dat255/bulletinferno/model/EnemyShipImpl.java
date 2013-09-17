package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;

public class EnemyShipImpl implements EnemyShip {
	
	private int health;
	private int initialHealth;
	private int score;
	private int credits; 
	private Vector2 velocity;
	private Vector2 position;
	
	public EnemyShipImpl(Vector2 position, Vector2 velocity, int initialHealth) {
		this.initialHealth = initialHealth;
		this.velocity = velocity;
		this.position = position;
	}

	public void update(float delta) {
		position.add(velocity.cpy().scl(delta));
	}
	
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	
	public Vector2 getVelocity() {
		return this.velocity;
	}

	public Vector2 getPosition() {
		return position;
	}
	
	public void setPosition(Vector2 position) {
		this.position = position;
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
