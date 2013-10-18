package se.dat255.bulletinferno.model.map;

import se.dat255.bulletinferno.model.entity.EntityEnvironment;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.weapon.WeaponEnvironment;
import se.dat255.bulletinferno.util.Listener;

import com.badlogic.gdx.math.Vector2;

/**
 * A definition of a Slice type.
 */
public interface SliceDefinition {
	/**
	 * Returns a Slice instance with the provided properties.
	 * 
	 * @param physics
	 *        the {@link PhysicsEnvironment} to run against.
	 * @param entities
	 *        the {@link EntityEnvironment} to run against.
	 * @param weapons
	 *        the {@link WeaponEnvironment} to run against.
	 * @param position
	 *        The world-coordinates the Slice will be placed at, in the physics world.
	 * @return A new Slice located at the provided position.
	 */
	public Slice createSlice(PhysicsEnvironment physics, EntityEnvironment entities,
			WeaponEnvironment weapons, Vector2 position, Listener<Integer> scoreListener);

	/**
	 * Returns the entry height held by this definition.
	 * See {@link Slice#getEntryHeight()}
	 * 
	 * @return The entry height (meters)
	 */
	public float getEntryHeight();

	/**
	 * Returns the exit height held by this definition.
	 * See {@link Slice#getExitHeight()}
	 * 
	 * @return The exit height (meters)
	 */
	public float getExitHeight();
}
