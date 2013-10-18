package se.dat255.bulletinferno.model.map;

import java.util.Arrays;
import java.util.List;

import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinitionImpl;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinitionImpl.BodyType;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.util.PhysicsShapeFactory;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;

/**
 * Configurations of simple ObstacleDefinitions.
 * 
 * @see ObstacleDefinition
 */
public enum ObstacleDefinitionImpl implements ObstacleDefinition {

	MOUNTAIN_1_GROUND(Arrays.asList(
			PhysicsShapeFactory.getChainShape(new Vector2[] { new Vector2(),
					new Vector2(0.73f, 2.68f), new Vector2(1.37f, 3.91f), new Vector2(2.1f, 4.65f),
					new Vector2(2.65f, 4.85f), new Vector2(3.22f, 4.85f),
					new Vector2(3.85f, 4.57f),
					new Vector2(4.48f, 3.72f), new Vector2(5.48f, 1.54f),
					new Vector2(6.39f, 0.78f),
					new Vector2(7.37f, 0.64f), new Vector2(12.63f, 0.73f),
					new Vector2(13.8f, 1.55f),
					new Vector2(14.35f, 1.75f), new Vector2(15.94f, 1.75f)
			}))),
	MOUNTAIN_2_GROUND(Arrays.asList(
			PhysicsShapeFactory.getChainShape(new Vector2[] { new Vector2(0, 1.75f),
					new Vector2(1.36f, 2f), new Vector2(3.07f, 3.33f), new Vector2(3.81f, 3.63f),
					new Vector2(4.37f, 3.53f), new Vector2(5.39f, 2.76f),
					new Vector2(6.52f, 2.88f),
					new Vector2(7.33f, 3.65f), new Vector2(7.37f, 4.93f),
					new Vector2(7.13f, 5.26f),
					new Vector2(7.88f, 5.50f), new Vector2(8.87f, 5.58f),
					new Vector2(9.50f, 5.26f),
					new Vector2(10.26f, 4.92f), new Vector2(10.89f, 4.96f),
					new Vector2(11.62f, 5.28f),
					new Vector2(11.81f, 5.13f), new Vector2(11.93f, 4.20f),
					new Vector2(12.21f, 3.88f),
					new Vector2(13.20f, 3.74f), new Vector2(16f, 3.80f)
			}))),
	MOUNTAIN_3_GROUND(Arrays.asList(
			PhysicsShapeFactory.getChainShape(new Vector2[] { new Vector2(0, 1.75f),
					new Vector2(1.36f, 2f), new Vector2(3.07f, 3.33f), new Vector2(3.81f, 3.63f),
					new Vector2(4.37f, 3.53f), new Vector2(5.39f, 2.76f),
					new Vector2(6.52f, 2.88f),
					new Vector2(7.33f, 3.65f), new Vector2(7.37f, 4.93f),
					new Vector2(7.13f, 5.26f),
					new Vector2(7.88f, 5.50f), new Vector2(8.87f, 5.58f),
					new Vector2(9.50f, 5.26f),
					new Vector2(10.26f, 4.92f), new Vector2(10.89f, 4.96f),
					new Vector2(11.62f, 5.28f),
					new Vector2(11.81f, 5.13f), new Vector2(11.93f, 4.20f),
					new Vector2(12.21f, 3.88f),
					new Vector2(13.20f, 3.74f), new Vector2(16f, 3.80f) })
			,
			// Cave top
			PhysicsShapeFactory.getChainShape(new Vector2[] { new Vector2(5.05f, 8.72f),
					new Vector2(4.73f, 8.54f), new Vector2(4.65f, 8.44f),
					new Vector2(4.78f, 8.08f),
					new Vector2(5.15f, 7.73f), new Vector2(5.41f, 7.55f),
					new Vector2(5.83f, 7.55f),
					new Vector2(6.03f, 7.61f), new Vector2(7.05f, 7.7f), new Vector2(8.02f, 7.67f),
					new Vector2(8.85f, 7.57f), new Vector2(9.67f, 7.45f),
					new Vector2(10.69f, 7.43f),
					new Vector2(11.83f, 7.51f), new Vector2(11.96f, 7.53f),
					new Vector2(11.81f, 8f),
					new Vector2(11.36f, 8.39f)
			}))),
	MOUNTAIN_4_GROUND(Arrays.asList(
			PhysicsShapeFactory.getChainShape(new Vector2[] { new Vector2(0, 3.8f),
					new Vector2(1.47f, 3.85f), new Vector2(3.29f, 4.38f),
					new Vector2(3.87f, 4.60f),
					new Vector2(4.44f, 4.67f), new Vector2(5f, 4.66f), new Vector2(5.66f, 4.52f),
					new Vector2(6.16f, 4.31f), new Vector2(6.53f, 4.06f),
					new Vector2(6.78f, 3.81f),
					new Vector2(6.97f, 3.48f), new Vector2(7.04f, 2.24f),
					new Vector2(7.16f, 2.71f),
					new Vector2(7.36f, 1.28f), new Vector2(7.58f, 1f), new Vector2(7.81f, 0.82f),
					new Vector2(8.25f, 0.61f), new Vector2(8.76f, 0.5f), new Vector2(9.57f, 0.54f),
					new Vector2(10.58f, 0.72f), new Vector2(12.5f, 1.28f),
					new Vector2(13.56f, 1.51f),
					new Vector2(14.41f, 1.64f), new Vector2(15.2f, 1.71f), new Vector2(16f, 1.75f)
			}))),
	MOUNTAIN_6_GROUND(Arrays.asList(
			PhysicsShapeFactory.getChainShape(new Vector2[] { new Vector2(0, 1.75f),
					new Vector2(9.15f, 1.75f), new Vector2(8.3f, 3f), new Vector2(5.73f, 4.68f),
					new Vector2(4.84f, 4.8f), new Vector2(4.25f, 5.4f), new Vector2(4.08f, 6.35f),
					new Vector2(4.23f, 6.51f), new Vector2(4.57f, 6.6f), new Vector2(5.18f, 6.6f),
					new Vector2(5.97f, 6.5f), new Vector2(6.52f, 6.28f), new Vector2(7.36f, 5.83f),
					new Vector2(7.96f, 5.65f), new Vector2(9.21f, 5.55f), new Vector2(10f, 5.29f),
					new Vector2(10.95f, 4.77f), new Vector2(11.82f, 3.9f),
					new Vector2(12.17f, 3.27f), new Vector2(12.74f, 2.84f),
					new Vector2(14.11f, 2.2f),
					new Vector2(15.55f, 1.75f), new Vector2(16, 1.75f)
			}))),

