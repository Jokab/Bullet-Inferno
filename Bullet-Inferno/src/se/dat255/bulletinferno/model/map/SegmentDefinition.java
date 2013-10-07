package se.dat255.bulletinferno.model.map;

import se.dat255.bulletinferno.model.entity.EntityEnvironment;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;

import com.badlogic.gdx.math.Vector2;

/**
 * A definition of a Segment type.
 */
public interface SegmentDefinition {
	/**
	 * Returns a Segment instance with the provided properties.
	 * 
	 * @param physics
	 *        the {@link PhysicsEnvironment} to run against.
	 * @param entities
	 *        the {@link EntityEnvironment} to run against.
	 * @param position
	 *        The world-coordinates the Segment will be placed at, in the physics world.
	 * @param sliceAmount
	 *        The number of slices the segment should consist of
	 * @return A Segment made up of <i>sliceAmount</i> slices.
	 */
	public Segment createSegment(PhysicsEnvironment physics, EntityEnvironment entities,
			Vector2 position, int sliceAmount);
}
