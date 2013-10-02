package se.dat255.bulletinferno.model.map;

import se.dat255.bulletinferno.model.Game;

import com.badlogic.gdx.math.Vector2;

public interface SliceDefinition {
	/**
	 * Returns an instance of a slice defined by this definition
	 * @return slice
	 */
	public Slice createSlice(Game game, Vector2 position);
	
	/**
	 * Returns the entry height held by this definition.
	 * See {@link Slice#getEntryHeight()}
	 * @return entry height (m)
	 */
	public float getEntryHeight();
	
	/**
	 * Returns the exit height held by this definition.
	 * See {@link Slice#getExitHeight()}
	 * @return exit height (m)
	 */
	public float getExitHeight();
}
