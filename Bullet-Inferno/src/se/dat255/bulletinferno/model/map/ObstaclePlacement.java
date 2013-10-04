package se.dat255.bulletinferno.model.map;

import com.badlogic.gdx.math.Vector2;

/**
 * The type links an {@link ObstacleDefinition} to a placement within a {@link Slice}.
 */
public interface ObstaclePlacement {

	/**
	 * @return an {@link ObstacleDefinition} for this {@link ObstaclePlacement}.
	 */
	public ObstacleDefinition getObstacleDefinition();
	
	/**
	 * @return the {@link Slice}-relative position of the obstacle in world-(delta)-coordinates.
	 */
	public Vector2 getPosition();
	
}