package se.dat255.bulletinferno.model.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;

/**
 * Standard implementation of {@link ObstaclePlacement}.
 */
public class ObstaclePlacementImpl implements ObstaclePlacement {

	/** The obstacle definition. */
	private final ObstacleDefinition obstacleDefinition;

	/**
	 * The position where to place the obstacle (from its definition), in {@link Shape}-relative
	 * world-coordinates.
	 */
	private final Vector2 position;

	/**
	 * @param obstacleDefinition
	 *        as for {@link ObstaclePlacement#getObstacleDefinition()}.
	 * @param position
	 *        as for {@link ObstaclePlacement#getPosition()}
	 */
	public ObstaclePlacementImpl(ObstacleDefinition obstacleDefinition, Vector2 position) {
		this.obstacleDefinition = obstacleDefinition;
		this.position = position;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ObstacleDefinition getObstacleDefinition() {
		return obstacleDefinition;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector2 getPosition() {
		return position;
	}

}