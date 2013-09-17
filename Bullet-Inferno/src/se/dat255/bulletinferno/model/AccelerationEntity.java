package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;

public interface AccelerationEntity extends VelocityEntity {
	public Vector2 getAcceleration();

	public void setAcceleration(Vector2 acceleration);
}