	FLOATING_ROCK(Arrays.asList(
			PhysicsShapeFactory.getChainShape(new Vector2[] { new Vector2(1.04f, 0.09f),
					new Vector2(0.86f, 0.26f), new Vector2(0.52f, 0.52f),
					new Vector2(0.40f, 0.94f),
					new Vector2(0.48f, 1.05f), new Vector2(0.41f, 1.43f),
					new Vector2(0.43f, 1.65f),
					new Vector2(0.39f, 1.89f), new Vector2(0.71f, 1.89f),
					new Vector2(0.47f, 2.49f),
					new Vector2(0.61f, 2.57f), new Vector2(0.1f, 3.06f), new Vector2(0.14f, 3.13f),
					new Vector2(0.4f, 3.2f), new Vector2(0.27f, 3.39f), new Vector2(0, 3.32f),
					new Vector2(0.13f, 3.93f), new Vector2(0.18f, 4.05f),
					new Vector2(0.28f, 4.11f),
					new Vector2(2.66f, 4.11f), new Vector2(2.74f, 4.06f),
					new Vector2(2.78f, 4.01f),
					new Vector2(2.77f, 3.88f), new Vector2(2.70f, 3.74f),
					new Vector2(2.46f, 3.60f),
					new Vector2(2.51f, 3.22f), new Vector2(2.78f, 3.11f),
					new Vector2(2.67f, 2.89f),
					new Vector2(2.36f, 2.54f), new Vector2(2.24f, 2.43f),
					new Vector2(2.31f, 2.13f),
					new Vector2(2.38f, 1.46f), new Vector2(2.44f, 1.20f),
					new Vector2(2.25f, 0.76f),
					new Vector2(2.2f, 0.49f), new Vector2(1.97f, 0.31f), new Vector2(1.83f, 0.33f),
					new Vector2(1.82f, 0.17f), new Vector2(1.70f, 0.05f),
					new Vector2(1.54f, 0.08f),
					new Vector2(1.35f, 0), new Vector2(1.20f, 0), new Vector2(1.06f, 0.08f)

			}))),
	MOUNTAIN_7_GROUND(Arrays.asList(
			PhysicsShapeFactory.getChainShape(new Vector2[] { new Vector2(16, 1.75f),
					new Vector2(14.64f, 2f), new Vector2(12.93f, 3.33f),
					new Vector2(12.19f, 3.63f),
					new Vector2(11.63f, 3.53f), new Vector2(10.61f, 2.76f),
					new Vector2(9.48f, 2.88f),
					new Vector2(8.67f, 3.65f), new Vector2(8.63f, 4.93f),
					new Vector2(8.87f, 5.26f),
					new Vector2(8.12f, 5.50f), new Vector2(7.13f, 5.58f), new Vector2(6.5f, 5.26f),
					new Vector2(5.74f, 4.92f), new Vector2(5.11f, 4.96f),
					new Vector2(4.38f, 5.28f),
					new Vector2(4.19f, 5.13f), new Vector2(4.07f, 4.20f),
					new Vector2(3.79f, 3.88f),
					new Vector2(2.8f, 3.74f), new Vector2(0, 3.80f)
			}))),

	MOUNTAIN_8_GROUND(Arrays.asList(
			PhysicsShapeFactory.getChainShape(new Vector2[] { new Vector2(0, 1.75f),
					new Vector2(1.65f, 1.75f), new Vector2(2.2f, 1.55f),
					new Vector2(3.37f, 0.73f), new Vector2(3.8f, 0.69f),
					new Vector2(14.62f, 0.69f), new Vector2(15.13f, 0.65f),
					new Vector2(15.87f, 0.35f), new Vector2(16, 0)
			}))),
	FLAT_GROUND(PhysicsShapeFactory.getChainShape(new Vector2[] {
			new Vector2(0, 0), new Vector2(16, 0)
	})),
	SMALL_TREE(PhysicsShapeFactory.getRectangularShape(1.62f, 1.62f)),
	DEFAULT_TREE(PhysicsShapeFactory.getRectangularShape(2f, 2f));

	/** A body definition for the obstacle. */
	private final PhysicsBodyDefinition bodyDefinition;

	/**
	 * 
	 * @param shape
	 *        the shape that make up the obstacle definition
	 */
	private ObstacleDefinitionImpl(Shape shape) {
		this(Arrays.asList(shape));
	}

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
	public Obstacle createObstacle(PhysicsEnvironment physics, Vector2 position) {
		return new ObstacleImpl(physics, bodyDefinition, position);
	}

}