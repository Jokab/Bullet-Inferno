package se.dat255.bulletinferno.units.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Enemy {
	
	private int hitPoints;
	private Vector2 velocity;
	private Vector2 position;
	
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
	
	public void setHitpoints(int hitPoints) {
		this.hitPoints = hitPoints;
	}
	
	public int getHitPoints() {
		return this.hitPoints;
	}
}
