package se.dat255.bulletinferno.units.enemy;

import com.badlogic.gdx.math.Vector2;

public abstract class EnemyImpl implements Enemy {
	
	private int hitPoints;
	private Vector2 velocity;
	private Vector2 position;
	
	public EnemyImpl(Vector2 position, Vector2 velocity, int hitPoints) {
		this.hitPoints = hitPoints;
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
	
	public void setHitPoints(int hitPoints) {
		this.hitPoints = hitPoints;
	}
	
	public int getHitPoints() {
		return this.hitPoints;
	}
}
