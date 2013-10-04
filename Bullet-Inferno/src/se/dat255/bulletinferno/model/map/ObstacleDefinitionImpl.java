package se.dat255.bulletinferno.model.map;

import java.util.Arrays;
import java.util.List;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinitionImpl;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinitionImpl.BodyType;
import se.dat255.bulletinferno.util.PhysicsShapeFactory;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;

/**
 * Configurations of simple ObstacleDefinitions.
 * 
 * @see ObstacleDefinition
 */
public enum ObstacleDefinitionImpl implements ObstacleDefinition {

	FLAT_GROUND(Arrays.asList(
			PhysicsShapeFactory.getRectangularShape(5f, 10f, new Vector2(-7.5f, -8f)),
			PhysicsShapeFactory.getRectangularShape(10f, 12f, new Vector2(0f, -8f)),
			PhysicsShapeFactory.getRectangularShape(5f, 10f, new Vector2(7.5f, -8f))
			)), ;

	/** A body definition for the obstacle. */
	private final PhysicsBodyDefinition bodyDefinition;

	/**
	 * @param shapes
	 *        the shapes that make up the ObstacleDefinition. Positions will be relative to a
	 *        center, which will be shared by all shapes in the obstacle definition.
	 */
	private ObstacleDefinitionImpl(List<? extends Shape> shapes) {
		bodyDefinition = new PhysicsBodyDefinitionImpl(shapes, BodyType.STATIC);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Obstacle createObstacle(Game game, Vector2 position) {
		return new ObstacleImpl(game, bodyDefinition, position);
	}

}