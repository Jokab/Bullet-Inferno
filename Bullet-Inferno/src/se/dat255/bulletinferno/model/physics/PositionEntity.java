package se.dat255.bulletinferno.model.physics;

import com.badlogic.gdx.math.Vector2;

public interface PositionEntity {

	/**
	 * Returns a Vector2 representation of a position of the PositionEntity.
	 * 
	 * @return The position of the PositionEntity.
	 */
	public Vector2 getPosition();

	/**
	 * Returns the dimensions of an object
	 * 
	 * @return The dimensions of an object
	 */
	public Vector2 getDimensions();
	
}