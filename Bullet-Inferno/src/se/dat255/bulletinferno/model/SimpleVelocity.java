package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;

public abstract class SimpleVelocity implements VelocityEntity {

	private Vector2 point;
	private Vector2 velocity;

	public SimpleVelocity(Vector2 point, Vector2 velocity) {
		this.point = point;
		this.velocity = velocity;
	}

	public SimpleVelocity() {
		point = new Vector2();
		velocity = new Vector2();
	}

	@Override
	public Vector2 getPosition() {
		return point;
	}

	@Override
	public void setPosition(Vector2 point) {
		this.point = point;
	}

	@Override
	public Vector2 getVelocity() {
		return velocity;
	}

	@Override
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

}
