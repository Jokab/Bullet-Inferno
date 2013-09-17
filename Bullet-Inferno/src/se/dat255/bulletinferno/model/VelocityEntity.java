package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;

public interface VelocityEntity extends PositionEntity {
	public Vector2 getVelocity();

	public void setVelocity(Vector2 velocity);
}
