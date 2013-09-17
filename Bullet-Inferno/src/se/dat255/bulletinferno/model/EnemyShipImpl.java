package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;

public class EnemyShipImpl implements EnemyShip {

	private int hitPoints;
	private Vector2 velocity;
	private Vector2 position;

	public EnemyShipImpl(Vector2 position, Vector2 velocity, int hitPoints) {
		this.hitPoints = hitPoints;
		this.velocity = velocity;
		this.position = position;
	}

	public void update(float delta) {
		position.add(velocity.cpy().scl(delta));
	}

	@Override
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	@Override
	public Vector2 getVelocity() {
		return velocity;
	}

	@Override
	public Vector2 getPosition() {
		return position;
	}

	@Override
	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public void setHitPoints(int hitPoints) {
		this.hitPoints = hitPoints;
	}

	public int getHitPoints() {
		return hitPoints;
	}

	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCredits() {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub

	}

	@Override
	public int getHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getInitialHealth() {
		// TODO Auto-generated method stub
		return 0;
	}
}
