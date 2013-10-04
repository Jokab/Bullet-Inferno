package se.dat255.bulletinferno.model.map;

import se.dat255.bulletinferno.model.Game;

import com.badlogic.gdx.math.Vector2;

/**
 * A definition of an Slice type.
 */
public interface SliceDefinition {
	/**
	 * Returns a Slice instance with the provided properties.
	 * 
	 * @param game
	 *        The game instance.
	 * @param position
	 *        The world-coordinates the Slice will be placed at, in the physics world.
	 * @return A new Slice located at the provided postion.
	 */
	public Slice createSlice(Game game, Vector2 position);

	/**
	 * Returns the entry height held by this definition.
	 * See {@link Slice#getEntryHeight()}
	 * 
	 * @return The entry height (m)
	 */
	public float getEntryHeight();

	/**
	 * Returns the exit height held by this definition.
	 * See {@link Slice#getExitHeight()}
	 * 
	 * @return The exit height (m)
	 */
	public float getExitHeight();
}
